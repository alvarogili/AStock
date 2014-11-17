package agili.astock;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.Utilidades.PasswordAdmin;
import Herramientas.GenerarBackupBD;
import Herramientas.RestaurarBackupBD;
import Utilidades.DESEncript;
import agili.astock.BD.AccesosBD;
import agili.astock.Clientes.AStockMediadorClientes;
import agili.astock.Herramientas.GenerarBackupThread;
import agili.astock.Herramientas.Procesando;
import agili.astock.Herramientas.RestaurarBackupThread;
import agili.astock.Productos.AStockMediadorProductos;
import agili.astock.Proveedores.AStockMediadorProveedor;
import agili.astock.Utilidades.ThreadActualizador;
import java.awt.event.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Mediador principal
 * @author Alvaro Gili
 */
public class AStockMediador implements ActionListener, MouseListener, ItemListener, KeyListener {

    //Main Frame
    private AStock aStock;
    private AStockMediadorClientes aStockMediadorClientes = null;
    private AStockMediadorProveedor aStockMediadorProveedor = null;
    private AStockMediadorProductos aStockMediadorProductos = null;
    private ThreadActualizador ta;
    private AccesosBD accesosBD;

    /**
     * Constructor
     */
    public AStockMediador() {
        try {
            
            aStock = new AStock();
            aStockMediadorClientes = new AStockMediadorClientes(aStock);
            aStockMediadorProveedor = new AStockMediadorProveedor(aStock);
            aStockMediadorProductos = new AStockMediadorProductos(aStock);
            aStock.getjMenuItemGenBackup().addActionListener(this);
            aStock.getjMenuItemRestoreBackup().addActionListener(this);
            ta = ThreadActualizador.getInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AStockMediador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void show() throws InterruptedException {        
        PasswordAdmin passwordAdmin = null;
        try {
            passwordAdmin = (PasswordAdmin) accesosBD.getObjeto(PasswordAdmin.class, null);
        } catch (Exception e) {
        }
        if (passwordAdmin != null) {
            if (passwordAdmin.appGetSolicitarPassInicio()) {
                while (true) {
                    PasswordInicioAdmin passwordInicioAdmin = new PasswordInicioAdmin(aStock, true);
                    passwordInicioAdmin.setLocationRelativeTo(aStock);
                    passwordInicioAdmin.setVisible(true);
                    if (passwordInicioAdmin.isIngresoPass()) {
                        //comparo con la pass guardada en BD
                        String passIngresada = passwordInicioAdmin.getPass();
                        DESEncript dESEncript = new DESEncript();
                        if (!passIngresada.equals(dESEncript.decrypt(passwordAdmin.appGetPassAdmin()))) {
                            //ingreso mal la contraseña
                            JOptionPane.showMessageDialog(aStock, "La contraseña ingresada es incorrecta.",
                                    "Error", JOptionPane.ERROR_MESSAGE,
                                    new ImageIcon(getClass().getResource("/img/error.gif")));
                        } else {
                            aStock.setVisible(true);
                            break;
                        }
                    } else {
                        System.exit(0);
                    }
                }
            } else {
                aStock.setVisible(true);
            }
        } else {
            //no hay password admin muestro la interface
            aStock.setVisible(true);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(aStock.getjMenuItemGenBackup())) {

            GenerarBackupBD generarBackupBD = new GenerarBackupBD(aStock, true);
            generarBackupBD.setVisible(true);
            if (generarBackupBD.getAccepted()) {

                GenerarBackupThread backupThread = new GenerarBackupThread(generarBackupBD.getDirectory(),
                        generarBackupBD.getFileName(), aStock);
                Procesando procesando = new Procesando(backupThread, "Generando backup de la base de datos...");
                procesando.mostrar(true, aStock);
                if (backupThread.isEstadoDeTerminacion()) {
                    JOptionPane.showMessageDialog(aStock, "El backup se generó correctamente en el directorio seleccionado previamente.",
                            "Confirmación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                } else {
                    JOptionPane.showMessageDialog(aStock, "Se produjo un error al generar el backup.",
                            "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            }
        } else if (e.getSource().equals(aStock.getjMenuItemRestoreBackup())) {
            RestaurarBackupBD restaurarBackupBD = new RestaurarBackupBD(aStock, true);
            restaurarBackupBD.setVisible(true);
            if (restaurarBackupBD.getAccepted()) {

                String s = restaurarBackupBD.getDirectory() + File.separatorChar + restaurarBackupBD.getFileName();
                RestaurarBackupThread restaurarBackupThread = new RestaurarBackupThread(s, aStock);

                Procesando procesando = new Procesando(restaurarBackupThread, "Restaurando la base de datos...");
                procesando.mostrar(true, aStock);

                if (restaurarBackupThread.isEstadoDeTerminacion()) {
                    ta.actualizarClientes();
                    ta.actualizarProveedores();
                    JOptionPane.showMessageDialog(aStock, "La base de datos se restauró correctamente.",
                            "Confirmación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                } else {
                    JOptionPane.showMessageDialog(aStock, "Se produjo un error al restaurar la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            }
        }
    }

    /**
     * Borra recursivamente un directorio
     * @param directorio Directorio a borrar
     */
    public void borrarDirectorio(File directorio) {

        File[] ficheros = directorio.listFiles();

        if (ficheros != null) {
            for (int x = 0; x < ficheros.length; x++) {
                if (ficheros[x].isDirectory()) {
                    borrarDirectorio(ficheros[x]);
                }
                ficheros[x].delete();
            }
        }
        directorio.delete();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public void setAccesosBD(AccesosBD accesosBD) {
        this.accesosBD = accesosBD;
    }
        
}
