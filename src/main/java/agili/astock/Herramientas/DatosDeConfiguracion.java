/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agili.astock.Herramientas;

import Utilidades.DESEncript;
import agili.astock.AStock;
import agili.astock.XMLParser.XMLNode;
import agili.astock.XMLParser.XMLParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Clase que contiene los datos de configuración de la unidad
 * @author Alvaro Gili
 */
public class DatosDeConfiguracion {

    private static DatosDeConfiguracion instance;
    private String URLBD;
    private String UsuarioBD;
    private String ContraseñaBD;
    private boolean VerificarEventosAlInicio;
    private int tiempoDeActualizacion;
    private int iva;


    private DatosDeConfiguracion() {

        //////////////////////////////////////////////////////
        //leo el archivo de configuracion
        String configPath = System.getProperty("user.dir");
        configPath += File.separator + "Configuracion.xml";
        XMLParser xmlp = new XMLParser();
        if (!xmlp.parseFile(configPath)) {
            JOptionPane.showMessageDialog(null, "Error al cargar la configuración, consulte a su proveedor.",
                    "Error", JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/error.gif")));
            System.exit(1);
        } else {
            XMLNode rootNode = xmlp.getRootNode();
            setURLBD(rootNode.getChild("URLBD").getValue().toString());
            setUsuarioBD(rootNode.getChild("UsuarioBD").getValue().toString());
            setVerificarEventosAlInicio(new Boolean(rootNode.getChild("VerificarEventosAlInicio").getValue().toString()));
            setTiempoDeActualizacion(new Integer(rootNode.getChild("tiempoDeActualizacion").getValue().toString()));
            setIva(new Integer(rootNode.getChild("IVA").getValue().toString()));
        }
        //////////////////////////////////////////////////////
        //si no existe contraseña de base de datos la pregunto sino levanto la encriptada
        File passBD = new File("BDP.des");
        DESEncript encript = new DESEncript();
        if (!passBD.exists()) {
            String passwordBD = JOptionPane.showInputDialog(null, "Por favor, especifique la contraseña de la base de datos", "Primera vez", JOptionPane.QUESTION_MESSAGE);

            try {
                if (passwordBD != null) {
                    String pdbEncripted = encript.encrypt(passwordBD);
                    FileOutputStream fileOutputStream = new FileOutputStream(passBD);
                    fileOutputStream.write(pdbEncripted.getBytes());
                    fileOutputStream.close();
                    setContraseñaBD(passwordBD);
                } else {
                    setContraseñaBD("");
                }

            } catch (Exception ex) {
                Logger.getLogger(AStock.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                BufferedReader input = new BufferedReader(new FileReader(passBD));
                try {
                    String password = input.readLine();
                    try {
                        setContraseñaBD(encript.decrypt(password));
                    } catch (Exception ex) {
                        Logger.getLogger(AStock.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(AStock.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(AStock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    public static DatosDeConfiguracion getInstance() {
        if(instance == null)
            instance = new DatosDeConfiguracion();
        return instance;
    }

    public String getContraseñaBD() {
        return ContraseñaBD;
    }

    public void setContraseñaBD(String ContraseñaBD) {
        this.ContraseñaBD = ContraseñaBD;
    }

    public String getURLBD() {
        return URLBD;
    }

    public void setURLBD(String URLBD) {
        this.URLBD = URLBD;
    }

    public String getUsuarioBD() {
        return UsuarioBD;
    }

    public void setUsuarioBD(String UsuarioBD) {
        this.UsuarioBD = UsuarioBD;
    }

    public boolean isVerificarEventosAlInicio() {
        return VerificarEventosAlInicio;
    }

    public void setVerificarEventosAlInicio(boolean VerificarEventosAlInicio) {
        this.VerificarEventosAlInicio = VerificarEventosAlInicio;
    }

    public int getTiempoDeActualizacion() {
        return tiempoDeActualizacion;
    }

    public void setTiempoDeActualizacion(int tiempoDeActualizacion) {
        this.tiempoDeActualizacion = tiempoDeActualizacion;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva){
        this.iva = iva;
    }

    public boolean guardarConfiguracion(){
        FileOutputStream fileOutputStream = null;
        try {
            String configText = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + "<configuracion>\n" + "\t<URLBD>" + getURLBD() + "</URLBD>\n" + "\t<UsuarioBD>" + getUsuarioBD() + "</UsuarioBD>\n" + "\t<VerificarEventosAlInicio>" + isVerificarEventosAlInicio() + "</VerificarEventosAlInicio>\n" + "</configuracion>";
            String configPath = System.getProperty("user.dir");
            configPath += File.separator + "Configuracion.xml";
            fileOutputStream = new FileOutputStream(new File(configPath));
            fileOutputStream.write(configText.getBytes());
            fileOutputStream.close();
            return true;
        } catch (IOException ex) {
            return false;
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                return false;
            }
        }
    }
}
