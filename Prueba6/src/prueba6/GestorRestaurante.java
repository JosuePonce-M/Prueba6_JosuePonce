
package prueba6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import prueba6.Excepciones.DatoInvalidoException;

public class GestorRestaurante {

    private ArrayList<Producto> productos;
    private ArrayList<Pedido> pedidos;

    public GestorRestaurante() {
        productos = new ArrayList<>();
        pedidos = new ArrayList<>();
        File carpeta = new File("restaurante");
        carpeta.mkdir();
    }

    public Producto buscarProducto(int codigo) {
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    public void agregarProducto(Producto producto) throws DatoInvalidoException {
        if (producto.getCodigo() <= 0) {
            throw new DatoInvalidoException("El codigo debe ser mayor a 0.");
        }
        if (producto.getNombre().isEmpty()) {
            throw new DatoInvalidoException("El nombre no puede estar vacio.");
        }
        if (producto.getPrecio() <= 0) {
            throw new DatoInvalidoException("El precio debe ser mayor a 0.");
        }
        if (buscarProducto(producto.getCodigo()) != null) {
            throw new DatoInvalidoException("Ya existe un producto con el codigo " + producto.getCodigo() + ".");
        }
        productos.add(producto);
    }

    public void agregarPedido(Pedido pedido) throws DatoInvalidoException {
        if (pedido.getDetalles().isEmpty()) {
            throw new DatoInvalidoException("No se puede crear un pedido sin productos.");
        }
        pedido.setId(pedidos.size() + 1);
        pedidos.add(pedido);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void guardar() throws IOException {
        ObjectOutputStream salidaProductos = new ObjectOutputStream(new FileOutputStream("restaurante/productos.dat"));
        salidaProductos.writeObject(productos);
        salidaProductos.close();

        ObjectOutputStream salidaPedidos = new ObjectOutputStream(new FileOutputStream("restaurante/pedidos.dat"));
        salidaPedidos.writeObject(pedidos);
        salidaPedidos.close();
    }

    public boolean cargar() throws IOException, ClassNotFoundException {
        boolean huboDatos = false;

        File archivoProductos = new File("restaurante/productos.dat");
        if (archivoProductos.exists()) {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivoProductos));
            productos = (ArrayList<Producto>) entrada.readObject();
            entrada.close();
            huboDatos = true;
        }

        File archivoPedidos = new File("restaurante/pedidos.dat");
        if (archivoPedidos.exists()) {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivoPedidos));
            pedidos = (ArrayList<Pedido>) entrada.readObject();
            entrada.close();
            huboDatos = true;
        }

        return huboDatos;
    }
}