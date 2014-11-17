/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Herramientas;

import Utilidades.UtilidadesArchivos;
import Utilidades.Zip;
import java.io.File;
import java.io.FileFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;

/**
 *
 * @author Alvaro Gili
 */
public class RestaurarBackupThread extends Thread {

    private boolean estadoDeTerminacion = false;
    private Procesando procesando;
    private JFrame parent;
    private String zipPath;

    public RestaurarBackupThread(String zipPath, JFrame parent) {
        this.parent = parent;
        this.zipPath = zipPath;
    }

    public void setProcesando(Procesando procesando) {
        this.procesando = procesando;
    }

    @Override
    public void run() {
        try {
            DatosDeConfiguracion ddc = DatosDeConfiguracion.getInstance();
            //creo una carpeta temporal para meter los txt
            File tempDir = new File(System.getProperty("user.dir") + File.separatorChar + "temp");
            //borro el directorio temporal
            UtilidadesArchivos ua = new UtilidadesArchivos();
            ua.borrarDirectorio(tempDir);
            tempDir.mkdir();
            //descomprimo
            Zip zip = new Zip();
            zip.uncompressZip(zipPath, tempDir.getAbsolutePath());



            File[] files = tempDir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    if (pathname.getAbsolutePath().endsWith(".txt")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            //me conecto
            Connection con = DriverManager.getConnection("jdbc:mysql://" + ddc.getURLBD() + "/astock", ddc.getUsuarioBD(), ddc.getContraseñaBD());
            Statement statement = (Statement) con.createStatement();

            //obtengo la lista de tablas
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.tables where TABLE_SCHEMA='astock'");
            ResultSet resultSet = preparedStatement.executeQuery();

            //reccorro la lista de archivos descomprimidos y voy restaurando la base de datos  
            for (int i = 0; i < files.length; i++) {

                if (resultSet.first()) {
                    do {
                        if (!resultSet.getString(1).equals("jpox_tables")) {
                            //veo si para la table corriente existe el txt
                            File f = new File(tempDir.getAbsolutePath() + File.separatorChar + resultSet.getString(1) + ".txt");
                            if (f.exists()) {
                                String sentence;
                                sentence = "LOAD DATA LOCAL INFILE '" + f.getAbsolutePath() + "' INTO TABLE " + resultSet.getString(1);
                                sentence = sentence.replace("\\", "\\\\");
                                try{
                                statement.execute(sentence);
                                }catch(SQLException ex){
                                    
                                }
                            }
                        }
                    } while (resultSet.next());
                }

                //borro los archivos txt

                for (i = 0; i < files.length; i++) {
                    files[i].delete();
                }

                //borro el directorio temporal
                ua.borrarDirectorio(tempDir);

            }
            con.close();


            estadoDeTerminacion = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            procesando.mostrar(false, parent);
        }
    }

    public boolean isEstadoDeTerminacion() {
        return estadoDeTerminacion;
    }
}
