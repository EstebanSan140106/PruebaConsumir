import com.esteban.webservice.services.Notas;
import com.esteban.webservice.services.ServiciosWs;
import com.esteban.webservice.services.ServiciosWsImplementService;
import com.esteban.webservice.services.Estudiante;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiciosWs service = new ServiciosWsImplementService().getServiciosWsImplementPort();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE ESTUDIANTES ===");
            System.out.println("1. Registrar nuevo estudiante");
            System.out.println("2. Registrar notas de estudiante");
            System.out.println("3. Listar todos los estudiantes");
            System.out.println("4. Consultar estudiante por ID");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            try {
                switch (opcion) {
                    case 1:
                        registrarEstudiante(service, scanner);
                        break;
                    case 2:
                        registrarNotas(service, scanner);
                        break;
                    case 3:
                        listarEstudiantes(service);
                        break;
                    case 4:
                        consultarEstudiante(service, scanner);
                        break;
                    case 5:
                        System.out.println("Saliendo del sistema...");
                        System.exit(0);
                    default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void registrarEstudiante(ServiciosWs service, Scanner scanner) {
        System.out.println("\n--- REGISTRAR NUEVO ESTUDIANTE ---");

        Estudiante estudiante = new Estudiante();
        System.out.print("Nombre: ");
        estudiante.setNombre(scanner.nextLine());
        System.out.print("Apellido: ");
        estudiante.setApellido(scanner.nextLine());
        System.out.print("Email: ");
        estudiante.setEmail(scanner.nextLine());
        System.out.print("Dirección: ");
        estudiante.setDireccion(scanner.nextLine());
        System.out.print("Teléfono: ");
        estudiante.setTelefono(scanner.nextLine());

        service.registrarEstudiante(estudiante);
        System.out.println("Estudiante registrado exitosamente!");
    }

    private static void registrarNotas(ServiciosWs service, Scanner scanner) {
        System.out.println("\n--- REGISTRAR NOTAS DE ESTUDIANTE ---");
        System.out.print("ID del estudiante: ");
        long idEstudiante = scanner.nextLong();

        Notas notas = new Notas();
        notas.setIdEstudiante(idEstudiante);

        System.out.print("Nota 1 (22%): ");
        notas.setNota1(scanner.nextDouble());
        System.out.print("Nota 2 (22%): ");
        notas.setNota2(scanner.nextDouble());
        System.out.print("Nota 3 (22%): ");
        notas.setNota3(scanner.nextDouble());
        System.out.print("Examen (34%): ");
        notas.setExamen(scanner.nextDouble());

        service.calcularNota(notas);

        Notas resultado = service.obtenerNotasPorEstudiante(idEstudiante);
        System.out.println("\nResultados:");
        System.out.println("Promedio: " + resultado.getPromedio());
        System.out.println("Estado: " + resultado.getEstado());
    }

    private static void listarEstudiantes(ServiciosWs service) {
        System.out.println("\n--- LISTA DE ESTUDIANTES ---");
        service.listarEstudiantes().forEach(e -> {
            System.out.println("\nID: " + e.getId());
            System.out.println("Nombre: " + e.getNombre() + " " + e.getApellido());
            System.out.println("Email: " + e.getEmail());
            System.out.println("Teléfono: " + e.getTelefono());
        });
    }

    private static void consultarEstudiante(ServiciosWs service, Scanner scanner) {
        System.out.println("\n--- CONSULTAR ESTUDIANTE ---");
        System.out.print("ID del estudiante: ");
        long id = scanner.nextLong();

        Estudiante e = service.buscarEstudiantePorId(id);
        if (e == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.println("\nDatos del estudiante:");
        System.out.println("ID: " + e.getId());
        System.out.println("Nombre: " + e.getNombre() + " " + e.getApellido());
        System.out.println("Email: " + e.getEmail());
        System.out.println("Dirección: " + e.getDireccion());
        System.out.println("Teléfono: " + e.getTelefono());

        Notas notas = service.obtenerNotasPorEstudiante(id);
        if (notas != null) {
            System.out.println("\nNotas:");
            System.out.println("Nota 1: " + notas.getNota1());
            System.out.println("Nota 2: " + notas.getNota2());
            System.out.println("Nota 3: " + notas.getNota3());
            System.out.println("Examen: " + notas.getExamen());
            System.out.println("Promedio: " + notas.getPromedio());
            System.out.println("Estado: " + notas.getEstado());
        } else {
            System.out.println("El estudiante no tiene notas registradas.");
        }
    }
}
