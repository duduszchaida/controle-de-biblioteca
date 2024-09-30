package br.edu.ifpr.foz.ifprstore.controllers.Autor;

import br.edu.ifpr.foz.ifprstore.models.Autor;
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

@WebServlet(urlPatterns = {"/autores"})
public class AutorController extends HttpServlet {

    private LivroDAO livroDAO;
    private AutorDAO autorDAO;

    public AutorController() {
        livroDAO = new LivroDAO();
        autorDAO = new AutorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Autor> autores = autorDAO.buscarTodos();
            RequestDispatcher dispatcher = req.getRequestDispatcher("/autores.jsp");
            req.setAttribute("autores", autores);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erro ao buscar autores", e);
        }
    }}
