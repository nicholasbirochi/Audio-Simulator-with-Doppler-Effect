package br.edu.cefsa.loja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.executeUpdate();
        }
    }

    public Timbre buscarPorId(int id) throws SQLException {
        String sql = "SELECT timbreID, instrumentoNome FROM timbre WHERE timbreID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, timbreID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Timbre(
                        resultSet.getInt("timbreID"),
                        resultSet.getString("instrumentoNome")
                );
            }
        }
        return null;
    }

    public List<Produto> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT id, nome, preco FROM produto WHERE nome LIKE ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nome + "%"); // Busca por nome contendo o termo
            ResultSet resultSet = statement.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (resultSet.next()) {
                produtos.add(new Produto(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getDouble("preco")
                ));
            }
            return produtos;
        }
    }

    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.setInt(3, produto.getId());
            statement.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, preco FROM produto";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (resultSet.next()) {
                produtos.add(new Produto(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getDouble("preco")
                ));
            }
            return produtos;
        }
    }
}
