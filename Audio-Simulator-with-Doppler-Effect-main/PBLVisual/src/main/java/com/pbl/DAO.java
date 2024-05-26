package com.pbl;

import com.pbl.model.Ambiente;
import com.pbl.model.Experimento;
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
    
    public Timbre buscarTimbrePorNome(String nome) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        String nomeDaClasse = nome;

        // Obtendo a classe usando reflection
        Class<?> clazz = Class.forName(nomeDaClasse);

        // Obtendo o construtor padrão (sem argumentos)
        Constructor<?> constructor = clazz.getConstructor();

        // Instanciando a classe
        return (Timbre) constructor.newInstance();

    }
    
    public Fonte buscarFontePorNome(String nome) {
        String sql = "EXEC sp_fonteBuscarPorNome ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double potencia = resultSet.getDouble("potencia");
                double frequencia = resultSet.getDouble("frequencia");
                String fonteNome = resultSet.getString("fonteNome");
                String timbreNome = resultSet.getString("timbreNome");
                Timbre timbre = buscarTimbrePorNome(timbreNome);
                return new Fonte(fonteNome, potencia, frequencia, timbre);
            } else {
                // Caso não encontre o ambiente, retornar null ou lançar uma exceção apropriada
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
                
                int taxaAmostragem = resultSet.getInt("taxaAmostragem");
                int tempoDuracao = resultSet.getInt("tempoDuracao");
                
                double velocidadeObservador = resultSet.getDouble("velocidadeObservador");
                double posicaoLateral = resultSet.getDouble("posicaoLateral");
                double posicaoInicialObservador = resultSet.getDouble("posicaoInicialObservador");
                double velocidadeFonte = resultSet.getDouble("velocidadeFonte");
                double posicaoInicialFonte = resultSet.getDouble("posicaoInicialFonte");
                
                
                Fonte fonte = buscarFontePorNome(fonteNome);
                Ambiente ambiente = buscarAmbientePorNome(ambienteNome);
                return new Experimento(experimentoNome, posicaoInicialFonte, posicaoLateral, velocidadeFonte, velocidadeObservador, tempoDuracao, taxaAmostragem, ambiente, fonte);
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
    
    public void adicionaAmbiente(Ambiente ambiente) throws SQLException{
        String sql = "exec sp_adicionarAmbiente ?, ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            String nome = ambiente.getNome();
            Double velocidadeSom = ambiente.getVelocidadeSom();
            statement.setString(1, nome);
            statement.setDouble(2, velocidadeSom);
            statement.executeUpdate();
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
            String fonteNome = fonte.getTimbre().getClass().getSimpleName();
            
            statement.setString(1, nome);
            statement.setDouble(2, frequencia);
            statement.setDouble(3, potencia);
            statement.setString(4, fonteNome);
            
            statement.executeUpdate();
        }
    }
    
    public void adicionaExperimento(Experimento experimento) throws SQLException{
        String sql = "exec sp_adicionarFonte ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            String nomeExperimento = experimento.getNome();
            Double velocidadeObservador = experimento.getVelocidadeObservador();
            Double posicaoLateral = experimento.getDistanciaLateral();
            Double posicaoInicialObservador = 0.0;
            Double velocidadeFonte = experimento.getVelocidadeFonte();
            Double posicaoInicialFonte = experimento.getPosicaoInicialFonte();
            double tempoDuracao = experimento.getTempoDuracao();
            int taxaAmostragem = experimento.getTaxaAmostragem();
            
            String nomeAmbiente = experimento.getAmbiente().getNome();
            String nomeFonte = experimento.getFonte().getNome();
            
            statement.setString(1, nomeExperimento);
            statement.setDouble(2, velocidadeObservador);
            statement.setDouble(3, posicaoLateral);
            statement.setDouble(4, posicaoInicialObservador);
            statement.setDouble(5, velocidadeFonte);
            statement.setDouble(6, posicaoInicialFonte);
            statement.setDouble(7, tempoDuracao);
            statement.setInt(8, taxaAmostragem);
            statement.setString(9, nomeAmbiente);
            statement.setString(10, nomeFonte);              
            
            statement.executeUpdate();
        }
    }
}
