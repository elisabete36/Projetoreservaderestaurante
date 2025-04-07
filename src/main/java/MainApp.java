// MainApp.java
import java.util.Date;

public class MainApp {
    public static void main(String[] args) {
        ReservaControlador reservaControlador = new ReservaControlador();

        // Criar uma nova reserva
        reservaControlador.criarReserva(1, 1, new Date());
        //
        System.out.println("Reservas:");
        for (Reserva reserva : reservaControlador.listarReservas()) {
            System.out.println("ID: " + reserva.getId() + ", Usuário ID: " + reserva.getUsuarioId() +
                    ", Restaurante ID: " + reserva.getRestauranteId() +
                    ", Data: " + reserva.getDataReserva());
        }

        // Cancelar uma reserva
        reservaControlador.cancelarReserva(1);
    }
}
