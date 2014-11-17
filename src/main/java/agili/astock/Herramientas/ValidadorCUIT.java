
package Herramientas;

/**
 * Valida CUITs
 * @author CHETURA
 */
public class ValidadorCUIT {

    /** 
     * Creates a new instance of ValidadorCIUT
     */
    public void ValidadorCUIT() {
    }

    /**
     * Verifica si un CUIT es correcto
     * @param cuit CUIT de la forma 20-29494566-1
     * @return true si es correcto
     */
    public boolean verify(String cuit) {
        if (cuit == null || cuit.length() > 13 || cuit.length() < 12 || cuit.charAt(2) != '-') {
            return false;
        }
        if(cuit.length() == 12){
            String temp = cuit.substring(0, 3);
            temp += "0";
            temp += cuit.substring(3, 12);
            cuit = temp;
        }
        int valor = 11 - ((Character.getNumericValue(cuit.charAt(0)) * 5 +
                Character.getNumericValue(cuit.charAt(1)) * 4 +
                Character.getNumericValue(cuit.charAt(3)) * 3 +
                Character.getNumericValue(cuit.charAt(4)) * 2 +
                Character.getNumericValue(cuit.charAt(5)) * 7 +
                Character.getNumericValue(cuit.charAt(6)) * 6 +
                Character.getNumericValue(cuit.charAt(7)) * 5 +
                Character.getNumericValue(cuit.charAt(8)) * 4 +
                Character.getNumericValue(cuit.charAt(9)) * 3 +
                Character.getNumericValue(cuit.charAt(10)) * 2) % 11);

        switch (valor) {
            case 11:
                valor = 0;
                break;
            case 10:
                valor = 9;
                break;
        }
        return Character.getNumericValue(cuit.charAt(12)) == valor;
    }

    /**
     * Genera el ultimo número del CUIT
     * @param cuit Ejemplo: 20-29494566 --> retorna 1
     * @return Ultimo número o -1 en caso de error
     */
    public int generarUltimoNumero(String cuit) {
        if (cuit == null || cuit.length() > 11 || cuit.length() < 10 || cuit.charAt(2) != '-') {
            return -1;
        }
        if(cuit.length() == 10){
            String temp = cuit.substring(0, 3);
            temp += "0";
            temp += cuit.substring(3, 10);
            cuit = temp;
        }
        int valor = 11 - ((Character.getNumericValue(cuit.charAt(0)) * 5 +
                Character.getNumericValue(cuit.charAt(1)) * 4 +
                Character.getNumericValue(cuit.charAt(3)) * 3 +
                Character.getNumericValue(cuit.charAt(4)) * 2 +
                Character.getNumericValue(cuit.charAt(5)) * 7 +
                Character.getNumericValue(cuit.charAt(6)) * 6 +
                Character.getNumericValue(cuit.charAt(7)) * 5 +
                Character.getNumericValue(cuit.charAt(8)) * 4 +
                Character.getNumericValue(cuit.charAt(9)) * 3 +
                Character.getNumericValue(cuit.charAt(10)) * 2) % 11);

        switch (valor) {
            case 11:
                valor = 0;
                break;
            case 10:
                valor = 9;
                break;
        }
        return valor;
    }
}
