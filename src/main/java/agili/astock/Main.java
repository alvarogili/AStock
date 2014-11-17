package agili.astock;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import BD.Utilidades.Skin;
import agili.astock.Agenda.MediadorAgenda;
import agili.astock.BD.AccesosBD;
import agili.astock.Herramientas.DatosDeConfiguracion;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Alvaro Gili
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    //////////////////////////////////////////////////////
                    //iniciando transacción con la base de datos
                    DatosDeConfiguracion ddc = DatosDeConfiguracion.getInstance();
//                    HashMap<String, String> properties = new HashMap<String, String>();
//                    properties.put("javax.jdo.PersistenceManagerFactoryClass", "org.jpox.PersistenceManagerFactoryImpl");
//                    properties.put("javax.jdo.option.ConnectionDriverName", "org.gjt.mm.mysql.Driver");
//                    ////////para leer y escribir fuera de una transacción
//                    properties.put("javax.jdo.option.NontransactionalRead", "true");
//                    properties.put("javax.jdo.option.NontransactionalWrite", "true");
//                    //////////////////////////////////////////////////////
//                    properties.put("javax.jdo.option.ConnectionURL", "jdbc:mysql://" + ddc.getURLBD() + "/astock");
//                    properties.put("javax.jdo.option.ConnectionUserName", ddc.getUsuarioBD());
//                    properties.put("javax.jdo.option.ConnectionPassword", ddc.getContraseñaBD());
//                    properties.put("org.jpox.autoCreateSchema", "true");
//                    properties.put("org.jpox.autoCreateTables", "true");
//                    properties.put("org.jpox.autoCreateColumns", "true");
//                    properties.put("org.jpox.validateTables", "false");
//                    properties.put("org.jpox.validateConstraints", "false");

                    AccesosBD abd = AccesosBD.getInstance();
                    abd.init(AccesosBD.DBEngine.MySQL, ddc.getURLBD(), ddc.getUsuarioBD(), ddc.getContraseñaBD());
//                    try {
//                        abd.init(properties);//inicio la conexion con la BD
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        JOptionPane.showMessageDialog(null, "Los datos configurados para acceso a la base de datos son incorrectos. Por favor diríjase a el menú"
//                                + " \"Herramientas->Configuración\" y verifique los mismos.",
//                                "Error", JOptionPane.ERROR_MESSAGE,
//                                new ImageIcon(getClass().getResource("/img/error.gif")));
//                    }
                    AStockMediador aStock = new AStockMediador();

                    Skin skin = null;
                    try {
                        skin = (Skin) abd.getObjeto(Skin.class, "skinType == \'aplicacion\'");
                    } catch (Exception e) {
                    }
                    if (skin == null) {
                        //creo un skin por defecto
                        skin = new Skin();
                        skin.appSetSkinName("Business Black Steel");
                        skin.appSetSkinType("aplicacion");
                        abd.guardarEnBD(skin);
                    }
//                    SkinInfo skinInfo = SubstanceLookAndFeel.getAllSkins().get(skin.appGetSkinName());
//                    SubstanceLookAndFeel.setSkin(skinInfo.getClassName());
                    MediadorAgenda agenda = MediadorAgenda.getInstance();
                    agenda.setAbd(abd);
                    aStock.setAccesosBD(abd);
                    aStock.show();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


    }
}
