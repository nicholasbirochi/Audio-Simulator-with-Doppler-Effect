package com.pbl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.pbl.model.Fonte;
import com.pbl.model.Timbre;

public class DAO {

    private final Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    public Timbre buscarTimbrePorId(int id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String sql = "SELECT timbreID, instrumentoNome FROM timbre WHERE timbreID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            String nomeDaClasse = resultSet.getString("instrumentoNome");

            // Obtendo a classe usando reflection
            Class<?> clazz = Class.forName(nomeDaClasse);

            // Obtendo o construtor padrão (sem argumentos)
            Constructor<?> constructor = clazz.getConstructor();

            // Instanciando a classe
            return (Timbre) constructor.newInstance();
            
        } catch(Exception e){
             return null;
        }
    }
    
    public int buscarTimbreIDPorNome(String nome) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String sql = "SELECT timbreID, instrumentoNome FROM timbre WHERE instrumentoNome = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("timbreID");
        } catch(Exception e){
             return -1;
        }
       
    }

    public void inserirFonte(int id, Timbre timbre, Double potencia, Double frequencia, String nome) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String sql = "INSERT into fonte(fonteID, timbreID, potencia, frequencia, fonteNome) values(?,?,?,?,?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, buscarTimbreIDPorNome(timbre.getClass().getSimpleName()));
            statement.setDouble(3, potencia);
            statement.setDouble(4, frequencia);
            statement.setString(5, nome);
            statement.executeUpdate();
        }
    }
    
    public Fonte buscarFontePorId(int id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String sql = "SELECT fonteID, timbreID, potencia, frequencia, fonteNome FROM fonte WHERE fonteID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            double potencia = resultSet.getDouble("potencia");
            double frequencia = resultSet.getDouble("frequencia");
            Timbre timbre = buscarTimbrePorId(resultSet.getInt("timbreID"));


            // Instanciando a classe
            return new Fonte(potencia, frequencia, timbre);
            
        } catch(Exception e){
             return null;
        }
    }
    public List<String> buscarNomesDeAmbientes()throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String sql = "SELECT ambienteNome FROM ambiente";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<String> nomesAmbiente = new ArrayList<String>();
            while(resultSet.next()){
                nomesAmbiente.add(resultSet.getString("ambienteNome"));
            }
            return nomesAmbiente;    
        }catch (Exception e){
            return null;
        }
    }
    /*
    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.executeUpdate();
        }
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
*/
}
