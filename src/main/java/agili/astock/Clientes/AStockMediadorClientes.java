/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Clientes;

import BD.Empresas.EmpresaCliente;
import BD.Personas.PersonaCliente;
import agili.astock.BD.AccesosBD;
import agili.astock.AStock;
import agili.astock.Utilidades.ThreadActualizador;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Mediador de la clase AStock
 *
 * @author Alvaro Gili
 */
public class AStockMediadorClientes implements ActionListener, MouseListener, ItemListener, KeyListener {

    AStock aStock = null;
    private ThreadActualizador ta;
    private ManejadorGrillaClientes grids = new ManejadorGrillaClientes();

    public AStockMediadorClientes(AStock aStock) {
        super();
        this.aStock = aStock;
        aStock.getjButtonNuevaPersona().addActionListener(this);
        aStock.getjMenuItemNuevaPersonaCliente().addActionListener(this);
        aStock.getEditarCliente().addActionListener(this);
        aStock.getjMenuItemEditarCliente().addActionListener(this);
        aStock.getBorrarCliente().addActionListener(this);
        aStock.getjMenuItemBorrarCliente().addActionListener(this);
        aStock.getjButtonNuevaEmpresa().addActionListener(this);
        aStock.getjMenuItemNuevaEmpresaCliente().addActionListener(this);
        aStock.getBuscarClienteNombrejTextField().addKeyListener(this);
        aStock.getBuscarClienteDNIjTextField().addKeyListener(this);
        aStock.getBuscarPrimeraParteCuit().addKeyListener(this);
        aStock.getBuscarSegundaParteCuit().addKeyListener(this);
        aStock.getBuscarTerceraParteCuit().addKeyListener(this);
        aStock.getLimpiarBusquedajButton().addActionListener(this);
        aStock.getClientesjTable().addMouseListener(this);
        aStock.getClientesjTable().addKeyListener(this);
        aStock.getBuscarClienteNombrejTextField().addMouseListener(this);
        aStock.getBuscarClienteDNIjTextField().addMouseListener(this);
        aStock.getBuscarPrimeraParteCuit().addMouseListener(this);
        aStock.getBuscarSegundaParteCuit().addMouseListener(this);
        aStock.getBuscarTerceraParteCuit().addMouseListener(this);
        ta = ThreadActualizador.getInstance();
    }

    ;

    @Override
    public void actionPerformed(ActionEvent e) {
        //nueva persona
        if (e.getSource().equals(aStock.getjButtonNuevaPersona())
                || e.getSource().equals(aStock.getjMenuItemNuevaPersonaCliente())) {
            //Hago visible la ventana de clientes
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Clientes"));
            AltaClientePersonaMediador acp = new AltaClientePersonaMediador();
            acp.show(aStock);
            if (!acp.wasCanceled()) {
                ta.actualizarClientes();
            }

        }
        if (e.getSource().equals(aStock.getjButtonNuevaEmpresa())
                || e.getSource().equals(aStock.getjMenuItemNuevaEmpresaCliente())) {
            //Hago visible la ventana de clientes
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Clientes"));
            AltaClienteEmpresaMediador acp = new AltaClienteEmpresaMediador();
            acp.show(aStock);

            if (!acp.wasCanceled()) {
                ta.actualizarClientes();
            }

        } else if (e.getSource().equals(aStock.getEditarCliente())
                || e.getSource().equals(aStock.getjMenuItemEditarCliente())) {//Editar cliente
            //Hago visible la ventana de clientes
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Clientes"));
            editarCliente();
        } else if (e.getSource().equals(aStock.getBorrarCliente())
                || e.getSource().equals(aStock.getjMenuItemBorrarCliente())) {//Borrar cliente
            //Hago visible la ventana de clientes
            aStock.getjTabbedPane1().setSelectedIndex(aStock.getjTabbedPane1().indexOfTab("Clientes"));
            borrarCliente();
        } else if (e.getSource().equals(aStock.getLimpiarBusquedajButton())) {//Limpiar busqueda

            //borra lo escrito y recarga la grilla

            aStock.getBuscarClienteNombrejTextField().setText("");
            grids.getClientesFiltradosPorNombre("", aStock.getClientesjTable());

            aStock.getBuscarClienteDNIjTextField().setText("");

            aStock.getBuscarClientePrimeraParteCuit().setText("");
            aStock.getBuscarClienteSegundaParteCuit().setText("");
            aStock.getBuscarClienteTerceraParteCuit().setText("");

        }
    }

