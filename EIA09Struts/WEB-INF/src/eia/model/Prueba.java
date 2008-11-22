package eia.model;

import eia.util.Arbol;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//PRUEBA DE MONTAJE DE UN �RBOL DE ACCIONES
		
		//Presas
		Accion raiz1 = new Accion("Presas");
		Arbol<Accion> acciones = new Arbol<Accion>(raiz1);
		
		//Fase de planeamiento
		Accion raiz2 = new Accion("Fase de Planeamiento");
		Arbol<Accion> planeamiento = new Arbol<Accion>(raiz2);
		
		//Dise�o y localizacion
		Accion raiz3 = new Accion("Dise�o y localizaci�n de alternativas");
		Arbol<Accion> diseno = new Arbol<Accion>(raiz3);
		
		//Fase de construcci�n
		Accion raiz4 = new Accion("Fase de Construcci�n");
		Arbol<Accion> construccion = new Arbol<Accion>(raiz4);

		//Arbol
		acciones.a�adirHijo(planeamiento);
		acciones.a�adirHijo(construccion);
		planeamiento.a�adirHijo(diseno);
	}

}
