package Modelo;

import java.util.LinkedList;

public class Serpiente {
    private LinkedList<Posicion> segmentos;
    private Posicion direccion;

    public Serpiente(Posicion posicionInicial) {
        segmentos = new LinkedList<>();
        segmentos.add(posicionInicial);
        segmentos.add(new Posicion(posicionInicial.getX() - 1, posicionInicial.getY()));
        segmentos.add(new Posicion(posicionInicial.getX() - 2, posicionInicial.getY()));
        direccion = new Posicion(1, 0); // Dirección inicial hacia la derecha
    }

    public LinkedList<Posicion> getSegmentos() {
        return segmentos;
    }

    public Posicion getDireccion() {
        return direccion;
    }

    public void setDireccion(Posicion nuevaDireccion) {
        // Evita moverse en dirección opuesta
        if ((direccion.getX() + nuevaDireccion.getX() != 0) || (direccion.getY() + nuevaDireccion.getY() != 0)) {
            this.direccion = nuevaDireccion;
        }
    }

    public void mover() {
        Posicion cabeza = segmentos.getFirst();
        Posicion nuevaCabeza = new Posicion(cabeza.getX() + direccion.getX(), cabeza.getY() + direccion.getY());
        segmentos.addFirst(nuevaCabeza);
        segmentos.removeLast();
    }

    public void crecer() {
        Posicion nuevaCabeza = new Posicion(segmentos.getFirst().getX() + direccion.getX(), segmentos.getFirst().getY() + direccion.getY());
        segmentos.addFirst(nuevaCabeza);
    }

    public boolean haComido(Comida comida) {
        return segmentos.getFirst().equals(comida.getPosicion());
    }

    public boolean colisionaConLimites(int ancho, int alto) {
        Posicion cabeza = segmentos.getFirst();
        return cabeza.getX() < 0 || cabeza.getX() >= ancho || cabeza.getY() < 0 || cabeza.getY() >= alto;
    }

    public boolean colisionaConCuerpo() {
        Posicion cabeza = segmentos.getFirst();
        for (int i = 1; i < segmentos.size(); i++) {
            if (cabeza.equals(segmentos.get(i))) {
                return true;
            }
        }
        return false;
    }
}
