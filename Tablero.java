public class Tablero {
    // Propiedades :3
    private int filas;
    private int columnas;
    private String [][] simbolos;        // estos son los emojis
    private boolean[][] revelada;     
    private boolean[][] emparejada;   // casillas ya emparejadas
    private int paresTotales;

    // Constructor vacío: se configura con crear los objetos
    public Tablero() {
        this.filas = 0;
        this.columnas = 0;
        this.simbolos = null;
        this.revelada = null;
        this.emparejada = null;
        this.paresTotales = 0;
    }

    // Configura el tablero con tamaño y matriz de símbolos ya barajada
    public void crear(int filas, int columnas, String [][] simbolos) {
        this.filas = filas;
        this.columnas = columnas;
        this.simbolos = simbolos;

        this.revelada = new boolean[filas][columnas];   // todo sale false por defecto
        this.emparejada = new boolean[filas][columnas]; 

        this.paresTotales = (filas * columnas) / 2;     // área par validada en Juego, porque si no es par no va a ser un cuadrado
        //Se podría decir que es mi primera validación
    }

    // Mira si la posición existe dentro del tablero
    public boolean dentroRango(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    // Muestra si la casilla esta oculta, si lo está es porque no han encontrado las parejas. 
    public boolean estaOculta(int fila, int columna) {
        return !emparejada[fila][columna];
    }

    // pregunta si ya está emparejada
    public boolean estaEmparejada(int fila, int columna) {
        return emparejada[fila][columna];
    }

    // Marca una casilla como emparejada y se queda visible el resto del juego
    public void marcarPareja(int fila, int columna) {
        emparejada[fila][columna] = true;
        revelada[fila][columna] = true;
    }

    // Devuelve el emoji de la casilla
    public String verSimbolo(int fila, int columna) {
        return simbolos[fila][columna];
    }

    // esto mira si todas las casillas están emparejadas
    public boolean completado() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!emparejada[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Vuelve a ocultar una casilla si no está emparejada 
    public void ocultar(int fila, int columna) {
        if (!emparejada[fila][columna]) {
            revelada[fila][columna] = false;
        }
    }
}
