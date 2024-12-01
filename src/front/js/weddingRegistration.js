async function createWedding() {
    const token = localStorage.getItem('Authorization');
    const userId = localStorage.getItem('id');

    const weddingData = {
        name: document.getElementById("name").value,
        date: document.getElementById("date").value,
        time: document.getElementById("time").value,
        local: document.getElementById("local").value,
        budget: document.getElementById("orcamento").value,
        user: {
            id: userId
        }
    };

    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };

    try {
        const response = await fetch("http://localhost:8080/wedding", {
            method: "POST",
            headers: headers,
            body: JSON.stringify(weddingData)
        });

        if (response.ok) {
            const data = await response.json();
            const weddingId = data.id;
            window.localStorage.setItem("weddingId", weddingId);
            alert("Wedding created");
            window.location = 'main.html';
        } else {
            alert("Wedding not created");
        }
    } catch (error) {
        console.error("Error creating wedding:", error);
        alert("An error occurred while creating the wedding");
    }
}

document.getElementById("createWeddingButton").addEventListener("click", function (event) {
    event.preventDefault();
    createWedding();
});
