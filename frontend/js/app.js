const tabs = document.querySelectorAll('.tab');
const tabContents = document.querySelectorAll('.tab-content');

tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        document.querySelector('.tab.active').classList.remove('active');
        document.querySelector('.tab-content.active').classList.remove('active');
        loadAccountBalance();
        tab.classList.add('active');
        document.getElementById(tab.dataset.target).classList.add('active');
    });
});

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("transactionHistoryButton").addEventListener("click", function () {
        initializeTransactions();
        document.getElementById("transactionList").style.display = "block";
        document.getElementById("holdingList").style.display = "none";
    });

    document.getElementById("holdingsButton").addEventListener("click", function () {
        initializeHoldings();
        document.getElementById("transactionList").style.display = "none";
        document.getElementById("holdingList").style.display = "block";
    });
});