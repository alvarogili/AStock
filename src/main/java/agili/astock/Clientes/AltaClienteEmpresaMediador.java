/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Clientes;


import BD.Empresas.EmpresaCliente;
import Clientes.AltaClienteEmpresa;
import Herramientas.ValidadorCUIT;
import agili.astock.BD.AccesosBD;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Clase mediador de la clase AltaClienteEmpresa
 * @author Alvaro Gili
 */
public class AltaClienteEmpresaMediador implements ActionListener, MouseListener, ItemListener, KeyListener {

    /**
     * Ventana
     */
    AltaClienteEmpresa ace = new AltaClienteEmpresa(null, true);

    public AltaClienteEmpresaMediador() {
        super();
        ace.getCodPostEmpresajTextField().addActionListener(this);
        ace.getCorreoEmpresajTextField().addActionListener(this);
        ace.getDireccionEmpresajTextField().addActionListener(this);
        ace.getFaxClientejTextField().addActionListener(this);
        ace.getjButton1().addActionListener(this);
        ace.getjButton2().addActionListener(this);
        ace.getLocalidadEmpresajTextField().addActionListener(this);
        ace.getNombreEmpresajTextField().addActionListener(this);
        ace.getPrimeraParteCuit().addActionListener(this);
        ace.getProvinciaComboBox().addActionListener(this);
        ace.getSegundaParteCuit().addActionListener(this);
        ace.getTelefono2EmpresajTextField().addActionListener(this);
        ace.getTelefonoEmpresajTextField().addActionListener(this);
        ace.getTerceraParteCuit().addActionListener(this);
    }

    /**
     * Muestra la ventana
     * @param parent Ventana padre
     */
    public void show(Frame parent) {
        ace.setLocationRelativeTo(parent);
        ace.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ace.getjButton1())) {
            guardar();
        }
    }

    /**
     * Guarda el objeto
     */
    public void guardar() {
        String cuit = ace.getPrimeraParteCuit().getText() + "-";
        cuit += ace.getSegundaParteCuit().getText() + "-" + ace.getTerceraParteCuit().getText();

        if (ace.getNombreEmpresajTextField().getText().equals("")
                || cuit.equals("")) {
            JOptionPane.showMessageDialog(ace,
                    "Los campos Nombre y CUIT son obligatorios", "Error", JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/error.gif")));
        } else {
            ValidadorCUIT validadorCUIT = new ValidadorCUIT();
            if (!validadorCUIT.verify(cuit)) {
                JOptionPane.showMessageDialog(ace,
                        "El CUIL / CUIT ingresado es erroneo", "Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/error.gif")));
                return;
            }

            if (!editando) {

                String provincia;
                if (ace.getProvinciaComboBox().getSelectedItem() == null) {
                    provincia = "";
                } else {
                    provincia = ace.getProvinciaComboBox().getSelectedItem().toString();
                }

                EmpresaCliente p = new EmpresaCliente(ace.getNombreEmpresajTextField().getText(),
                        cuit,
                        ace.getDireccionEmpresajTextField().getText(),
                        ace.getCodPostEmpresajTextField().getText(),
                        ace.getLocalidadEmpresajTextField().getText(),
                        provincia,
                        ace.getTelefonoEmpresajTextField().getText(),
                        ace.getTelefono2EmpresajTextField().getText(),
                        ace.getCorreoEmpresajTextField().getText(),
                        ace.getFaxClientejTextField().getText());



                if (!abd.guardarEnBD(p)) {
                    JOptionPane.showMessageDialog(ace,
                            "El Cliente ya existe o el DNI / CUIT fue ingresado incorrectamente", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                } else {

                    JOptionPane.showMessageDialog(ace,
                            "El Cliente ha sido guardado correctamente", "Confirmaciòn",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                    ace.dispose();
                }

            } else {//es una modificacion
                try {

                    String verificacion = "cuit==\'" + cuit + "\'";

                    EmpresaCliente empresaCliente = (EmpresaCliente) abd.getObjeto(EmpresaCliente.class,
                            verificacion);

                    String provincia;
                    if (ace.getProvinciaComboBox().getSelectedItem() == null) {
                        provincia = "";
                    } else {
                        provincia = ace.getProvinciaComboBox().getSelectedItem().toString();
                    }

                    empresaCliente.appSetNombre(ace.getNombreEmpresajTextField().getText());
                    empresaCliente.appSetDireccion(ace.getDireccionEmpresajTextField().getText());
                    empresaCliente.appSetCodigoPostal(ace.getCodPostEmpresajTextField().getText());
                    empresaCliente.appSetLocalidad(ace.getLocalidadEmpresajTextField().getText());
                    empresaCliente.appSetProvincia(provincia);
                    empresaCliente.appSetTelefono(ace.getTelefonoEmpresajTextField().getText());
                    empresaCliente.appSetTelefono2(ace.getTelefono2EmpresajTextField().getText());
                    empresaCliente.appSetCorreo(ace.getCorreoEmpresajTextField().getText());
                    empresaCliente.appSetFax(ace.getFaxClientejTextField().getText());

                    abd.actualizarObjeto(EmpresaCliente.class, empresaCliente, verificacion);

                    JOptionPane.showMessageDialog(ace,
                            "La Empresa ha sido guardada correctamente", "Confirmaciòn", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                    ace.dispose();
                } catch (JDODataStoreException e) {
                    JOptionPane.showMessageDialog(ace,
                            "La empresa no pudo ser actualizada", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            }

        }
    }

    public void setTitulo(String titulo) {
        ace.setTitle(titulo);
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

        ace.getNombreEmpresajTextField().setText(nombre);
        String temp = dni.substring(0, dni.indexOf("-"));
        ace.getPrimeraParteCuit().setText(temp);
        dni = dni.substring(dni.indexOf("-") + 1);
        temp = dni.substring(0, dni.indexOf("-"));
        ace.getSegundaParteCuit().setText(temp);
        dni = dni.substring(dni.indexOf("-") + 1);
        ace.getTerceraParteCuit().setText(dni);
        ace.getPrimeraParteCuit().setEditable(false);
        ace.getSegundaParteCuit().setEditable(false);
        ace.getTerceraParteCuit().setEditable(false);
        ace.getDireccionEmpresajTextField().setText(direccion);
        ace.getCodPostEmpresajTextField().setText(codPostal);
        ace.getLocalidadEmpresajTextField().setText(localidad);
        ace.getProvinciaComboBox().setSelectedItem(provincia);
        ace.getTelefonoEmpresajTextField().setText(telefono);
        ace.getTelefono2EmpresajTextField().setText(telAlternativo);
        ace.getFaxClientejTextField().setText(fax);
        ace.getCorreoEmpresajTextField().setText(eMail);
        editando = true;
    }

    /**
     * Indica si se canceló la accion
     * @return true si se cancelo
     */
    public boolean wasCanceled() {
        return ace.wasCanceled();
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
    private AccesosBD abd = AccesosBD.getInstance();
    boolean editando = false;

    @Override
    public void keyTyped(KeyEvent e) {        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {        
    }
}
