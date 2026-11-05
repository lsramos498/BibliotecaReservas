
package biblioteca;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Sala> disponibles = List.of(
                new Sala("A", "Sala A", 2),
                new Sala("B", "Sala B", 4),
                new Sala("C", "Sala C", 6),
                new Sala("D", "Sala D", 8)
        );

        EstrategiaSeleccionSala porCapacidad =
                new SeleccionPorCapacidad();

        System.out.println("Capacidad: " +
                porCapacidad.seleccionar(disponibles, 4)
                        .orElse(null));

        EstrategiaSeleccionSala preferida =
                new SeleccionSalaPreferida("C");

        System.out.println("Preferida: " +
                preferida.seleccionar(disponibles, 4)
                        .orElse(null));

        EstrategiaSeleccionSala preferidaInadecuada =
                new SeleccionSalaPreferida("B");

        System.out.println("Preferida inadecuada: " +
                preferidaInadecuada.seleccionar(disponibles, 5)
                        .orElse(null));
    }
}
