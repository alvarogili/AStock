/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Clientes;

import BD.Personas.PersonaCliente;
import Clientes.AltaClientePersona;
import Herramientas.ValidadorCUIT;
import agili.astock.BD.AccesosBD;
import java.awt.Frame;
import java.awt.event.*;
import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Clase mediador para la interface AltaClientePersona
 * @author Alvaro
 */
public class AltaClientePersonaMediador implements ActionListener, MouseListener, ItemListener {

    /**
     * Constructor
     */
    public AltaClientePersonaMediador() {
        super();
        acp.getApellidoClientejTextField().addActionListener(this);
        acp.getCodPostnClientejTextField().addActionListener(this);
        acp.getCorreoClientejTextField().addActionListener(this);
        acp.getDireccionClientejTextField().addActionListener(this);
        acp.getDniClientejTextField().addActionListener(this);
        acp.getFaxClientejTextField().addActionListener(this);
        acp.getjButton1().addActionListener(this);
        acp.getjButton2().addActionListener(this);
        acp.getjRadioButtonCUIL().addActionListener(this);
        acp.getjRadioButtonDNI().addActionListener(this);
        acp.getLocalidadClientejTextField().addActionListener(this);
        acp.getNombreClientejTextField().addActionListener(this);
        acp.getPrimeraParteCuit().addActionListener(this);
        acp.getProvinciaComboBox().addActionListener(this);
        acp.getSegundaParteCuit().addActionListener(this);
        acp.getTelefono2ClientejTextField().addActionListener(this);
        acp.getTelefonoClientejTextField().addActionListener(this);
        acp.getTerceraParteCuit().addActionListener(this);
        acp.getjPanel1().setSize(565, 204);
    }

    /**
     * Muestra la ventana
     * @param parent Ventana padre
     */
    public void show(Frame parent) {
        acp.setLocationRelativeTo(parent);
        acp.setVisible(true);
    }

