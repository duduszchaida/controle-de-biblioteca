package br.edu.ifpr.foz.ifprstore.controllers.Livro;


import br.edu.ifpr.foz.ifprstore.models.Autor;
import br.edu.ifpr.foz.ifprstore.models.Livro;
import br.edu.ifpr.foz.ifprstore.models.LivroStatus;
import br.edu.ifpr.foz.ifprstore.repositories.AutorDAO;
import br.edu.ifpr.foz.ifprstore.repositories.LivroDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/livros/cadastrar")
public class CriarLivroController extends HttpServlet {

    LivroDAO livroDAO = new LivroDAO();
    AutorDAO autorDAO = new AutorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Autor> autores;
        try {
            autores = autorDAO.buscarTodos();
        } catch (Exception e) {
            throw new ServletException("Erro ao buscar autores", e);
        }
        req.setAttribute("autores", autores);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros-criar.jsp");
        dispatcher.forward(req, resp);

    }
       @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("campo_nome").trim();
        String dataString = req.getParameter("campo_data");
        String statusString = req.getParameter("campo_status");
        String autorIdString = req.getParameter("campo_autor");

        if (nome == null || nome.isEmpty()) {
            req.setAttribute("errorMessage", "O campo 'Nome' é obrigatório.");
            req.getRequestDispatcher("/livros/formulario.jsp").forward(req, resp);
            return;
        }

        LocalDate data;
        try {
            data = LocalDate.parse(dataString);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "O campo 'Data' está inválido.");
            req.getRequestDispatcher("/livros/formulario.jsp").forward(req, resp);
            return;
        }

        LivroStatus status;
        try {
            status = LivroStatus.valueOf(statusString);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", "O campo 'Status' está inválido.");
            req.getRequestDispatcher("/livros/formulario.jsp").forward(req, resp);
            return;
        }

        int autorId;
            autorId = Integer.parseInt(autorIdString);

           AutorDAO autorDAO = new AutorDAO();
           Autor autor = null;

           try {
               autor = autorDAO.buscarPorId(autorId);

               if (autor == null) {
                   req.setAttribute("errorMessage", "Autor não encontrado.");
                   req.getRequestDispatcher("/livros/criar").forward(req, resp);
                   return;
               }
           } catch (SQLException e) {
               req.setAttribute("errorMessage", "Erro ao buscar o autor.");
               req.getRequestDispatcher("/livros/criar").forward(req, resp);
               return;
           }

           Livro livro = new Livro(nome, data, status, autor);


           try {
            LivroDAO livroDAO = new LivroDAO();
            livroDAO.salvar(livro);
            resp.sendRedirect(req.getContextPath() + "/livros");
        } catch (SQLException e) {
            throw new ServletException("Erro ao cadastrar o livro", e);
        }
    }
}
