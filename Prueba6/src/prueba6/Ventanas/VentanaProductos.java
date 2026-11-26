
package prueba6.Ventanas;




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
import prueba6.Bebida;
import prueba6.Enums.Categoria;
import prueba6.Excepciones.DatoInvalidoException;
import prueba6.GestorRestaurante;
import prueba6.Plato;
import prueba6.Postre;
import prueba6.Producto;

public class VentanaProductos extends JFrame implements ActionListener {

    private GestorRestaurante gestor;
    private JComboBox<Categoria> cbCategoria;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtExtra;
    private JLabel lblExtra;
    private JButton btnAgregar, btnCerrar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public VentanaProductos(GestorRestaurante gestor) {
        this.gestor = gestor;

        setTitle("Gestion de Productos");
        setSize(700, 510);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, 20, 110, 25);
        add(lblCategoria);

        cbCategoria = new JComboBox<>(Categoria.values());
        cbCategoria.setBounds(140, 20, 180, 25);
        cbCategoria.addActionListener(this);
        add(cbCategoria);

        JLabel lblCodigo = new JLabel("Codigo:");
        lblCodigo.setBounds(20, 55, 110, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(140, 55, 180, 25);
        add(txtCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 90, 110, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 90, 180, 25);
        add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio (Lps.):");
        lblPrecio.setBounds(20, 125, 110, 25);
        add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(140, 125, 180, 25);
        add(txtPrecio);

        lblExtra = new JLabel("Minutos prep.:");
        lblExtra.setBounds(20, 160, 110, 25);
        add(lblExtra);

        txtExtra = new JTextField();
        txtExtra.setBounds(140, 160, 180, 25);
        add(txtExtra);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 200, 140, 30);
        btnAgregar.addActionListener(this);
        add(btnAgregar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(180, 200, 140, 30);
        btnCerrar.addActionListener(this);
        add(btnCerrar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Categoria");
        modelo.addColumn("Detalle");

        tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 250, 645, 200);
        add(scroll);

        cargarTabla();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cbCategoria) {
            actualizarEtiqueta();
        } else if (e.getSource() == btnAgregar) {
            agregarProducto();
        } else if (e.getSource() == btnCerrar) {
            dispose();
        }
    }

    private void actualizarEtiqueta() {
        Categoria categoria = (Categoria) cbCategoria.getSelectedItem();
        if (categoria == Categoria.PLATO) {
            lblExtra.setText("Minutos preparacion.:");
        } else if (categoria == Categoria.BEBIDA) {
            lblExtra.setText("Mililitros:");
        } else {
            lblExtra.setText("Sabor:");
        }
    }

    private void agregarProducto() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            String extra = txtExtra.getText().trim();
            Categoria categoria = (Categoria) cbCategoria.getSelectedItem();
            Producto producto;

            if (categoria == Categoria.PLATO) {
                int minutos = Integer.parseInt(extra);
                if (minutos <= 0) {
                    throw new DatoInvalidoException("Los minutos de preparacion deben ser mayor a 0.");
                }
                producto = new Plato(codigo, nombre, precio, minutos);
            } else if (categoria == Categoria.BEBIDA) {
                int mililitros = Integer.parseInt(extra);
                if (mililitros <= 0) {
                    throw new DatoInvalidoException("Los mililitros deben ser mayor a 0.");
                }
                producto = new Bebida(codigo, nombre, precio, mililitros);
            } else {
                if (extra.isEmpty()) {
                    throw new DatoInvalidoException("El sabor no puede estar vacio.");
                }
                producto = new Postre(codigo, nombre, precio, extra);
            }

            gestor.agregarProducto(producto);
            JOptionPane.showMessageDialog(this, "Producto agregado.");
            limpiarCampos();
            cargarTabla();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese numeros validos en codigo, precio y " + lblExtra.getText().toLowerCase());
        } catch (DatoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtExtra.setText("");
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Categoria c : Categoria.values()) {
            for (Producto p : gestor.getProductos()) {
                if (p.getCategoria() == c) {
                    modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio(), p.getCategoria(), p.getDetalle()});
                }
            }
        }
    }
}