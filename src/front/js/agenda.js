document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080";

    const userId = localStorage.getItem("id");
    const token = localStorage.getItem("Authorization");
    const username = localStorage.getItem("username");
    const weddingId = localStorage.getItem("weddingId");

    const tasksList = document.getElementById("task-list");
    const btnCreateTask = document.getElementById("btn-create-task");

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
            
        } catch (error) {
            console.error("Erro ao buscar dados da API:", error);
        }
    }

    getAPI(`${baseUrl}/task/user/${userId}`);

    // Função para buscar dados da API por email do fornecedor
    async function getAPIByEmail(url) {
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
            return data;
        } catch (error) {
            console.error("Erro ao buscar dados da API:", error);
            return null;
        }
    }
    
    // Função para criar elementos HTML
    async function show(tasks) {
        let tab = `<thead>
                        <th scope="col">#</th>
                        <th scope="col">Título</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Data</th>
                        <th scope="col">Hora</th>
                        <th scope="col">Fornecedor</th>
                        <th scope="col">Status</th>
                        <th scope="col">Ação</th>
                    </thead>
                    <tbody>`;

        for (let task of tasks) {
            const date = new Date(task.date);
            const formattedDate = date.toLocaleDateString();
            
            // let supplierUsername = "Não informado";

            // // Obter os dados do fornecedor usando o id do fornecedor da tarefa
            if (task.supplier !== null) {
                const supplierData = await getAPIByEmail(`${baseUrl}/user/${task.supplier}`);

                var supplierUsername = supplierData ? supplierData.username : "Não informado";
            } else {
                var supplierUsername = "Não informado";
            }

            tab += `<tr>
            <td>${task.id}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${formattedDate}</td>
            <td>${task.time.slice(0, 5)}</td>
            <td>${supplierUsername}</td>
            <td>
                <select class="form-select status-select" data-task-id="${task.id}" style="width: 128px;">
                    <option value="Pendente" ${task.status === 'Pendente' ? 'selected' : ''}>Pendente</option>
                    <option value="Feito" ${task.status === 'Feito' ? 'selected' : ''}>Feito</option>
                    <option value="Cancelado" ${task.status === 'Cancelado' ? 'selected' : ''}>Cancelado</option>
                </select>
            </td>
            <td><button type="button" class="btn btn-danger btn-remove" data-task-id="${task.id}">Excluir</button></td>
        </tr>`;

            
        }

        tab += `</tbody>`;
        document.getElementById("task-list").innerHTML = tab;
    }

    async function addTask() {
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;
        const date = document.getElementById("date").value;
        const time = document.getElementById("time").value;
        const email_fornecedor = document.getElementById("email_fornecedor").value;

        // postAPI(`${baseUrl}/task/invite/${username}/${email_fornecedor}/${date}/${time}/${title}/${description}`);

        let taskData = {};

        if (email_fornecedor !== "") {
            postAPI(`${baseUrl}/task/invite/${username}/${email_fornecedor}/${date}/${time}/${title}/${description}`);

            alert("Fornecedor encontrado com o e-mail fornecido.");
    
            let supplierData = await getAPIByEmail(`${baseUrl}/user/supplier/email/${email_fornecedor}`);
            let supplierId = supplierData[0].id;
    
            taskData = {
                "title": title,
                "description": description,
                "date": date,
                "time": time,
                "user": {
                    "id": userId
                },
                "supplier": {
                    "id": supplierId
                }
            };
        } else {
            alert("Nenhum fornecedor encontrado com o e-mail fornecido.");
    
            taskData = {
                "title": title,
                "description": description,
                "date": date,
                "time": time,
                "user": {
                    "id": userId
                }
            };
        }
    
        try {
            const response = await fetch(`${baseUrl}/task`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(taskData)
            });
    
            if (!response.ok) {
                alert("Erro ao adicionar tarefa.");
                throw new Error("Erro ao adicionar tarefa.");
            } else {
                alert("Tarefa adicionada com sucesso!");
    
                // Limpar campos do formulário após o sucesso
                document.getElementById("title").value = "";
                document.getElementById("description").value = "";
                document.getElementById("date").value = "";
                document.getElementById("time").value = "";
                document.getElementById("email_fornecedor").value = "";
    
                // Atualizar a tabela após adicionar a tarefa
                getAPI(`${baseUrl}/task/user/${userId}`);
            }
        } catch (error) {
            console.error("Erro ao adicionar tarefa:", error);
        }
    }
    

    // Evento para remover convidado
    tasksList.addEventListener("click", (event) => {
        if (event.target.classList.contains("btn-remove")) {
            const taskId = event.target.getAttribute("data-task-id");
            if (confirm("Deseja realmente excluir esta task?"))
                removeTask(taskId);
        }
    });

    // Função para remover convidado
    async function removeTask(taskId) {
        try {
            const response = await fetch(`${baseUrl}/task/${taskId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
                    
            });
    
            if (!response.ok) {
                throw new Error("Erro ao remover a task.");
            }
    
            getAPI(`${baseUrl}/task/user/${userId}`);
        } catch (error) {
            console.error("Erro ao remover a task:", error);
        }
    }

    document.getElementById("task-form").addEventListener("submit", function (event) {
        event.preventDefault();
    });

    document.getElementById("btn-create-task").addEventListener("click", addTask);

    // Obter o e-mail do fornecedor da URL
    const urlParams = new URLSearchParams(window.location.search);
    const supplierEmail = urlParams.get('supplierEmail');

    if (supplierEmail) {
        const emailField = document.getElementById("email_fornecedor");
        emailField.value = supplierEmail;
    }

    async function postAPI(url) {
        try {
            const response = await fetch(url, {
                method: "POST",
                header: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error("Erro ao enviar e-mail para fornecedor.");
            } else {
                alert("E-mail de compromisso enviado com sucesso!");
            }
        } catch (error) {
            alert(error);
        }
    }
});
