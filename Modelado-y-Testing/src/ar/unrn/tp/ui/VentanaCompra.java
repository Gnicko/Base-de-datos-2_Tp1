package ar.unrn.tp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Tarjeta;

public class VentanaCompra extends JFrame {

	private JPanel contentPane;
	private ProductoService productoService;
	private Tarjeta tarjetaSeleccionada;

	private VentaService ventaService;
	private ClienteService clienteService;
	private Long idCliente;
	private List<Producto> productos = new ArrayList<>();
	private List<Tarjeta> tarjetas = new ArrayList();
	private DefaultListModel<Producto> modeloProductosCompra = new DefaultListModel();
	private Long idTienda;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaCompra frame = new VentanaCompra(new ProductoManager("jpa-objectdb"),
//							new VentaManager("jpa-objectdb"), new ClienteManager("jpa-objectdb"), 1L);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaCompra(ProductoService ps, VentaService vs, ClienteService cs, Long idCliente, Long idTienda) {
		this.productoService = ps;
		this.ventaService = vs;
		this.clienteService = cs;
		this.idCliente = idCliente;
		this.idTienda = idTienda;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 919, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cargarPanelProductos();
		cargarPanelTarjeta();

	}

	private void cargarPanelProductos() {
		DefaultListModel<Producto> modeloProductos = new DefaultListModel();
		JList listProductos = new JList();
		listProductos.setModel(modeloProductos);
		JScrollPane scrollPane = new JScrollPane(listProductos);
		scrollPane.setBounds(30, 52, 337, 159);
		contentPane.add(scrollPane);

		JButton btnAgregar = new JButton("Agregar Producto");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] index = listProductos.getSelectedIndices();
				if (index.length == 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un producto", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (int i = 0; i < index.length; i++) {

					modeloProductosCompra.addElement(modeloProductos.getElementAt(index[i]));
				}
			}
		});
		btnAgregar.setBounds(30, 243, 160, 21);
		contentPane.add(btnAgregar);

		JButton btnListarProductos = new JButton("Listar Productos");
		btnListarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloProductos.removeAllElements();
				productos = productoService.listarProductos();
				for (Producto p : productos) {

					modeloProductos.addElement(p);
				}
			}
		});
		btnListarProductos.setBounds(240, 243, 128, 21);
		contentPane.add(btnListarProductos);

		JList listProductosCompra = new JList();
		listProductosCompra.setModel(modeloProductosCompra);

		JScrollPane scrollPane_1 = new JScrollPane(listProductosCompra);
		scrollPane_1.setBounds(528, 52, 337, 159);
		contentPane.add(scrollPane_1);

		JButton btnEliminar = new JButton("Eliminar Producto");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] index = listProductosCompra.getSelectedIndices();
				if (index.length == 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un producto", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (int i = 0; i < index.length; i++) {

					modeloProductosCompra.remove(index[0]);
				}

			}
		});
		btnEliminar.setBounds(543, 243, 142, 21);
		contentPane.add(btnEliminar);

		JLabel lblNewLabel = new JLabel("Carrito");
		lblNewLabel.setBounds(531, 29, 252, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Productos");
		lblNewLabel_1.setBounds(29, 19, 139, 32);
		contentPane.add(lblNewLabel_1);

		JButton btnPrecioTotal = new JButton("Precio Total");
		btnPrecioTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Long> productosCompra = new ArrayList();

					for (int i = 0; i < modeloProductosCompra.getSize(); i++) {
						productosCompra.add(modeloProductosCompra.get(i).getId());
					}
					if (tarjetaSeleccionada == null) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar una tarjeta", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(null,
							"El monto total es : "
									+ ventaService.calcularMonto(productosCompra, tarjetaSeleccionada.getId()),
							"Monto", JOptionPane.INFORMATION_MESSAGE);

				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnPrecioTotal.setBounds(702, 243, 128, 21);
		contentPane.add(btnPrecioTotal);
	}

	private void cargarPanelTarjeta() {
		DefaultListModel<Tarjeta> modeloTarjeta = new DefaultListModel();

		JList listTarjetas = new JList();
		listTarjetas.setModel(modeloTarjeta);

		JScrollPane scrollPaneTarjeta = new JScrollPane(listTarjetas);
		scrollPaneTarjeta.setBounds(40, 322, 337, 82);
		contentPane.add(scrollPaneTarjeta);

		JButton btnListarTarjeta = new JButton("Mostrar Tarjetas");
		btnListarTarjeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloTarjeta.removeAllElements();
				tarjetas = clienteService.listarTarjetas(idCliente);
				for (Tarjeta t : tarjetas) {
					modeloTarjeta.addElement(t);
				}
			}
		});
		btnListarTarjeta.setBounds(212, 291, 128, 21);
		contentPane.add(btnListarTarjeta);

		JLabel lblTarjeta = new JLabel("");
		lblTarjeta.setBounds(201, 453, 365, 21);
		contentPane.add(lblTarjeta);

		JButton btnSeleccionarTarjeta = new JButton("Elegir tarjeta");
		btnSeleccionarTarjeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = listTarjetas.getSelectedIndex();
				if (index == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una tarjeta", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				tarjetaSeleccionada = modeloTarjeta.getElementAt(index);
				lblTarjeta.setText(tarjetaSeleccionada.toString());
			}
		});
		btnSeleccionarTarjeta.setBounds(40, 424, 128, 21);
		contentPane.add(btnSeleccionarTarjeta);

		JLabel lblNewLabel_2 = new JLabel("Tarjeta seleccionada:");
		lblNewLabel_2.setBounds(201, 424, 139, 21);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Tarjetas");
		lblNewLabel_3.setBounds(46, 291, 166, 21);
		contentPane.add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Comprar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Long> productosCompra = new ArrayList();

					for (int i = 0; i < modeloProductosCompra.getSize(); i++) {
						productosCompra.add(modeloProductosCompra.get(i).getId());
					}
					if (tarjetaSeleccionada == null) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar una tarjeta", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					ventaService.realizarVenta(idCliente, productosCompra, tarjetaSeleccionada.getId());
					JOptionPane.showMessageDialog(null, "La venta se realizo correctametne", "Exito",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (RuntimeException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			}
		});
		btnNewButton.setBounds(592, 384, 238, 50);
		contentPane.add(btnNewButton);

	}
}
