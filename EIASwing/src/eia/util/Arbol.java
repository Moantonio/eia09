package eia.util;

import java.util.ArrayList;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

/**
 * Clase que implementa la estructura '�rbol general'.
 */
@SuppressWarnings("hiding")
public class Arbol<Object> {
	
	/**
	 * Objeto contenido por el nodo.
	 */
	private Object elemento;

	/**
	 * Listado de �rboles hijo del nodo (ramificaciones).
	 */
	private ArrayList<Arbol<Object>> hijos;

	/**
	 * Constructor por par�metros.
	 * @param elemento Objeto a contener el nodo (raiz) del �rbol.
	 * @param hijos �rboles hijo del nodo (raiz) del �rbol.
	 */
	public Arbol(Object elemento, ArrayList<Arbol<Object>> hijos){
		this.elemento = elemento;
		this.hijos = hijos;
	}

	/**
	 * Constructor por defecto.
	 */
	public Arbol(){
		elemento = null;
		hijos = new ArrayList<Arbol<Object>>();
	}
	
	/**
	 * Constructor por par�metro.
	 * @param elemento Objeto a contener el nodo (raiz) del �rbol.
	 */
	public Arbol(Object elemento){
		this.elemento = elemento;
		hijos = new ArrayList<Arbol<Object>>();
	}
	
	/**
	 * Mutador para el atributo 'elemento'.
	 * @param elemento Objeto a asociar al nodo (raiz) del �rbol.
	 */
	public void setElemento(Object elemento){
		this.elemento = elemento;
	}
	
	/**
	 * Accesor para el atributo 'elemento'.
	 * @return Objeto contenido por el nodo (raiz) del �rbol.
	 */
	public Object getElemento(){
		return elemento;
	}
	
	/**
	 * Accesor para el atributo 'hijos'.
	 * @return �rboles hijo asociados al nodo (raiz) del �rbol.
	 */
	public ArrayList<Arbol<Object>> getHijos(){
		return hijos;
	}
	
	/**
	 * Mutador para el atributo 'hijos'.
	 * @param hijos �rboles hijo a asociar al nodo (raiz) del �rbol.
	 */
	public void setHijos(ArrayList<Arbol<Object>> hijos){
		this.hijos = hijos;
	}
	
	/**
	 * Comprueba si el �rbol representa un nodo hoja (sin hijos).
	 * @return TRUE si es hoja, FALSE en caso contrario.
	 */
	public boolean esHoja(){
		return hijos.size() == 0;
	}
	
	/**
	 * Inserta un �rbol hijo al �rbol (nodo) actual.
	 * @param hijo �rbol a a�adir como hijo.
	 */
	public void a�adirHijo(Arbol<Object> hijo){
		hijos.add(hijo);
	}
	
	/**
	 * Busca informaci�n acerca de un elemento en el �rbol.
	 * @param padre Padre del nodo �rbol actual donde iniciamos la b�squeda.
	 * @param posicion Posici�n de hijo del nodo actual del �rbol donde iniciamos la b�squeda.
	 * @param id Identificaci�n para la b�squeda del elemento en el �rbol.
	 * @return Informaci�n general acerca del elemento buscado (si se ha encontrado),
	 * 		   siendo 'null' en caso contrario.
	 */
	private NodoBusqueda<Object> buscarInfoElemento(Arbol<Object> padre, int posicion, String id){
		
		if (elemento!= null && elemento.toString().equals(id)){
			NodoBusqueda<Object> elementoEncontrado = new NodoBusqueda<Object>(padre,elemento,posicion);
			return elementoEncontrado;
		}
		if (!this.esHoja()){
			int i = 0;
			int numHijos = hijos.size();
			NodoBusqueda<Object> elementoEncontrado = null;
			while (i<numHijos && elementoEncontrado == null){
				elementoEncontrado = hijos.get(i).buscarInfoElemento(this,i,id);
				i++;
			}
			return elementoEncontrado;
		}
		return null;
	}
	
	/**
	 * Busca un elemento en el �rbol.
	 * @param id Identificaci�n para la b�squeda del elemento en el �rbol.
	 * @return Elemento buscado (si se ha encontrado), siendo 'null' en caso contrario.
	 */
	public Object buscarElemento(String id){
		NodoBusqueda<Object> busqueda = (buscarInfoElemento(null,0,id));
		if (busqueda!=null){
			return (Object) busqueda.getElemento();
		}
		return null;
	}
	
	/**
	 * Elimina un elemento (si existe) del �rbol.
	 * @param id Identificaci�n del elemento para su eliminaci�n del �rbol.
	 * @return TRUE si se ha podido eliminar, FALSE en caso contrario.
	 */
	public boolean eliminaElemento(String id){
		boolean exito = false;
		if (elemento.toString() == id){
			elemento = null;
			exito = true;
		}else{
			NodoBusqueda<Object> elemBusqueda = buscarInfoElemento(null,0,id);
			if (elemBusqueda!=null){
				int posicion = elemBusqueda.getPosicion();
				elemBusqueda.getPadre().getHijos().remove(posicion);	
				exito = true;
			}
		}
		return exito;
	}
}
