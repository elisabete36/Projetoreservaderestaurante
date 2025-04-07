// controle/ReservaControlador.java
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaControlador {

    // Método para criar uma nova reserva
    public void criarReserva(int usuarioId, int restauranteId, Date dataReserva) {
        // Verifica se o usuário existe
        if (!usuarioExists(usuarioId)) {
            System.out.println("Usuário não encontrado.");
            return; // Sai do método se o usuário não existir
        }
        // Verifica se o restaurante existe
        if (!restauranteExists(restauranteId)) {
            System.out.println("Restaurante não encontrado.");
            return; // Sai do método se o restaurante não existir
        }

        // SQL para inserir uma nova reserva
        String sql = "INSERT INTO reservas (usuario_id, restaurante_id, data_reserva) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a instrução SQL
            // Define os parâmetros da consulta
            stmt.setInt(1, usuarioId); // Substitui o primeiro ? pelo ID do usuário
            stmt.setInt(2, restauranteId); // Substitui o segundo ? pelo ID do restaurante
            stmt.setTimestamp(3, new java.sql.Timestamp(dataReserva.getTime())); // Substitui o terceiro ? pela data da reserva
            stmt.executeUpdate(); // Executa a inserção no banco de dados
            System.out.println("Reserva criada com sucesso."); // Mensagem de sucesso
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime a pilha de erros em caso de exceção
        }
    }

    // Método para listar todas as reservas
    public List<Reserva> listarReservas() {
        List<Reserva> reservas = new ArrayList<>(); // Lista para armazenar as reservas
        String sql = "SELECT * FROM reservas"; // SQL para selecionar todas as reservas
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql); // Prepara a instrução SQL
             ResultSet rs = stmt.executeQuery()) { // Executa a consulta e obtém os resultados
            while (rs.next()) { // Itera sobre os resultados
                int id = rs.getInt("id"); // Obtém o ID da reserva
                int usuarioId = rs.getInt("usuario_id"); // Obtém o ID do usuário
                int restauranteId = rs.getInt("restaurante_id"); // Obtém o ID do restaurante
                Date dataReserva = rs.getTimestamp("data_reserva"); // Obtém a data da reserva
                // Adiciona a nova reserva à lista
                reservas.add(new Reserva(id, usuarioId, restauranteId, dataReserva));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime a pilha de erros em caso de exceção
        }
        return reservas; // Retorna a lista de reservas
    }

    // Método para cancelar uma reserva
    public void cancelarReserva(int reservaId) {
        String sql = "DELETE FROM reservas WHERE id = ?"; // SQL para deletar uma reserva
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a instrução SQL
            stmt.setInt(1, reservaId); // Substitui o ? pelo ID da reserva
            int rowsAffected = stmt.executeUpdate(); // Executa a deleção e obtém o número de linhas afetadas
            if (rowsAffected > 0) {
                System.out.println("Reserva cancelada com sucesso."); // Mensagem de sucesso
            } else {
                System.out.println("Reserva não encontrada."); // Mensagem se a reserva não existir
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime a pilha de erros em caso de exceção
        }
    }

    // Método para listar reservas de um usuário específico
    public List<Reserva> listarReservasPorUsuario(int usuarioId) {
        List<Reserva> reservas = new ArrayList<>(); // Lista para armazenar as reservas
        String sql = "SELECT * FROM reservas WHERE usuario_id = ?"; // SQL para selecionar reservas de um usuário específico
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
