/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BD.Utilidades;

/**
 * Contiene la configuración de la contraseña de administrador
 * @author Alvaro Gili
 */
public class PasswordAdmin {

    private boolean solicitarPassInicio;
    private String passAdmin;

    public PasswordAdmin() {
    }

    public String appGetPassAdmin() {
        return passAdmin;
    }

    public void appSetPassAdmin(String passAdmin) {
        this.passAdmin = passAdmin;
    }

    public boolean appGetSolicitarPassInicio() {
        return solicitarPassInicio;
    }

    public void appSetSolicitarPassInicio(boolean solicitarPassInicio) {
        this.solicitarPassInicio = solicitarPassInicio;
    }



}
