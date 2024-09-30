package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.models.Autor;
import br.edu.ifpr.foz.ifprstore.models.Livro;
import br.edu.ifpr.foz.ifprstore.models.LivroStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void salvar(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (nome, data, status, autor_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getNome());
            stmt.setDate(2, java.sql.Date.valueOf(livro.getData()));
            stmt.setString(3, livro.getStatus().name());
            stmt.setInt(4, livro.getAutor().getId());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE Livro SET nome = ?, data = ?, status = ?, autor_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getNome());
            stmt.setDate(2, Date.valueOf(livro.getData()));
            stmt.setString(3, livro.getStatus().name());

            if (livro.getAutor() != null) {
                stmt.setInt(4, livro.getAutor().getId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.setInt(5, livro.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Nenhum livro encontrado com o ID: " + livro.getId());
            }
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Livro WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public List<Livro> buscarPorAutor(int autorId) throws SQLException {
        String sql = "SELECT * FROM livro WHERE autor_id = ?";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDate("data").toLocalDate(),
                            LivroStatus.valueOf(rs.getString("status")),
                            new Autor(rs.getInt("autor_id"), null)
                    );
                    livros.add(livro);
                }
            }
        }
        return livros;
    }

    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livro WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int autorId = rs.getInt("autor_id");
                    Autor autor = buscarAutorPorId(autorId);

                    return new Livro(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getDate("data").toLocalDate(),
                            LivroStatus.valueOf(rs.getString("status")),
                            autor
                    );
                }
            }
        }
        return null;
    }
    public Autor buscarAutorPorId(int id) throws SQLException {
        String sql = "SELECT * FROM autor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Autor(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );
                }
            }
        }
        return null;
    }


    public List<Livro> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM livro";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int autorId = rs.getInt("autor_id");
                Autor autor = buscarAutorPorId(autorId);

                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDate("data").toLocalDate(),
                        LivroStatus.fromString(rs.getString("status")),
                        autor
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Livros encontrados: ");
        for (Livro livro : livros) {
            System.out.println(livro);
        }
        return livros;
    }

}
