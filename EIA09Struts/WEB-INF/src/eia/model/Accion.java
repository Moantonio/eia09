package eia.model;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

/**
 * Clase que implementa una acci�n ambiental.
 */
public class Accion {

	/**
	 * Nombre de la acci�n ambiental.
	 */
	private String id;
	
	/**
	 * Constructor por defecto.
	 */
	public Accion(){
		id = "";
	}
	
	/**
	 *  Constructor por par�metros.
	 * @param id Nombre de la acci�n ambiental.
	 */
	public Accion(String id){
		this.id = id;
	}
	
	/**
	 * Mutador para el atributo 'id'.
	 * @param id Nombre a asignar a la acci�n ambiental. 
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * Accesor para el atributo 'id'.
	 * @return Nombre de la acci�n ambiental.
	 */
	public String getId(){
		return id;
	}
}
