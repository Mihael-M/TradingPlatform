function loadAccountBalance() {
    fetch('http://localhost:8080/account/balance')
        .then(response => response.json())
        .then(data => {
            console.log("Account balance response:", data);
            const balanceElement = document.getElementById('balanceDisplay');
            const balance = parseFloat(data.balance);

            if (balanceElement && !isNaN(balance)) {
                balanceElement.textContent = `$${balance.toFixed(2)}`;
            } else {
                console.warn("Invalid balance value:", data.balance);
            }
        })
        .catch(error => console.error('Error fetching account balance:', error));
}

function loadAccount() {
    fetch('http://localhost:8080/account/account')
        .then(response => response.json())
        .then(data => {
            console.log("Account response:", data);
            const balanceElement = document.getElementById('balanceDisplay');
            const idElement = document.getElementById('id');
            const emailElement = document.getElementById('email');
            const balance = parseFloat(data.balance);
            const id = data.id;
            const email = data.email;

            if (idElement && id) {
                idElement.textContent = id;
            }

            if (emailElement && email) {
                emailElement.textContent = email;
            }

            if (balanceElement && !isNaN(balance)) {
                balanceElement.textContent = `$${balance.toFixed(2)}`;
            } else {
                console.warn("Invalid balance value:", data.balance);
            }
        })
        .catch(error => console.error('Error fetching account balance:', error));
}

function resetAccount() {
    fetch('http://localhost:8080/account/reset', {
        method: 'POST'
    })
        .then(response => {
            console.log("Reset response status:", response.status);
            if (!response.ok) throw new Error("Reset failed");
            return response.json();
        })
        .then(() => {
            loadAccountBalance();
            const message = document.getElementById('resetMessage');
            if (message) {
                message.textContent = "Account reset successfully!";
                setTimeout(() => message.textContent = "", 3000);
            } else {
                console.warn("resetMessage element not found");
            }
        })
        .catch(error => console.error('Error resetting account:', error));
}

document.addEventListener('DOMContentLoaded', () => {
    const resetButton = document.getElementById('resetButton');
    if (resetButton) {
        resetButton.addEventListener('click', resetAccount);
    }

    // Initial load
    loadAccountBalance();
});