package eia.model;

import java.util.ArrayList;
import javax.swing.tree.DefaultTreeModel;


/**
 * @author SI: EIA'09
 * 		   Vicente Cruz Mínguez.
 *         Enrique Gallego Martín.
 *         Luis González de Paula.
 */

/**
 * Clase que implementa una alternativa de realización de un proyecto.
 */
public class Alternativa {

	/**
	 * Nombre de la alternativa de realización.
	 */
	private String id;

	/**
	 * Arbol de acciones de la alternativa de realización.
	 */
	private DefaultTreeModel acciones;  //  @jve:decl-index=0:

	/**
	 * Lista de efectos (impactos) asociados a la alternativa de realización.
	 */
	private ArrayList<Efecto> efectos;

	/**
	 * Valor total del impacto de la alternativa de realización.
	 */
	private double valorTotal;

	private boolean valorada;

	/**
	 * Constructor por defecto.
	 */
	public Alternativa(){

		id ="";
		acciones = null;
		efectos = new ArrayList<Efecto>();
		valorTotal = 0;
		valorada = false;
	}

	/**
	 * Constructor por parámetro.
	 * @param id Nombre de la alternativa de realización.
	 */
	public Alternativa(String id){

		this.id = id;
		acciones = null;
		efectos = new ArrayList<Efecto>();
		valorTotal = 0;
		valorada = false;
	}

	/**
	 * Accesor para el atributo 'id'.
	 * @return Nombre de la alternativa de realización.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Mutador para el atributo 'id'.
	 * @param id Nombre a asignar a la alternativa de realización.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Accesor para el atributo 'efectos'.
	 * @return Lista de efectos asociados a la alternativa de realización.
	 */
	public ArrayList<Efecto> getEfectos() {
		return efectos;
	}

	/**
	 * Mutador para el atributo 'efectos'.
	 * @param efectos Lista de efectos a asociar a la alternativa de realización.
	 */
	public void setEfectos(ArrayList<Efecto> efectos) {
		this.efectos = efectos;
	}

	/**
	 * Accesor para el atributo 'valorTotal'.
	 * @return Valor total del impacto de la alternativa de realización.
	 */
	public double getValorTotal() {
		return valorTotal;
	}

	/**
	 * Mutador para el atributo 'valorTotal'.
	 * @param valorTotal Valor total del impacto a asociar a la alternativa.
	 */
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
	 * Accesor para el atributo 'acciones'.
	 * @return Arbol de acciones asociadas a la alternativa de realización.
	 */
	public DefaultTreeModel  getAcciones() {
		return acciones;
	}

	/**
	 * Mutador para el atributo 'acciones'.
	 * @param acciones Lista de acciones a asociar a la alternativa de realización.
	 */
	public void setAcciones(DefaultTreeModel acciones) {
		this.acciones = acciones;
	}

	/**
	 * Función para calcular el valor total de impacto de la alternativa,
	 * estableciendo dicho valor en el atributo 'valorTotal'.
	 */
	public void calcularValorTotal(){
		double valor = 0;
		if (efectos.size()!=0){
			for(int i = 0; i<efectos.size();i++){
				if (efectos.get(i).getValorTotal() == 0)
					efectos.get(i).calcularValorTotal();
				valor += efectos.get(i).getValorTotal();
			}
			valorTotal = redondear(valor,3);
		}
	}

	private double redondear(double nD, int nDec){
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);
	}

	public boolean getValorada() {
		return valorada;
	}

	public void setValorada(boolean valorada) {
		this.valorada = valorada;
	}

}
