async function initializeTransactions() {
    try {
        const priceListElement = document.getElementById("transactionList");
        priceListElement.innerHTML = "";

        const response = await fetch("http://localhost:8080/transactions");
        const transactions = await response.json();

        transactions.forEach(transaction => {
            const item = document.createElement("div");
            item.className = "transaction-item";
            item.innerHTML = `
                <span>Type: ${transaction.type}</span>
                <span>Crypto: ${transaction.crypto}</span>
                <span>Amount: ${transaction.quantity}</span>
                <span>Unit Price: ${transaction.unitPrice}</span>
                <span>Profit/Loss: ${transaction.profitLoss}</span>
                <span>Date: ${transaction.timestamp}</span>
            `;
            priceListElement.appendChild(item);
        });

    } catch (err) {
        console.error("Error loading transaction history:", err);
    }
}

document.getElementById("transactionHistoryButton").addEventListener("click", function () {
    initializeTransactions();
});