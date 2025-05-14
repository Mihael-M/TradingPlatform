// Function to fetch and display user holdings
async function initializeHoldings() {
    try {
        const priceListElement = document.getElementById("holdingList");
        priceListElement.innerHTML = "";

        const response = await fetch("http://localhost:8080/Holding");
        const holdings = await response.json();

        holdings.forEach(holding => {
            const item = document.createElement("div");
            item.className = "holding-item";
            item.innerHTML = `
                <span>Crypto: ${holding.crypto}</span>
                <span>Quantity: ${holding.quantity}</span>
                <span>Total Value: ${holding.totalValue}</span>
            `;
            priceListElement.appendChild(item);
        });

    } catch (err) {
        console.error("Error loading holdings:", err);
    }
}


document.getElementById("holdingsButton").addEventListener("click", function () {
    initializeHoldings();
});