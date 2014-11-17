/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AltaClientePersona.java
 *
 * Created on 07-jul-2009, 18:33:07
 */
package Clientes;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Alvaro Gili
 */
public class AltaClienteEmpresa extends javax.swing.JDialog {

    /** Creates new form AltaClientePersona */
    public AltaClienteEmpresa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nombreEmpresajTextField = new javax.swing.JTextField();
        codPostEmpresajTextField = new javax.swing.JTextField();
        telefono2EmpresajTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        faxClientejTextField = new javax.swing.JTextField();
        direccionEmpresajTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        correoEmpresajTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        provinciaComboBox = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        telefonoEmpresajTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        localidadEmpresajTextField = new javax.swing.JTextField();
        primeraParteCuit = new javax.swing.JTextField();
        terceraParteCuit = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        segundaParteCuit = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Nueva Empresa");
        setIconImage(null);
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        telefono2EmpresajTextField.setToolTipText("Teléfono Alternativo");

        jLabel5.setText("Tel. Alternativo:");
        jLabel5.setToolTipText("Teléfono Alternativo");

        direccionEmpresajTextField.setToolTipText("DNI o CUIT del nuevo cliente");

        jLabel6.setText("Provincia:");

        correoEmpresajTextField.setToolTipText("ejemplo: juanperugia@gmail.com");

        jLabel3.setText("Dirección:");

        jLabel1.setText("*Nombre:");

        jLabel7.setText("*CUIT:");

        jLabel4.setText("Fax:");

        provinciaComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buenos Aires", "Capital Federal", "Catamarca", "Chaco", "Chubut", "Córdoba", "Corrientes", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fé", "Santiago del Estero", "Tierra del Fuego", "Tucumán" }));
        provinciaComboBox.setSelectedIndex(-1);
        provinciaComboBox.setSelectedItem("");

        jLabel8.setText("Correo:");
        jLabel8.setToolTipText("E-mail");

        jLabel9.setText("Tel. Principal:");
        jLabel9.setToolTipText("Teléfono Principal");

        telefonoEmpresajTextField.setToolTipText("Teléfono Principal");

        jLabel10.setText("Localidad:");

        jLabel11.setText("Código Postal:");

        primeraParteCuit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        primeraParteCuit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                primeraParteCuitKeyReleased(evt);
            }
        });

        terceraParteCuit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        terceraParteCuit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                terceraParteCuitKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                terceraParteCuitKeyReleased(evt);
            }
        });

        jLabel13.setText(" - ");

        jLabel12.setText(" - ");

        segundaParteCuit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        segundaParteCuit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                segundaParteCuitKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                segundaParteCuitKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel15.setText("Los campos marcados con * son obligatorios");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel3)
                            .add(jLabel10)
                            .add(jLabel9)
                            .add(jLabel4))
                        .add(9, 9, 9)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(faxClientejTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, telefonoEmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .add(direccionEmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .add(nombreEmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .add(localidadEmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                        .add(25, 25, 25)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, telefono2EmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .add(codPostEmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, provinciaComboBox, 0, 165, Short.MAX_VALUE)
                            .add(correoEmpresajTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(primeraParteCuit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel12)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(segundaParteCuit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel13)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(terceraParteCuit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                        .add(264, 264, 264))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(jLabel1)
                                    .add(primeraParteCuit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(terceraParteCuit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel13)
                                    .add(segundaParteCuit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel12)
                                    .add(jLabel7)))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(49, 49, 49)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                    .add(codPostEmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel11))))
                        .add(15, 15, 15)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(provinciaComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6))
                        .add(18, 18, 18)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(telefonoEmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel9)
                            .add(telefono2EmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel5)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(nombreEmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(direccionEmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3))
                        .add(18, 18, 18)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(localidadEmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel10))))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(faxClientejTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(jLabel8)
                    .add(correoEmpresajTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel15)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dialog-ok-apply.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/application-exit.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user-group-new32.png"))); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 271, Short.MAX_VALUE)
                        .add(jButton1)
                        .add(32, 32, 32)
                        .add(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton2)
                    .add(jButton1)
                    .add(jLabel2))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (JOptionPane.showConfirmDialog(this, "El Cliente no se va a guardar, ¿Desea cerrar de todas maneras?",
                "Advertencia", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(getClass().getResource("/img/important.png"))) == JOptionPane.OK_OPTION) {
            canceled = true;
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (JOptionPane.showConfirmDialog(this, "El Cliente no se va a guardar, ¿Desea cerrar de todas maneras?",
                "Advertencia", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,
                new ImageIcon(getClass().getResource("/img/important.png"))) == JOptionPane.OK_OPTION) {
            canceled = true;
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void primeraParteCuitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primeraParteCuitKeyReleased
        String texto = primeraParteCuit.getText();
        if (texto.length() == 2 && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            segundaParteCuit.setFocusable(true);
            segundaParteCuit.requestFocusInWindow();
        }
}//GEN-LAST:event_primeraParteCuitKeyReleased

    private void segundaParteCuitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaParteCuitKeyReleased
        String texto = segundaParteCuit.getText();
        if (texto.length() == 8 && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            terceraParteCuit.setFocusable(true);
            terceraParteCuit.requestFocusInWindow();
        }
}//GEN-LAST:event_segundaParteCuitKeyReleased

    private void segundaParteCuitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_segundaParteCuitKeyPressed
        String texto = segundaParteCuit.getText();
        if (texto.length() == 0 && evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            primeraParteCuit.setFocusable(true);
            primeraParteCuit.requestFocusInWindow();
        }
    }//GEN-LAST:event_segundaParteCuitKeyPressed

    private void terceraParteCuitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_terceraParteCuitKeyPressed
        String texto = terceraParteCuit.getText();
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && texto.length() == 0) {
            segundaParteCuit.setFocusable(true);
            segundaParteCuit.requestFocusInWindow();

        }
    }//GEN-LAST:event_terceraParteCuitKeyPressed

    private void terceraParteCuitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_terceraParteCuitKeyReleased
        String texto = terceraParteCuit.getText();
        if (texto.length() > 1) {
            texto = texto.substring(0, 1);
            terceraParteCuit.setText(texto);
        }
    }//GEN-LAST:event_terceraParteCuitKeyReleased

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (JOptionPane.showConfirmDialog(this, "El Cliente no se va a guardar, ¿Desea cerrar de todas maneras?",
                    "Advertencia", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/important.png"))) == JOptionPane.OK_OPTION) {
                canceled = true;
                this.dispose();
            }
        }
    }//GEN-LAST:event_formKeyPressed

    public JTextField getCodPostEmpresajTextField() {
        return codPostEmpresajTextField;
    }

    public JTextField getCorreoEmpresajTextField() {
        return correoEmpresajTextField;
    }

    public JTextField getDireccionEmpresajTextField() {
        return direccionEmpresajTextField;
    }

    public JTextField getFaxClientejTextField() {
        return faxClientejTextField;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public JButton getjButton2() {
        return jButton2;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JTextField getLocalidadEmpresajTextField() {
        return localidadEmpresajTextField;
    }

    public JTextField getNombreEmpresajTextField() {
        return nombreEmpresajTextField;
    }

    public JTextField getPrimeraParteCuit() {
        return primeraParteCuit;
    }

    public JComboBox getProvinciaComboBox() {
        return provinciaComboBox;
    }

    public JTextField getSegundaParteCuit() {
        return segundaParteCuit;
    }

    public JTextField getTelefono2EmpresajTextField() {
        return telefono2EmpresajTextField;
    }

    public JTextField getTelefonoEmpresajTextField() {
        return telefonoEmpresajTextField;
    }

    public JTextField getTerceraParteCuit() {
        return terceraParteCuit;
    }

    /**
     * Retorna si se cancelo la inserción o actualización
     * @return true si cancelo
     */
    public boolean wasCanceled() {
        return canceled;
    }
    ///variable que indica si se cancelo
    private boolean canceled = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codPostEmpresajTextField;
    private javax.swing.JTextField correoEmpresajTextField;
    private javax.swing.JTextField direccionEmpresajTextField;
    private javax.swing.JTextField faxClientejTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField localidadEmpresajTextField;
    private javax.swing.JTextField nombreEmpresajTextField;
    private javax.swing.JTextField primeraParteCuit;
    private javax.swing.JComboBox provinciaComboBox;
    private javax.swing.JTextField segundaParteCuit;
    private javax.swing.JTextField telefono2EmpresajTextField;
    private javax.swing.JTextField telefonoEmpresajTextField;
    private javax.swing.JTextField terceraParteCuit;
    // End of variables declaration//GEN-END:variables
}
