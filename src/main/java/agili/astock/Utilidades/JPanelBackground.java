package Utilidades;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelBackground extends JPanel {

    ImageIcon imagen = null;

    public JPanelBackground(String imagePath) {
        imagen = new ImageIcon(getClass().getResource(imagePath));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getBackground());
        int posX = getWidth() - imagen.getIconWidth();
        int posY = getHeight() - imagen.getIconHeight();
        g.fillRect(posX, posY, getWidth(), getHeight());
        if (imagen != null) {
            g.drawImage(imagen.getImage(), posX, posY, null);
        }
        Component c;
        for (int i = 0; i < getComponentCount(); i++) {
            c = getComponent(i);
            g.translate(c.getX(), c.getY());
            c.print(g);
            g.translate(-c.getX(), -c.getY());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension tamanio = getSize();
        Dimension panelDim = this.getSize();
        int posX = getWidth() - imagen.getIconWidth();
        int posY = getHeight() - imagen.getIconHeight();
        g.drawImage(imagen.getImage(), posX, posY, tamanio.width, tamanio.height, null);
        setOpaque(false);
        super.paintComponent(g);
    }
}
