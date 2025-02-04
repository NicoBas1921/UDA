package Vista;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;

public class VistaJuego extends JPanel {
    private static final int TAMANO_CELDA = 20;
    private int[][] segmentosSerpiente;
    private int[] comida;

    public VistaJuego(int ancho, int alto) {
        setPreferredSize(new Dimension(ancho * TAMANO_CELDA, alto * TAMANO_CELDA));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void agregarKeyListener(KeyListener listener) {
        addKeyListener(listener);
        requestFocusInWindow(); // Asegura que reciba eventos de teclado
    }

    public void actualizarSerpiente(int[][] segmentos) {
        this.segmentosSerpiente = segmentos;
        repaint();
    }

    public void actualizarComida(int x, int y) {
        this.comida = new int[]{x, y};
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        // Borra cualquier dibujo anterior y asegura que la pantalla se actualice correctamente.
        super.paintComponent(g);
        
        /*
            Primero se fija que la serpiente tenga datos con el !=NULL
            Le asigna el color Verde
            Con el for-each recorre la matriz de la de segmentos de las serpiente 
            Con el fill.Rect dibuja el rectangulo Verde en la posicion correcta siguiendo las cord X e Y
        */
        if (segmentosSerpiente != null) {
            g.setColor(Color.GREEN);
            for (int[] segmento : segmentosSerpiente) {
                g.fillRect(segmento[0] * TAMANO_CELDA, segmento[1] * TAMANO_CELDA, TAMANO_CELDA, TAMANO_CELDA);
            }
        }
        
        // Lo mismo que pasa con la serpiente con la comida
        if (comida != null) {
            g.setColor(Color.RED);
            g.fillRect(comida[0] * TAMANO_CELDA, comida[1] * TAMANO_CELDA, TAMANO_CELDA, TAMANO_CELDA);
        }
    }
}
