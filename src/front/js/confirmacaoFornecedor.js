document.getElementById("btnConfirmar").addEventListener("click", (event) => {
    let divMain = document.getElementById("divMain");
    divMain.innerHTML = `
    <h1 style="display: flex; justify-content: center;">Obrigado!</h1>
    `
});

document.getElementById("confirmar").addEventListener("click", function () {
    confirmarPresenca();
});

document.getElementById("negar").addEventListener("click", function () {
    negarPresenca();
});

function confirmarPresenca() {
    // Implemente uma solicitação AJAX para o endpoint do Spring Boot para confirmação
    // Use o token ou identificador do compromisso na URL
}

function negarPresenca() {
    // Implemente uma solicitação AJAX para o endpoint do Spring Boot para negação
    // Use o token ou identificador do compromisso na URL
}
