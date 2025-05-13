const socket = new WebSocket("ws://localhost:8080/prices");

const cryptoPrices = {};

async function initializeCryptoPrices() {
    try {
        // Fetch crypto names (this should be served from your backend API)
        const cryptoNames = await fetchCryptoNames();

        if (!cryptoNames || cryptoNames.length === 0) {
            document.getElementById("cryptoList").textContent = "No cryptocurrencies available.";
            return;
        }

        const priceListElement = document.getElementById("cryptoList");
        priceListElement.innerHTML = ""; // Clear the loading message

        cryptoNames.forEach(symbol => {
            const priceItem = document.createElement("div");
            priceItem.className = "price-item";
            priceItem.id = `crypto-${symbol}`;
            priceItem.textContent = `${symbol}: Loading...`;
            priceListElement.appendChild(priceItem);
        });

        setupWebSocket(cryptoNames);
    } catch (err) {
        console.error("Error initializing crypto prices:", err);
    }
}

function setupWebSocket(cryptoNames) {
    socket.onopen = () => {
        console.log("✅ WebSocket connection established.");
    };

    socket.onmessage = (event) => {
        try {
            const data = JSON.parse(event.data);

            if (!data.symbol || !data.price || !cryptoNames.includes(data.symbol.split('/')[0])) {
                console.warn("Invalid data:", data);
                return;
            }

            const symbol = data.symbol.split('/')[0];
            const price = data.price;

            cryptoPrices[symbol] = price;

            const priceItem = document.getElementById(`crypto-${symbol}`);
            if (priceItem) {
                priceItem.textContent = `${symbol}: $${parseFloat(price).toFixed(4)}`;
            }
        } catch (err) {
            console.error("Error processing WebSocket message:", err);
        }
    };

    socket.onerror = (err) => {
        console.error("WebSocket error:", err);
    };
}

async function fetchCryptoNames() {
    try {
        const response = await fetch("http://localhost:8080/api/cryptos");
        if (response.ok) {
            return await response.json();
        } else {
            console.error(`Failed to fetch crypto names: ${response.statusText}`);
            return [];
        }
    } catch (error) {
        console.error(`Error fetching crypto names: ${error}`);
        return [];
    }
}
initializeCryptoPrices();
