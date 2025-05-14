const socket = new WebSocket("ws://localhost:8080/prices");

let cryptoPrices = {};


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

            const sellButton = document.createElement("button");
            sellButton.className = "sell-button";
            sellButton.textContent = "Sell";
            sellButton.onclick = () => handleSellClick(holding.crypto, holding.quantity, holding.unitPrice);
            item.appendChild(sellButton);
        });

    } catch (err) {
        console.error("Error loading holdings:", err);
    }
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
            const price = parseFloat(data.price).toFixed(8);

            cryptoPrices[symbol] = price;

            const priceItem = document.getElementById(`crypto-${symbol}`);
            if (priceItem) {
                const priceSpan = priceItem.querySelector(".crypto-price");
                if (priceSpan) {
                    priceSpan.textContent = `$${price}`;
                }
                const buyButton = priceItem.querySelector(".buy-button");
                if (buyButton) {
                    buyButton.setAttribute("data-price", price);
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

// ---- MODAL HANDLING & CLICK HANDLERS ----

function showSellModal(symbol, maxQuantity, acquisitionPrice) {
    const sellModal = document.getElementById("sellModal");
    const sellAmountInput = document.getElementById("sell-amount");
    const confirmSellButton = document.getElementById("confirmSellButton");
    const cryptoSymbolSpan = document.getElementById("sellCryptoSymbol");
    const maxQuantitySpan = document.getElementById("sellMaxQuantity");
    // Optional: Display acquisition price for user context
    // const acquisitionPriceSpan = document.getElementById("sellAcquisitionPrice");


    if (!sellModal || !sellAmountInput || !confirmSellButton || !cryptoSymbolSpan || !maxQuantitySpan) {
        console.error("Sell modal elements not found. Check IDs: sellModal, sell-amount, confirmSellButton, sellCryptoSymbol, sellMaxQuantity.");
        alert("Error: Could not open sell dialog.");
        return;
    }

    cryptoSymbolSpan.textContent = symbol;
    const numericMaxQuantity = parseFloat(maxQuantity);
    maxQuantitySpan.textContent = numericMaxQuantity.toFixed(8);
    sellAmountInput.value = "";
    sellAmountInput.max = numericMaxQuantity.toFixed(8);
    sellAmountInput.placeholder = `Max: ${numericMaxQuantity.toFixed(8)}`;
    // if (acquisitionPriceSpan && acquisitionPrice) {
    //     acquisitionPriceSpan.textContent = `$${parseFloat(acquisitionPrice).toFixed(4)} each`;
    // }


    sellModal.classList.add("show");
    sellModal.style.display = "block";

    const newConfirmSellButton = confirmSellButton.cloneNode(true);
    confirmSellButton.parentNode.replaceChild(newConfirmSellButton, confirmSellButton);

    newConfirmSellButton.onclick = async () => {
        const amountToSell = parseFloat(sellAmountInput.value);
        if (isNaN(amountToSell) || amountToSell <= 0 || amountToSell > numericMaxQuantity) {
            alert(`Please enter a valid amount to sell (0 - ${numericMaxQuantity.toFixed(8)}).`);
            return;
        }
        await handleTransaction(symbol, amountToSell, "SELL");
        sellModal.style.display = "none";
    };
}

function showBuyModal(symbol, price) {
    const buyModal = document.getElementById("buyModal");
    const buyAmountInput = document.getElementById("buy-amount");
    const confirmBuyButton = document.getElementById("confirmBuyButton");

    if (!buyModal || !buyAmountInput || !confirmBuyButton) {
        console.error("Buy modal elements not found. Check IDs: buyModal, buy-amount, confirmBuyButton.");
        alert("Error: Could not open buy dialog.");
        return;
    }

    buyModal.classList.add("show");
    buyModal.style.display = "block";
    buyAmountInput.value = "";
    buyAmountInput.placeholder = "Enter amount to buy";

    const newConfirmBuyButton = confirmBuyButton.cloneNode(true);
    confirmBuyButton.parentNode.replaceChild(newConfirmBuyButton, confirmBuyButton);

    newConfirmBuyButton.onclick = async () => {
        const amount = parseFloat(buyAmountInput.value);
        if (isNaN(amount) || amount <= 0) {
            alert("Please enter a valid amount to buy.");
            return;
        }
        await handleTransaction(symbol, amount, "BUY");
        buyModal.style.display = "none";
    };
}

function handleSellClick(symbol, quantity, acquisitionPrice) {
    const numericQuantity = parseFloat(quantity);
    if (isNaN(numericQuantity) || numericQuantity <= 0) {
        alert(`Invalid quantity (${quantity}) for ${symbol} to sell.`);
        return;
    }
    showSellModal(symbol, numericQuantity, acquisitionPrice);
}

function handleBuyClick(symbol) {
    const buyButton = document.querySelector(`#crypto-${symbol} .buy-button`);
    if (!buyButton) {
        alert(`Could not find buy button for ${symbol}.`);
        return;
    }
    const price = parseFloat(buyButton.getAttribute("data-price"));
    if (isNaN(price) || price <= 0) {
        alert(`Price for ${symbol} not available. Please wait for live prices.`);
        return;
    }
    showBuyModal(symbol, price);
}

handleTransaction.inProgress = false;

async function handleTransaction(symbol, quantity, type) {
    if (handleTransaction.inProgress) {
        alert("A transaction is already in progress. Please wait.");
        return;
    }
    handleTransaction.inProgress = true;

    let account;
    try {
        const accountResponse = await fetch('http://localhost:8080/account/account');
        if (!accountResponse.ok) {
            throw new Error(`Failed to fetch account details: ${accountResponse.statusText}`);
        }
        account = await accountResponse.json();
        if (!account || !account.id) {
            throw new Error("Invalid account data received.");
        }

        const buyButton = document.querySelector(`#crypto-${symbol} .buy-button`);
        const actualTransactionPrice = buyButton ? parseFloat(buyButton.getAttribute("data-price")) : NaN;

        if (isNaN(actualTransactionPrice) || actualTransactionPrice <= 0) {
            throw new Error(`Price for ${symbol} is not available or invalid. Please wait for live prices.`);
        }

        const transaction = {
            type: type,
            crypto: symbol,
            quantity: parseFloat(quantity).toFixed(8),
            unitPrice: actualTransactionPrice.toFixed(8),
            profitLoss: 0.0,//type === "SELL" ? await calculateProfitLoss(symbol, parseFloat(quantity), actualTransactionPrice, account.id) : 0.0,
            timestamp: new Date().toISOString(),
            accountId: account.id,
        };
        console.log("Sending transaction:", transaction);
        const transactionResponse = await fetch('http://localhost:8080/transactions', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(transaction),
        });
        if (!transactionResponse.ok) {
            const errorText = await transactionResponse.text();
            throw new Error(`Transaction failed: ${transactionResponse.statusText}. Server: ${errorText}`);
        }

        const responseData = await transactionResponse.json();
        console.log("Transaction completed:", responseData);
        alert(`Successfully ${type === "BUY" ? "bought" : "sold"} ${parseFloat(quantity).toFixed(8)} of ${symbol} at $${actualTransactionPrice.toFixed(4)} each.`);

        initializeHoldings();


    } catch (error) {
        console.error("Transaction error:", error);
        alert(`Transaction failed: ${error.message || "An unknown error occurred."}`);
    } finally {
        handleTransaction.inProgress = false;
    }
}

async function calculateProfitLoss(symbol, quantitySold, sellingPrice, accountId) {
    console.warn(`Profit/Loss calculation for ${symbol} is a placeholder. Needs actual implementation (e.g., FIFO/Average Cost based on purchase history). Returning 0.0.`);
// Example: Fetch historical buys
// try {
//     const response = await fetch(`http://localhost:8080/transactions?accountId=${accountId}&crypto=${symbol}&type=BUY&sortBy=timestamp&order=asc`);
//     if (!response.ok) throw new Error("Failed to fetch purchase history for P/L");
//     const buyTransactions = await response.json();
//     // --- Implement FIFO or Average Cost Logic here ---
//
}
    document.addEventListener("DOMContentLoaded", () => {

        const sellModalElement = document.getElementById("sellModal");
        const closeSellModalButton = sellModalElement ? sellModalElement.querySelector(".close") : null;
        const cancelSellButton = document.getElementById("cancelSellButton");

        const buyModalElement = document.getElementById("buyModal");
        const closeBuyModalButton = buyModalElement ? buyModalElement.querySelector(".close") : null;
        const cancelBuyButton = document.getElementById("cancelBuyButton");

        if (closeSellModalButton && sellModalElement) {
            closeSellModalButton.onclick = function () {
                sellModalElement.style.display = "none";
                sellModalElement.classList.remove("show");
            };
        }
        if (cancelSellButton && sellModalElement) {
            cancelSellButton.onclick = function () {
                sellModalElement.style.display = "none";
                sellModalElement.classList.remove("show");
            };
        }

        if (closeBuyModalButton && buyModalElement) {
            closeBuyModalButton.onclick = function () {
                buyModalElement.style.display = "none";
                buyModalElement.classList.remove("show");
            };
        }
        if (cancelBuyButton && buyModalElement) {
            cancelBuyButton.onclick = function () {
                buyModalElement.style.display = "none";
                buyModalElement.classList.remove("show");
            };
        }

        window.onclick = function (event) {
            if (sellModalElement && event.target === sellModalElement) {
                sellModalElement.style.display = "none";
                sellModalElement.classList.remove("show");
            }
            if (buyModalElement && event.target === buyModalElement) {
                buyModalElement.style.display = "none";
                buyModalElement.classList.remove("show");
            }
        };


        initializeHoldings();
        initializeCryptoPrices();
    });

