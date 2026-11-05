package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<ObservadorReserva> observadores = List.of(
                new NotificadorEstudiante(),
                new RegistroEventos()
        );

        // PRUEBA 1: Confirmar una reserva pendiente
        System.out.println("=== PRUEBA 1: CONFIRMAR PENDIENTE ===");

        Reserva reserva1 = crearReserva("E001", "Laura");
        reserva1.agregarObservador(observadores.get(0));
        reserva1.agregarObservador(observadores.get(1));

        System.out.println("Estado inicial: " +
                reserva1.getNombreEstado());

        reserva1.confirmar();

        System.out.println("Estado final: " +
                reserva1.getNombreEstado());


        // PRUEBA 2: Cancelar una reserva pendiente
        System.out.println("\n=== PRUEBA 2: CANCELAR PENDIENTE ===");

        Reserva reserva2 = crearReserva("E002", "Andres");
        reserva2.agregarObservador(observadores.get(0));
        reserva2.agregarObservador(observadores.get(1));

        reserva2.cancelar();

        System.out.println("Estado final: " +
                reserva2.getNombreEstado());


        // PRUEBA 3: Cancelar una reserva confirmada
        System.out.println("\n=== PRUEBA 3: CANCELAR CONFIRMADA ===");

        Reserva reserva3 = crearReserva("E003", "Camila");
        reserva3.agregarObservador(observadores.get(0));
        reserva3.agregarObservador(observadores.get(1));

        reserva3.confirmar();
        reserva3.cancelar();

        System.out.println("Estado final: " +
                reserva3.getNombreEstado());


        // PRUEBA 4: Finalizar una reserva confirmada
        System.out.println("\n=== PRUEBA 4: FINALIZAR CONFIRMADA ===");

        Reserva reserva4 = crearReserva("E004", "Sofia");
        reserva4.agregarObservador(observadores.get(0));
        reserva4.agregarObservador(observadores.get(1));

        reserva4.confirmar();
        reserva4.finalizar();

        System.out.println("Estado final: " +
                reserva4.getNombreEstado());


        // PRUEBA 5: Intentar confirmar una reserva cancelada
        probarRechazo(
                "=== PRUEBA 5: CONFIRMAR CANCELADA ===",
                reserva2,
                reserva2::confirmar
        );


        // PRUEBA 6: Intentar cancelar una reserva finalizada
        probarRechazo(
                "=== PRUEBA 6: CANCELAR FINALIZADA ===",
                reserva4,
                reserva4::cancelar
        );
    }

    // Construir una reserva válida para las pruebas
    private static Reserva crearReserva(
            String identificador,
            String nombre) {

        return new ReservaBuilder()
                .estudiante(new Estudiante(identificador, nombre))
                .fecha(LocalDate.now().plusDays(7))
                .horaInicio(LocalTime.of(14, 0))
                .duracionHoras(2)
                .cantidadPersonas(4)
                .sala(new Sala("B", "Sala B", 4))
                .build();
    }

    // Comprobar que una operación no permitida sea rechazada
    private static void probarRechazo(
            String titulo,
            Reserva reserva,
            Runnable operacion) {

        System.out.println("\n" + titulo);

        try {
            operacion.run();

            System.out.println(
                    "ERROR: la operación debería haber sido rechazada."
            );

        } catch (IllegalStateException e) {

            System.out.println(
                    "Rechazo esperado: " + e.getMessage()
            );
        }

        System.out.println(
                "Estado conservado: " + reserva.getNombreEstado()
        );
    }
}
