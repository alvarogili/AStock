/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Proveedores;

import Herramientas.ValidadorCUIT;
import BD.Proveedores.Proveedor;
import Proveedores.AltaProveedor;
import agili.astock.BD.AccesosBD;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Clase mediador de la clase AltaClienteEmpresa
 * @author Alvaro Gili
 */
public class AltaProveedorMediador implements ActionListener, MouseListener, ItemListener {

    private AccesosBD abd = AccesosBD.getInstance();
    /**
     * Ventana
     */
    AltaProveedor altaProveedor = new AltaProveedor(null, true);

    public AltaProveedorMediador() {
        super();
        altaProveedor.getCodPostEmpresajTextField().addActionListener(this);
        altaProveedor.getCorreoEmpresajTextField().addActionListener(this);
        altaProveedor.getDireccionEmpresajTextField().addActionListener(this);
        altaProveedor.getFaxClientejTextField().addActionListener(this);
        altaProveedor.getjButton1().addActionListener(this);
        altaProveedor.getjButton2().addActionListener(this);
        altaProveedor.getLocalidadEmpresajTextField().addActionListener(this);
        altaProveedor.getNombreEmpresajTextField().addActionListener(this);
        altaProveedor.getPrimeraParteCuit().addActionListener(this);
        altaProveedor.getProvinciaComboBox().addActionListener(this);
        altaProveedor.getSegundaParteCuit().addActionListener(this);
        altaProveedor.getTelefono2EmpresajTextField().addActionListener(this);
        altaProveedor.getTelefonoEmpresajTextField().addActionListener(this);
        altaProveedor.getTerceraParteCuit().addActionListener(this);
    }

    /**
     * Muestra la ventana
     * @param parent Ventana padre
     */
    public void show(Frame parent) {
        altaProveedor.setLocationRelativeTo(parent);
        altaProveedor.setVisible(true);
    }

