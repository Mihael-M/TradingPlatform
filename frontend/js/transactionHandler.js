// export async function handleTransaction(symbol, amount, price, type = "BUY") {
//     try {
//         const accountResponse = await fetch('http://localhost:8080/account/account');
//         if (!accountResponse.ok) throw new Error("Failed to fetch account");
//         const account = await accountResponse.json();
//
//         const transaction = {
//             type: type,
//             crypto: symbol,
//             quantity: parseFloat(amount).toFixed(8),
//             unitPrice: parseFloat(price).toFixed(8),
//             profitLoss: type === "SELL" ? calculateProfitLoss(symbol, amount, price) : 0.0,
//             timestamp: new Date().toISOString(),
//             accountId: account.id,
//         };
//
//         const response = await fetch('http://localhost:8080/transactions', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify(transaction),
//         });
//
//         if (!response.ok) throw new Error("Transaction failed");
//
//         const data = await response.json();
//         console.log("Transaction completed:", data);
//         alert(`${type} ${amount} of ${symbol} for $${price} each.`);
//     } catch (error) {
//         console.error("Transaction error:", error);
//         alert("Transaction failed. Please try again.");
//     }
// }
//
// function calculateProfitLoss(symbol, quantity, price) {
//     // Add logic for calculating profit or loss
//     return 0.0;  // Placeholder logic for now
// }