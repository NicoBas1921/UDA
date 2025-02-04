package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaJuego extends JFrame {
    public VentanaJuego(VistaJuego vista, ActionListener accionReiniciar) {
        setTitle("Juego de la Serpiente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(vista, BorderLayout.CENTER);

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(accionReiniciar);
        add(btnReiniciar, BorderLayout.SOUTH);

        pack(); // Ajusta el tamaño de la ventana según sus componentes.
        setLocationRelativeTo(null); // Centra la ventana en la pantalla.
        setVisible(true);// Muestra la ventana en la pantalla.
    }
}