    /**
     * Rellena los campos para la edicion
     * @param nombre
     * @param apellido
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
            String nombre, String apellido,
            String dni, String direccion, String codPostal,
            String localidad, String provincia, String telefono,
            String telAlternativo, String fax, String eMail, String cumpleaños) {

        acp.getNombreClientejTextField().setText(nombre);
        acp.getApellidoClientejTextField().setText(apellido);
        String dniTemp = dni;
        //es un cuit
        if (dniTemp.contains("-")) {

            String temp = dni.substring(0, dniTemp.indexOf("-"));
            acp.getPrimeraParteCuit().setText(temp);
            dniTemp = dniTemp.substring(dniTemp.indexOf("-") + 1);
            temp = dniTemp.substring(0, dniTemp.indexOf("-"));
            acp.getSegundaParteCuit().setText(temp);
            dniTemp = dniTemp.substring(dniTemp.indexOf("-") + 1);
            acp.getTerceraParteCuit().setText(dniTemp);
            acp.getjRadioButtonCUIL().setSelected(true);

        } else {
            acp.getDniClientejTextField().setText(dni);
        }
        acp.getDireccionClientejTextField().setText(direccion);
        acp.getCodPostnClientejTextField().setText(codPostal);
        acp.getLocalidadClientejTextField().setText(localidad);
        acp.getProvinciaComboBox().setSelectedItem(provincia);
        acp.getTelefonoClientejTextField().setText(telefono);
        acp.getTelefono2ClientejTextField().setText(telAlternativo);
        acp.getFaxClientejTextField().setText(fax);
        acp.getCorreoClientejTextField().setText(eMail);
        if (!cumpleaños.equals("")) {
            acp.getjComboBoxDia().setSelectedItem(new Integer(cumpleaños.substring(0, cumpleaños.indexOf("/"))));
            acp.getjComboBoxMes().setSelectedItem(cumpleaños.substring(cumpleaños.indexOf("/") + 1, cumpleaños.length()));
        }
        editando = true;
        //como es una edicion deshabilito todo lo de DNI y CUIT
        acp.getjRadioButtonCUIL().setEnabled(false);
        acp.getjRadioButtonDNI().setEnabled(false);
        acp.getDniClientejTextField().setEditable(false);
        acp.getPrimeraParteCuit().setEditable(false);
        acp.getSegundaParteCuit().setEditable(false);
        acp.getTerceraParteCuit().setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(acp.getjButton1())) { //Guardar
            guardar();
        }
    }

    /**
     * Guarda el cliente
     */
    private void guardar() {
        String dniOcuit = null;
        boolean cuitSel = false;
        //obtengo el dni o cuit dependiendo de la seleccion
        if (acp.getjRadioButtonDNI().isSelected()) {

            dniOcuit = acp.getDniClientejTextField().getText();
            //veo que sea un numero
            try {
                Integer check = new Integer(dniOcuit);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(acp,
                        "El DNI debe estar formado solo por números", "Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/error.gif")));
                acp.getDniClientejTextField().requestFocusInWindow();
                return;
            }

        } else {
            //veo que este todo completo el cuit
            if (acp.getPrimeraParteCuit().getText().length() == 0
                    || acp.getSegundaParteCuit().getText().length() == 0
                    || acp.getTerceraParteCuit().getText().length() == 0) {
                dniOcuit = "";
            } else {
                dniOcuit = acp.getPrimeraParteCuit().getText() + "-"
                        + acp.getSegundaParteCuit().getText() + "-"
                        + acp.getTerceraParteCuit().getText();
                cuitSel = true;
            }
        }

        if (acp.getNombreClientejTextField().getText().equals("")
                || acp.getApellidoClientejTextField().getText().equals("")
                || dniOcuit.equals("")) {
            JOptionPane.showMessageDialog(acp,
                    "Los campos Nombre, Apellido y DNI/CUIL son obligatorios", "Error", JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/error.gif")));

        } else {

            //valido el cuit
            if (cuitSel) {
                ValidadorCUIT cUIT = new ValidadorCUIT();
                if (!cUIT.verify(dniOcuit)) {
                    JOptionPane.showMessageDialog(acp,
                            "El CUIL / CUIT ingresado es erroneo", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                    return;
                }
            }

            if (!editando) {//es un cliente nuevo

                String provincia;
                if (acp.getProvinciaComboBox().getSelectedItem() == null) {
                    provincia = "";
                } else {
                    provincia = acp.getProvinciaComboBox().getSelectedItem().toString();
                }
                String cumpleaños = "";
                if (acp.getjComboBoxDia().getSelectedIndex() != -1 && acp.getjComboBoxMes().getSelectedIndex() != -1) {
                    cumpleaños = acp.getjComboBoxDia().getSelectedItem() + "/" + acp.getjComboBoxMes().getSelectedItem();
                }

                PersonaCliente p = new PersonaCliente(acp.getNombreClientejTextField().getText(),
                        acp.getApellidoClientejTextField().getText(),
                        dniOcuit,
                        acp.getDireccionClientejTextField().getText(),
                        acp.getCodPostnClientejTextField().getText(),
                        acp.getLocalidadClientejTextField().getText(),
                        provincia,
                        acp.getTelefonoClientejTextField().getText(),
                        acp.getTelefono2ClientejTextField().getText(),
                        acp.getCorreoClientejTextField().getText(),
                        acp.getFaxClientejTextField().getText(),
                        cumpleaños);

                if (!abd.guardarEnBD(p)) {
                    JOptionPane.showMessageDialog(acp,
                            "El Cliente ya existe o el DNI / CUIT fue ingresado incorrectamente", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                } else {

                    JOptionPane.showMessageDialog(acp,
                            "El Cliente ha sido guardado correctamente", "Confirmaciòn",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                    acp.dispose();
                }

            } else {//es una modificacion
                try {
                    String verificacion = "dni==\'" + dniOcuit + "\'";

                    PersonaCliente personaCliente = (PersonaCliente) abd.getObjeto(PersonaCliente.class,
                            verificacion);

                    String provincia;
                    if (acp.getProvinciaComboBox().getSelectedItem() == null) {
                        provincia = "";
                    } else {
                        provincia = acp.getProvinciaComboBox().getSelectedItem().toString();
                    }

                    String cumpleaños = "";
                    if (acp.getjComboBoxDia().getSelectedIndex() != -1 && acp.getjComboBoxMes().getSelectedIndex() != -1) {
                        cumpleaños = acp.getjComboBoxDia().getSelectedItem() + "/" + acp.getjComboBoxMes().getSelectedItem();
                    }

                    personaCliente.appSetNombre(acp.getNombreClientejTextField().getText());
                    personaCliente.appSetApellido(acp.getApellidoClientejTextField().getText());
                    personaCliente.appSetDireccion(acp.getDireccionClientejTextField().getText());
                    personaCliente.appSetCodigoPostal(acp.getCodPostnClientejTextField().getText());
                    personaCliente.appSetLocalidad(acp.getLocalidadClientejTextField().getText());
                    personaCliente.appSetProvincia(provincia);
                    personaCliente.appSetTelefono(acp.getTelefonoClientejTextField().getText());
                    personaCliente.appSetTelefono2(acp.getTelefono2ClientejTextField().getText());
                    personaCliente.appSetCorreo(acp.getCorreoClientejTextField().getText());
                    personaCliente.appSetFax(acp.getFaxClientejTextField().getText());
                    personaCliente.appSetCumpleaños(cumpleaños);

                    abd.actualizarObjeto(PersonaCliente.class, personaCliente, verificacion);

                    JOptionPane.showMessageDialog(acp,
                            "El Cliente ha sido guardado correctamente", "Confirmaciòn",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                    acp.dispose();
                } catch (JDODataStoreException e) {
                    JOptionPane.showMessageDialog(acp,
                            "El Cliente no pudo ser actualizado", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            }

        }
    }

    /**
     * Indica si se canceló la accion
     * @return true si se cancelo
     */
    public boolean wasCanceled() {
        return acp.wasCanceled();
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
    /**
     * Objeto ventana
     */
    private AltaClientePersona acp = new AltaClientePersona(null, true);
    boolean editando = false;
    private AccesosBD abd = AccesosBD.getInstance();

    public void setTitle(String string) {
        acp.setTitle(string);
    }
}
