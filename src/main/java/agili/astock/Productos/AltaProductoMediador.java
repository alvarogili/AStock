package agili.astock.Productos;

import BD.Productos.Categoria;
import BD.Proveedores.Proveedor;
import agili.astock.BD.AccesosBD;
import agili.astock.Proveedores.AltaProveedorMediador;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Mediador para AltaProducto
 * @author Alvaro Gili
 */
public class AltaProductoMediador implements ActionListener, MouseListener, ItemListener, KeyListener {

    private AltaProducto altaProducto = new AltaProducto(null, true);
    private AccesosBD abd = AccesosBD.getInstance();

    public AltaProductoMediador() {

        altaProducto.getjComboBoxCategoria().addItemListener(this);
        altaProducto.getjComboBoxProveedor().addItemListener(this);

        Object categorias[] = null;
        try {
            categorias = abd.getObjetos(Categoria.class, null);
        } catch (Exception e) {
        }
        //hay categorias
        if (categorias != null && categorias.length > 0) {
            for (int i = 0; i < categorias.length; i++) {
                altaProducto.getjComboBoxCategoria().addItem(((Categoria) categorias[i]).appGetNombre());
            }
        }

        Object proveedores[] = null;
        try {
            proveedores = abd.getObjetos(Proveedor.class, null);
        } catch (Exception e) {
        }
        //hay proveedores
        if (proveedores != null && proveedores.length > 0) {
            for (int i = 0; i < proveedores.length; i++) {
                altaProducto.getjComboBoxProveedor().addItem(((Proveedor) proveedores[i]).appGetNombre());
            }
        }
        ResultSet resultSet = null;
        try {
            resultSet = abd.executeSQLSentence("select max(CODIGO) from producto");
        } catch (Exception e) {
        }
        int codigo = 0;
        try {
            resultSet.next();
            codigo = resultSet.getInt(1) + 1;
        } catch (SQLException ex) {
            Logger.getLogger(AltaProductoMediador.class.getName()).log(Level.SEVERE, null, ex);
        }
        altaProducto.getjTextFieldCodigo().setText(new Integer(codigo).toString());
    }

    public void show(Frame parent) {

        altaProducto.setLocationRelativeTo(parent);
        altaProducto.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(altaProducto.getjComboBoxCategoria())) {
            if (altaProducto.getjComboBoxCategoria().getSelectedItem() != null
                    && altaProducto.getjComboBoxCategoria().getSelectedItem().equals("Nueva categoria...")) {
                //agregar categoria
                String nuevaCat = JOptionPane.showInputDialog(altaProducto, "Escriba la nueva categoría", "Nueva categoría", JOptionPane.QUESTION_MESSAGE);
                if (nuevaCat != null) {
                    //veo si la categoria ya está en BD
                    if (abd.existeEnBD(Categoria.class, "nombre==\"" + nuevaCat + "\"")) {
                        JOptionPane.showMessageDialog(altaProducto, "La categoría ya existe.",
                                "Error", JOptionPane.ERROR_MESSAGE,
                                new ImageIcon(getClass().getResource("/img/error.gif")));
                    }else{
                        //no existe esta categoria
                        //la guardo en BD y la pongo como elegida
                        Categoria categoria = new Categoria();
                        categoria.appSetNombre(nuevaCat);
//                        no anda
                        abd.guardarEnBD(nuevaCat);
                        altaProducto.getjComboBoxCategoria().addItem(nuevaCat);
                        altaProducto.getjComboBoxCategoria().setSelectedItem(nuevaCat);
                    }
                }
            }
        } else if (e.getSource().equals(altaProducto.getjComboBoxProveedor())) {
            if (altaProducto.getjComboBoxProveedor().getSelectedItem() != null
                    && altaProducto.getjComboBoxProveedor().getSelectedItem().equals("Nuevo proveedor...")) {
                AltaProveedorMediador altaProveedor = new AltaProveedorMediador();
                altaProveedor.show(altaProducto);
                if (!altaProveedor.wasCanceled()) {
                    //agrego el nuevo proveedor
                    altaProducto.getjComboBoxProveedor().addItem(altaProveedor.getNombreProveedor());
                    //selecciono el cargado
                    altaProducto.getjComboBoxProveedor().setSelectedItem(altaProveedor.getNombreProveedor());
                } else {
                    altaProducto.getjComboBoxProveedor().setSelectedItem(null);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
