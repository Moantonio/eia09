package eia.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import eia.util.ValorJuicio;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

public class FormCrearEfecto extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel accionLabel = null;
	private JLabel factorLabel = null;
	private JTextField accionTextField = null;
	private JTextField factorTextField = null;
	private JPanel crearEfectoPanel = null;
	private JLabel nombreLabel = null;
	private JLabel descripcionLabel = null;
	private JLabel enjuiciamientoLabel = null;
	private JTextField nombreTextField = null;
	private JTextArea descripcionTextArea = null;
	private JButton aceptarButton = null;
	private JButton cancelarButton = null;
	private JComboBox enjuiciamientoComboBox = null;
	private JScrollPane barraDesplazamiento = null;

	// Variables del modelo
	private boolean flagAceptar = false;
	private String nombreAccion;
	private String nombreFactor;
	private JButton asistenteButton = null;

	public FormCrearEfecto(String nAccion, String nFactor) {
		super();
		nombreAccion = nAccion;
		nombreFactor = nFactor;
		initialize();
	}

	public FormCrearEfecto(Frame owner, String nAccion, String nFactor) {
		super(owner);
		nombreAccion = nAccion;
		nombreFactor = nFactor;
		initialize();
	}

	private void initialize() {
		this.setSize(475, 275);
		this.setContentPane(getJContentPane());
		this.setTitle("Nuevo efecto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(".//images//window-new.png"));
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			factorLabel = new JLabel();
			factorLabel.setBounds(new Rectangle(242, 19, 42, 13));
			factorLabel.setText("Factor:");
			accionLabel = new JLabel();
			accionLabel.setBounds(new Rectangle(29, 17, 45, 15));
			accionLabel.setText("Acci�n:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(accionLabel, null);
			jContentPane.add(factorLabel, null);
			jContentPane.add(getAccionTextField(), null);
			jContentPane.add(getFactorTextField(), null);
			jContentPane.add(getCrearEfectoPanel(), null);
			jContentPane.add(getAceptarButton(), null);
			jContentPane.add(getCancelarButton(), null);
		}
		return jContentPane;
	}

	private JTextField getAccionTextField() {
		if (accionTextField == null) {
			accionTextField = new JTextField();
			accionTextField.setLocation(new Point(78, 15));
			accionTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			accionTextField.setEnabled(false);
			accionTextField.setSize(new Dimension(150, 18));
			accionTextField.setText(nombreAccion);
		}
		return accionTextField;
	}

	private JTextField getFactorTextField() {
		if (factorTextField == null) {
			factorTextField = new JTextField();
			factorTextField.setLocation(new Point(287, 15));
			factorTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			factorTextField.setEnabled(false);
			factorTextField.setSize(new Dimension(150, 18));
			factorTextField.setText(nombreFactor);
		}
		return factorTextField;
	}

	private JPanel getCrearEfectoPanel() {
		if (crearEfectoPanel == null) {
			enjuiciamientoLabel = new JLabel();
			enjuiciamientoLabel.setBounds(new Rectangle(30, 119, 135, 18));
			enjuiciamientoLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			enjuiciamientoLabel.setText("Simple enjuiciamiento:");
			descripcionLabel = new JLabel();
			descripcionLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			descripcionLabel.setLocation(new Point(38, 57));
			descripcionLabel.setSize(new Dimension(80, 16));
			descripcionLabel.setText("Descripci�n:");
			nombreLabel = new JLabel();
			nombreLabel.setText("Nombre:");
			nombreLabel.setSize(new Dimension(51, 16));
			nombreLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			nombreLabel.setLocation(new Point(60, 30));
			crearEfectoPanel = new JPanel();
			crearEfectoPanel.setLayout(null);
			crearEfectoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Informaci�n del efecto", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			crearEfectoPanel.setSize(new Dimension(441, 153));
			crearEfectoPanel.setLocation(new Point(13, 40));
			crearEfectoPanel.add(nombreLabel, null);
			crearEfectoPanel.add(descripcionLabel, null);
			crearEfectoPanel.add(enjuiciamientoLabel, null);
			crearEfectoPanel.add(getNombreTextField(), null);
			crearEfectoPanel.add(getScrollPane(), null);
			crearEfectoPanel.add(getEnjuiciamientoComboBox(), null);
			crearEfectoPanel.add(getAsistenteButton(), null);
		}
		return crearEfectoPanel;
	}

	private JTextField getNombreTextField() {
		if (nombreTextField == null) {
			nombreTextField = new JTextField();
			nombreTextField.setLocation(new Point(135, 31));
			nombreTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			nombreTextField.setSize(new Dimension(273, 18));
		}
		return nombreTextField;
	}

	private JTextArea getDescripcionTextArea() {
		if (descripcionTextArea == null) {
			descripcionTextArea = new JTextArea();
			descripcionTextArea.setBounds(new Rectangle(135, 54, 272, 47));
			descripcionTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
			descripcionTextArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			descripcionTextArea.setLineWrap(true);
			descripcionTextArea.setWrapStyleWord(true);
		}
		return descripcionTextArea;
	}

	private JScrollPane getScrollPane() {
		if (barraDesplazamiento == null) {
			barraDesplazamiento = new JScrollPane(getDescripcionTextArea());
			barraDesplazamiento.setBounds(new Rectangle(135, 54, 272, 47));
			barraDesplazamiento.setEnabled(true);
			barraDesplazamiento.setBorder(BorderFactory.createLineBorder(new Color(116, 158, 192), 0));
		}
		return barraDesplazamiento;
	}

	private JButton getAceptarButton() {
		if (aceptarButton == null) {
			aceptarButton = new JButton();
			aceptarButton.setName("");
			aceptarButton.setText("Aceptar");
			aceptarButton.setLocation(new Point(139, 205));
			aceptarButton.setSize(new Dimension(85, 20));
			aceptarButton.setBackground(Color.white);
			aceptarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comprobarCondiciones()){
						flagAceptar = true;
						setVisible(false);
					}
				}
			});
		}
		return aceptarButton;
	}

	private boolean comprobarCondiciones() {
		boolean error = false;
		if (nombreTextField.getText().equals("")){
			error = true;
			JOptionPane.showMessageDialog(this, "�Sin nombre de efecto ambiental! \n Introduzca un nombre para identificar el efecto.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		if ((!error)&(descripcionTextArea.getText().equals(""))){
			error = true;
			JOptionPane.showMessageDialog(this, "�Sin descripci�n del efecto ambiental! \n Introduzca una breve descripci�n del efecto.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		return !error;
	}

	private JButton getCancelarButton() {
		if (cancelarButton == null) {
			cancelarButton = new JButton();
			cancelarButton.setName("");
			cancelarButton.setSelected(false);
			cancelarButton.setText("Cancelar");
			cancelarButton.setLocation(new Point(244, 205));
			cancelarButton.setSize(new Dimension(85, 20));
			cancelarButton.setBackground(Color.white);
			cancelarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return cancelarButton;
	}

	public boolean isFlagAceptar() {
		return flagAceptar;
	}

	public String getNombre(){
		return nombreTextField.getText();
	}

	public String getDescripcion(){
		return descripcionTextArea.getText();
	}

	public ValorJuicio getEnjuiciamiento(){
		String seleccion = enjuiciamientoComboBox.getSelectedItem().toString();
		ValorJuicio juicio = null;
		if (seleccion.compareTo("despreciable") == 0){
			juicio = ValorJuicio.despreciable;
		} else if (seleccion.compareTo("especial") == 0){
			juicio = ValorJuicio.especial;
		} else if (seleccion.compareTo("impredecible") == 0){
			juicio = ValorJuicio.impredecible;
		} else if (seleccion.compareTo("significativo") == 0){
			juicio = ValorJuicio.significativo;
		}
		return juicio;
	}

	private JComboBox getEnjuiciamientoComboBox() {
		if (enjuiciamientoComboBox == null) {
			String[] opciones = {ValorJuicio.despreciable.toString(),ValorJuicio.especial.toString(),
					ValorJuicio.impredecible.toString(), ValorJuicio.significativo.toString()};
			enjuiciamientoComboBox = new JComboBox(opciones);
			enjuiciamientoComboBox.setFont(new Font("Dialog", Font.BOLD, 12));
			enjuiciamientoComboBox.setLocation(new Point(168, 120));
			enjuiciamientoComboBox.setSize(new Dimension(160, 17));
		}
		return enjuiciamientoComboBox;
	}

	private JButton getAsistenteButton() {
		if (asistenteButton == null) {
			asistenteButton = new JButton();
			asistenteButton.setName("");
			asistenteButton.setText("Difuso");
			asistenteButton.setActionCommand("Asistente");
			asistenteButton.setSize(new Dimension(70, 18));
			asistenteButton.setLocation(new Point(337, 120));
			asistenteButton.setBackground(Color.white);
			asistenteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					calculoDifuso();
				}
			});
		}
		return asistenteButton;
	}

	private void calculoDifuso(){
		FormJuicioDifuso ventanaDifuso = new FormJuicioDifuso(null);
		Point posActual = getDialog().getLocation();
		posActual.translate(20, 20);
		ventanaDifuso.setLocation(posActual);
		ventanaDifuso.setModal(true);
		ventanaDifuso.setVisible(true);
		if(ventanaDifuso.isFlagAceptar()){
			ValorJuicio valor = ventanaDifuso.getValorJuicio();
			if(valor == ValorJuicio.despreciable){
				enjuiciamientoComboBox.setSelectedIndex(0);
			}else{
				enjuiciamientoComboBox.setSelectedIndex(3);
			}
		}
		ventanaDifuso.dispose();
	}

	private JDialog getDialog() {
		return this;
	}
}
