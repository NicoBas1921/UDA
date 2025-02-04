package Controlador;

import Modelo.Posicion;
import Modelo.Tablero;
import Vista.VistaJuego;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ControladorJuego implements KeyListener {
    private Tablero tablero;
    private VistaJuego vista;
    private boolean juegoEnCurso;
    private int velocidad;
    private int puntaje;
    private String nombreJugador;
    private static final String RANKING_FILE = "ranking.txt";

    public ControladorJuego(Tablero tablero, String nombreJugador, int velocidad, VistaJuego vista) {
        this.tablero = tablero;
        this.vista = vista;
        this.juegoEnCurso = true;
        this.velocidad = velocidad;
        this.puntaje = 0;
        this.nombreJugador = nombreJugador;
    }

    public void iniciarJuego() {
        new Thread(() -> {
            while (juegoEnCurso) {
                tablero.actualizarEstado();

                if (tablero.comioComida()) {
                    puntaje += 10;
                }

                actualizarVista();

                if (tablero.juegoTerminado()) {
                    juegoEnCurso = false;
                    guardarPuntaje();
                    mostrarRanking();
                    int opcion = JOptionPane.showConfirmDialog(null,
                        "¡Juego terminado! Tu puntaje fue: " + puntaje + "\n¿Quieres reiniciar?",
                        "Fin del Juego",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        reiniciarJuego();
                    }
                }

                try {
                    Thread.sleep(velocidad);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void reiniciarJuego() {
        String[] opciones = {"Fácil", "Normal", "Difícil"};
        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Selecciona la dificultad:",
            "Reiniciar Juego",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[1]
        );

        switch (seleccion) {
            case 0: velocidad = 200; break;
            case 1: velocidad = 150; break;
            case 2: velocidad = 100; break;
            default: velocidad = 150;
        }

        // Crear un nuevo tablero y reiniciar el estado del juego
        tablero = new Tablero(tablero.getAncho(), tablero.getAlto());
        puntaje = 0;
        juegoEnCurso = true;

        actualizarVista();
        iniciarJuego();
    }

    private void actualizarVista() {
        LinkedList<Posicion> segmentos = tablero.getSerpiente().getSegmentos();
        int[][] segmentosArray = new int[segmentos.size()][2];
        for (int i = 0; i < segmentos.size(); i++) {
            segmentosArray[i][0] = segmentos.get(i).getX();
            segmentosArray[i][1] = segmentos.get(i).getY();
        }

        int comidaX = tablero.getComida().getPosicion().getX();
        int comidaY = tablero.getComida().getPosicion().getY();

        vista.actualizarSerpiente(segmentosArray);
        vista.actualizarComida(comidaX, comidaY);
    }

    public void guardarPuntaje() {
        try (FileWriter fw = new FileWriter(RANKING_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(nombreJugador + " - " + puntaje + " puntos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarRanking() {
        List<String> ranking = new ArrayList<>();

        File archivoRanking = new File(RANKING_FILE);
        if (!archivoRanking.exists()) {
            JOptionPane.showMessageDialog(null, "No hay puntajes registrados aún.", "Ranking", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(RANKING_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                ranking.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!ranking.isEmpty()) {
            ranking.sort((a, b) -> {
                try {
                    int puntosA = Integer.parseInt(a.split(" - ")[1].replace(" puntos", "").trim());
                    int puntosB = Integer.parseInt(b.split(" - ")[1].replace(" puntos", "").trim());
                    return Integer.compare(puntosB, puntosA); // Orden descendente
                } catch (NumberFormatException e) {
                    return 0;
                }
            });

            StringBuilder mensaje = new StringBuilder("🏆 **Ranking de jugadores** 🏆\n");
            for (int i = 0; i < Math.min(5, ranking.size()); i++) { // Mostrar top 5
                mensaje.append(i + 1).append(". ").append(ranking.get(i)).append("\n");
            }

            JOptionPane.showMessageDialog(null, mensaje.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay puntajes registrados.", "Ranking", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Posicion direccionActual = tablero.getSerpiente().getDireccion();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direccionActual.getY() == 0) {
                    tablero.getSerpiente().setDireccion(new Posicion(0, -1));
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direccionActual.getY() == 0) {
                    tablero.getSerpiente().setDireccion(new Posicion(0, 1));
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direccionActual.getX() == 0) {
                    tablero.getSerpiente().setDireccion(new Posicion(-1, 0));
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direccionActual.getX() == 0) {
                    tablero.getSerpiente().setDireccion(new Posicion(1, 0));
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
