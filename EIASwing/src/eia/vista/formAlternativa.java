package eia.vista;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import eia.model.Accion;
import eia.model.Alternativa;
import eia.model.Efecto;
import eia.model.Factor;
import eia.util.TablaColores;
import eia.util.TablaNoEditable;
import eia.util.ValorJuicio;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

public class formAlternativa extends JDialog{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel altDeRealizacionLabel = null;
	private JTextField altDeRealizcionTextField = null;
	private JPanel AccionesPanel = null;
	private JPanel factoresPanel = null;
	private JButton AnadirButton = null;
	private JButton eliminarButton = null;
	private JTree accionesTree = null;
	private JTree factoresTree = null;
	private JButton crearEfectoButton = null;
	private JPanel efectossPanel = null;
	private JScrollPane efectosScrollPane = null;
	private TablaNoEditable modeloTabla = new TablaNoEditable();
	private JTable efectosTable = null;
	private JButton editarEfectosButton = null;
	private JButton eliminarEfectosButton = null;
	private JLabel valoracionLabel = null;
	private JTextField valoracionTextField = null;
	private JButton aceptarButton = null;
	private JButton cancelarButton = null;
	private JPanel valoracionPanel = null;
	private JScrollPane factoresScrollPane = null;
	private JScrollPane accionesScrollPane = null;
	private boolean flagAceptar = false;
	private JButton valorarButton = null;

	//Variables del modelo
	private Alternativa alternativa;
	private DefaultTreeModel factores;
	private boolean valorada = false;

	public formAlternativa(Alternativa alt, DefaultTreeModel fact) {
		super();
		// TODO alternativa ser� clon de alt
		alternativa = alt;
		factores = fact;
		initialize();
	}

	public formAlternativa(Frame owner, Alternativa alt,DefaultTreeModel fact) {
		super(owner);
		// TODO alternativa ser� clon de alt
		alternativa = alt;
		factores = fact;
		initialize();
	}

	private void initialize() {
		this.setSize(500, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("Alternativas de realizaci�n");
		actualizarVista();
	}

	private JDialog getDialog() {
		return this;
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			valoracionLabel = new JLabel();
			valoracionLabel.setFont(new Font("Dialog", Font.BOLD, 16));
			valoracionLabel.setBounds(new Rectangle(9, 3, 138, 25));
			valoracionLabel.setText(" Valoraci�n Total:");
			altDeRealizacionLabel = new JLabel();
			altDeRealizacionLabel.setText("Alternativa de realizaci�n:");
			altDeRealizacionLabel.setSize(new Dimension(149, 16));
			altDeRealizacionLabel.setLocation(new Point(12, 6));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(altDeRealizacionLabel, null);
			jContentPane.add(getAltDeRealizcionTextField(), null);
			jContentPane.add(getAccionesPanel(), null);
			jContentPane.add(getFactoresPanel(), null);
			jContentPane.add(getCrearEfectoButton(), null);
			jContentPane.add(getEfectossPanel(), null);
			jContentPane.add(getAceptarButton(), null);
			jContentPane.add(getCancelarButton(), null);
			jContentPane.add(getValoracionPanel(), null);

		}
		return jContentPane;
	}

