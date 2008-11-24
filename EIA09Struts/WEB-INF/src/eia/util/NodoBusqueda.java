package eia.util;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

/**
 * Clase que implementa una estructura de informaci�n de un objeto contenido en un �rbol.
 */
@SuppressWarnings("hiding")
public class NodoBusqueda<Object> {

	/**
	 * �rbol padre donde est� contenido el elemento.
	 */
	Arbol<Object> padre;
	
	/**
	 * Objeto elemento del que se presenta la informaci�n.
	 */
	Object elemento;
	
	/**
	 * Posici�n de hijo del elemento respecto al �rbol donde est�.
	 */
	int posicion;
	
	/**
	 * Constructor por defecto.
	 */
	public NodoBusqueda(){
		padre = null;
		elemento = null;
		posicion = 0;
	}
	
	/**
	 * Constructor por par�metros.
	 * @param padre �rbol padre donde est� el elemento.
	 * @param elemento Elemento del que se presenta la informaci�n.
	 * @param posicion Posici�n de hijo del elemento respecto al �rbol donde est�.
	 */
	public NodoBusqueda(Arbol<Object> padre, Object elemento, int posicion){
		this.padre = padre;
		this.elemento = elemento;
		this.posicion = posicion;
	}
	
	/**
	 * Accesor para el atributo 'padre'.
	 * @return �rbol padre donde est� el elemento.
	 */
	public Arbol<Object> getPadre() {
		return padre;
	}
	
	/**
	 * Mutador para el atributo 'padre'.
	 * @param padre �rbol padre a asociar, donde est� el elemento.
	 */
	public void setPadre(Arbol<Object> padre) {
		this.padre = padre;
	}
	
	/**
	 * Accesor para el atributo 'elemento'.
	 * @return Elemento del que se presenta la informaci�n.
	 */
	public Object getElemento() {
		return elemento;
	}
	
	/**
	 * Mutador para el atributo 'elemento'.
	 * @param elemento Elemento a asociar del que se presenta la informaci�n.
	 */
	public void setElemento(Object elemento) {
		this.elemento = elemento;
	}
	
	/**
	 * Accesor para el atributo 'posicion'.
	 * @return Posici�n de hijo del elemento respecto al �rbol donde est�.
	 */
	public int getPosicion() {
		return posicion;
	}
	
	/**
	 * Mutador para el atributo 'posicion'.
	 * @param posicion Posici�n de hijo a asociar, del elemento respecto al �rbol donde est�.
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
}
