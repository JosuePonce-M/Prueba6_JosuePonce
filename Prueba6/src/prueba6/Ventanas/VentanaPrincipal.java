
package prueba6.Ventanas;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import prueba6.GestorRestaurante;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private GestorRestaurante gestor;
    private JButton btnProductos, btnPedidos, btnConsulta, btnAlmacenamiento, btnSalir;

    public VentanaPrincipal() {
        gestor = new GestorRestaurante();
        try {
            gestor.cargar();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al leer los datos: " + e.getMessage());
        }

        setTitle("Sistema de Pedidos - Restaurante");
        setSize(400, 420);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Sistema de Pedidos");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitulo.setBounds(85, 20, 250, 40);
        add(lblTitulo);

        btnProductos = new JButton("Gestion de Productos");
        btnProductos.setBounds(100, 80, 200, 40);
        btnProductos.addActionListener(this);
        add(btnProductos);

        btnPedidos = new JButton("Gestion de Pedidos");
        btnPedidos.setBounds(100, 135, 200, 40);
        btnPedidos.addActionListener(this);
        add(btnPedidos);

        btnConsulta = new JButton("Consulta de Pedidos");
        btnConsulta.setBounds(100, 190, 200, 40);
        btnConsulta.addActionListener(this);
        add(btnConsulta);

        btnAlmacenamiento = new JButton("Almacenamiento");
        btnAlmacenamiento.setBounds(100, 245, 200, 40);
        btnAlmacenamiento.addActionListener(this);
        add(btnAlmacenamiento);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(100, 300, 200, 40);
        btnSalir.addActionListener(this);
        add(btnSalir);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnProductos) {
            new VentanaProductos(gestor).setVisible(true);
        } else if (e.getSource() == btnPedidos) {
            new VentanaPedidos(gestor).setVisible(true);
        } else if (e.getSource() == btnSalir) {
            try {
                gestor.guardar();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
            System.exit(0);
        }
    }
}

    