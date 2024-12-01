async function register() {
    let username = document.getElementById("username").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    let confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("As senhas não coincidem. Por favor, confirme sua senha.");
    }

    const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=UTF-8",
            Accept: "application/json",
        }),
        body: JSON.stringify({
            username: username,
            email: email,
            password: password,
            email: email,
            role: "USER",
        }),
    });

    if(response.ok) {
        alert("Register successful");
        window.location = '/src/front/login.html';
    } else 
        alert("Register failed");
}

// Vincular a função de registrar ao evento de clique do botão de login
document.getElementById("registerButton").addEventListener("click", function (event) {
    event.preventDefault();
    register();
});
