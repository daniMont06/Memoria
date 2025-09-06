public class Juego {
    private Tablero tablero;
    private Jugadores j1;
    private Jugadores j2;
    private boolean turno; // true = J1, false = J2

    public Juego(String nombreJ1, String nombreJ2) {
        this.j1 = new Jugadores(nombreJ1);
        this.j2 = new Jugadores(nombreJ2);
        this.tablero = new Tablero();
        this.turno = true; // inicia Jugador 1 (J1) 
    }

    // Inician y renuevan partidas usando try cathches. 
    public void nuevaPartida(int filas, int columnas) {
        try {
            if (filas < 2 || columnas < 2) {
                throw new IllegalArgumentException("El tablero mínimo es 2x2.");
            }
            if (filas > 10 || columnas > 10) {
                throw new IllegalArgumentException("El máximo permitido es 10x10.");
            }
            int total = filas * columnas;
            if ((total % 2) != 0) {
                throw new IllegalArgumentException("El número de casillas debe ser par para formar parejas.");
            }

            Dinamica dinamica = new Dinamica();
            String[] duplicados = dinamica.generarduplicados(filas, columnas);
            String[][] matriz = dinamica.bajarycolocar(filas, columnas, duplicados);

            // Este estabnlece a Tablero
            tablero.crear(filas, columnas, matriz);

            // Reiniciar puntuaciones y turno
            j1.reset();
            j2.reset();
            turno = true;
        } catch (IllegalArgumentException e) {
            //Lo re-tiramos al main para que el main decida qué quiere hacer con esto
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al iniciar la partida.", e);
        }
    }

    // esta parte del código se encanrga de los turnos, acá el jugador actual elige dos casillas
    public String jugarTurno(int f1, int c1, int f2, int c2) {
        try {
            // 1) Validaciones de rango
            if (!tablero.dentroRango(f1, c1) || !tablero.dentroRango(f2, c2)) {
                return "Coordenadas fuera de rango.";
            }
            // 2) Misma casilla dos veces
            if (f1 == f2 && c1 == c2) {
                return "No puedes seleccionar la misma casilla dos veces.";
            }
            //Acá ya emparejada
            if (tablero.estaEmparejada(f1, c1) || tablero.estaEmparejada(f2, c2)) {
                return "Alguna de las casillas ya está emparejada.";
            }

            // Comparar símbolos
            String s1 = tablero.verSimbolo(f1, c1);
            String s2 = tablero.verSimbolo(f2, c2);

            if (s1.equals(s2)) {
                // Match: marcar ambas y sumar punto; el jugador conserva turno
                tablero.marcarPareja(f1, c1);
                tablero.marcarPareja(f2, c2);
                getActual().SumarPuntos();
                // turno NO cambia
                if (terminado()) {
                    return "¡Pareja encontrada! Juego completado.";
                }
                return "¡Pareja encontrada! Conservas el turno.";
            } else {
                // No hay match: se ocultan y se alterna turno
                tablero.ocultar(f1, c1);
                tablero.ocultar(f2, c2);
                alternarTurno();
                return "No hubo match. Cambia el turno.";
            }
        } catch (IndexOutOfBoundsException e) {
            // Por si accidentalmente se accede mal 
            return "Acceso inválido al tablero.";
        } catch (Exception e) {
            // Cualquier imprevisto devuelve este mensaje
            return "Ocurrió un error durante el turno.";
        }
    }

    // Mira si el juego ya terminó
    public boolean terminado() {
        try {
            return tablero.completado();
        } catch (Exception e) {
            // No debería pasar, pero devolvemos false para no romper el flujo
            return false;
        }
    }

    // Alterna el boolean del turno
    public void alternarTurno() {
        turno = !turno;
    }

    // Devuelve el jugador actual 
    public Jugadores getActual() {
        return turno ? j1 : j2;
    }

    // Devuelve ambos jugadores
    public Jugadores getJ1() {
        return j1;
    }
    public Jugadores getJ2() {
        return j2;
    }

    public Tablero getTablero() {
    return tablero;
    }
}
