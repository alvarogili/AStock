/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BD.Utilidades;

/**
 * Clase que contiene el nombre del skin actual
 * @author Alvaro Gili
 */
public class Skin {

    ///indica de que es el skin, por ejemplo aplicacion
    private String skinType;

    ///nombre del skin
    private String skinName;

    public Skin() {
    }

    public String appGetSkinName() {
        return skinName;
    }

    public void appSetSkinName(String skinName) {
        this.skinName = skinName;
    }

    public String appGetSkinType() {
        return skinType;
    }

    public void appSetSkinType(String skinType) {
        this.skinType = skinType;
    }

}
