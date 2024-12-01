document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080";

    const token = localStorage.getItem('Authorization');
    const weddingId = localStorage.getItem('weddingId');

    async function getAPI(url) {
        try {
            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`

                },
            
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
    
    getAPI(`${baseUrl}/giftMessage/wedding/${weddingId}`);

    // Função para criar elementos HTML
    async function show(giftMessages) {
        let tab = `<thead>
                        <th scope="col">#</th>
                        <th scope="col">Convidado</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Presente</th>
                        <th scope="col">Mensagem ao casal</th>
                    </thead>
                    <tbody>`;

        for(let giftMessage of giftMessages) {
            tab += `<tr>
                    <td>${giftMessage.id}</td>
                    <td>${giftMessage.name}</td>
                    <td>${giftMessage.email}</td>
                    <td>${giftMessage.giftName}</td>
                    <td>${giftMessage.description}</td>
                </tr>`;

            tab += `</tbody>`;
            
            }

        document.getElementById("giftMessagesList").innerHTML = tab;
    }



});
