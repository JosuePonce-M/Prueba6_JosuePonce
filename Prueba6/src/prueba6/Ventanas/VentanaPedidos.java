
package prueba6.Ventanas;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import prueba6.Enums.Modalidad;
import prueba6.Excepciones.DatoInvalidoException;
import prueba6.GestorRestaurante;
import prueba6.InfoPedido;
import prueba6.Pedido;
import prueba6.Producto;

public class VentanaPedidos extends JFrame implements ActionListener {

    private GestorRestaurante gestor;
    private Pedido pedido;
    private JComboBox<Producto> cbProductos;
    private JComboBox<Modalidad> cbModalidad;
    private JTextField txtCantidad;
    private JButton btnAgregar, btnEliminar, btnCrear, btnCerrar;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel lblTotal;

    public VentanaPedidos(GestorRestaurante gestor) {
        this.gestor = gestor;
        pedido = new Pedido();

        setTitle("Gestion de Pedidos");
        setSize(700, 510);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(20, 20, 100, 25);
        add(lblProducto);

        cbProductos = new JComboBox<>();
        for (Producto p : gestor.getProductos()) {
            cbProductos.addItem(p);
        }
        cbProductos.setBounds(130, 20, 300, 25);
        add(cbProductos);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(20, 55, 100, 25);
        add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(130, 55, 100, 25);
        add(txtCantidad);

        btnAgregar = new JButton("Agregar al pedido");
        btnAgregar.setBounds(250, 55, 180, 25);
        btnAgregar.addActionListener(this);
        add(btnAgregar);

        JLabel lblModalidad = new JLabel("Modalidad:");
        lblModalidad.setBounds(20, 90, 100, 25);
        add(lblModalidad);

        cbModalidad = new JComboBox<>(Modalidad.values());
        cbModalidad.setBounds(130, 90, 300, 25);
        add(cbModalidad);

        modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Producto");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Subtotal");

        tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 135, 645, 220);
        add(scroll);

        lblTotal = new JLabel("Total: Lps. 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setBounds(20, 365, 300, 30);
        add(lblTotal);

        btnEliminar = new JButton("Eliminar producto");
        btnEliminar.setBounds(20, 410, 180, 30);
        btnEliminar.addActionListener(this);
        add(btnEliminar);

        btnCrear = new JButton("Crear pedido");
        btnCrear.setBounds(250, 410, 180, 30);
        btnCrear.addActionListener(this);
        add(btnCrear);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(485, 410, 180, 30);
        btnCerrar.addActionListener(this);
        add(btnCerrar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            agregarProducto();
        } else if (e.getSource() == btnEliminar) {
            eliminarProducto();
        } else if (e.getSource() == btnCrear) {
            crearPedido();
        } else if (e.getSource() == btnCerrar) {
            dispose();
        }
    }

    private void agregarProducto()  {
        Producto producto = (Producto) cbProductos.getSelectedItem();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "No hay productos registrados.");
            return;
        }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            pedido.agregarProducto(producto, cantidad);
            txtCantidad.setText("");
            actualizarTabla();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad valida.");
        } catch (DatoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.");
            return;
        }
        pedido.eliminarDetalle(fila);
        actualizarTabla();
    }

    private void crearPedido() {
        try {
            pedido.setModalidad((Modalidad) cbModalidad.getSelectedItem());
            gestor.agregarPedido(pedido);
            JOptionPane.showMessageDialog(this, "Pedido #" + pedido.getId() + " creado. Total: Lps. " + String.format("%.2f", pedido.calcularTotal()));
            pedido = new Pedido();
            actualizarTabla();
        } catch (DatoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (InfoPedido d : pedido.getDetalles()) {
            modelo.addRow(new Object[]{d.getProducto().getCodigo(), d.getProducto().getNombre(), d.getProducto().getPrecio(), d.getCantidad(), d.getSubtotal()});
        }
        lblTotal.setText("Total: Lps. " + String.format("%.2f", pedido.calcularTotal()));
    }
}