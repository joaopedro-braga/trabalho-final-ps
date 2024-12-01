document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080";

    const token = localStorage.getItem("Authorization");
    const weddingId = localStorage.getItem("weddingId");
    const username = localStorage.getItem("username");

    const sendInviteButton = document.getElementById("btn-send-invite");
    const convidadosList = document.getElementById("guest-table");

    // Função para buscar dados da API
    async function getAPI(url) {
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
            show(data);
            console.log(data);

        } catch (error) {
            console.error("Erro ao buscar dados da API:", error);
        }
    }

    async function postAPI(url) {
        try {
            const response = await fetch(url, {
                method: "POST",
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            });

            if (!response.ok) {
                throw new Error("Erro ao enviar convites.");
            } else {
                alert("Convites enviados com sucesso!");
            }
        } catch (error) {
            alert(error);
        }
    }

    getAPI(`${baseUrl}/guest/wedding/${weddingId}`);

    // Função para criar elementos HTML
    async function show(guests) {
        let tab = `<thead>
                        <th scope="col">#</th>
                        <th scope="col">Nome</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Nº de Pessoas</th>
                        <th scope="col">Nome das Pessoas</th>
                        <th scope="col">Ação</th>
                    </thead>
                    <tbody>`;

        for(let guest of guests) {
            tab += `<tr>
                    <td>${guest.id}</td>
                    <td>${guest.name}</td>
                    <td>${guest.email}</td>
                    <td>${guest.numPeople}</td>
                    <td>${guest.namePeople}</td>
                    <td><button type="button" class="btn btn-danger btn-remove" data-guestid="${guest.id}">Excluir</button></td>
                </tr>`;

            tab += `</tbody>`;
            
            }

        document.getElementById("guest-table").innerHTML = tab;
    }

    sendInviteButton.addEventListener("click", (event) => {
        postAPI(`${baseUrl}/guest/wedding/${weddingId}/invite/${username}`);
    })

    // Evento para remover convidado
    convidadosList.addEventListener("click", (event) => {
        if (event.target.classList.contains("btn-remove")) {
            const guestId = event.target.getAttribute("data-guestid");
            if (confirm("Deseja realmente excluir este convidado?"))
                removeGuest(guestId);
        }
    });

    // Função para remover convidado
    async function removeGuest(guestId) {
        try {
            const response = await fetch(`${baseUrl}/guest/${guestId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error("Erro ao remover o convidado.");
            }

            // Atualizar a tabela após remover o convidado
            getAPI(`${baseUrl}/guest/wedding/${weddingId}`);
        } catch (error) {
            console.error("Erro ao remover o convidado:", error);
        }
    }

});
