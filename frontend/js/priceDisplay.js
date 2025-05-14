const socket = new WebSocket("ws://localhost:8080/prices");

const cryptoPrices = {};

async function initializeCryptoPrices() {
    try {
        const cryptoNames = await fetchCryptoNames();

        if (!cryptoNames || cryptoNames.length === 0) {
            document.getElementById("cryptoList").textContent = "No cryptocurrencies available.";
            return;
        }

        const priceListElement = document.getElementById("cryptoList");
        priceListElement.innerHTML = "";

        cryptoNames.forEach(symbol => {
            const priceItem = document.createElement("div");
            priceItem.className = "price-item";
            priceItem.id = `crypto-${symbol}`;

            const img = document.createElement("img");
            img.src = `assets/crypto_images/${symbol.toLowerCase()}.png`;
            img.alt = `${symbol} logo`;
            img.className = "crypto-icon";

            const name = document.createElement("span");
            name.className = "crypto-name";
            name.textContent = symbol;

            const price = document.createElement("span");
            price.className = "crypto-price";
            price.textContent = "Loading...";

            const buyButton = document.createElement("button");
            buyButton.className = "buy-button";
            buyButton.textContent = "Buy";
            buyButton.onclick = () => handleBuyClick(symbol);


            priceItem.appendChild(img);
            priceItem.appendChild(name);
            priceItem.appendChild(price);
            priceItem.appendChild(buyButton);

            priceListElement.appendChild(priceItem);
        });

        setupWebSocket(cryptoNames);
    } catch (err) {
        console.error("Error initializing crypto prices:", err);
    }

}

document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("buyModal");
    const amountInput = document.getElementById("amount");
    const confirmButton = document.getElementById("confirmBuyButton");
    const closeButton = document.querySelector(".close");

    if (!modal || !amountInput || !confirmButton || !closeButton) {
        console.error("Modal elements not found in DOM.");
        return;
    }

   window.showBuyModal = function(symbol, price) {
       const modal = document.getElementById("buyModal");
       const amountInput = document.getElementById("amount");
       const confirmButton = document.getElementById("confirmBuyButton");

       modal.style.display = "block";

       confirmButton.onclick = () => {
           const amount = parseFloat(amountInput.value); 
           if (isNaN(amount) || amount <= 0) {
               alert("Please enter a valid amount.");
               return;
           }
           handleTransaction(symbol, amount, price); 
           modal.style.display = "none";
       };
   };

    closeButton.onclick = function () {
        modal.style.display = "none";
    };

    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
});

function handleBuyClick(symbol) {
    const buyButton = document.querySelector(`#crypto-${symbol} .buy-button`);

    const price = parseFloat(buyButton.getAttribute("data-price"));
    if (isNaN(price) || price <= 0) {
        alert(`Price for ${symbol} not available. Please wait for live prices.`);
        return;
    }

    showBuyModal(symbol, price);
}

async function handleTransaction(symbol, amount, price) {
    try {
        const accountResponse = await fetch('http://localhost:8080/account/account');
        if (!accountResponse.ok) throw new Error("Failed to fetch account");
        const account = await accountResponse.json();


        const transaction = {
            type: "BUY",
            crypto: symbol,
            quantity: parseFloat(amount).toFixed(8),
            unitPrice: parseFloat(price).toFixed(8),
            profitLoss: 0.0,
            timestamp: new Date().toISOString(),
            accountId: account.id,
        };

        const response = await fetch('http://localhost:8080/transactions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(transaction),
        });

        console.log("Transaction response status:", response.status);
        if (!response.ok) throw new Error("Transaction failed");

        const data = await response.json();
        console.log("Transaction completed:", data);
        alert(`Bought ${amount} of ${symbol} for $${price} each.`);
    } catch (error) {
        console.error("Transaction error:", error);
        alert("Transaction failed. Please try again.");
    }
}

function setupWebSocket(cryptoNames) {
    socket.onopen = () => {
        console.log("WebSocket connection established.");
    };

    socket.onmessage = (event) => {
        try {
            const data = JSON.parse(event.data);

            if (!data.symbol || !data.price || !cryptoNames.includes(data.symbol.split('/')[0])) {
                console.warn("Invalid data:", data);
                return;
            }

            const symbol = data.symbol.split('/')[0];
            const price = parseFloat(data.price).toFixed(8); // Ensure price is formatted correctly

            cryptoPrices[symbol] = price;

            const priceItem = document.getElementById(`crypto-${symbol}`);
            if (priceItem) {
                const priceSpan = priceItem.querySelector(".crypto-price");
                if (priceSpan) {
                    priceSpan.textContent = `$${price}`;
                }
                const buyButton = priceItem.querySelector(".buy-button");
                if (buyButton) {
                    buyButton.setAttribute("data-price", price); // Store data-price for the Buy button
                }
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
