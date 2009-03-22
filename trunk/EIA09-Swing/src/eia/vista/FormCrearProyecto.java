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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

public class FormCrearProyecto extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel crearProyectoPanel = null;
	private JLabel nombreLabel = null;
	private JLabel descripcionLabel = null;
	private JTextField nombreTextField = null;
	private JTextArea descripcionTextArea = null;
	private JButton aceptarButton = null;
	private JButton cancelarButton = null;
	private JLabel tipoLabel = null;
	private JTextField tipoTextField = null;
	private JScrollPane barraDesplazamiento = null;
	private boolean flagAceptar = false;
	// Variables del modelo
	private String tipoProyecto;

	public FormCrearProyecto(String tipo) {
		super();
		tipoProyecto = tipo;
		initialize();
	}

	private JPanel getCrearProyectoPanel() {
		if (crearProyectoPanel == null) {
			tipoLabel = new JLabel();
			tipoLabel.setText("Tipo:");
			tipoLabel.setSize(new Dimension(27, 16));
			tipoLabel.setLocation(new Point(80, 111));
			tipoLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			descripcionLabel = new JLabel();
			descripcionLabel.setText("Descripci�n:");
			descripcionLabel.setSize(new Dimension(78, 16));
			descripcionLabel.setLocation(new Point(39, 58));
			descripcionLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			nombreLabel = new JLabel();
			nombreLabel.setLocation(new Point(60, 30));
			nombreLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			nombreLabel.setText("Nombre:");
			nombreLabel.setSize(new Dimension(51, 16));
			crearProyectoPanel = new JPanel();
			crearProyectoPanel.setLayout(null);
			crearProyectoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Informaci�n del proyecto", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			crearProyectoPanel.setSize(new Dimension(441, 143));
			crearProyectoPanel.setLocation(new Point(13, 13));
			crearProyectoPanel.add(nombreLabel, null);
			crearProyectoPanel.add(descripcionLabel, null);
			crearProyectoPanel.add(getNombreTextField(), null);
			crearProyectoPanel.add(getScrollPane(), null);
			crearProyectoPanel.add(tipoLabel, null);
			crearProyectoPanel.add(getTipoTextField(), null);
		}
		return crearProyectoPanel;
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
			descripcionTextArea.setBounds(new Rectangle(135, 56, 272, 47));
			descripcionTextArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			descripcionTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
			descripcionTextArea.setLineWrap(true);
			descripcionTextArea.setWrapStyleWord(true);
		}
		return descripcionTextArea;
	}

	private JScrollPane getScrollPane() {
		if (barraDesplazamiento == null) {
			barraDesplazamiento = new JScrollPane(getDescripcionTextArea());
			barraDesplazamiento.setBounds(new Rectangle(135, 56, 272, 47));
			barraDesplazamiento.setEnabled(true);
			barraDesplazamiento.setBorder(BorderFactory.createLineBorder(new Color(116, 158, 192), 0));
		}
		return barraDesplazamiento;
	}

	private JButton getAceptarButton() {
		if (aceptarButton == null) {
			aceptarButton = new JButton();
			aceptarButton.setText("Aceptar");
			aceptarButton.setSize(new Dimension(79, 20));
			aceptarButton.setLocation(new Point(138, 167));
			aceptarButton.setBackground(Color.white);
			aceptarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarCondiciones()){
						flagAceptar = true;
						setVisible(false);
					}
				}
			});
		}
		return aceptarButton;
	}

	private JButton getCancelarButton() {
		if (cancelarButton == null) {
			cancelarButton = new JButton();
			cancelarButton.setName("");
			cancelarButton.setSelected(false);
			cancelarButton.setText("Cancelar");
			cancelarButton.setSize(new Dimension(85, 20));
			cancelarButton.setLocation(new Point(243, 167));
			cancelarButton.setBackground(Color.white);
			cancelarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return cancelarButton;
	}

	private boolean comprobarCondiciones() {
		boolean error = false;
		if (nombreTextField.getText().equals("")){
			error = true;
			JOptionPane.showMessageDialog(this, "�Sin nombre de proyecto! \n Introduzca un nombre para identificar el proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		if ((!error)&(descripcionTextArea.getText().equals(""))){
			error = true;
			JOptionPane.showMessageDialog(this, "�Sin descripci�n del proyecto! \n Introduzca una breve descripci�n del proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		return !error;
	}

	public FormCrearProyecto(Frame owner, String tipo) {
		super(owner);
		tipoProyecto = tipo;
		initialize();
	}

	private void initialize() {
		this.setSize(475, 234);
		this.setContentPane(getJContentPane());
		this.setTitle("Nuevo proyecto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(".//images//window-new.png"));
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCrearProyectoPanel(), null);
			jContentPane.add(getAceptarButton(), null);
			jContentPane.add(getCancelarButton(), null);
		}
		return jContentPane;
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

	private JTextField getTipoTextField() {
		if (tipoTextField == null) {
			tipoTextField = new JTextField();
			tipoTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			tipoTextField.setSize(new Dimension(134, 18));
			tipoTextField.setEnabled(false);
			tipoTextField.setLocation(new Point(136, 111));
			tipoTextField.setText(tipoProyecto);
		}
		return tipoTextField;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
