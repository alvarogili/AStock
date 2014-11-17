/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agili.astock.Agenda;

import Agenda.RenderAgendaDia;
import agili.astock.BD.AccesosBD;
import agili.astock.BD.Agenda.Evento;
import java.awt.event.*;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Alvaro Gili
 */
public class MediadorAgenda implements ActionListener, MouseListener, ItemListener, KeyListener {

    private Agenda agenda = new Agenda();
    private static String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private GregorianCalendar calendar = new GregorianCalendar();
    private int mesCorriente;
    private int añoCorriente;
    private int diaCorriente;
    private RenderCalendario miRender = new RenderCalendario();
    private boolean flag = true;//indica si es el inicio o no
    private AccesosBD abd;
    private ManejadorGrillaEventos manejadorGrillaEventos = new ManejadorGrillaEventos(agenda.getjTableDiaAgenda(), abd);
    //indica si el evento actual se está editando o no
    private boolean editando = false;
    static private MediadorAgenda singleton = null;

    /**
     * Metodo que retorna una instancia de la clase
     * @return Instancia de la clase
     */
    static public MediadorAgenda getInstance() {
        if (singleton == null) {
            singleton = new MediadorAgenda();
        }
        singleton.agenda.setVisible(true);
        return singleton;
    }

    private MediadorAgenda() {
        mesCorriente = calendar.get(GregorianCalendar.MONTH);
        añoCorriente = calendar.get(GregorianCalendar.YEAR);
        diaCorriente = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        //cargo el render para el calendario
        miRender.setMesYAño(mesCorriente, añoCorriente);
        agenda.getjTableCalendario().getColumnModel().getColumn(0).setCellRenderer(miRender);
        agenda.getjTableCalendario().getColumnModel().getColumn(1).setCellRenderer(miRender);
        agenda.getjTableCalendario().getColumnModel().getColumn(2).setCellRenderer(miRender);
        agenda.getjTableCalendario().getColumnModel().getColumn(3).setCellRenderer(miRender);
        agenda.getjTableCalendario().getColumnModel().getColumn(4).setCellRenderer(miRender);
        agenda.getjTableCalendario().getColumnModel().getColumn(5).setCellRenderer(miRender);
        agenda.getjTableCalendario().getColumnModel().getColumn(6).setCellRenderer(miRender);

        //cargo el render de la agenda diaria
        RenderAgendaDia renderAgendaDia = new RenderAgendaDia();
        agenda.getjTableDiaAgenda().getColumnModel().getColumn(2).setCellRenderer(renderAgendaDia);

        cargarMes(mesCorriente, añoCorriente);
        agenda.setLocationRelativeTo(agenda.getParent());
        agenda.setVisible(true);
        agenda.getjButtonMesAnterior().addActionListener(this);
        agenda.getjButtonMesSiguiente().addActionListener(this);
        agenda.getjTableCalendario().setRowSelectionAllowed(false);
        agenda.getjTableCalendario().setColumnSelectionAllowed(false);
        agenda.getjTableCalendario().setCellSelectionEnabled(true);
        agenda.getjTableCalendario().addMouseListener(this);
        agenda.getjTableCalendario().addKeyListener(this);
        agenda.getjButtonCancelarGuardado().addActionListener(this);
        agenda.getjButtonGuardarEvento().addActionListener(this);
        agenda.getjButtonBorrarEvento().addActionListener(this);
        agenda.getjSpinnerAño().addKeyListener(this);
        agenda.getjTableDiaAgenda().addKeyListener(this);
        agenda.getjTableDiaAgenda().addMouseListener(this);
        agenda.getjSpinnerAño().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                añoCorriente = new Integer(agenda.getjSpinnerAño().getValue().toString());
                miRender.setMesYAño(mesCorriente, añoCorriente);
                cargarMes(mesCorriente, añoCorriente);
                agenda.getjButtonNuevoEvento().setEnabled(false);
                agenda.getjTableCalendario().clearSelection();
            }
        });
        agenda.getjButtonNuevoEvento().addActionListener(this);
        agenda.getjComboBoxCategorias().addItemListener(this);

        agenda.getjComboBoxCategorias().setSelectedItem(null);
        Date temp = new Date(añoCorriente - 1900, mesCorriente, diaCorriente, 0, 0);
        agenda.getjSpinnerHoraYFechaNuevoEvento().getModel().setValue(temp);
        cambiarCamposEventos(false);

        manejadorGrillaEventos.actualizaGrilla(añoCorriente, mesCorriente, diaCorriente);

    }

    public void setAbd(AccesosBD abd) {
        this.abd = abd;
    }       

    private void cargarMes(int mes, int año) {

        agenda.getjTextFieldMes().setText(meses[mes]);
        agenda.getjSpinnerAño().setValue(año);
        //calendario para obtener el primer dia del mes
        GregorianCalendar calPrimerDiaMes = new GregorianCalendar(año, mes, 1);
        int primerDia = calPrimerDiaMes.get(GregorianCalendar.DAY_OF_WEEK);

        //veamos cuantos dias tiene el mes
        int cantidadDeDiasMes;
        if (mes == 10 || mes == 3 || mes == 5 || mes == 8) {
            cantidadDeDiasMes = 30;
        } else if (mes == 1) {//es febrero, ver si es año bisiesto
            if ((año % 4 == 0) && ((año % 100 != 0) || (año % 400 == 0))) {
                //es bisiesto
                cantidadDeDiasMes = 29;
            } else {
                cantidadDeDiasMes = 28;
            }
        } else {
            cantidadDeDiasMes = 31;
        }
        //comienzo a rellenar la tabla
        int dia = 1;
        //primer semana
        for (int i = 0; i < 7; i++) {
            if (i < primerDia - 1) {//si no va número de día, inserto blanco
                //       ver que no se pueda elegir
                agenda.getjTableCalendario().setValueAt(null, 0, i);

            } else {
                agenda.getjTableCalendario().setValueAt(dia, 0, i);
                dia++;
            }
        }
        int semana = 1;
        while (dia <= cantidadDeDiasMes || semana < 6) {
            int i;
            for (i = 0; i < 7 && dia <= cantidadDeDiasMes; i++) {
                agenda.getjTableCalendario().setValueAt(dia, semana, i);
                dia++;
            }
            if (dia > cantidadDeDiasMes) {
                //termino de completar la tabla con blancos
                for (; i < 7; i++) {
                    agenda.getjTableCalendario().setValueAt(null, semana, i);
                }
            }
            //siguiente semana
            semana++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(agenda.getjButtonMesAnterior())) {
            if (mesCorriente > 0) {
                mesCorriente--;
            } else { //esta en enero
                mesCorriente = 11;
                añoCorriente--;

            }
            miRender.setMesYAño(mesCorriente, añoCorriente);
            cargarMes(mesCorriente, añoCorriente);
            agenda.getjButtonNuevoEvento().setEnabled(false);
            agenda.getjTableCalendario().clearSelection();

        } else if (e.getSource().equals(agenda.getjButtonMesSiguiente())) {
            if (mesCorriente < 11) {
                mesCorriente++;
            } else { //esta en enero
                mesCorriente = 0;
                añoCorriente++;

            }
            miRender.setMesYAño(mesCorriente, añoCorriente);
            cargarMes(mesCorriente, añoCorriente);
            agenda.getjButtonNuevoEvento().setEnabled(false);
            agenda.getjTableCalendario().clearSelection();

        } else if (e.getSource().equals(agenda.getjButtonNuevoEvento())) {
            //habilito todos los campos para un nuevo evento
            cambiarCamposEventos(true);
            agenda.getjComboBoxCategorias().setSelectedIndex(-1);
            agenda.getjTextFieldDescripcion().setText(null);
            agenda.getjCheckBoxAlarma().setSelected(false);
            agenda.getjLabelImagen().setIcon(null);
            Date temp = new Date(añoCorriente - 1900, mesCorriente, diaCorriente, 0, 0);
            agenda.getjSpinnerHoraYFechaNuevoEvento().getModel().setValue(temp);
            editando = false;
        } else if (e.getSource().equals(agenda.getjButtonGuardarEvento())) {

            Evento evento = null;
            String verificacion = null;
            if (editando) {//si se está editando obtengo desde el vector y con este busco en la base de datos
                Evento temp = (Evento) manejadorGrillaEventos.getListaEventos()[agenda.getjTableDiaAgenda().getSelectedRow()];
                verificacion = "categoria.equals('" + temp.appGetCategoria() + "') && descripcion.equals('" + temp.appGetDescripción() + "')";
                evento = abd.getEvento(verificacion, temp.appGetHoraYFecha());
            } else {
                evento = new Evento();
            }
            evento.appSetHoraYFecha((Date) agenda.getjSpinnerHoraYFechaNuevoEvento().getValue());
            evento.appSetAlarma(agenda.getjCheckBoxAlarma().isSelected());
            if (agenda.getjComboBoxCategorias().getSelectedItem() == null) {
                JOptionPane.showMessageDialog(agenda,
                        "Por favor elija una categoria.", "Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/error.gif")));
                return;
            }
            evento.appSetCategoria((String) agenda.getjComboBoxCategorias().getSelectedItem());
            evento.appSetDescripción(agenda.getjTextFieldDescripcion().getText());

            boolean flag1 = true;
            if (!editando) {
                flag1 = abd.guardarEnBD(evento);//si hubo un error se hace false
            } else {
                abd.actualizarObjeto(Evento.class, evento, verificacion);
            }

            if (flag1) {
                //actualizar grilla eventos
                manejadorGrillaEventos.actualizaGrilla(añoCorriente, mesCorriente, diaCorriente);
                //deshabilitar todos los campos de evento
                cambiarCamposEventos(false);
            } else {
                JOptionPane.showMessageDialog(agenda,
                        "Hubo un problema al guardar el evento.", "Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(getClass().getResource("/img/error.gif")));
            }
        } else if (e.getSource().equals(agenda.getjButtonCancelarGuardado())) {
            cambiarCamposEventos(false);
        } else if (e.getSource().equals(agenda.getjButtonBorrarEvento())) {
            Evento evento = (Evento) manejadorGrillaEventos.getListaEventos()[agenda.getjTableDiaAgenda().getSelectedRow()];
            if (JOptionPane.showConfirmDialog(agenda, "¿Realmente desea borrar este evento?",
                    "Confirmación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon(getClass().getResource("/img/svn_status.png"))) == JOptionPane.YES_OPTION) {

                if (abd.borrarObjeto(evento)) {

                    //actualizar grilla eventos
                    manejadorGrillaEventos.actualizaGrilla(añoCorriente, mesCorriente, diaCorriente);
                    cambiarCamposEventos(false);
                    agenda.getjButtonBorrarEvento().setEnabled(false);

                    JOptionPane.showMessageDialog(agenda,
                            "El evento ha sido borrado correctamente", "Confirmaciòn", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/dialog-ok.png")));
                } else {
                    JOptionPane.showMessageDialog(agenda,
                            "Hubo un problema al borrar el evento.", "Error", JOptionPane.ERROR_MESSAGE,
                            new ImageIcon(getClass().getResource("/img/error.gif")));
                }
            }
        }
    }

    /**
     * Habilita y dehabilita todos los campos para agregar, editar y borrar un evento
     * @param habilitar true: habilita, false: deshabilita
     */
    public void cambiarCamposEventos(boolean habilitar) {
        agenda.getjSpinnerHoraYFechaNuevoEvento().setEnabled(habilitar);
        agenda.getjLabelFechaYHora().setEnabled(habilitar);
        agenda.getjLabelCategoria().setEnabled(habilitar);
        agenda.getjComboBoxCategorias().setEnabled(habilitar);
        agenda.getjLabelDescripcion().setEnabled(habilitar);
        agenda.getjTextFieldDescripcion().setEnabled(habilitar);
        agenda.getjCheckBoxAlarma().setEnabled(habilitar);
        agenda.getjButtonGuardarEvento().setEnabled(habilitar);
        agenda.getjButtonCancelarGuardado().setEnabled(habilitar);
        if (!habilitar) {
            agenda.getjComboBoxCategorias().setSelectedIndex(-1);
            agenda.getjTextFieldDescripcion().setText("");
            agenda.getjCheckBoxAlarma().setSelected(false);
            agenda.getjLabelImagen().setIcon(null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(agenda.getjTableCalendario())) {
            cambioDeDia();

        } else if (e.getSource().equals(agenda.getjTableDiaAgenda())) {
            mostrarEvento();
        }
    }

    /**
     * Muestra el evento seleccionado para editarlo
     */
    private void mostrarEvento() {
        //habilito todo para edición del evento
        cambiarCamposEventos(true);
        Evento evento = new Evento();
        int selectedRow = agenda.getjTableDiaAgenda().getSelectedRow();
        evento = (Evento) manejadorGrillaEventos.getListaEventos()[selectedRow];
        agenda.getjSpinnerHoraYFechaNuevoEvento().setValue(evento.appGetHoraYFecha());
        agenda.getjTextFieldDescripcion().setText(evento.appGetDescripción());
        agenda.getjCheckBoxAlarma().setSelected(evento.appGetAlarma());
        agenda.getjComboBoxCategorias().setSelectedItem(evento.appGetCategoria());
        cambiarImagenCategoria(evento.appGetCategoria());
        editando = true;
        agenda.getjButtonBorrarEvento().setEnabled(true);
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
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (!flag) {//si es el inicio no hace nada
            if (e.getSource().equals(agenda.getjComboBoxCategorias())) {
                if (agenda.getjComboBoxCategorias().getSelectedIndex() != -1) {
                    cambiarImagenCategoria(agenda.getjComboBoxCategorias().getSelectedItem().toString());
                }
            }
        } else {
            flag = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource().equals(agenda.getjSpinnerAño())) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                añoCorriente = new Integer(agenda.getjSpinnerAño().getValue().toString());
                miRender.setMesYAño(mesCorriente, añoCorriente);
                cargarMes(mesCorriente, añoCorriente);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(agenda.getjComboBoxCategorias())) {
            cambiarImagenCategoria(agenda.getjComboBoxCategorias().getSelectedItem().toString());
        } else if (e.getSource().equals(agenda.getjTableDiaAgenda())) {
            mostrarEvento();
        } else if (e.getSource().equals(agenda.getjTableCalendario())) {
            cambioDeDia();
        }
    }

    /**
     * Se ejecuta cuando se cambia de dia haciendo
     * click o con el teclado
     */
    private void cambioDeDia() {
        try {
            agenda.getjButtonNuevoEvento().setEnabled(true);
            //actualizo la fecha en el campo jSpinnerHoraYFechaNuevoEvento

            int dia = (Integer) agenda.getjTableCalendario().getValueAt(agenda.getjTableCalendario().getSelectedRow(),
                    agenda.getjTableCalendario().getSelectedColumn());
            Date temp = new Date(añoCorriente, mesCorriente, dia, 0, 0);
            diaCorriente = dia;
            agenda.getjSpinnerHoraYFechaNuevoEvento().getModel().setValue(temp);
            cambiarCamposEventos(false);
            //actualizo los eventos del dia
            manejadorGrillaEventos.actualizaGrilla(añoCorriente, mesCorriente, diaCorriente);
        } catch (Exception e) {
            agenda.getjButtonNuevoEvento().setEnabled(false);
        }
    }

    void cambiarImagenCategoria(String categoria) {
        //dependiendo del elegido coloco la imagen
        if (categoria == null) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/flag.png")));
        } else if (categoria.equals("Académico")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/books_048.png")));
        } else if (categoria.equals("Aniversario")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/package_favorite.png")));
        } else if (categoria.equals("Clientes")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/system-users.png")));
        } else if (categoria.equals("Cumpleaños")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/cumpleaños.png")));
        } else if (categoria.equals("Día festivo")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/balloons48.png")));
        } else if (categoria.equals("Feriado")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/date48.png")));
        } else if (categoria.equals("Importante")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/important_calendar.png")));
        } else if (categoria.equals("Llamada")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/internet-telephony.png")));
        } else if (categoria.equals("Médico")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/Ambulance.png")));
        } else if (categoria.toString().equals("Ocio")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/kteatime.png")));
        } else if (categoria.toString().equals("Proveedores")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/LorryGreen.png")));
        } else if (categoria.toString().equals("Proyecto")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/file-roller.png")));
        } else if (categoria.toString().equals("Recordatorio")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/knotes.png")));
        } else if (categoria.toString().equals("Trabajo")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/agt_utilities.png")));
        } else if (categoria.toString().equals("Vacaciones")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/Umbrella-48x48.png")));
        } else if (categoria.toString().equals("Viaje")) {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/plane.png")));
        } else {
            agenda.getjLabelImagen().setIcon(new ImageIcon(getClass().getResource("/img/flag.png")));
        }
    }
}
