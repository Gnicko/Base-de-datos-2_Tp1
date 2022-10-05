package ar.unrn.tp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import RSMaterialComponent.RSButtonMaterialIconOne;
import RSMaterialComponent.RSComboBoxMaterial;
import RSMaterialComponent.RSPanelMaterialGradient;
import RSMaterialComponent.RSTextFieldMaterial;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import rojeru_san.efectos.ValoresEnum.ICONS;

public class VentanaModificarProducto extends JFrame {

	private JPanel contentPane;
	private ProductoService productoService;
	private Producto producto;
	private List<Marca> marcas;
	private List<Categoria> categorias;
	private RSComboBoxMaterial comboMarca;
	private RSComboBoxMaterial comboCategoria;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaModificarProducto frame = new VentanaModificarProducto(new ProductoManager("jpa-mysql"), 9L);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public VentanaModificarProducto(ProductoService ps, Long id) {
		inicio(ps, id);

	}

	void inicio(ProductoService ps, Long id) {
		this.productoService = ps;
		producto = productoService.buscarProducto(id);

		categorias = productoService.listarCategorias();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		RSPanelMaterialGradient panel = new RSPanelMaterialGradient();
		panel.setColorSecundario(new Color(30, 144, 255));
		panel.setColorPrimario(new Color(230, 230, 250));
		panel.setBounds(0, 0, 851, 497);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Producto");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(83, 66, 193, 42);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(56, 168, 85, 39);
		panel.add(lblNewLabel_1);

		RSTextFieldMaterial textId = new RSTextFieldMaterial();
		textId.setEditable(false);
		textId.setEnabled(false);
		textId.setPlaceholder("Id");
		textId.setBounds(151, 168, 250, 42);
		textId.setText(String.valueOf(producto.getId()));
		panel.add(textId);

		RSTextFieldMaterial textCodigo = new RSTextFieldMaterial();
		textCodigo.setPlaceholder("Ingrese el codigo del producto");
		textCodigo.setBounds(151, 228, 250, 42);
		textCodigo.setText(producto.getCodigo());
		panel.add(textCodigo);

		JLabel lblNewLabel_1_1 = new JLabel("Codigo:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(56, 230, 95, 40);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Precio:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(56, 399, 85, 40);
		panel.add(lblNewLabel_1_1_1);

		RSTextFieldMaterial textPrecio = new RSTextFieldMaterial();
		textPrecio.setPlaceholder("Ingrese el precio del producto");
		textPrecio.setBounds(151, 397, 250, 42);
		textPrecio.setText(String.valueOf(producto.getPrecio()));
		panel.add(textPrecio);

		JLabel lblNewLabel_1_2 = new JLabel("Marca:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(486, 156, 92, 39);
		panel.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_2_1 = new JLabel("Categoria:");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2_1.setBounds(486, 244, 77, 40);
		panel.add(lblNewLabel_1_2_1);

		comboMarca = new RSComboBoxMaterial();
		comboMarca.setModel(new DefaultComboBoxModel());
		comboMarca.setBounds(573, 156, 200, 40);
		panel.add(comboMarca);

		comboCategoria = new RSComboBoxMaterial();
		comboCategoria.setModel(new DefaultComboBoxModel());

		comboCategoria.setBounds(573, 244, 200, 40);
		panel.add(comboCategoria);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Descripcion:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1.setBounds(56, 309, 83, 39);
		panel.add(lblNewLabel_1_1_1_1);

		JTextArea textDescripcion = new JTextArea();
		textDescripcion.setFont(new Font("Tahoma", Font.BOLD, 14));
		textDescripcion.setWrapStyleWord(true);
		textDescripcion.setBounds(151, 300, 250, 48);
		textDescripcion.setText(producto.getDescripcion());
		panel.add(textDescripcion);

		RSButtonMaterialIconOne btnmtrlcnGuardar = new RSButtonMaterialIconOne();
		btnmtrlcnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float precio = 0;
				try {
					precio = Float.parseFloat(textPrecio.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "ingrese un numero para el precio", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					productoService.modificarProducto(producto.getId(), textCodigo.getText(), textDescripcion.getText(),
							precio, ((Categoria) comboCategoria.getSelectedItem()).getId(),
							((Marca) comboMarca.getSelectedItem()).getId(), producto.getVersion());
				} catch (RuntimeException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "Producto modificado con exito", "Exito",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
				VentanaModificarProducto frame = new VentanaModificarProducto(ps, id);
				frame.setVisible(true);
			}
		});
		btnmtrlcnGuardar.setIcons(ICONS.SAVE);
		btnmtrlcnGuardar.setText("Guardar");
		btnmtrlcnGuardar.setBounds(573, 399, 119, 40);
		panel.add(btnmtrlcnGuardar);

		cargarMarcas();
		cargarCategorias();
		comboMarca.updateUI();
		comboCategoria.updateUI();

	}

	private void cargarCategorias() {
		categorias = productoService.listarCategorias();
		if (categorias.isEmpty()) {
			JOptionPane.showMessageDialog(null, "no existen categorias para listar", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		for (Categoria c : categorias) {
			comboCategoria.addItem(c);
		}
		comboCategoria.setSelectedItem(producto.getCategoria());
	}

	private void cargarMarcas() {
		marcas = productoService.listarMarcas();
		if (marcas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "no existen marcas para listar", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		for (Marca m : marcas) {
			comboMarca.addItem(m);
		}
		comboMarca.setSelectedItem(producto.getMarca());
	}
}
