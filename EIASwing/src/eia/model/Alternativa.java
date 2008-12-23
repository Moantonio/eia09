package eia.model;

import java.util.ArrayList;
import eia.util.Arbol;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

/**
 * Clase que implementa una alternativa de realizaci�n de un proyecto.
 */
public class Alternativa {
	
	/**
	 * Nombre de la alternativa de realizaci�n.
	 */
	private String id;
	 
	/**
	 * Arbol de acciones de la alternativa de realizaci�n.
	 */
	private Arbol<Accion> acciones;
	
	/**
	 * Lista de efectos (impactos) asociados a la alternativa de realizaci�n.
	 */
	private ArrayList<Efecto> efectos;
	
	/**
	 * Valor total del impacto de la alternativa de realizaci�n.
	 */
	private double valorTotal;
	
	/**
	 * Constructor por defecto.
	 */
	public Alternativa(){
		
		id ="";
		acciones = new Arbol<Accion>();
		efectos = new ArrayList<Efecto>();
		valorTotal = 0;
	}

	/**
	 * Constructor por par�metro.
	 * @param id Nombre de la alternativa de realizaci�n.
	 */
	public Alternativa(String id){
		
		this.id = id;
		acciones = new Arbol<Accion>();
		efectos = new ArrayList<Efecto>();
		valorTotal = 0;
	}
	
	/**
	 * Accesor para el atributo 'id'.
	 * @return Nombre de la alternativa de realizaci�n.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Mutador para el atributo 'id'.
	 * @param id Nombre a asignar a la alternativa de realizaci�n.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Accesor para el atributo 'efectos'.
	 * @return Lista de efectos asociados a la alternativa de realizaci�n.
	 */
	public ArrayList<Efecto> getEfectos() {
		return efectos;
	}

	/**
	 * Mutador para el atributo 'efectos'.
	 * @param efectos Lista de efectos a asociar a la alternativa de realizaci�n.
	 */
	public void setEfectos(ArrayList<Efecto> efectos) {
		this.efectos = efectos;
	}

	/**
	 * Accesor para el atributo 'valorTotal'.
	 * @return Valor total del impacto de la alternativa de realizaci�n.
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
	 * @return Arbol de acciones asociadas a la alternativa de realizaci�n.
	 */
	public Arbol<Accion> getAcciones() {
		return acciones;
	}

	/**
	 * Mutador para el atributo 'acciones'.
	 * @param acciones Lista de acciones a asociar a la alternativa de realizaci�n.
	 */
	public void setAcciones(Arbol<Accion> acciones) {
		this.acciones = acciones;
	}
	
	/**
	 * Funci�n para calcular el valor total de impacto de la alternativa,
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
			valorTotal = valor;
		}
	}
	
}
