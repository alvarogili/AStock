/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BD.Empresas;

/**
 * Cliente de la empresa
 *
 * @author Alvaro Gili
 */
public class EmpresaCliente extends Empresa {

     /**
     * Constructor
     *
     * @param nombre: Nombre de la empresa
     * @param dni: Número de DNI. Ej: 20-29494566-1
     * @param direccion: Dirección postal
     * @param codigoPostal: Código postal
     * @param localidad: Localidad de residencia
     * @param provincia: Provincia de residencia
     * @param telefono: Telefono principal
     * @param telefono2: Telefono secundario;
     * @param correo: Correo electrónico
     * @param fax: Número de fax
     */
    public EmpresaCliente(String nombre, String cuit,
            String direccion, String codPost, String localidad,
            String provincia, String telefono, String telefono2,
            String correo, String fax){
        super(nombre, cuit, direccion, codPost, localidad, provincia, telefono, telefono2, correo, fax);
    }
    
}
