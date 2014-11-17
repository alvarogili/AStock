/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agili.astock.BD.Agenda;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Representa un evento de un dia
 * @author Alvaro Gili
 */

@Entity
public class Evento {

    @Column(nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date horaYFecha;
    @Column
    private String categoria;
    @Column
    private String descripcion;
    @Column
    private boolean alarma;
    //indica si el usuario vió el evento
    @Column
    private boolean mostrado;
    @Id
    private Long id;

    /**
     * Constructor
     */
    public Evento() {
    }

    public boolean appGetAlarma() {
        return alarma;
    }

    public void appSetAlarma(boolean alarma) {
        this.alarma = alarma;
    }

    public String appGetCategoria() {
        return categoria;
    }

    public void appSetCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date appGetHoraYFecha() {
        return horaYFecha;
    }

    public String appGetHoraYFechaHowString(){
        String result = null;
        if(horaYFecha.getDate() < 10)
            result = "0";
        result = horaYFecha.getDate() + "/";
        if((horaYFecha.getMonth() + 1) < 10)
            result += "0";
        result += (horaYFecha.getMonth() + 1) + "/" + (horaYFecha.getYear() + 1900) + " ";
        if((horaYFecha.getHours() + 1) < 10)
            result += "0";
        result += horaYFecha.getHours() + ":";
        if(horaYFecha.getMinutes() < 10)
            result += "0";
        result += horaYFecha.getMinutes();
        return result;
    }

    public void appSetHoraYFecha(Date horaYFecha) {
        this.horaYFecha = horaYFecha;
    }

    public boolean appGetMostrado() {
        return mostrado;
    }

    public void appSetMostrado(boolean mostrado) {
        this.mostrado = mostrado;
    }

    public String appGetDescripción() {
        return descripcion;
    }

    public void appSetDescripción(String descripción) {
        this.descripcion = descripción;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}
