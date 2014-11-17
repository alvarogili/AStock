/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agili.astock.Productos;

import agili.astock.AStock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase mediador para los productos
 * @author Alvaro Gili
 */
public class AStockMediadorProductos implements ActionListener, MouseListener {

    private AStock localAStock;

    public AStockMediadorProductos(AStock aStock) {
        localAStock = aStock;
        localAStock.getjButtonNuevoProducto().addActionListener(this);
        localAStock.getjButtonEditarProducto().addActionListener(this);
        localAStock.getjButtonBorrarProducto().addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(localAStock.getjButtonNuevoProducto())){
            AltaProductoMediador altaProductoMediador = new AltaProductoMediador();
            altaProductoMediador.show(localAStock);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
