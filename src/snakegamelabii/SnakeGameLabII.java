package snakegamelabii;

import Controlador.ControladorJuego;
import Vista.VentanaJuego;
import Vista.VistaJuego;
import Modelo.Tablero;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;

public class SnakeGameLabII {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int ancho = 30;
            int alto = 20;

            String nombreJugador = JOptionPane.showInputDialog("Ingrese su nombre:");

            if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
                System.exit(0);
            }

            String[] opciones = {"Fácil", "Normal", "Difícil"};
            int seleccion = JOptionPane.showOptionDialog(
                null,
                "Selecciona la dificultad:",
                "Configuración del Juego",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                null
            );

            int velocidad;
            switch (seleccion) {
                case 0: velocidad = 200; break;
                case 1: velocidad = 150; break;
                case 2: velocidad = 100; break;
                default: velocidad = 150;
            }

            Tablero tablero = new Tablero(ancho, alto);
            VistaJuego vista = new VistaJuego(ancho, alto);
            ControladorJuego controlador = new ControladorJuego(tablero, nombreJugador, velocidad, vista);

            // Asignar el KeyListener al controlador
            vista.agregarKeyListener(controlador);

            // Crear la ventana pasando la vista y la acción de reinicio
            VentanaJuego ventana = new VentanaJuego(vista, e -> controlador.reiniciarJuego());

            controlador.iniciarJuego();
        });
    }
}