	private JButton getValorarButton() {
		if (valorarButton == null) {
			valorarButton = new JButton();
			valorarButton.setBounds(new Rectangle(300, 6, 80, 18));
			valorarButton.setPreferredSize(new Dimension(71, 26));
			valorarButton.setFont(new Font("Dialog", Font.BOLD, 10));
			valorarButton.setText("Valorar");
			valorarButton.setEnabled(false);
			valorarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					valorarAlternativa();
				}
			});
		}
		return valorarButton;
	}

	private JTextField getAltDeRealizcionTextField() {
		if (altDeRealizcionTextField == null) {
			altDeRealizcionTextField = new JTextField();
			altDeRealizcionTextField.setSize(new Dimension(307, 20));
			altDeRealizcionTextField.setLocation(new Point(163, 4));
			altDeRealizcionTextField.setText(alternativa.getId());
		}
		return altDeRealizcionTextField;
	}

	private JPanel getAccionesPanel() {
		if (AccionesPanel == null) {
			AccionesPanel = new JPanel();
			AccionesPanel.setLayout(null);
			AccionesPanel.setLocation(new Point(13, 29));
			AccionesPanel.setSize(new Dimension(249, 186));
			AccionesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Acciones" +
					""));
			AccionesPanel.add(getAnadirButton(), null);
			AccionesPanel.add(getEliminarButton(), null);
			AccionesPanel.add(getAccionesScrollPane(), null);
		}
		return AccionesPanel;
	}

	private JPanel getFactoresPanel() {
		if (factoresPanel == null) {
			factoresPanel = new JPanel();
			factoresPanel.setLayout(null);
			factoresPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Factores", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			factoresPanel.setLocation(new Point(263, 29));
			factoresPanel.setSize(new Dimension(207, 186));
			factoresPanel.add(getFactoresScrollPane(), null);
		}
		return factoresPanel;
	}

	private JButton getAnadirButton() {
		if (AnadirButton == null) {
			AnadirButton = new JButton();
			AnadirButton.setBounds(new Rectangle(9, 160, 69, 20));
			AnadirButton.setPreferredSize(new Dimension(71, 26));
			AnadirButton.setFont(new Font("Dialog", Font.BOLD, 10));
			AnadirButton.setText("A�adir");
			AnadirButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!accionesTree.isSelectionEmpty()){
						anadirAccion();
					}
				}
			});
		}
		return AnadirButton;
	}

	private JButton getEliminarButton() {
		if (eliminarButton == null) {
			eliminarButton = new JButton();
			eliminarButton.setText("Eliminar");
			eliminarButton.setFont(new Font("Dialog", Font.BOLD, 10));
			eliminarButton.setBounds(new Rectangle(161, 160, 77, 20));
			eliminarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!accionesTree.isSelectionEmpty()){
						eliminarAccion();
					}
				}
			});
		}
		return eliminarButton;
	}

	private JTree getAccionesTree() {
		if (accionesTree == null) {
			DefaultTreeSelectionModel defaultTreeSelectionModel = new DefaultTreeSelectionModel();
			defaultTreeSelectionModel.setSelectionMode(4);
			DefaultTreeCellRenderer defaultTreeCellRenderer = new DefaultTreeCellRenderer();
			defaultTreeCellRenderer.setAutoscrolls(true);
			accionesTree = new JTree(alternativa.getAcciones());
		}
		return accionesTree;
	}

	private JTree getFactoresTree() {
		if (factoresTree == null) {
			DefaultTreeSelectionModel defaultTreeSelectionModel = new DefaultTreeSelectionModel();
			defaultTreeSelectionModel.setSelectionMode(4);
			DefaultTreeCellRenderer defaultTreeCellRenderer = new DefaultTreeCellRenderer();
			defaultTreeCellRenderer.setAutoscrolls(true);


			factoresTree = new JTree(factores);
			factoresTree.setRootVisible(true);
			factoresTree.setShowsRootHandles(false);
			factoresTree.setToggleClickCount(2);
			factoresTree.setCellRenderer(defaultTreeCellRenderer);
			factoresTree.setSelectionModel(defaultTreeSelectionModel);
			factoresTree.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return factoresTree;
	}

	private JButton getCrearEfectoButton() {
		if (crearEfectoButton == null) {
			crearEfectoButton = new JButton();
			crearEfectoButton.setText("Crear Efecto");
			crearEfectoButton.setSize(new Dimension(458, 20));
			crearEfectoButton.setLocation(new Point(13, 219));
			crearEfectoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearEfecto();
				}
			});
		}
		return crearEfectoButton;
	}

	private JPanel getEfectossPanel() {
		if (efectossPanel == null) {
			efectossPanel = new JPanel();
			efectossPanel.setLayout(null);
			efectossPanel.setBounds(new Rectangle(13, 239, 457, 150));
			efectossPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Efectos", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			efectossPanel.add(getEfectosScrollPane(), null);
			efectossPanel.add(getEditarEfectosButton(), null);
			efectossPanel.add(getEliminarEfectosButton(), null);
		}
		return efectossPanel;
	}

	private JScrollPane getEfectosScrollPane() {
		if (efectosScrollPane == null) {
			efectosScrollPane = new JScrollPane();
			efectosScrollPane.setBounds(new Rectangle(11, 17, 436, 107));
			efectosScrollPane.setViewportView(getEfectosTable());
		}
		return efectosScrollPane;
	}

	private JTable getEfectosTable() {
		if (efectosTable == null) {
			efectosTable = new TablaColores(modeloTabla);
			modeloTabla.addColumn("Nombre");
			modeloTabla.addColumn("Juicio");
			modeloTabla.addColumn("Cualitativa");
			modeloTabla.addColumn("Cuantitativa");
			modeloTabla.addColumn("Total");
			modeloTabla.addColumn("Caracter");

			efectosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			efectosTable.setLocation(new Point(2, 20));
			efectosTable.setSize(new Dimension(434, 80));
			efectosTable.setShowVerticalLines(false);
			efectosTable.getTableHeader().setReorderingAllowed(false);
			TableColumnModel col = efectosTable.getColumnModel();
			col.getColumn(0).setResizable(false);
			col.getColumn(1).setResizable(false);
			col.getColumn(2).setResizable(false);
			col.getColumn(3).setResizable(false);
			col.getColumn(4).setResizable(false);
			col.getColumn(0).setPreferredWidth(200);
			col.getColumn(1).setPreferredWidth(60);
			col.getColumn(2).setPreferredWidth(80);
			col.getColumn(3).setPreferredWidth(90);
			col.getColumn(4).setPreferredWidth(50);
		}
		return efectosTable;
	}

	private JButton getEditarEfectosButton() {
		if (editarEfectosButton == null) {
			editarEfectosButton = new JButton();
			editarEfectosButton.setText("Editar");
			editarEfectosButton.setSize(new Dimension(217, 20));
			editarEfectosButton.setLocation(new Point(11, 126));
			editarEfectosButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int indice = efectosTable.getSelectedRow();
					if (indice != -1){
						Efecto efecto = alternativa.getEfectos().get(indice);
						editarEfecto(efecto,indice);
					}
				}
			});
		}
		return editarEfectosButton;
	}

	private JButton getEliminarEfectosButton() {
		if (eliminarEfectosButton == null) {
			eliminarEfectosButton = new JButton();
			eliminarEfectosButton.setText("Eliminar");
			eliminarEfectosButton.setSize(new Dimension(217, 20));
			eliminarEfectosButton.setLocation(new Point(227, 126));
			eliminarEfectosButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminarEfecto();
				}
			});
		}
		return eliminarEfectosButton;
	}

	public boolean isFlagAceptar() {
		return flagAceptar;
	}

	private JTextField getValoracionTextField() {
		if (valoracionTextField == null) {
			valoracionTextField = new JTextField();
			valoracionTextField.setFont(new Font("Dialog", Font.PLAIN, 16));
			valoracionTextField.setEnabled(false);
			valoracionTextField.setBounds(new Rectangle(156, 4, 105, 23));
		}
		return valoracionTextField;
	}

	private JButton getAceptarButton() {
		if (aceptarButton == null) {
			aceptarButton = new JButton();
			aceptarButton.setText("Aceptar");
			aceptarButton.setSize(new Dimension(90, 20));
			aceptarButton.setPreferredSize(new Dimension(80, 26));
			aceptarButton.setLocation(new Point(278, 435));
			aceptarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					flagAceptar = true;
					setVisible(false);
					alternativa.setId(altDeRealizcionTextField.getText());
				}
			});
		}
		return aceptarButton;
	}

	private JButton getCancelarButton() {
		if (cancelarButton == null) {
			cancelarButton = new JButton();
			cancelarButton.setText("Cancelar");
			cancelarButton.setSize(new Dimension(90, 20));
			cancelarButton.setLocation(new Point(375, 435));
			cancelarButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return cancelarButton;
	}

	private JPanel getValoracionPanel() {
		if (valoracionPanel == null) {
			valoracionPanel = new JPanel();
			valoracionPanel.setLayout(null);
			valoracionPanel.setBounds(new Rectangle(40, 393, 400, 31));
			valoracionPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			valoracionPanel.add(getValoracionTextField(), null);
			valoracionPanel.add(valoracionLabel, null);
			valoracionPanel.add(getValorarButton(), null);
		}
		return valoracionPanel;
	}

	public Alternativa getAlternativa() {
		return alternativa;
	}

	private JScrollPane getFactoresScrollPane() {
		if (factoresScrollPane == null) {
			factoresScrollPane = new JScrollPane();
			factoresScrollPane.setBounds(new Rectangle(7, 18, 191, 163));
			factoresScrollPane.setViewportView(getFactoresTree());
		}
		return factoresScrollPane;
	}

	private JScrollPane getAccionesScrollPane() {
		if (accionesScrollPane == null) {
			accionesScrollPane = new JScrollPane();
			accionesScrollPane.setBounds(new Rectangle(7, 17, 233, 141));
			accionesScrollPane.setViewportView(getAccionesTree());
		}
		return accionesScrollPane;
	}

	private void crearEfecto(){
		if (!accionesTree.isSelectionEmpty()&&
				!factoresTree.isSelectionEmpty()&&
				alternativa.getAcciones().isLeaf(accionesTree.getLastSelectedPathComponent())&&
				factores.isLeaf(factoresTree.getLastSelectedPathComponent())){
				Accion accion = (Accion)((DefaultMutableTreeNode)accionesTree.getLastSelectedPathComponent()).getUserObject();
				Factor factor = (Factor)((DefaultMutableTreeNode)factoresTree.getLastSelectedPathComponent()).getUserObject();
				formCrearEfecto formNuevoEfecto = new formCrearEfecto(accion.getId(), factor.getId());
				Point posActual = getDialog().getLocation();
				posActual.translate(20, 20);
				formNuevoEfecto.setLocation(posActual);
				formNuevoEfecto.setModal(true);
				formNuevoEfecto.setVisible(true);
				if (formNuevoEfecto.isFlagAceptar()){
					// Creamos un nuevo efecto
					String id = formNuevoEfecto.getNombre();
					String descripcion = formNuevoEfecto.getDescripcion();
					Efecto efecto = new Efecto(accion, factor, id, descripcion);
					// Seteamos el simple enjuiciamiento
					ValorJuicio juicio = formNuevoEfecto.getEnjuiciamiento();
					efecto.setJuicio(juicio);
					// Lo a�adimos a la alternativa
					alternativa.getEfectos().add(efecto);
				    // Lo a�adimos a la tabla
					String[] datos = {id,"","","","",""};
					modeloTabla.addRow(datos);
					valorarButton.setEnabled(false);
					valoracionTextField.setText("");
					valorada = false;
				}
				formNuevoEfecto.dispose();
			}
	}

	private void eliminarEfecto(){
		int indice = efectosTable.getSelectedRow();
		if (indice != -1){
			int seleccion = JOptionPane.showConfirmDialog (null,
					"�Est� seguro que desea eliminar este efecto?",
					"Eliminar efecto",
					JOptionPane.YES_NO_OPTION);
			if (seleccion==JOptionPane.YES_OPTION){
			    // Elimina el efecto de la tabla
				modeloTabla.removeRow(indice);
				// Eliminamos el efecto de la alternativa
				alternativa.getEfectos().remove(indice);
			}
		}
	}

	private void editarEfecto(Efecto efecto,int indice) {
		formEfecto editarEfecto = new formEfecto(efecto);
		Point posActual = getDialog().getLocation();
		posActual.translate(20, 20);
		editarEfecto.setLocation(posActual);
		editarEfecto.setModal(true);
		editarEfecto.setVisible(true);
		if (editarEfecto.isFlagAceptar()){
			// TODO Seteamos los cambios cogiendo el clon
			// efecto = editarEfecto.getEfecto();
			modeloTabla.setValueAt(efecto.getJuicio(), indice, 1);
			if(efecto.getValCualitativa()!= null)
				modeloTabla.setValueAt(efecto.getValCualitativa().getIncidencia(), indice, 2);
			if(efecto.getValCuantitativa()!= null){
				modeloTabla.setValueAt(efecto.getValCuantitativa().getMagnitudImpacto(), indice, 3);
				if(efecto.getValCualitativa()!= null){
					modeloTabla.setValueAt(efecto.getValorTotal(), indice, 4);
					modeloTabla.setValueAt(efecto.getCaracter(), indice, 5);
				}
			}

			// Comprobamos si todos los efectos est�n valorados
			valorada = false;
			valoracionTextField.setText("");
			if(comprobarValorados()){
				valorarButton.setEnabled(true);
			}
		}
		editarEfecto.dispose();
	}

	private void eliminarAccion(){
		int seleccion = JOptionPane.showConfirmDialog (null,
				"�Est� seguro que desea eliminar esta acci�n?",
				"Eliminar acci�n",
				JOptionPane.YES_NO_OPTION);
		if (seleccion==JOptionPane.YES_OPTION){
			// Eliminamos de la tabla
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)accionesTree.getLastSelectedPathComponent();
			Accion accion = (Accion) nodo.getUserObject();
			alternativa.getAcciones().removeNodeFromParent(nodo);
			//Eliminamos los efectos que tengan esa acci�n
			eliminarEfectos(accion);
		}
	}

	private void eliminarEfectos(Accion accion) {
		// Recorremos la lista de efectos
		for (int i=0;i<alternativa.getEfectos().size();i++){
			Efecto efecto = alternativa.getEfectos().get(i);
			// Si el efecto tiene la acci�n lo eliminamos
			if(efecto.getAccion() == accion){
			    // Elimina el efecto de la tabla
				modeloTabla.removeRow(i);
				// Eliminamos el efecto de la alternativa
				alternativa.getEfectos().remove(i);
				i--;
			}
		}
	}

	private void anadirAccion(){
		DefaultMutableTreeNode padre = (DefaultMutableTreeNode)accionesTree.getLastSelectedPathComponent();
		formCrearAccion formNuevaAccion = new formCrearAccion(null, padre.toString());
		Point posActual = getDialog().getLocation();
		posActual.translate(20, 20);
		formNuevaAccion.setLocation(posActual);
		formNuevaAccion.setModal(true);
		formNuevaAccion.setVisible(true);
		if(formNuevaAccion.isFlagAceptar()){

			// Insertamos en la tabla y en el arbol
			Accion accion = new Accion(formNuevaAccion.getNombreAccion());
			DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(accion);
			alternativa.getAcciones().insertNodeInto(nuevo,padre,0);
		}
		formNuevaAccion.dispose();
	}

	private void valorarAlternativa() {

		valorada = true;
		alternativa.calcularValorTotal();
		valoracionTextField.setText(String.valueOf(alternativa.getValorTotal()));
	}

	private boolean comprobarValorados(){
		// Comprobamos si todos los efecto est�n valorados
		int i = 0;
		int size = alternativa.getEfectos().size();
		boolean valorados = true;
		if (size>0){
			while (valorados&&i<size)
			{
				Efecto efecto = alternativa.getEfectos().get(i);

				// Si el efecto no se ha valorado
				if (efecto.getValCualitativa()==null || efecto.getValCuantitativa()==null){
					valorados = false;
				}
				i++;
			}
		}else{
			valorados = false;
		}
		return valorados;
	}

	private void actualizarVista(){
		for(int i=0;i<alternativa.getEfectos().size();i++){
			// Mostramos la informaci�n del efecto
		    Efecto efecto = alternativa.getEfectos().get(i);
		    String id = efecto.getId();
		    String juicio = efecto.getJuicio().toString();
		    String cualitativa = "";
		    if (efecto.getValCualitativa()!=null){
		    	cualitativa = String.valueOf(efecto.getValCualitativa().getIncidencia());
		    }
		    String cuantitativa = "";
		    if (efecto.getValCuantitativa()!=null){
		    	cuantitativa = String.valueOf(efecto.getValCuantitativa().getIndicador());
		    }
		    String total = "";
		    String caracter = "";
		    if(efecto.getValCualitativa()!=null&&efecto.getValCuantitativa()!=null){
		    	total = String.valueOf(efecto.getValorTotal());
		    	caracter = efecto.getCaracter().toString();
		    }
			String[] datos = {id,"",cualitativa,cuantitativa,total,caracter};
			modeloTabla.addRow(datos);
		}

		// Comprobamos si todos los efectos est�n valorados
		if(comprobarValorados()){
			valorada = true;
			valorarButton.setEnabled(true);
			valoracionTextField.setText(String.valueOf(alternativa.getValorTotal()));
		}
	}

	//TODO A eliminar en un futuro
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Alternativa alt = new Alternativa("AlternativaPrueba");
				Accion accionBisAbuelo = new Accion("Factor Bisabuelo");
				Accion accionAbuelo = new Accion("Factor abuelo");
				Accion accionAbuela = new Accion("Factor abuela");
				Accion accionHijo = new Accion("Factor hijo");
				Accion accionHija = new Accion("Factor hija");
				DefaultMutableTreeNode nodoBisAbuelo = new DefaultMutableTreeNode(accionBisAbuelo);
				DefaultMutableTreeNode nodoAbuelo = new DefaultMutableTreeNode(accionAbuelo);
				DefaultMutableTreeNode nodoAbuela = new DefaultMutableTreeNode(accionAbuela);
				DefaultMutableTreeNode nodoHija = new DefaultMutableTreeNode(accionHija);
				DefaultMutableTreeNode nodoHijo = new DefaultMutableTreeNode(accionHijo);
				DefaultTreeModel acciones = new DefaultTreeModel(nodoBisAbuelo);
				acciones.insertNodeInto(nodoAbuelo, nodoBisAbuelo, 0);
				acciones.insertNodeInto(nodoAbuela, nodoBisAbuelo, 1);
				acciones.insertNodeInto(nodoHijo, nodoAbuelo, 0);
				acciones.insertNodeInto(nodoHija, nodoAbuela, 0);

				Factor factorBisAbuelo = new Factor("Factor Bisabuelo",1);
				Factor factorAbuelo = new Factor("Factor abuelo",2);
				Factor factorAbuela = new Factor("Factor abuela",3);
				Factor factorHijo = new Factor("Factor hijo",4);
				Factor factorHija = new Factor("Factor hija",5);
				DefaultMutableTreeNode nodoBisAbuelo2 = new DefaultMutableTreeNode(factorBisAbuelo);
				DefaultMutableTreeNode nodoAbuelo2 = new DefaultMutableTreeNode(factorAbuelo);
				DefaultMutableTreeNode nodoAbuela2 = new DefaultMutableTreeNode(factorAbuela);
				DefaultMutableTreeNode nodoHija2 = new DefaultMutableTreeNode(factorHija);
				DefaultMutableTreeNode nodoHijo2 = new DefaultMutableTreeNode(factorHijo);
				DefaultTreeModel factores = new DefaultTreeModel(nodoBisAbuelo2);
				factores.insertNodeInto(nodoAbuelo2, nodoBisAbuelo2, 0);
				factores.insertNodeInto(nodoAbuela2, nodoBisAbuelo2, 1);
				factores.insertNodeInto(nodoHijo2, nodoAbuelo2, 0);
				factores.insertNodeInto(nodoHija2, nodoAbuela2, 0);

				alt.setAcciones(acciones);

				formAlternativa application = new formAlternativa(alt,factores);
				application.getDialog().setVisible(true);
			}
		});
	}

	public boolean isValorada() {
		return valorada;
	}

}  //  @jve:decl-index=0:visual-constraint="4,11"
