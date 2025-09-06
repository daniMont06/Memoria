public class Dinamica {
    // Cambies los emojis de char a string
    //Porque como buena UVgenia hice mi investigación y char aguanta poquitos emojis y no todos
    //Me di cuenta de eso cuando me empezó a fallar jajaja
    private String[] poolBase;

    public Dinamica() {
        this.poolBase = new String[] {
            // Me gustan los koreanos
            "🍜","🍲","🍚","🍙","🍘","🍢","🥟","🥢","🍗","🥬","🌶️","🧄","🧅","🫑","🥓","🍖","🍥","🧋","🍵","🍶",
            "🍡","🍱","🥠","🧆","🥘","🍤","🍣","🍱","🍛","🥚","🥗","🥦","🧀","🍞","🥖","🌰",
            "🐶","🐱","🐭","🐹","🐰","🦊","🐻","🐼","🐨","🐯","🦁","🐷","🐸","🐵","🐥","🐣","🐤",
            "🐮","🐴","🦄","🐧","🐢","🐙","🐠","🐳","🪿","🦆","🦉","🦥","🦔","🐞","🦋","🐝","🐰"
        };
    }

    public void setpoolbase(String[] nuevoPool) {
        if (nuevoPool != null && nuevoPool.length > 0) {
            this.poolBase = nuevoPool;
        }
    }

    // Este crea el arreglo de emojis de parejas
    public String[] generarduplicados(int filas, int columnas) {
        int total = filas * columnas;      // validación de pares la hace Juego, cada mico en su cajón :D
        int pares = total / 2;

        String[] duplicados = new String[total];
        for (int i = 0; i < pares; i++) {
            String simbolo = poolBase[i % poolBase.length]; // reutiliza si se acaban los emojis
            duplicados[2 * i]     = simbolo;
            duplicados[2 * i + 1] = simbolo;
        }
        return duplicados;
    }

    // Mezcla el arreglo y lo coloca en matriz que creamos (o sea el tablero)
    public String[][] bajarycolocar(int filas, int columnas, String[] duplicados) {
        for (int i = duplicados.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            String tmp = duplicados[i];
            duplicados[i] = duplicados[j];
            duplicados[j] = tmp; //tmp es temporal, lo vi en un video :D
        }

        // Pasar a tablero
        String[][] matriz = new String[filas][columnas];
        int k = 0;
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {
                matriz[r][c] = duplicados[k];
                k = k + 1;
            }
        }
        return matriz;
    }
}
