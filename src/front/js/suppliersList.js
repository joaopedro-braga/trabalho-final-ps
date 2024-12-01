document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080";

    const userId = localStorage.getItem("id");
    const token = localStorage.getItem("Authorization");

    // Função para buscar dados da API
    async function getAPI(url) {
        try {
            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
            });

            if (!response.ok) {
                throw new Error("Erro ao buscar dados da API.");
            }

            const data = await response.json();
            console.log(data);
            show(data);
        } catch (error) {
            console.error("Erro ao buscar dados da API:", error);
        }
    }

    getAPI(`${baseUrl}/user/supplier/all`);

    // Função para criar elementos HTML
    async function show(suppliers) {
        let tab = `<thead>
                        <th scope="col">#</th>
                        <th scope="col">Nome</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Ação</th>
                    </thead>
                    <tbody>`;

        for (let supplier of suppliers) {
            tab += `<tr>
                        <td>${supplier.id}</td>
                        <td>${supplier.username}</td>
                        <td>${supplier.email}</td>
                        <td>
                            <button type="button" class="btn btn-warning btn-profile" id ="btn-perfil-supplier" data-bs-toggle="modal" data-bs-target="#staticBackdrop" data-supplier-id="${supplier.id}">Perfil</button>
                            <button type="button" class="btn btn-primary btn-feedback" id ="AdicionarComentario" data-bs-toggle="modal" data-bs-target="#staticBackdrop" data-supplier-id="${supplier.id}">Avaliar</button>
                        </td>
                    </tr>`;

            tab += `</tbody>`;
        }

        document.getElementById("supplier-table").innerHTML = tab;

    }

    // Evento para avaliar fornecedor

    const supplierList = document.getElementById("supplier-table");

    var supChose = 0
    supplierList.addEventListener("click", (event) => {
        if (event.target.classList.contains("btn-feedback")) {
            const supplierId = event.target.getAttribute("data-supplier-id");
            supChose = supplierId
        }
    });

    var ratingSelected = 0
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


        const feedbackData = {
            "description": description,
            "rating": ratingSelected,
            "user": {
                "id": userId
            },
            "supplier": {
                "id": supChose
            }
        }

        try {
            const response = await fetch(`${baseUrl}/feedback`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
                body: JSON.stringify(feedbackData)
            });

            if (!response.ok) {
                throw new Error("Erro ao adicionar feedback.");
            }

            // Limpar campos do formulário após o sucesso
            // document.getElementById("rating").value = "";
            // document.getElementById("description").value = "";

        } catch (error) {
            console.error("Erro ao adicionar feedback:", error);
        }
    }

    document.getElementById("formComentario").addEventListener("submit", function (event) {
        event.preventDefault();
    });

    document.getElementById("btn-send-feedback").addEventListener("click", function () {
        addFeedback()
        alert("Feedback enviado com sucesso!");
    });


    supplierList.addEventListener("click", (event) => {
        if (event.target.classList.contains("btn-profile")) {
            const supplierId = event.target.getAttribute("data-supplier-id");
            window.location.href = `./supplierProfile.html?id=${supplierId}`;
            getSupplierData(supplierId);
        }
    });
  

});

