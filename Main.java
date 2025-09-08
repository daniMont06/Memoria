import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        System.out.print("Nombre J1: ");
        String n1 = sc.nextLine().trim();
        System.out.print("Nombre J2: ");
        String n2 = sc.nextLine().trim();
        if (n1.isEmpty()) n1 = "J1";
        if (n2.isEmpty()) n2 = "J2";

        Juego juego = new Juego(n1, n2);

        
        Integer filas = null, columnas = null;
        for (int intento = 0; intento < 3 && (filas == null || columnas == null); intento++) {
            try {
                System.out.print("Filas (2..10): ");
                filas = Integer.valueOf(Integer.parseInt(sc.nextLine().trim()));
                System.out.print("Columnas (2..10): ");
                columnas = Integer.valueOf(Integer.parseInt(sc.nextLine().trim()));
                juego.nuevaPartida(filas, columnas); // valida dentro
            } catch (Exception e) {
                System.out.println(e.getMessage() != null ? e.getMessage() : "Entrada inválida.");
                filas = null; columnas = null;
            }
        }
        if (filas == null || columnas == null) {
            System.out.println("Saliendo: no se pudo configurar el tablero.");
            sc.close();
            return;
        }

        
        int total = filas * columnas;
        int maxRondas = (total / 2) * 2;

        for (int ronda = 0; ronda < maxRondas && !juego.terminado(); ronda++) {
            // Mostrar tablero y marcador
            imprimirTablero(juego.getTablero(), filas, columnas);
            System.out.println(marcador(juego));
            System.out.println("Turno de: " + juego.getActual().getNombre());

            
            int[] p1 = pedirCoordenada(sc, "Casilla 1 (fila columna): ", 3);
            int[] p2 = pedirCoordenada(sc, "Casilla 2 (fila columna): ", 3);
            if (p1 == null || p2 == null) {
                System.out.println("Entradas inválidas. Fin de la partida.");
                break;
            }

            
            imprimirTableroPreview(juego.getTablero(), filas, columnas, p1[0], p1[1], p2[0], p2[1]);

            
            String mensaje = juego.jugarTurno(p1[0], p1[1], p2[0], p2[1]);
            System.out.println(mensaje);
        }

        
        imprimirTablero(juego.getTablero(), filas, columnas);
        System.out.println("¡Juego terminado!");
        System.out.println(marcador(juego));
        System.out.println(ganador(juego));
        sc.close();
    }

    

    // Pide "fila columna" 
    private static int[] pedirCoordenada(Scanner sc, String prompt, int reintentos) {
        int[] coord = null;
        for (int i = 0; i < reintentos && coord == null; i++) {
            System.out.print(prompt);
            String linea = sc.nextLine().trim();
            String[] partes = linea.split("\\s+");
            if (partes.length == 2) {
                try {
                    int f = Integer.parseInt(partes[0]);
                    int c = Integer.parseInt(partes[1]);
                    coord = new int[]{f, c};
                } catch (NumberFormatException e) {
                    System.out.println("Formato: <fila> <columna> (ej. 1 2).");
                }
            } else {
                System.out.println("Escribe dos enteros separados por espacio (ej. 1 2).");
            }
        }
        return coord; // null si fallaron los intentos
    }

    // Tablero normal: solo parejas emparejadas se ven
    private static void imprimirTablero(Tablero t, int filas, int columnas) {
        System.out.println();
        System.out.print("    ");
        for (int c = 0; c < columnas; c++) System.out.printf("%-3d", c);
        System.out.println();
        System.out.print("    ");
        for (int c = 0; c < columnas; c++) System.out.print("---");
        System.out.println();
        for (int f = 0; f < filas; f++) {
            System.out.printf("%-3d|", f);
            for (int c = 0; c < columnas; c++) {
                String celda = t.estaEmparejada(f, c) ? t.verSimbolo(f, c) : "■";
                System.out.printf("%-3s", celda);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Tablero "preview": muestra dos coordenadas destapadas temporalmente
    private static void imprimirTableroPreview(Tablero t, int filas, int columnas,
                                            int f1, int c1, int f2, int c2) {
        System.out.println();
        System.out.print("    ");
        for (int c = 0; c < columnas; c++) System.out.printf("%-3d", c);
        System.out.println();
        System.out.print("    ");
        for (int c = 0; c < columnas; c++) System.out.print("---");
        System.out.println();
        for (int f = 0; f < filas; f++) {
            System.out.printf("%-3d|", f);
            for (int c = 0; c < columnas; c++) {
                boolean esPick = (f == f1 && c == c1) || (f == f2 && c == c2);
                String celda = (t.estaEmparejada(f, c) || esPick) ? t.verSimbolo(f, c) : "■";
                System.out.printf("%-3s", celda);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String marcador(Juego juego) {
        Jugadores a = juego.getJ1();
        Jugadores b = juego.getJ2();
        return a.getNombre() + ": " + a.getPuntos() + " | "
            + b.getNombre() + ": " + b.getPuntos();
    }

    private static String ganador(Juego juego) {
        Jugadores a = juego.getJ1();
        Jugadores b = juego.getJ2();
        if (a.getPuntos() > b.getPuntos()) return "Ganador: " + a.getNombre();
        if (b.getPuntos() > a.getPuntos()) return "Ganador: " + b.getNombre();
        return "Empate.";
    }
}

