/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Proveedores;

import BD.Proveedores.Proveedor;
import agili.astock.AStock;
import agili.astock.BD.AccesosBD;
import agili.astock.Utilidades.ThreadActualizador;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Alvaro Gili
 */
public class AStockMediadorProveedor implements ActionListener, MouseListener, ItemListener, KeyListener {

    AStock aStock = null;
    private ThreadActualizador ta;
    private ManejadorGrillaProveedores grids = new ManejadorGrillaProveedores();

    // Constructor
    public AStockMediadorProveedor(AStock aStock) {
        super();
        this.aStock = aStock;
        aStock.getjButtonNuevoProveedor().addActionListener(this);
        aStock.getjMenuItemNewProv().addActionListener(this);
        aStock.getEditarProveedor().addActionListener(this);
        aStock.getBorrarProveedor().addActionListener(this);
        aStock.getjMenuItemEditProv().addActionListener(this);
        aStock.getjMenuItemBorrarProveedor().addActionListener(this);
        aStock.getBuscarProveedorNombrejTextField().addKeyListener(this);
        aStock.getBuscarProveedorPrimeraParteCuit().addKeyListener(this);
        aStock.getBuscarProveedorSegundaParteCuit().addKeyListener(this);
        aStock.getBuscarProveedorTerceraParteCuit().addKeyListener(this);
        aStock.getLimpiarBusquedaProveedorjButton().addActionListener(this);
        aStock.getProveedoresjTable().addMouseListener(this);
        aStock.getProveedoresjTable().addKeyListener(this);
        aStock.getBuscarProveedorNombrejTextField().addMouseListener(this);
        aStock.getBuscarProveedorPrimeraParteCuit().addMouseListener(this);
        aStock.getBuscarProveedorSegundaParteCuit().addMouseListener(this);
        aStock.getBuscarProveedorTerceraParteCuit().addMouseListener(this);
        ta = ThreadActualizador.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //nuevo proveedor
        if (e.getSource().equals(aStock.getjButtonNuevoProveedor())
                || e.getSource().equals(aStock.getjMenuItemNewProv())) {
            //Hago visible la ventana de proveedores
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Proveedores"));
            AltaProveedorMediador altaProveedor = new AltaProveedorMediador();
            altaProveedor.show(aStock);
            if (!altaProveedor.wasCanceled()) {

                ta.actualizarProveedores();
            }
        } else if (e.getSource().equals(aStock.getEditarProveedor())
                || e.getSource().equals(aStock.getjMenuItemEditProv())) {
            //editar proveedor
            //Hago visible la ventana de clientes
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Proveedores"));
            editarProveedor();
        } else if (e.getSource().equals(aStock.getLimpiarBusquedaProveedorjButton())) {//Limpiar busqueda

            //borra lo escrito y recarga la grilla
           
                aStock.getBuscarProveedorNombrejTextField().setText("");
                grids.getProveedoresFiltradosPorNombre("", aStock.getProveedoresjTable());
                           
                aStock.getBuscarProveedorPrimeraParteCuit().setText("");
                aStock.getBuscarProveedorSegundaParteCuit().setText("");
                aStock.getBuscarProveedorTerceraParteCuit().setText("");
                
        } else if (e.getSource().equals(aStock.getBorrarProveedor())
                || e.getSource().equals(aStock.getjMenuItemBorrarProveedor())) {//Borrar proveedor
            //Hago visible la ventana de proveedores
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Proveedores"));
            //borro
            borrarProveedor();
        }
    }

