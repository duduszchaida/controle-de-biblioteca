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

@WebServlet("/livros/editar")
public class EditarLivroController extends HttpServlet {

    LivroDAO livroDAO = new LivroDAO();
    AutorDAO autorDAO = new AutorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Livro livro = null;
        try {
            livro = livroDAO.buscarPorId(id);
        } catch (SQLException e) {
            throw new ServletException("Erro ao buscar livro", e);
        }

        List<Autor> autores;
        try {
            autores = autorDAO.buscarTodos();
        } catch (Exception e) {
            throw new ServletException("Erro ao buscar autores", e);
        }
        req.setAttribute("autores", autores);
        req.setAttribute("livro", livro);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros-editar.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("campo_nome");
        LocalDate data = LocalDate.parse(req.getParameter("campo_data"));
        LivroStatus status = LivroStatus.valueOf(req.getParameter("campo_status"));
        String autorIdString = req.getParameter("campo_autor");
        String livroIdString = req.getParameter("campo_id");

        int autorId = Integer.parseInt(autorIdString);
        int livroId = Integer.parseInt(livroIdString);
        AutorDAO autorDAO = new AutorDAO();
        Autor autor = null;

        try {
            autor = autorDAO.buscarPorId(autorId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Livro livro = new Livro(livroId, nome, data, status, autor);

        try {
            livroDAO.atualizar(livro);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o livro", e);
        }
        resp.sendRedirect(req.getContextPath() + "/livros");
    }
}
