
package prueba6;

import prueba6.Enums.Categoria;


  import java.io.Serializable;

public class Producto implements Serializable {

    private int codigo;
    private String nombre;
    private double precio;
    private Categoria categoria;

    public Producto(int codigo, String nombre, double precio, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getDetalle() {
        return "";
    }

    public String toString() {
        return codigo + " - " + nombre + " - Lps." + precio;
    }
}  

