const API_BASE_URL = "http://localhost:8080/prices";
// Replace with your API base URL

// Fetch list of cryptocurrency names
async function fetchCryptoNames() {
    try {
        const response = await fetch(`${API_BASE_URL}/prices`);
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

module.exports = { fetchCryptoNames };