async function initializeTransactions() {
    try {
        const priceListElement = document.getElementById("transactionList");
        priceListElement.innerHTML = "";

        const response = await fetch("http://localhost:8080/transactions");
        const transactions = await response.json();

        transactions.forEach(transaction => {
            const item = document.createElement("div");
            item.className = "transaction-item";

            const date = new Date(transaction.timestamp);
            const formattedDate = `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;

            item.innerHTML = `
                <span>Type: ${transaction.type}</span>
                <span>Crypto: ${transaction.crypto}</span>
                <span>Amount: ${transaction.quantity}</span>
                <span>Unit Price: ${transaction.unitPrice}</span>
                <span>Profit/Loss: ${transaction.profitLoss}</span>
                <span>Date: ${formattedDate}</span>
            `;
            priceListElement.appendChild(item);
        });

    } catch (err) {
        console.error("Error loading transaction history:", err);
    }
}