    /**
     * Muestra la ventana
     * @param parent Ventana padre
     */
    public void show(JDialog parent) {
        altaProveedor.setLocationRelativeTo(parent);
        altaProveedor.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(altaProveedor.getjButton1())) {
            guardar();
        }
    }

    /**
     * Retorna el nombre del proveedor
     * @return nombre de proveedor
     */
    public String getNombreProveedor(){
        return altaProveedor.getNombreEmpresajTextField().getText();
    }

    /**
     * Guarda el objeto
     */
    public void guardar() {
        String cuit = altaProveedor.getPrimeraParteCuit().getText() + "-";
        cuit += altaProveedor.getSegundaParteCuit().getText() + "-" + altaProveedor.getTerceraParteCuit().getText();

        if (altaProveedor.getNombreEmpresajTextField().getText().equals("") ||
                cuit.equals("")) {
            JOptionPane.showMessageDialog(altaProveedor,
                    "Los campos Nombre y CUIT son obligatorios", "Error", JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/error.gif")));
        } else {
            ValidadorCUIT validadorCUIT = new ValidadorCUIT();
            if (!validadorCUIT.verify(cuit)) {
                JOptionPane.showMessageDialog(altaProveedor,
                        "El CUIL / CUIT ingresado es erroneo", "Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/error.gif")));
                return;
            }

            if (!editando) {

                 String provincia;
                    if (altaProveedor.getProvinciaComboBox().getSelectedItem() == null) {
                        provincia = "";
                    } else {
                        provincia = altaProveedor.getProvinciaComboBox().getSelectedItem().toString();
                    }

                Proveedor p = new Proveedor(altaProveedor.getNombreEmpresajTextField().getText(),
                        cuit,
                        altaProveedor.getDireccionEmpresajTextField().getText(),
                        altaProveedor.getCodPostEmpresajTextField().getText(),
                        altaProveedor.getLocalidadEmpresajTextField().getText(),
                        provincia,
                        altaProveedor.getTelefonoEmpresajTextField().getText(),
                        altaProveedor.getTelefono2EmpresajTextField().getText(),
                        altaProveedor.getCorreoEmpresajTextField().getText(),
                        altaProveedor.getFaxClientejTextField().getText());

                if (abd.guardarEnBD(p)) {
                    JOptionPane.showMessageDialog(altaProveedor,
                            "El Proveedor ha sido guardado correctamente", "Confirmaciòn", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                    altaProveedor.dispose();
                } else {

                    JOptionPane.showMessageDialog(altaProveedor,
                            "El Proveedor ya existe o el CUIT fue ingresado incorrectamente", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            } else {//es una modificacion
                try {

                    String verificacion = "cuit==\'" + cuit + "\'";


                    Proveedor proveedor = (Proveedor) abd.getObjeto(Proveedor.class,
                            verificacion);

                    String provincia;
                    if (altaProveedor.getProvinciaComboBox().getSelectedItem() == null) {
                        provincia = "";
                    } else {
                        provincia = altaProveedor.getProvinciaComboBox().getSelectedItem().toString();
                    }

                    proveedor.appSetNombre(altaProveedor.getNombreEmpresajTextField().getText());
                    proveedor.appSetDireccion(altaProveedor.getDireccionEmpresajTextField().getText());
                    proveedor.appSetCodigoPostal(altaProveedor.getCodPostEmpresajTextField().getText());
                    proveedor.appSetLocalidad(altaProveedor.getLocalidadEmpresajTextField().getText());
                    proveedor.appSetProvincia(provincia);
                    proveedor.appSetTelefono(altaProveedor.getTelefonoEmpresajTextField().getText());
                    proveedor.appSetTelefono2(altaProveedor.getTelefono2EmpresajTextField().getText());
                    proveedor.appSetCorreo(altaProveedor.getCorreoEmpresajTextField().getText());
                    proveedor.appSetFax(altaProveedor.getFaxClientejTextField().getText());

                    abd.actualizarObjeto(Proveedor.class, proveedor, verificacion);

                    JOptionPane.showMessageDialog(altaProveedor,
                            "El Proveedor ha sido guardado correctamente", "Confirmaciòn", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                    altaProveedor.dispose();
                } catch (JDODataStoreException e) {
                    JOptionPane.showMessageDialog(altaProveedor,
                            "El Proveedor no pudo ser actualizada", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            }

        }
    }

    public void setTitulo(String titulo) {
        altaProveedor.setTitle(titulo);
    }

    /**
     * carga campos para edicion
     * @param nombre
     * @param dni
     * @param direccion
     * @param codPostal
     * @param localidad
     * @param provincia
     * @param telefono
     * @param telAlternativo
     * @param fax
     * @param eMail
     */
    public void setFields(
            String nombre,
            String dni, String direccion, String codPostal,
            String localidad, String provincia, String telefono,
            String telAlternativo, String fax, String eMail) {

        altaProveedor.getNombreEmpresajTextField().setText(nombre);
        String temp = dni.substring(0, dni.indexOf("-"));
        altaProveedor.getPrimeraParteCuit().setText(temp);
        dni = dni.substring(dni.indexOf("-") + 1);
        temp = dni.substring(0, dni.indexOf("-"));
        altaProveedor.getSegundaParteCuit().setText(temp);
        dni = dni.substring(dni.indexOf("-") + 1);
        altaProveedor.getTerceraParteCuit().setText(dni);
        altaProveedor.getPrimeraParteCuit().setEditable(false);
        altaProveedor.getSegundaParteCuit().setEditable(false);
        altaProveedor.getTerceraParteCuit().setEditable(false);
        altaProveedor.getDireccionEmpresajTextField().setText(direccion);
        altaProveedor.getCodPostEmpresajTextField().setText(codPostal);
        altaProveedor.getLocalidadEmpresajTextField().setText(localidad);
        altaProveedor.getProvinciaComboBox().setSelectedItem(provincia);
        altaProveedor.getTelefonoEmpresajTextField().setText(telefono);
        altaProveedor.getTelefono2EmpresajTextField().setText(telAlternativo);
        altaProveedor.getFaxClientejTextField().setText(fax);
        altaProveedor.getCorreoEmpresajTextField().setText(eMail);
        editando = true;
    }

    /**
     * Indica si se canceló la accion
     * @return true si se cancelo
     */
    public boolean wasCanceled() {
        return altaProveedor.wasCanceled();
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    boolean editando = false;

    public void setAltaProveedor(AltaProveedor altaProveedor) {
        this.altaProveedor = altaProveedor;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }
}
