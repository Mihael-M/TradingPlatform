const socket = new WebSocket("ws://localhost:8080/prices");

socket.onopen = () => {
    console.log("✅ WebSocket connection established.");
};

socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    console.log("💸 Received price update:", data);

    // Assuming the JSON has fields like: { "symbol": "BTC", "price": 65782.12 }
    const priceText = `${data.symbol}: $${parseFloat(data.price).toFixed(2)}`;

    const priceElement = document.getElementById("cryptoPrice");
    if (priceElement) {
        priceElement.textContent = priceText;
    }
};

socket.onerror = (error) => {
    console.log("WebSocket error:", error);
};