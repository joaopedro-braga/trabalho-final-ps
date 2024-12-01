document.addEventListener("DOMContentLoaded", async () => {
    const baseUrl = "http://localhost:8080";

    const usernameLogin = localStorage.getItem("username");
    const token = localStorage.getItem("Authorization");

    // Obter o ID do fornecedor da URL
    const urlParams = new URLSearchParams(window.location.search);
    const supplierId = urlParams.get("id");

    // Função para obter dados do fornecedor por ID
    async function getSupplierData(supplierId) {
        try {
            const response = await fetch(`${baseUrl}/user/${supplierId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },

            });

            if (!response.ok) {
                throw new Error("Erro ao obter dados do fornecedor.");
            }

            const data = await response.json();
            console.log("Dados do Fornecedor:", data);
            
            // Preencher os elementos HTML com os dados do fornecedor
            document.getElementById("supplier-name").textContent = data.username;
            document.getElementById("supplier-email").textContent = data.email;

            // Chamar a função para obter comentários do fornecedor
            await getSupplierComments(supplierId);
        } catch (error) {
            console.error("Erro ao obter dados do fornecedor:", error);
        }
    }

    // Verificar se o ID do fornecedor existe na URL
    if (supplierId) {
        console.log("ID do Fornecedor:", supplierId);
        // Chamar a função para obter dados do fornecedor
        await getSupplierData(supplierId);
    } else {
        console.error("ID do fornecedor não encontrado na URL.");
    }

    // Função para obter comentários do fornecedor por ID
    async function getSupplierComments(supplierId) {
        try {
            const response = await fetch(`${baseUrl}/feedback/supplier/${supplierId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },            
            });

            if (!response.ok) {
                throw new Error("Erro ao obter comentários do fornecedor.");
            }

            const comments = await response.json();
            console.log("Comentários do Fornecedor:", comments);
            
            // Renderizar os comentários na página
            renderComments(comments);
        } catch (error) {
            console.error("Erro ao obter comentários do fornecedor:", error);
        }
    }

    // Função para renderizar os comentários na página
    async function renderComments(comments) {
        const commentsList = document.getElementById("comments-list");

        // Limpar a lista de comentários antes de adicionar novos
        commentsList.innerHTML = '';

        for (const comment of comments) {
            const listItem = document.createElement("li");
            listItem.className = "list-group-item";

            const userStrong = document.createElement("strong");

            try {
                const userData = await getUserData(comment.user);
                if (userData && userData.username) {
                    userStrong.textContent = userData.username;
                } else {
                    userStrong.textContent = 'Nome de usuário não encontrado';
                }
            } catch (error) {
                console.error('Erro ao obter nome de usuário:', error);
                userStrong.textContent = 'Erro ao obter nome de usuário';
            }

            const ratingSpan = document.createElement("span");
            ratingSpan.textContent = ` (${comment.rating})`;

            const commentText = document.createTextNode(` - ${comment.description}`);

            listItem.appendChild(userStrong);
            listItem.appendChild(ratingSpan);
            listItem.appendChild(commentText);

            commentsList.appendChild(listItem);
        }
    }

    // Função para obter dados do usuário por ID
    async function getUserData(userId) {
        try {
            const response = await fetch(`${baseUrl}/user/${userId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
            });

            if (!response.ok) {
                throw new Error("Erro ao obter dados do usuário.");
            }

            return await response.json();
        } catch (error) {
            throw new Error("Erro ao obter dados do usuário.");
        }
    }


    // Função para buscar dados da API
    async function getApiTasks(url) {
        try {
            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error("Erro ao buscar dados da API.");
            }

            const data = await response.json();
            console.log(data);
            // show(data);
            
        } catch (error) {
            console.error("Erro ao buscar dados da API:", error);
        }
    }

    getApiTasks(`${baseUrl}/task?supplierId=1`);


});
