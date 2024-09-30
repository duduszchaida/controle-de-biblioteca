package br.edu.ifpr.foz.ifprstore.controllers.Livro;


import br.edu.ifpr.foz.ifprstore.repositories.LivroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/livros/excluir")
public class ExcluirLivroController extends HttpServlet {
    private LivroDAO livroDAO;

    @Override
    public void init() throws ServletException {
        livroDAO = new LivroDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String livroIdParametro = request.getParameter("id");

        if (livroIdParametro != null) {
            int livroId = Integer.parseInt(livroIdParametro);

            try {
                livroDAO.deletar(livroId);
                response.sendRedirect(request.getContextPath() + "/livros");
            } catch (Exception e) {
                throw new ServletException("Erro ao deletar o livro", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/livros");
        }
    }
}

