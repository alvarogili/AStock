/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilidades;

import java.io.File;

/**
 * Clase que proporciona utilidades para el manejo de archivos
 * @author Alvaro
 */
public class UtilidadesArchivos {

    public UtilidadesArchivos() {
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

}
