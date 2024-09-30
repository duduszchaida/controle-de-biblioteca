package br.edu.ifpr.foz.ifprstore.controllers.Autor;


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

@WebServlet("/autores/editar")
public class EditarAutorController extends HttpServlet {

    LivroDAO livroDAO = new LivroDAO();
    AutorDAO autorDAO = new AutorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Autor autor = null;
        try {
            autor = autorDAO.buscarPorId(id);
        } catch (SQLException e) {
            throw new ServletException("Erro ao buscar autor", e);
        }

        req.setAttribute("autor", autor);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/autores-editar.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("campo_nome");
        String autorIdString = req.getParameter("campo_id");
        int autorId = Integer.parseInt(autorIdString);
        Autor autor = null;

        try {
            autor = autorDAO.buscarPorId(autorId);
            autor.setNome(nome);
            autorDAO.atualizar(autor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/autores");
    }
}
