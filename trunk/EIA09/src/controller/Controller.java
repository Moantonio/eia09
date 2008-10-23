package controller;

import view.View;
import model.Model;

/**
 * Interface que modela el controlador en el patr�n 
 * Model-View-Controller (MVC).
 * @author Vicente Cruz % Enrique Gallego & Luis Gonz�lez.
 */
public interface Controller {

	/**
	 * Establece la vista del patr�n MVC a usar desde 
	 * el controlador.
	 * @param view Vista a asociar al controlar.
	 */
	public void setView(View view);

	/**
	 * Establece el modelo del patr�n MVC a usar desde 
	 * el controlador.
	 * @param model Modelo a asociar al controlar.
	 */
	public void setModel(Model model);

	/**
	 * Actualiza la vista asociada al controlador.
	 */
	public void refreshView();
}
