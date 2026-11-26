
package prueba6;

import prueba6.Enums.Categoria;



    public class Bebida extends Producto {

    private int mililitros;

    public Bebida(int codigo, String nombre, double precio, int mililitros) {
        super(codigo, nombre, precio, Categoria.BEBIDA);
        this.mililitros = mililitros;
    }

    public int getMililitros() {
        return mililitros;
    }

    public String getDetalle() {
        return "Tamano: " + mililitros + " ml";
    }
}

