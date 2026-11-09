/**
Edwin Ulises Pedrero Chavez
20/09/2026
**/
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Clase que simula el funcionamiento de una cola de impresión compartida.
 * Utiliza un arreglo circular para representar la estructura FIFO.
 * El menú principal se muestra en modo gráfico (JOptionPane); el resto
 * del programa sigue mostrando sus mensajes en consola.
 */
public class ColaImpresion {

    // Atributos de la clase
    private String[] cola;
    private int frente;
    private int fin;
    private int cantidad;
    private final int CAPACIDAD;

    private static final String[] COMPUTADORAS = {"PC1", "PC2", "PC3", "PC4", "PC5"};

    /**
     * Constructor de la clase.
     * Inicializa el arreglo y define la capacidad máxima de la cola.
     * @param capacidad Número máximo de trabajos que la cola puede contener.
     */
    public ColaImpresion(int capacidad) {
        this.CAPACIDAD = capacidad;
        this.cola = new String[capacidad];
        this.frente = 0;
        this.fin = -1;
        this.cantidad = 0;
    }

    /**
     * Verifica si la cola alcanzó su capacidad máxima.
     */
    public boolean estaLlena() {
        return cantidad == CAPACIDAD;
    }

    /**
     * Verifica si la cola no tiene trabajos pendientes.
     */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /**
     * Selecciona al azar una PC (PC1 a PC5) y agrega su trabajo al final de la cola.
     * Si la cola está llena, muestra un mensaje de error en vez de agregar.
     */
    public void agregarTrabajo() {
        if (estaLlena()) {
            System.out.println("La cola de impresión está LLENA");
            return;
        }

        Random rand = new Random();
        String pc = COMPUTADORAS[rand.nextInt(COMPUTADORAS.length)];

        fin = (fin + 1) % CAPACIDAD;
        cola[fin] = pc;
        cantidad++;

        System.out.println(pc + " envió un trabajo a imprimir.");
        mostrarCola();
    }

    /**
     * Elimina el primer elemento de la cola (el más antiguo) y muestra
     * qué PC está siendo atendida. Si la cola está vacía, muestra un mensaje de error.
     */
    public void imprimirDocumento() {
        if (estaVacia()) {
            System.out.println("La cola de impresión está VACÍA");
            return;
        }

        String pc = cola[frente];
        frente = (frente + 1) % CAPACIDAD;
        cantidad--;

        System.out.println("Impresión " + pc);
    }

    /**
     * Muestra en consola todos los trabajos pendientes, en orden de atención.
     */
    public void mostrarCola() {
        if (estaVacia()) {
            System.out.println("La cola de impresión está VACÍA");
            return;
        }
        System.out.print("Cola actual: ");
        for (int i = 0; i < cantidad; i++) {
            int indice = (frente + i) % CAPACIDAD;
            System.out.print(cola[indice] + " ");
        }
        System.out.println();
    }

    /**
     * Método principal que ejecuta el menú gráfico para el usuario.
     */
    public static void main(String[] args) {
        ColaImpresion cola = new ColaImpresion(6);

        String[] opciones = {
            "Agregar trabajo",
            "Imprimir documento",
            "Mostrar cola",
            "Salir"
        };

        while (true) {
            // Menú gráfico: devuelve el índice del botón (o -1 si se cierra la ventana)
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Elige una opción:",
                    "MENÚ DE LA COLA DE IMPRESIÓN",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            switch (opcion) {
                case 0:
                    cola.agregarTrabajo();
                    break;
                case 1:
                    cola.imprimirDocumento();
                    break;
                case 2:
                    cola.mostrarCola();
                    break;
                case 3:
                case -1: // cerró la ventana
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
