package br.edu.ifpr.foz.ifprstore.controllers.Autor;


import br.edu.ifpr.foz.ifprstore.models.Livro;
import br.edu.ifpr.foz.ifprstore.repositories.AutorDAO;
import br.edu.ifpr.foz.ifprstore.repositories.LivroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/autores/excluir")
public class ExcluirAutorController extends HttpServlet {

    private AutorDAO autorDAO;
    private LivroDAO livroDAO;

    @Override
    public void init() {
        autorDAO = new AutorDAO();
        livroDAO = new LivroDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int autorId = Integer.parseInt(req.getParameter("id"));

        try {
            List<Livro> livros = livroDAO.buscarPorAutor(autorId);
            if (!livros.isEmpty()) {
                for (Livro livro : livros) {
                    livroDAO.deletar(livro.getId());
                }
            }

            autorDAO.deletar(autorId);

            resp.sendRedirect(req.getContextPath() + "/autores");
        } catch (SQLException e) {
            throw new ServletException("Erro ao excluir o autor", e);
        }
    }
}

