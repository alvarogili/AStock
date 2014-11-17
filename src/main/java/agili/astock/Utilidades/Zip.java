package Utilidades;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Clase para comprimir y descomprimir
 * @author Alvaro Gili
 */
public class Zip {

    public Zip() {
    }

    /**
     * Create a zip file
     * @param zipName Zip name
     * @param Directory with files to zip
     * @throws IOException
     */
    public void compressToZip(String zipName, String directory) throws IOException {

        if (!directory.endsWith("\\") || !directory.endsWith("/")) {
            directory += "/";
        }
        if (!zipName.endsWith(".zip")) {
            zipName += ".zip";
        }

        zipName = directory + zipName;

        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[1024];

            File dir = new File(directory);
            File files[] = dir.listFiles(new FileFilter() {

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
                File forZip = new File("/" + files[i].getName());
                copyFile(files[i], forZip);
                FileInputStream fi = new FileInputStream(forZip);
                origin = new BufferedInputStream(fi, 1024);
                ZipEntry entry = new ZipEntry(files[i].getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0,
                        1024)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                forZip.delete();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Uncompress a ZIP file
     * @param pathZip Zip file path
     * @param pathDestine ZIP contain destine
     */
    public void uncompressZip(String pathZip, String pathDestine) {

        int BUFFER_SIZE = 2048;
        try {
            // Create a ZipInputStream to read the zip file
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(pathZip);
            ZipInputStream zis = new ZipInputStream(
                    new BufferedInputStream(fis));
            // Loop over all of the entries in the zip file
            int count;
            byte data[] = new byte[BUFFER_SIZE];
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    String destFN = pathDestine + File.separator + entry.getName();
                    // Write the file to the file system
                    FileOutputStream fos = new FileOutputStream(destFN);
                    dest = new BufferedOutputStream(fos, BUFFER_SIZE);
                    while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Copy a file to other directory
     * @param sourceFile Source File
     * @param destFile Destination File
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        InputStream in = null;
        FileOutputStream out = null;
        int BUFF_SIZE = 5 * 1024 * 1024; // 5MB

        byte[] buffer = new byte[BUFF_SIZE];

        try {
            in = new FileInputStream(sourceFile);
            out = new FileOutputStream(destFile);
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                out.write(buffer, 0, count);
            }
        } catch (IOException ex) {
        } finally {
            try {
                in.close();
                out.close();

            } catch (IOException ex) {
            }
        }
    }
}