    /**
     * Borrar un cliente
     */
    public void borrarCliente() {
        //obtengo el numero de fila de acuerdo al modelo ordenado
        Integer selectedRow = aStock.getClientesjTable().getSelectedRow();
        selectedRow = aStock.getClientesjTable().convertRowIndexToModel(selectedRow);
        String tipo = (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 11);

        String apellido = (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 0);

        String consulta = "¿Realmente desea eliminar a " + apellido + "?";

        if (JOptionPane.showConfirmDialog(aStock, consulta, "Borrar Cliente", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/img/svn_status.png"))) != JOptionPane.YES_OPTION) {
            return;
        }

        boolean borrado;
        if (tipo.equals("Persona")) {

            String verificacion = "dni==\'" + (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 1) + "\'";
            AccesosBD abd = AccesosBD.getInstance();
            PersonaCliente personaCliente = (PersonaCliente) abd.getObjeto(PersonaCliente.class,
                    verificacion);

            borrado = abd.borrarObjeto(personaCliente);

        } else {//Empresa
            String verificacion = "cuit==\'" + (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 1) + "\'";
            AccesosBD abd = AccesosBD.getInstance();
            EmpresaCliente personaCliente = (EmpresaCliente) abd.getObjeto(EmpresaCliente.class,
                    verificacion);

            borrado = abd.borrarObjeto(personaCliente);
        }
        if (borrado) {
            ta.actualizarClientes();

        } else {
            JOptionPane.showMessageDialog(aStock,
                    "El cliente no pudo ser borrado", "Error", JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/error.gif")));
        }
    }

    /**
     * Permite editar un cliente
     */
    public void editarCliente() {
        //indica si se cancelo la accion
        boolean canceled = false;
        //obtengo el numero de fila de acuerdo al modelo ordenado
        Integer selectedRow = aStock.getClientesjTable().getSelectedRow();
        selectedRow = aStock.getClientesjTable().convertRowIndexToModel(selectedRow);
        String tipo = (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 11);
        if (tipo.equals("Persona")) {
            AltaClientePersonaMediador acp = new AltaClientePersonaMediador();

            acp.setTitle("Modificar Cliente");
            String apellido = (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 0);
            String nombre = apellido.substring(apellido.indexOf(", ") + 2);
            apellido = apellido.substring(0, apellido.indexOf(", "));
            acp.setFields(nombre, apellido,
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 1),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 2),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 3),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 4),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 5),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 6),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 7),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 9),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 8),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 10));
            acp.show(aStock);
            canceled = acp.wasCanceled();
        } else {//Empresa
            AltaClienteEmpresaMediador acp = new AltaClienteEmpresaMediador();
            acp.setTitulo("Modificar Cliente");

            acp.setFields((String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 0),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 1),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 2),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 3),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 4),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 5),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 6),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 7),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 9),
                    (String) aStock.getClientesjTable().getModel().getValueAt(selectedRow, 8));


            acp.show(aStock);
            canceled = acp.wasCanceled();
        }

        if (!canceled) {
            ta.actualizarClientes();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(aStock.getClientesjTable())) {
            //es doble click
            if (e.getClickCount() == 2) {
                editarCliente();
            }
        } else if (e.getSource().equals(aStock.getBuscarClienteNombrejTextField())) {
            aStock.getBuscarClienteNombrejTextField().setText("");
            grids.getClientesFiltradosPorNombre("", aStock.getClientesjTable());
        } else if (e.getSource().equals(aStock.getBuscarClienteDNIjTextField())) {
            aStock.getBuscarClienteDNIjTextField().setText("");
            grids.getClientesFiltradosPorDNI("", aStock.getClientesjTable());
        } else if (e.getSource().equals(aStock.getBuscarClientePrimeraParteCuit())
                || e.getSource().equals(aStock.getBuscarClienteSegundaParteCuit())
                || e.getSource().equals(aStock.getBuscarClienteTerceraParteCuit())) {

            aStock.getBuscarClientePrimeraParteCuit().setText("");
            aStock.getBuscarClienteSegundaParteCuit().setText("");
            aStock.getBuscarClienteTerceraParteCuit().setText("");
            grids.getClientesFiltradosPorCUIT("", aStock.getClientesjTable());
            aStock.getBuscarClientePrimeraParteCuit().requestFocusInWindow();
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
            if (e.getSource().equals(aStock.getClientesjTable())) {
                editarCliente();
            }
        } else if (e.getKeyCode() == 127) {
            if (e.getSource().equals(aStock.getClientesjTable())) {
                borrarCliente();
            }
        }
    }

    public void buscarCliente() {
        DefaultTableModel clientesTableModel = (DefaultTableModel) aStock.getClientesjTable().getModel();
        //obtengo lo escrito
        String texto = aStock.getBuscarClienteNombrejTextField().getText();
        //recorro toda la grilla y borro los que no cumplen
        int rowCount = clientesTableModel.getRowCount();
        //usada para obtener la posición real en la grilla
        int rowOrder = 0;
        for (int i = rowCount - 1; i >= 0; i--) {
            rowOrder = aStock.getClientesjTable().convertRowIndexToModel(i);
            String nombre = (String) clientesTableModel.getValueAt(rowOrder, 0);
            if (!nombre.startsWith(texto)) {
                clientesTableModel.removeRow(rowOrder);
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(aStock.getBuscarClienteNombrejTextField())) {//busqueda por nombre
            ManejadorGrillaClientes grids = new ManejadorGrillaClientes();
            grids.getClientesFiltradosPorNombre(aStock.getBuscarClienteNombrejTextField().getText(),
                    aStock.getClientesjTable());

        } else if (e.getSource().equals(aStock.getBuscarClienteDNIjTextField())) {//busqueda por dni
            ManejadorGrillaClientes grids = new ManejadorGrillaClientes();
            grids.getClientesFiltradosPorDNI(aStock.getBuscarClienteDNIjTextField().getText(),
                    aStock.getClientesjTable());
        } else if (e.getSource().equals(aStock.getBuscarClientePrimeraParteCuit())
                || e.getSource().equals(aStock.getBuscarClienteSegundaParteCuit())
                || e.getSource().equals(aStock.getBuscarClienteTerceraParteCuit())) {//busqueda por cuit

            ManejadorGrillaClientes grids = new ManejadorGrillaClientes();
            String cuit = aStock.getBuscarClientePrimeraParteCuit().getText();
            if (!aStock.getBuscarClienteSegundaParteCuit().getText().isEmpty()) {
                cuit += "-" + aStock.getBuscarClienteSegundaParteCuit().getText();
            }
            if (!aStock.getBuscarClienteTerceraParteCuit().getText().isEmpty()) {
                cuit += "-" + aStock.getBuscarClienteTerceraParteCuit().getText();
            }

            grids.getClientesFiltradosPorCUIT(cuit, aStock.getClientesjTable());

        }
    }
}
