document.addEventListener("DOMContentLoaded", async () => {
    const baseUrl = "http://localhost:8080";

    const token = localStorage.getItem("Authorization");
    const weddingId = localStorage.getItem("weddingId");

    const suppliersList = document.getElementById("supplier-list"); // Referência à tabela de fornecedores


    // Função para buscar o valor do orçamento da API
    async function getBudget() {
        try {
        const response = await fetch(`${baseUrl}/wedding/${weddingId}`, {
            method: "GET",
            headers: {
            "Content-Type": "application/json",
            "Authorization": token
            },
        });
    
        if (!response.ok) {
            throw new Error("Erro ao buscar o orçamento da API.");
        }
    
        const data = await response.json();
        const budgetValue = data.budget;
    
        return budgetValue;
        } catch (error) {
        console.error("Erro ao buscar o orçamento da API:", error);
        }
    }
    
    // Função para buscar dados da API
    async function getApiExpense(url, budgetValue) {
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
        let totalExpense = 0;
        for (let expense of data) {
            totalExpense += expense.price;
        }
    
        const textTotalExpense = document.getElementById("total-price");
        textTotalExpense.textContent = `Total de despesas: R$ ${totalExpense}`;

        const textBudget = document.getElementById("budget-title");
        textBudget.textContent = `Orçamento: R$ ${budgetValue}`;
        
        // Verificar se o total de despesas ultrapassa o orçamento
        if (totalExpense > budgetValue) {
            const textBudget = document.getElementById("budget-title");
            textBudget.style.color = "red";
            
            // Atualiza o campo budgetExceeded para true via requisição para a API
            await updateBudgetExceeded(true);
        } else {
            const textBudget = document.getElementById("budget-title");
            textBudget.style.color = "green";
        }      
    
        showExpenses(data);
        } catch (error) {
        console.error("Erro ao buscar dados da API:", error);
        }
    }
    
    // Chamar a função para buscar e exibir o orçamento
    const budgetValue = await getBudget();
    getApiExpense(`${baseUrl}/expense/wedding/${weddingId}`, budgetValue);
    
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
            
            show(data);
        } catch (error) {
            console.error("Erro ao buscar dados da API:", error);
        }
    }

    getAPI(`${baseUrl}/user/supplier/all`);

    // getAPI(`${baseUrl}/user/supplier/all`);

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
                            <button type="button" class="btn btn-primary btn-escolher" data-supplier-email="${supplier.email}">Escolher</button>    
                        </td>
                    </tr>`;

            tab += `</tbody>`;
        }

        document.getElementById("supplier-list").innerHTML = tab;

        suppliersList.addEventListener("click", (event) => {
            if (event.target.classList.contains("btn-escolher")) {
                const supplierEmail = event.target.getAttribute("data-supplier-email");
                console.log(supplierEmail);
                
                window.location = "agenda.html?supplierEmail=" + supplierEmail;
                
        }

    }

    )};


    // Função para criar elementos HTML
    async function showExpenses(expenses) {
        let tab = `<thead>
                        <th scope="col">#</th>
                        <th scope="col">Título</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Preço</th>
                    </thead>
                    <tbody>`;

        for (let expense of expenses) {
            tab += `<tr>
                        <td>${expense.id}</td>
                        <td>${expense.title}</td>
                        <td>${expense.description}</td>
                        <td>${expense.price}</td>
                    </tr>`;



            tab += `</tbody>`;
        }

        document.getElementById("expense-list").innerHTML = tab;
    }

    async function addExpense() {
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;
        const price = document.getElementById("price").value;

        const expenseData = {
            "title": title,
            "description": description,
            "price": price,
            "wedding": {
                "id": weddingId
            }
        };

        try {
            const response = await fetch(`${baseUrl}/expense`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(expenseData)
            });
    
            if (!response.ok) {
                alert("Erro ao adicionar despesa.");
                throw new Error("Erro ao adicionar despesa.");
            } else {
                alert("Despesa adicionada com sucesso!");
    
                // Limpar campos do formulário após o sucesso
                document.getElementById("title").value = "";
                document.getElementById("description").value = "";
                document.getElementById("price").value = "";
 
                // Atualizar a tabela após adicionar a tarefa
                getApiExpense(`${baseUrl}/expense/wedding/${weddingId}`, budgetValue);
            }
        } catch (error) {
            console.error("Erro ao adicionar tarefa:", error);
        }
    }
    

//     // Evento para remover convidado
//     // tasksList.addEventListener("click", (event) => {
//     //     if (event.target.classList.contains("btn-remove")) {
//     //         const taskId = event.target.getAttribute("data-task-id");
//     //         if (confirm("Deseja realmente excluir esta task?"))
//     //             removeTask(taskId);
//     //     }
//     // });

//     // Função para remover convidado
//     // async function removeTask(taskId) {
//     //     try {
//     //         const response = await fetch(`${baseUrl}/task/${taskId}`, {
//     //             method: "DELETE",
//     //             "Authorization": `Bearer ${token}`
//     //         });
    
//     //         if (!response.ok) {
//     //             throw new Error("Erro ao remover a task.");
//     //         }
    
//     //         // Atualizar a tabela após remover o presente
//     //         getAPI(`${baseUrl}/task/user/${userId}`);
//     //     } catch (error) {
//     //         console.error("Erro ao remover a task:", error);
//     //     }
//     // }

    async function updateBudgetExceeded(budgetExceededValue) {
        try {
            const response = await fetch(`${baseUrl}/wedding/${weddingId}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token
                },
                body: JSON.stringify({
                    budgetExceeded: budgetExceededValue
                })
            });

            if (!response.ok) {
                throw new Error("Erro ao atualizar o campo budgetExceeded.");
            }

            console.log("Campo budgetExceeded atualizado com sucesso.");
        } catch (error) {
            console.error("Erro ao atualizar o campo budgetExceeded:", error);
        }
    }

    document.getElementById("expense-form").addEventListener("submit", function (event) {
        event.preventDefault();
    });

    document.getElementById("btn-create-expense").addEventListener("click", () => {
        addExpense();
    });

});
