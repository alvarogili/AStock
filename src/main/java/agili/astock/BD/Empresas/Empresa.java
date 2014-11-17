package BD.Empresas;

/**
 * Clase que representa a un objeto empresa
 * @author Alvaro Gili
 */
public class Empresa {

    private String nombre;
    private String cuit;
    private String direccion;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String telefono;
    private String telefono2;
    private String correo;
    private String fax;

    /**
     * Constructor
     *
     * @param nombre: Nombre de la empresa
     * @param cuit: Número de CUIT. Ej: 20-29494566-1
     * @param direccion: Dirección postal
     * @param codigoPostal: Código postal
     * @param localidad: Localidad de residencia
     * @param provincia: Provincia de residencia
     * @param telefono: Telefono principal
     * @param telefono2: Telefono secundario;
     * @param correo: Correo electrónico
     * @param fax: Fax
     */
    public Empresa(String nombre, String cuit,
            String direccion, String codPost, String localidad,
            String provincia, String telefono, String telefono2,
            String correo, String fax) {

        this.nombre = nombre;
        this.cuit = cuit;
        this.direccion = direccion;
        this.codigoPostal = codPost;
        this.localidad = localidad;
        this.provincia = provincia;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.correo = correo;
        this.fax = fax;
    }

    /**
     *
     * @return Restorna el Codigo Postal de la empresa
     */
    public String appGetCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Permite cambiar el codigo postal de la empresa
     * @param codigoPostal
     */
    public void appSetCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     *
     * @return Restorna el correo de la empresa
     */
    public String appGetCorreo() {
        return correo;
    }

    /**
     * Permite cambiar el correo de la empresa
     * @param correo
     */
    public void appSetCorreo(String correo) {
        this.correo = correo;
    }

    /**
     *
     * @return Restorna la direccion de la empresa
     */
    public String appGetDireccion() {
        return direccion;
    }

    /**
     * Permite cambiar la direccion de la empresa
     * @param direccion
     */
    public void appSetDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return Restorna el CUIT de la empresa
     */
    public String appGetCuit() {
        return cuit;
    }

    /**
     * Permite cambiar el cuit de la empresa
     * @param cuit
     */
    public void appSetCuit(String cuit) {
        this.cuit = cuit;
    }

    /**
     *
     * @return Restorna la localidad de la empresa
     */
    public String appGetLocalidad() {
        return localidad;
    }

    /**
     * Permite cambiar la localidad de la empresa
     * @param localidad
     */
    public void appSetLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     *
     * @return Restorna el nombre de la empresa
     */
    public String appGetNombre() {
        return nombre;
    }

    /**
     * Permite cambiar el nombre de la empresa
     * @param nombre
     */
    public void appSetNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return Restorna el provincia de la empresa
     */
    public String appGetProvincia() {
        return provincia;
    }

    /**
     * Permite cambiar la provincia de la empresa
     * @param provincia
     */
    public void appSetProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     *
     * @return Restorna el telefono de la empresa
     */
    public String appGetTelefono() {
        return telefono;
    }

    /**
     * Permite cambiar el telefono de la empresa
     * @param telefono
     */
    public void appSetTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return Restorna el telefono secundario de la empresa
     */
    public String appGetTelefono2() {
        return telefono2;
    }

    /**
     * Permite cambiar el telefono secundario de la empresa
     * @param telefono2
     */
    public void appSetTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    /**
     *
     * @return Restorna el fax de la empresa
     */
    public String appGetFax() {
        return fax;
    }

    /**
     * Permite cambiar el fax de la empresa
     * @param telefono2
     */
    public void appSetFax(String fax) {
        this.fax = fax;
    }
}
