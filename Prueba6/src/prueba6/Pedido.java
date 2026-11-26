
package prueba6;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import prueba6.Enums.Estado;
import prueba6.Enums.Modalidad;
import prueba6.Excepciones.DatoInvalidoException;

public class Pedido implements Serializable {

    private int id;
    private Date fecha;
    private ArrayList<InfoPedido> detalles;
    private Modalidad modalidad;
    private Estado estado;

    public Pedido() {
        fecha = new Date();
        detalles = new ArrayList<>();
        modalidad = Modalidad.ESTABLECIMIENTO;
        estado = Estado.PENDIENTE;
    }

   public void agregarProducto(Producto producto, int cantidad) throws DatoInvalidoException {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0.");
        }
        for (InfoPedido d : detalles) {
            if (d.getProducto().getCodigo() == producto.getCodigo()) {
                d.setCantidad(d.getCantidad() + cantidad);
                return;
            }
        }
        detalles.add(new InfoPedido(producto, cantidad));
    }

    public void eliminarDetalle(int indice) {
        detalles.remove(indice);
    }

    public double calcularTotal() {
        return calcularTotal(0);
    }
  // Recursividad
    private double calcularTotal(int indice) {
        if (indice == detalles.size()) {
            return 0;
        }
        return detalles.get(indice).getSubtotal() + calcularTotal(indice + 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public ArrayList<InfoPedido> getDetalles() {
        return detalles;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
