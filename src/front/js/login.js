async function login() {
    let username = document.getElementById("usernameInput").value;
    let password = document.getElementById("passwordInput").value;

    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
            Accept: "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
        }),
    });

    if (response.ok) {
        alert("Login successful");
        const data = await response.json();
        const token = data.token;
        const id = data.id;
        const weddingId = data.weddingId;

        window.localStorage.setItem("Authorization", token);
        window.localStorage.setItem("username", username);
        window.localStorage.setItem("id", id);
        window.localStorage.setItem("weddingId", weddingId);
        
        // document.getElementById("username").textContent = username;
        if(weddingId == 0 || weddingId == null) 
            window.location = 'weddingRegistration.html';
        else 
            window.location = 'main.html';
    } else 
        alert("Login failed");
}

// Vincular a função de login ao evento de clique do botão de login
document.getElementById("loginButton").addEventListener("click", function (event) {
    event.preventDefault();
    login();
});

document.getElementById("logoutButton").addEventListener("click", function() {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('username');
    localStorage.removeItem('id');
    localStorage.removeItem('weddingId');
    window.location = 'login.html';
});
