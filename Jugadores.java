public class Jugadores {
    // Mis lindas propiedades
    private String nombre;
    private int puntos;

    // Constructores :DDDD
    public Jugadores(String nombre) {
        this.nombre = nombre;
        this.puntos = 0; // inicia en 0
    }

    

    // Suma 1 punto al jugador
    public void SumarPuntos() {
        this.puntos = this.puntos + 1;
    }

    // Getter de puntos 
    public int getPuntos() {
        return this.puntos;
    }


    // Getter de nombre 
    public String getNombre() {
        return this.nombre;
    }


    // Reinicia el puntaje a 0 
    public void reset() {
        this.puntos = 0;
    }
}
