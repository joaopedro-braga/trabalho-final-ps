let notaComentario = 0;

window.onload = (e) => {
    let stars = document.querySelectorAll('input[name="rating"]');
    for (let p = 0; p < stars.length; p++) {
        stars[p].addEventListener('click', function () {
            notaComentario = this.value;
            console.log(notaComentario);    
        })
    }
};



function imprimeDados() {
    let tela = document.getElementById('tela');
    let tela_estrela = document.getElementById("tela_estrela");
    let strMediaEstrela = '';
    let strHtml = '';
    let objDados = leDados();
    let media = 0;

    for (i = 0; i < objDados.comentario.length; i++) {
        media += objDados.comentario[i].avaliacao;
        strHtml += `<div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <p class="card-text"><b>${objDados.comentario[i].nome}</b><br> ${objDados.comentario[i].avaliacao} estrelas <br> ${objDados.comentario[i].comentario}</p></p>
                        </div>
                    </div>`
    }
    console.log(media);
    media /= objDados.comentario.length;

    if (media >= 0.5 && media < 1) {
        strMediaEstrela = `<i class="fas fa-star-half-alt estrelas_media"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i>`;
    } else if (media == 1) {
        strMediaEstrela = `<i class="fas fa-star estrelas_media"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i>`;
    } else if (media > 1 && media <= 1.5) {
        strMediaEstrela = `<i class="fas fa-starestrelas_media"></i> <i class="fas fa-star-half-alt"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i>`;
    } else if (media > 1.5 && media <= 2) {
        strMediaEstrela = `<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i>`;
    } else if (media > 2 && media <= 2.5) {
        strMediaEstrela = `<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star-half-alt"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i>`;
    } else if (media > 2.5 && media <= 3) {
        strMediaEstrela = `<i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fa fa-star-o" aria-hidden="true"></i><i class="fa fa-star-o" aria-hidden="true"></i>`;
    } else if (media > 3 && media <= 3.5) {
        strMediaEstrela = `<i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star-half-alt estrelas_media"></i>`;
    } else if (media > 3.5 && media <= 4) {
        strMediaEstrela = `<i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i>`;
    } else if (media > 4 && media <= 4.5) {
        strMediaEstrela = `<i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star-half-alt estrelas_media"></i>`;
    } else if (media > 4.5 && media <= 5) {
        strMediaEstrela = `<i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i><i class="fas fa-star estrelas_media"></i>`;
    }


    tela.innerHTML = strHtml;
    tela_estrela.innerHTML = strMediaEstrela;
}

// Configura os botões
// document.getElementById('btnCarregaDados').addEventListener('click', imprimeDados);
// document.getElementById('EnviarComentario').addEventListener('click', incluirComentario);
