package com.pbl;

import com.pbl.model.Ambiente;
import com.pbl.model.Experimento;
import com.pbl.model.ConexaoBD;
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

    /* Buscando todos os nomes de instâncias de uma classe: */
    public List<String> ambientesTodosNomes(){
        String sql = "EXEC sp_ambientesTodosNomes";
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
    
    public List<String> fontesTodosNomes(){
        String sql = "EXEC sp_fontesTodosNomes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<String> nomesFonte = new ArrayList<String>();
            while(resultSet.next()){
                nomesFonte.add(resultSet.getString("fonteNome"));
            }
            return nomesFonte;    
        }catch (Exception e){
            return null;
        }
    }
    
    public List<String> experimentosTodosNomes(){
        String sql = "EXEC sp_experimentoTodosNomes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<String> nomesExperimento = new ArrayList<String>();
            while(resultSet.next()){
                nomesExperimento.add(resultSet.getString("experimentoNome"));
            }
            return nomesExperimento;    
        }catch (Exception e){
            return null;
        }
    }
    
    /* Busca de um registro pelo nome, retornando a instância da classe: */
    public Ambiente buscarAmbientePorNome(String nome) {
        String sql = "EXEC sp_ambienteBuscarPorNome ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double velocidadeSom = resultSet.getDouble("velocidadeSom");
                return new Ambiente(nome, velocidadeSom);
            } else {
                // Caso não encontre o ambiente, retornar null ou lançar uma exceção apropriada
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Fonte buscarFontePorNome(String nome) {
        String sql = "EXEC sp_fonteBuscarPorNome ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double potencia = resultSet.getDouble("potencia");
                double frequencia = resultSet.getDouble("frequencia");
                String timbreNome = resultSet.getString("timbreNome");
                Timbre timbre = this.buscarTimbrePorNome(timbreNome);
                return new Fonte(nome, potencia, frequencia, timbre);
            } else {
                // Caso não encontre a fonte, retornar null ou lançar uma exceção apropriada
                System.out.println("Fonte não encontrada");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar fonte");
            return null;
        }
    }

    public Timbre buscarTimbrePorNome(String nome) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        String nomeDaClasse = "com.pbl.model." + nome;

        // Obtendo a classe usando reflection
        Class<?> clazz = Class.forName(nomeDaClasse);

        // Obtendo o construtor padrão (sem argumentos)
        Constructor<?> constructor = clazz.getConstructor();

        // Instanciando a classe
        return (Timbre) constructor.newInstance();
    }
    

    
    public Experimento buscarExperimentoPorNome(String nome){
        String sql = "EXEC sp_experimentoBuscarPorNome ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                
                String experimentoNome = resultSet.getString("experimentoNome");
                String fonteNome = resultSet.getString("fonteNome");
                String ambienteNome = resultSet.getString("ambienteNome");
                
                double velocidadeObservador = resultSet.getDouble("velocidadeObservador");
                double posicaoLateral = resultSet.getDouble("posicaoLateral");
                double velocidadeFonte = resultSet.getDouble("velocidadeFonte");
                double posicaoInicialFonte = resultSet.getDouble("posicaoInicialFonte");
                
                
                Fonte fonte = buscarFontePorNome(fonteNome);
                Ambiente ambiente = buscarAmbientePorNome(ambienteNome);
                return new Experimento(experimentoNome, posicaoInicialFonte, posicaoLateral, velocidadeFonte, velocidadeObservador, ambiente, fonte);
            } else {
                // Caso não encontre o ambiente, retornar null ou lançar uma exceção apropriada
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /* Adicionando um registro de uma tabela: */

    public void adicionaAmbiente(Ambiente ambiente) throws SQLException {
        String sql = "exec sp_adicionarAmbiente ?, ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            String nome = ambiente.getNome();
            Double velocidadeSom = ambiente.getVelocidadeSom();
            statement.setString(1, nome);
            statement.setDouble(2, velocidadeSom);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Log detailed error information
            System.err.println("Erro ao adicionar ambiente: " + e.getMessage());
            throw e;
        }
    }

    
    public void adicionaTimbre(Timbre timbre) throws SQLException{
        String sql = "exec sp_adicionarTimbre ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            
            String nome = timbre.getClass().getSimpleName(); 
            statement.setString(1, nome);
  
            statement.executeUpdate();
        }
    }
    
    public void adicionaFonte(Fonte fonte) throws SQLException{
        String sql = "exec sp_adicionarFonte ?, ?, ?, ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            String nome = fonte.getNome();
            Double frequencia = fonte.getFrequencia();
            Double potencia = fonte.getPotencia();
            String timbre = fonte.getTimbre().getClass().getSimpleName();
            
            statement.setString(1, nome);
            statement.setString(2, timbre);
            statement.setDouble(3, potencia);
            statement.setDouble(4, frequencia);


            statement.executeUpdate();
        }
    }
    
    public void adicionaExperimento(Experimento experimento) throws SQLException{
        String sql = "exec sp_adicionarExperimento ?, ?, ?, ?, ?, ?, ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            String nomeExperimento = experimento.getNome();
            Double velocidadeObservador = experimento.getVelocidadeObservador();
            Double posicaoLateral = experimento.getDistanciaLateral();
            Double velocidadeFonte = experimento.getVelocidadeFonte();
            Double posicaoInicialFonte = experimento.getPosicaoInicialFonte();
            
            String nomeAmbiente = experimento.getAmbiente().getNome();
            String nomeFonte = experimento.getFonte().getNome();
            
            statement.setString(1, nomeExperimento);
            statement.setDouble(2, velocidadeObservador);
            statement.setDouble(3, posicaoLateral);
            statement.setDouble(4, velocidadeFonte);
            statement.setDouble(5, posicaoInicialFonte);
            statement.setString(6, nomeAmbiente);
            statement.setString(7, nomeFonte);              
            
            statement.executeUpdate();
        }
    }
}
