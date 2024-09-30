package br.edu.ifpr.foz.ifprstore.controllers.Livro;

import br.edu.ifpr.foz.ifprstore.models.Livro;
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
import java.util.List;

@WebServlet(urlPatterns = {"/livros"})
public class LivroController extends HttpServlet {

    private LivroDAO livroDAO;
    private AutorDAO autorDAO;

    public LivroController() {
        livroDAO = new LivroDAO();
        autorDAO = new AutorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Livro> livros = livroDAO.buscarTodos();
            RequestDispatcher dispatcher = req.getRequestDispatcher("/livros.jsp");
            req.setAttribute("livros", livros);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erro ao buscar livros", e);
        }
    }}
