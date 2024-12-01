document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080";

    // Extrair o wedding_id da URL
    const params = new URLSearchParams(window.location.search);
    const weddingIdFromURL = params.get('wedding_id');

    // Verificar se o usuário está logado
    const isUser = localStorage.getItem('Authorization');

    // Elementos da página
    const btnGuestList = document.getElementById("btn-guests-list");

    if (isUser) {
        // O usuário está logado
        btnGuestList.style.display = "inline";
    } else {
        // O usuário não está logado
        btnGuestList.style.display = "none";
    }

    const token = localStorage.getItem("Authorization");
    const weddingId = localStorage.getItem("weddingId");

    async function addGuest() {
        const name = document.getElementById("nome").value;
        const email = document.getElementById("email").value;
        const num_people = parseInt(document.getElementById("qtdPessoas").value); 
        const name_people = document.getElementById("nomesPessoas").value;  
        
        const guestData = {
            "name": name,
            "email": email,
            "numPeople": num_people,
            "namePeople": name_people,
            "wedding": {
                "id": weddingId,
            }
        };

        try {
            const response = await fetch(`${baseUrl}/guest`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(guestData)
            });

            if (!response.ok) {
                throw new Error("Erro ao adicionar convidado.");
            } else {
                alert("Convidado adicionado com sucesso!");
                console.log(name)
                console.log(email)
                console.log(num_people)
            }

            // Limpar campos do formulário após o sucesso
            document.getElementById("nome").value = "";
            document.getElementById("email").value = "";
            document.getElementById("qtdPessoas").value = "";
            document.getElementById("nomesPessoas").value = "";

        } catch (error) {
            console.error("Erro ao adicionar convidado:", error);
        }
    }

    document.getElementById("btn-create-guest").addEventListener("click", addGuest);

    document.getElementById("convidadoForm").addEventListener("submit", function (event) {
        event.preventDefault();
    });
});

