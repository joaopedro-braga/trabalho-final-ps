async function register() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let email = document.getElementById("email").value;
    let confirmPassword = document.getElementById("fornecedorConfirmPassword").value;

    if (password !== confirmPassword) {
        alert("As senhas não coincidem. Por favor, confirme sua senha.");
        return; // Stop execution if passwords don't match
    }

    const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=UTF-8",
            Accept: "application/json",
        }),
        body: JSON.stringify({
            username: username,
            password: password,
            email: email,
            role: "SUPPLIER",
        }),
    });

    if(response.ok) {
        alert("Registro realizado com sucesso");
        window.location = '/src/front/login.html';
    } else {
        alert("Falha no registro");
    }
}

// Bind the register function to the click event of the register button
document.getElementById("registerButton").addEventListener("click", function (event) {
    event.preventDefault();
    register();
});
