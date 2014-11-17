
package BD.Personas;

/**
 * Clase que representa a un objeto persona
 * @author Alvaro Gili
 */

public class Persona {

    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String telefono;
    private String telefono2;
    private String correo;
    private String fax;
    private String cumpleaños;


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
     * @param fax: Fax
     * @param cumpleaños; Cumpleaños
     */
    public Persona(String nombre, String apellido, String dni,
            String direccion, String codPost, String localidad,
            String provincia, String telefono, String telefono2,
            String correo, String fax, String cumpleaños){

            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
            this.direccion = direccion;
            this.codigoPostal = codPost;
            this.localidad = localidad;
            this.provincia = provincia;
            this.telefono = telefono;
            this.telefono2 = telefono2;
            this.correo = correo;
            this.fax = fax;
            this.cumpleaños = cumpleaños;
    }

    /**
     *
     * @return Restorna el apellido de la persona
     */
    public String appGetApellido() {
        return apellido;
    }

    /**
     * Permite cambiar el apellido de la persona
     * @param apellido
     */
    public void appSetApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     *
     * @return Restorna el Codigo Postal de la persona
     */
    public String appGetCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Permite cambiar el codigo postal de la persona
     * @param codigoPostal
     */
    public void appSetCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     *
     * @return Restorna el correo de la persona
     */
    public String appGetCorreo() {
        return correo;
    }

    /**
     * Permite cambiar el correo de la persona
     * @param correo
     */
    public void appSetCorreo(String correo) {
        this.correo = correo;
    }

    /**
     *
     * @return Restorna la direccion de la persona
     */
    public String appGetDireccion() {
        return direccion;
    }

    /**
     * Permite cambiar la direccion de la persona
     * @param direccion
     */
    public void appSetDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return Restorna el DNI de la persona
     */
    public String appGetDni() {
        return dni;
    }

    /**
     * Permite cambiar el dni de la persona
     * @param dni
     */
    public void appSetDni(String dni) {
        this.dni = dni;
    }

    /**
     *
     * @return Restorna la localidad de la persona
     */
    public String appGetLocalidad() {
        return localidad;
    }

    /**
     * Permite cambiar la localidad de la persona
     * @param localidad
     */
    public void appSetLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     *
     * @return Restorna el nombre de la persona
     */
    public String appGetNombre() {
        return nombre;
    }

    /**
     * Permite cambiar el nombre de la persona
     * @param nombre
     */
    public void appSetNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return Restorna el provincia de la persona
     */
    public String appGetProvincia() {
        return provincia;
    }

    /**
     * Permite cambiar la provincia de la persona
     * @param provincia
     */
    public void appSetProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     *
     * @return Restorna el telefono de la persona
     */
    public String appGetTelefono() {
        return telefono;
    }

    /**
     * Permite cambiar el telefono de la persona
     * @param telefono
     */
    public void appSetTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return Restorna el telefono secundario de la persona
     */
    public String appGetTelefono2() {
        return telefono2;
    }

    /**
     * Permite cambiar el telefono secundario de la persona
     * @param telefono2
     */
    public void appSetTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    /**
     *
     * @return Restorna el fax de la persona
     */
    public String appGetFax() {
        return fax;
    }

    /**
     * Permite cambiar el fax de la persona
     * @param telefono2
     */
    public void appSetFax(String fax) {
        this.fax = fax;
    }

    /**
     * Retorna el cumpleaños de una persona
     * @return Fecha de cumpleaños
     */
    public String appGetCumpleaños() {
        return cumpleaños;
    }

    /**
     * Permite cambiar el cumpleaños de una persona
     * @param cumpleaños cumpleaños a guardar
     */
    public void appSetCumpleaños(String cumpleaños) {
        this.cumpleaños = cumpleaños;
    }



}
