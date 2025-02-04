package Modelo;

public class Tablero {
    private int ancho;
    private int alto;
    private Serpiente serpiente;
    private Comida comida;
    private boolean comioComida; // Variable para saber si comió comida

    public Tablero(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        serpiente = new Serpiente(new Posicion(ancho / 2, alto / 2));
        comida = new Comida(ancho, alto);
        comida.generarNuevaPosicion(ancho, alto, serpiente.getSegmentos());
    }

    public void actualizarEstado() {
        serpiente.mover();
        if (serpiente.getSegmentos().getFirst().equals(comida.getPosicion())) {
            serpiente.crecer();
            comida.generarNuevaPosicion(ancho, alto, serpiente.getSegmentos());
            comioComida = true; // Marcar que se comió comida
        } else {
            comioComida = false; // Si no comió, se mantiene falso
        }
    }

    public boolean juegoTerminado() {
        return serpiente.colisionaConLimites(ancho, alto) || serpiente.colisionaConCuerpo();
    }

    public boolean comioComida() {
        return comioComida;
    }

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public Comida getComida() {
        return comida;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
