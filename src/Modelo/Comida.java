package Modelo;

import java.util.LinkedList;
import java.util.Random;

public class Comida {
    private Posicion posicion;

    public Comida(int ancho, int alto) {
        generarNuevaPosicion(ancho, alto, new LinkedList<>());
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void generarNuevaPosicion(int ancho, int alto, LinkedList<Posicion> segmentosSerpiente) {
        Random random = new Random();
        boolean posicionValida;
        do {
            int x = random.nextInt(ancho);
            int y = random.nextInt(alto);
            posicion = new Posicion(x, y);
            posicionValida = true;
            //Aca compara la ubicacion con el "Equals"
            for (Posicion segmento : segmentosSerpiente) {
                if (posicion.equals(segmento)) {
                    posicionValida = false;
                    break;
                }
            }
        } while (!posicionValida);
    }
}
