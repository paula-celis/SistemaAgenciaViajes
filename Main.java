import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// ==========================================
// 1. CLASES, SUBCLASES Y RELACIONES
// ==========================================

// Clase principal 1
class Persona {
    protected String nombre;
    protected String identificacion;

    public Persona(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }
    public String getNombre() { return nombre; }
}

// Subclase que hereda de Persona (Cumple requerimiento de herencia)
class Viajero extends Persona {
    private String tipoViajero; // Ej: Frecuente, Ocasional

    public Viajero(String nombre, String identificacion, String tipoViajero) {
        super(nombre, identificacion); // Llama al constructor de Persona
        this.tipoViajero = tipoViajero;
    }
    public String getTipoViajero() { return tipoViajero; }
}

// Clase principal 2
class PaqueteTuristico {
    private String destino;
    private double precio;

    public PaqueteTuristico(String destino, double precio) {
        this.destino = destino;
        this.precio = precio;
    }
    public String getDestino() { return destino; }
    public double getPrecio() { return precio; }
}

// Clase principal 3: Implementa relación de agregación (Una Reserva contiene Viajero y Paquete)
class Reserva {
    private Viajero viajero;
    private PaqueteTuristico paquete;
    private int numeroPersonas;

    public Reserva(Viajero viajero, PaqueteTuristico paquete, int numeroPersonas) {
        this.viajero = viajero;
        this.paquete = paquete;
        this.numeroPersonas = numeroPersonas;
    }

    public Viajero getViajero() { return viajero; }
    public double getTotal() { return paquete.getPrecio() * numeroPersonas; }
    
    @Override
    public String toString() {
        return "Reserva de: " + viajero.getNombre() + " | Destino: " + paquete.getDestino() + " | Total: $" + getTotal();
    }
}

// ==========================================
// 2. PATRONES DE DISEÑO
// ==========================================

// Patrón Factory Method (Para creación flexible de objetos)
class PaqueteFactory {
    public static PaqueteTuristico crearPaquete(String tipo) {
        if (tipo.equalsIgnoreCase("Playa")) {
            return new PaqueteTuristico("San Andrés", 1200000.0);
        } else if (tipo.equalsIgnoreCase("Montaña")) {
            return new PaqueteTuristico("Eje Cafetero", 850000.0);
        } else {
            return new PaqueteTuristico("Tour de Ciudad", 300000.0);
        }
    }
}

// Patrón Singleton (Para administrar el sistema / base de datos simulada)
class AgenciaViajes {
    private static AgenciaViajes instancia;
    private List<Reserva> listaReservas;

    // Constructor privado para evitar que se creen múltiples agencias
    private AgenciaViajes() {
        listaReservas = new ArrayList<>();
    }

    // Método para obtener la única instancia de la Agencia
    public static AgenciaViajes getInstancia() {
        if (instancia == null) {
            instancia = new AgenciaViajes();
        }
        return instancia;
    }

    public void agregarReserva(Reserva reserva) {
        listaReservas.add(reserva);
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }
}

// ==========================================
// CLASE PRINCIPAL (MAIN)
// ==========================================
public class Main {
    public static void main(String[] args) {
        // Obtenemos el sistema usando Singleton
        AgenciaViajes agencia = AgenciaViajes.getInstancia();

        // Creamos viajeros
        Viajero v1 = new Viajero("Carlos Perez", "101", "Frecuente");
        Viajero v2 = new Viajero("Ana Gomez", "102", "Ocasional");
        Viajero v3 = new Viajero("Luis Suarez", "103", "Frecuente");

        // Creamos paquetes usando el patrón Factory
        PaqueteTuristico packPlaya = PaqueteFactory.crearPaquete("Playa");
        PaqueteTuristico packMontana = PaqueteFactory.crearPaquete("Montaña");

        // Agregamos reservas al sistema
        agencia.agregarReserva(new Reserva(v1, packPlaya, 2));
        agencia.agregarReserva(new Reserva(v2, packMontana, 1));
        agencia.agregarReserva(new Reserva(v3, packPlaya, 3));

        // ==========================================
        // 4. GESTIÓN DE ERRORES Y VALIDACIONES
        // ==========================================
        Scanner scanner = new Scanner(System.in);
        double filtroPrecio = 0;
        boolean datoValido = false;

        System.out.println("=== SISTEMA DE AGENCIA DE VIAJES ===");
        
        // Bucle para asegurar que el programa no se detenga abruptamente
        while (!datoValido) {
            try {
                System.out.print("Ingrese el presupuesto mínimo para filtrar reservas (Ej. 1000000): ");
                String entrada = scanner.nextLine();
                
                filtroPrecio = Double.parseDouble(entrada); // Intenta convertir texto a número
                
                if (filtroPrecio < 0) {
                    // Personalizar mensaje de error cuando los datos no sean válidos
                    throw new IllegalArgumentException("El presupuesto no puede ser negativo.");
                }
                
                datoValido = true; // Si todo sale bien, salimos del bucle
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido, sin letras ni símbolos especiales.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error de validación: " + e.getMessage());
            } finally {
                System.out.println("[Sistema] Validación de entrada finalizada.\n");
            }
        }

        // ==========================================
        // 3. PROGRAMACIÓN FUNCIONAL (STREAMS Y LAMBDAS)
        // ==========================================
        System.out.println("--- RESERVAS QUE SUPERAN EL PRESUPUESTO DE $" + filtroPrecio + " ---");
        
        List<Reserva> todasLasReservas = agencia.getListaReservas();
        final double precioMinimo = filtroPrecio;

        // Uso de Streams, filter, map, collect y forEach
        List<String> reporte = todasLasReservas.stream()
                .filter(r -> r.getTotal() > precioMinimo) // Filtramos por condición
                .map(r -> r.getViajero().getNombre() + " gastó $" + r.getTotal()) // Transformamos el objeto a String
                .collect(Collectors.toList()); // Lo guardamos en una nueva lista

        if (reporte.isEmpty()) {
            System.out.println("No se encontraron reservas que superen ese monto.");
        } else {
            // Imprimimos la lista resultante con forEach
            reporte.forEach(mensaje -> System.out.println("- " + mensaje));
        }
        
        System.out.println("\nFin de la ejecución.");
        scanner.close();
    }
}