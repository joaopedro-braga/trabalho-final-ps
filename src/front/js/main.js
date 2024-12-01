document.addEventListener('DOMContentLoaded', function() {
    const baseUrl = "http://localhost:8080";

    const weddingId = localStorage.getItem('weddingId');
    const token = localStorage.getItem("Authorization");

    if (weddingId) {
        const giftLink = document.getElementById('giftLink');
        const photoAlbumLink = document.getElementById('photoAlbumLink');
        const guestLink = document.getElementById('guestLink');
        const budgetLink = document.getElementById('budgetLink');

        const urlGiftWithWeddingId = `registerGifts.html?wedding_id=${weddingId}`;
        const urlPhotoAlbumWithWeddingId = `photoAlbum.html?wedding_id=${weddingId}`;
        const urlGuestWithWeddingId = `confirmacaoDePresenca.html?wedding_id=${weddingId}`;
        const urlBudgetWithWeddingId = `orcamento.html?wedding_id=${weddingId}`;

        giftLink.href = urlGiftWithWeddingId;
        photoAlbumLink.href = urlPhotoAlbumWithWeddingId;
        guestLink.href = urlGuestWithWeddingId;
        budgetLink.href = urlBudgetWithWeddingId;
    }

    // Função para verificar e comparar a data do casamento com a data atual
    async function checkWeddingDate() {
        try {
            const response = await fetch(`${baseUrl}/wedding/${weddingId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
            });

            if (!response.ok) {
                throw new Error("Erro ao buscar os casamentos da API.");
            }

            const weddingData = await response.json();
            console.log(weddingData);

            const weddingDate = new Date(weddingData.date[0], weddingData.date[1] - 1, weddingData.date[2]);
            const currentDate = new Date();

            if (currentDate >= weddingDate) {
                if (weddingData.finished) {
                    console.log("O casamento já ocorreu e está marcado como finalizado.");
                } else {
                    console.log("O casamento está agendado para hoje ou já passou, mas não foi marcado como finalizado.");
                    const modal = document.getElementById('weddingModal');
                    modal.classList.add('show');
                    modal.style.display = 'block';
                }
            } else {
                console.log("O casamento ainda não ocorreu.");
            }

        } catch (error) {
            console.error("Erro ao buscar os casamentos da API:", error);
        }
    }

    checkWeddingDate();

    // Event listener para o botão "Ok" do modal
    const okButton = document.getElementById('okButton'); // Supondo que o botão tem o ID 'okButton'
    okButton.addEventListener('click', async () => {
        try {
            const response = await fetch(`${baseUrl}/wedding/${weddingId}/finished`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
                body: JSON.stringify({ finished: true })
            });

            if (!response.ok) {
                throw new Error('Erro ao atualizar o status do casamento.');
            }

            // Atualização bem-sucedida
            console.log('O status do casamento foi atualizado para "finished".');
            
            window.location.href = 'comentarioSite.html';

            // Fechar o modal após atualização
            const modal = document.getElementById('weddingModal');
            modal.classList.remove('show');
            modal.style.display = 'none';
        } catch (error) {
            console.error('Erro ao atualizar o status do casamento:', error);
        }
    });


});