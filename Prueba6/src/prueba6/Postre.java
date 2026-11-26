
package prueba6;

import prueba6.Enums.Categoria;


public class Postre extends Producto {

    private String sabor;

    public Postre(int codigo, String nombre, double precio, String sabor) {
        super(codigo, nombre, precio, Categoria.POSTRE);
        this.sabor = sabor;
    }

    public String getSabor() {
        return sabor;
    }

    public String getDetalle() {
        return "Sabor: " + sabor;
    }
}