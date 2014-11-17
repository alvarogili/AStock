/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Herramientas;


import Utilidades.UtilidadesArchivos;
import Utilidades.Zip;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Alvaro Gili
 */
public class GenerarBackupThread extends Thread {

    private String directorio;
    private String nombreZip;
    private JFrame parent;
    private Procesando procesando;
    private boolean estadoDeTerminacion = false;

    public GenerarBackupThread(String directorio, String nombreZip, JFrame parent) {
        this.directorio = directorio;
        this.nombreZip = nombreZip;
        this.parent = parent;
    }

    public void setProcesando(Procesando procesando) {
        this.procesando = procesando;
    }

    @Override
    public void run() {
        try {
            //creo una carpeta temporal para meter los txt
            File tempDir = new File("./temp");
            UtilidadesArchivos ua = new UtilidadesArchivos();
            ua.borrarDirectorio(tempDir); //borro por si existe
            tempDir.mkdir();
            //me conecto
            DatosDeConfiguracion ddc = DatosDeConfiguracion.getInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://" + ddc.getURLBD() + "/astock", ddc.getUsuarioBD(), ddc.getContraseñaBD());
            Statement statement = (Statement) con.createStatement();
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.tables where TABLE_SCHEMA='astock'");
            ResultSet resultSet = preparedStatement.executeQuery();
            //obtengo la lista de tablas            
            if (resultSet.first()) {
                do {
                    if (!resultSet.getString(1).equals("jpox_tables")) {
                        File f = new File(tempDir.getAbsolutePath() + File.separatorChar + resultSet.getString(1) + ".txt");
                        if (f.exists()) {
                            if (!f.delete()) {
                                return;
                            }
                        }
                        String sentence;
                        sentence = "SELECT * INTO OUTFILE '" + tempDir.getAbsolutePath();
                        sentence += File.separatorChar + resultSet.getString(1) + ".txt' from " + resultSet.getString(1);
                        sentence = sentence.replace("\\", "\\\\");
                        statement.execute(sentence);
                    }
                } while (resultSet.next());
            }
            con.close();
            Zip zip = new Zip();
            try {
                zip.compressToZip(nombreZip, tempDir.getAbsolutePath());
            } catch (IOException ex) {
                return;
            }
            File dir = new File(directorio);
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    if (pathname.getAbsolutePath().endsWith(".txt")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            try {
                //muevo el zip al directorio final
                Zip.copyFile(new File(tempDir.getAbsolutePath() + File.separatorChar + nombreZip), new File(directorio + File.separatorChar + nombreZip));
                //borro el directorio temporal
                ua.borrarDirectorio(tempDir);
            } catch (IOException ex) {
                return;
            }
            estadoDeTerminacion = true;
        } catch (SQLException ex) {
        } finally {
            procesando.mostrar(false, parent);
        }
    }

    public boolean isEstadoDeTerminacion() {
        return estadoDeTerminacion;
    }
}
