/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BD.Personas;

/**
 * Cliente de la empresa
 *
 * @author Alvaro Gili
 */
public class PersonaCliente extends Persona {

     /**
     * Constructor
     *
     * @param nombre: Nombre de la persona
     * @param apellido: Apellido de la persona
     * @param dni: Número de DNI. Ej: 29494566
     * @param direccion: Dirección postal
     * @param codigoPostal: Código postal
     * @param localidad: Localidad de residencia
     * @param provincia: Provincia de residencia
     * @param telefono: Telefono principal
     * @param telefono2: Telefono secundario;
     * @param correo: Correo electrónico
     * @param fax: Número de fax
     * @param cumpleaños: cumpleaños
     */
    public PersonaCliente(String nombre, String apellido, String dni,
            String direccion, String codPost, String localidad,
            String provincia, String telefono, String telefono2,
            String correo, String fax, String cumpleaños){
        super(nombre, apellido, dni, direccion, codPost, localidad, provincia, telefono, telefono2, correo, fax, cumpleaños);
    }
}
