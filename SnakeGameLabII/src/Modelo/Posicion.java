package Modelo;

public class Posicion {
    
    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) { 
        // Verifica si el objeto es igual a esta instancia comparando sus coordenadas X e Y
        // Nos sirve para verificar que la comida no se genere en el mismo lugar de la serpiente
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posicion posicion = (Posicion) obj;
        return x == posicion.x && y == posicion.y;
    }

    
    @Override
    public int hashCode() { 
        //Genera un código hash único basado en las coordenadas X e Y
        return 31 * x + y;
    }
    
    // MENSAJES DE DEPURACION CON COORDENADAS
    //    @Override
    //    public String toString() {
    //        return "(" + x + ", " + y + ")";
    //    }
}
