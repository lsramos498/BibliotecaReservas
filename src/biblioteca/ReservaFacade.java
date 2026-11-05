package biblioteca;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ReservaFacade {

    private final RepositorioSalas repositorioSalas;
    private final RepositorioReservas repositorioReservas;

    // Lista de observadores que recibirán las notificaciones
    private final List<ObservadorReserva> observadores;

    public ReservaFacade(
            RepositorioSalas repositorioSalas,
            RepositorioReservas repositorioReservas,
            List<ObservadorReserva> observadores) {

        this.repositorioSalas = repositorioSalas;
        this.repositorioReservas = repositorioReservas;
        this.observadores = List.copyOf(observadores);
    }

    public Optional<Reserva> realizarReserva(
            Estudiante estudiante,
            LocalDate fecha,
            LocalTime hora,
            int duracion,
            int personas,
            EstrategiaSeleccionSala estrategia) {

        // 1. Consultar las salas disponibles
        var disponibles = repositorioSalas.obtenerDisponibles();

        // 2. Seleccionar una sala mediante Strategy
        Optional<Sala> sala =
                estrategia.seleccionar(disponibles, personas);

        if (sala.isEmpty()) {
            System.out.println(
                    "No hay una sala disponible que cumpla la solicitud."
            );
            return Optional.empty();
        }

        // 3. Construir la reserva mediante Builder
        Reserva reserva = new ReservaBuilder()
                .estudiante(estudiante)
                .fecha(fecha)
                .horaInicio(hora)
                .duracionHoras(duracion)
                .cantidadPersonas(personas)
                .sala(sala.get())
                .build();

        // 4. Suscribir los observadores a la reserva
        observadores.forEach(reserva::agregarObservador);

        // 5. Registrar la reserva
        repositorioReservas.guardar(reserva);

        // 6. Confirmar la reserva mediante State
        reserva.confirmar();

        return Optional.of(reserva);
    }
}