    /**
     * Permite editar un proveedor
     */
    public void editarProveedor() {
        //indica si se cancelo la accion
        boolean canceled = false;
        //obtengo el numero de fila de acuerdo al modelo ordenado
        Integer selectedRow = aStock.getProveedoresjTable().getSelectedRow();
        selectedRow = aStock.getProveedoresjTable().convertRowIndexToModel(selectedRow);

        AltaProveedorMediador altaProv = new AltaProveedorMediador();

        altaProv.setTitulo("Modificar Proveedor");

        altaProv.setFields((String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 0),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 1),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 2),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 3),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 4),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 5),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 6),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 7),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 8),
                (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 9));

        altaProv.show(aStock);
        canceled = altaProv.wasCanceled();

        if (!canceled) {
            ta.actualizarProveedores();
        }
    }

    /**
     * Borrar un proveedor
     */
    public void borrarProveedor() {
        //obtengo el numero de fila de acuerdo al modelo ordenado
        Integer selectedRow = aStock.getProveedoresjTable().getSelectedRow();
        selectedRow = aStock.getProveedoresjTable().convertRowIndexToModel(selectedRow);


        String nombre = (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 0);

        String consulta = "¿Realmente desea eliminar a " + nombre + "?";

        if (JOptionPane.showConfirmDialog(aStock, consulta, "Borrar Proveedor", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/img/svn_status.png"))) != JOptionPane.YES_OPTION) {
            return;
        }

        String verificacion = "cuit==\'" + (String) aStock.getProveedoresjTable().getModel().getValueAt(selectedRow, 1) + "\'";
        AccesosBD abd = AccesosBD.getInstance();
        Proveedor proveedor = (Proveedor) abd.getObjeto(Proveedor.class,
                verificacion);

        abd.borrarObjeto(proveedor);

        ta.actualizarProveedores();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(aStock.getProveedoresjTable())) {
            //es doble click
            if (e.getClickCount() == 2) {
                editarProveedor();
            }
        } else if (e.getSource().equals(aStock.getBuscarProveedorNombrejTextField())) {
            aStock.getBuscarProveedorNombrejTextField().setText("");
            grids.getProveedoresFiltradosPorNombre("", aStock.getProveedoresjTable());
        } else if (e.getSource().equals(aStock.getBuscarProveedorPrimeraParteCuit())
                || e.getSource().equals(aStock.getBuscarProveedorSegundaParteCuit())
                || e.getSource().equals(aStock.getBuscarProveedorTerceraParteCuit())) {

            aStock.getBuscarProveedorPrimeraParteCuit().setText("");
            aStock.getBuscarProveedorSegundaParteCuit().setText("");
            aStock.getBuscarProveedorTerceraParteCuit().setText("");
            grids.getProveedoresFiltradosPorCUIT("", aStock.getProveedoresjTable());
            aStock.getBuscarProveedorPrimeraParteCuit().requestFocusInWindow();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource().equals(aStock.getProveedoresjTable())) {
                editarProveedor();
            }
        } else if (e.getKeyCode() == 127) {
            if (e.getSource().equals(aStock.getProveedoresjTable())) {
                borrarProveedor();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(aStock.getBuscarProveedorNombrejTextField())) {//busqueda por nombre
            ManejadorGrillaProveedores grids = new ManejadorGrillaProveedores();
            grids.getProveedoresFiltradosPorNombre(aStock.getBuscarProveedorNombrejTextField().getText(),
                    aStock.getProveedoresjTable());

        } else if (e.getSource().equals(aStock.getBuscarProveedorPrimeraParteCuit())
                || e.getSource().equals(aStock.getBuscarProveedorSegundaParteCuit())
                || e.getSource().equals(aStock.getBuscarProveedorTerceraParteCuit())) {//busqueda por cuit

            ManejadorGrillaProveedores grids = new ManejadorGrillaProveedores();
            String cuit = aStock.getBuscarProveedorPrimeraParteCuit().getText();
            if (!aStock.getBuscarProveedorSegundaParteCuit().getText().isEmpty()) {
                cuit += "-" + aStock.getBuscarProveedorSegundaParteCuit().getText();
            }
            if (!aStock.getBuscarProveedorTerceraParteCuit().getText().isEmpty()) {
                cuit += "-" + aStock.getBuscarProveedorTerceraParteCuit().getText();
            }

            grids.getProveedoresFiltradosPorCUIT(cuit, aStock.getProveedoresjTable());

        }
    }
}
