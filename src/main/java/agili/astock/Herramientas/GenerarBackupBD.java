/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GenerarBackupBD.java
 *
 * Created on 15-mar-2010, 19:20:52
 */
package Herramientas;

import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Alvaro Gili
 */
public class GenerarBackupBD extends javax.swing.JDialog {

    /** Creates new form GenerarBackupBD */
    public GenerarBackupBD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jFileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".zip")) {
                    return true;
                } else if (f.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Archivos comprimidos (*.zip).";
            }
        });

        this.setLocationRelativeTo(parent);

        //jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setSelectedFile(new File(jFileChooser.getCurrentDirectory().getAbsolutePath() + "/" + defaultFileName));
    }

    public String getFileName() {
        return defaultFileName;
    }

    public String getDirectory() {
        return directory;
    }

    public boolean getAccepted() {
        return accepted;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccione la ubicación y el nombre del archivo de backup...");

        jFileChooser.setDialogTitle("");
        jFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooserActionPerformed(evt);
            }
        });
        jFileChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFileChooserKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooserActionPerformed
        String s = evt.getActionCommand();
        if (s.equals("ApproveSelection")) {//acepto
            File f = new File(jFileChooser.getCurrentDirectory().getAbsolutePath() + File.separatorChar + jFileChooser.getSelectedFile().getName());
            if (f.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this,
                        "El archivo ya existe, ¿desea reemplazarlo?",
                        "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/svn_status.png")))) {
                    f.delete();
                } else {
                    return;
                }
            }

            defaultFileName = jFileChooser.getSelectedFile().getName();
            if (!defaultFileName.endsWith(".zip")) {
                defaultFileName += ".zip";
            }
            directory = jFileChooser.getCurrentDirectory().getAbsolutePath();
            accepted = true;
            
        } else if (s.equals("CancelSelection")) {
            accepted = false;            
        }
        dispose();
    }//GEN-LAST:event_jFileChooserActionPerformed

    private void jFileChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFileChooserKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            File f = new File(jFileChooser.getCurrentDirectory().getAbsolutePath() + File.separatorChar + jFileChooser.getSelectedFile().getName());
            if (f.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this,
                        "El archivo ya existe, ¿desea reemplazarlo?",
                        "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/svn_status.png")))) {
                    f.delete();
                } else {
                    return;
                }
            }

            defaultFileName = jFileChooser.getSelectedFile().getName();
            if (!defaultFileName.endsWith(".zip")) {
                defaultFileName += ".zip";
            }
            directory = jFileChooser.getCurrentDirectory().getAbsolutePath();
            accepted = true;
            dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            accepted = false;
            dispose();
        }
    }//GEN-LAST:event_jFileChooserKeyPressed
    private boolean accepted = false;
    private String defaultFileName = "backup.zip";
    private String directory;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser;
    // End of variables declaration//GEN-END:variables
}
