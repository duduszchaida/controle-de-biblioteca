<%@ page import="java.util.List" %>
<%@ page import="br.edu.ifpr.foz.ifprstore.DateUtils" %>
<%@ page import="br.edu.ifpr.foz.ifprstore.models.Livro" %>
<%@ page import="br.edu.ifpr.foz.ifprstore.models.Autor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<Autor> autores = (List<Autor>) request.getAttribute("autores"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administração de Livros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="css/dashboard.css">

</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg shadow-sm navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="livros"> <img src="images/logo.png" alt="">Biblioteca</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="livros">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="livros">Livros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="autores">Autores</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">


        <!-- Conteúdo principal -->
        <main class="col-12 px-md-4 main-content">

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Autores Cadastrados</h1>

                <a class="btn btn-info" href="<%= request.getContextPath() %>/autores/cadastrar">Cadastrar</a>
            </div>

            <!-- Tabela de livros -->
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Autor autor : autores) { %>
                    <tr>
                        <td><%= autor.getId() %></td>
                        <td><%= autor.getNome() %></td>
                        <td>
                            <a href="<%= request.getContextPath() %>/autores/excluir?id=<%= autor.getId() %>" class="btn btn-sm btn-danger">Excluir</a>
                            <a href="<%= request.getContextPath() %>/autores/editar?id=<%= autor.getId() %>" class="btn btn-sm btn-primary">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>

        </main>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>