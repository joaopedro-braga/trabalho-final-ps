document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080";
    const userId = localStorage.getItem("id");
    const token = localStorage.getItem("Authorization");

    // Função para buscar dados do usuário por ID
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
                throw new Error("Erro ao buscar dados do usuário.");
            }

            return await response.json();
        } catch (error) {
            console.error("Erro ao buscar dados do usuário:", error);
            return null;
        }
    }

    // Função para buscar dados dos feedbacks
    async function getFeedbackData() {
        try {
            const response = await fetch(`${baseUrl}/feedback/nullSupplier`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
            });

            if (!response.ok) {
                throw new Error("Erro ao buscar dados dos feedbacks.");
            }

            const feedbackData = await response.json();
            show(feedbackData);
        } catch (error) {
            console.error("Erro ao buscar dados dos feedbacks:", error);
        }
    }

    // Função para criar elementos HTML
    async function show(comments) {
        let tab = `<thead>
                        <th scope="col">Nome</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Avaliação</th>
                    </thead>
                    <tbody>`;

        for (let comment of comments) {
            const userData = await getUserData(comment.user);
            console.log(userData);

            const username = userData && userData.username ? userData.username : "Nome não encontrado";

            tab += `<tr>
                        <td>${username}</td>
                        <td>${comment.rating}</td>
                        <td>${comment.description}</td>
                    </tr>`;
        }

        tab += `</tbody>`;
        document.getElementById("comment-table").innerHTML = tab;
    }

    getFeedbackData(); // Busca dados dos feedbacks inicialmente

    var ratingSelected = 0;
    const rating = document.querySelectorAll(".rating");
    rating.forEach((star) => {
        star.addEventListener("click", (event) => {
            ratingSelected = event.target.getAttribute("value");
            console.log(ratingSelected);
        });
    });

    // Função para adicionar um feedback
    async function addFeedback() {
        const description = document.getElementById("description").value;

        const commentData = {
            "description": description,
            "rating": ratingSelected,
            "user": {
                "id": userId
            }
        };

        try {
            const response = await fetch(`${baseUrl}/feedback`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
                body: JSON.stringify(commentData)
            });

            if (!response.ok) {
                throw new Error("Erro ao adicionar comentário.");
            }

            // Atualiza os dados dos feedbacks após adicionar o novo feedback
            getFeedbackData();
            alert("Feedback enviado com sucesso!");
        } catch (error) {
            console.error("Erro ao adicionar feedback:", error);
        }
    }

    document.getElementById("formComentario").addEventListener("submit", function (event) {
        event.preventDefault();
    });

    document.getElementById("btn-send-feedback").addEventListener("click", function () {
        addFeedback();
    });
});
