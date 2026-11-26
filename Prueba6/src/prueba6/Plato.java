
package prueba6;

import prueba6.Enums.Categoria;


public class Plato extends Producto {

    private int minutosP;

    public Plato(int codigo, String nombre, double precio, int minutosP) {
        super(codigo, nombre, precio, Categoria.PLATO);
        this.minutosP = minutosP;
    }

    public int getMinutosPreparacion() {
        return minutosP;
    }

    public String getDetalle() {
        return "Preparacion: " + minutosP + " min";
    }
}