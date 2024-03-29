package eia.vista;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

public class FiltreSimple extends FileFilter{
	private String description;
	private String extension;

	public FiltreSimple(String description, String extension){
		if(description == null || extension ==null){
			throw new NullPointerException("La descripci�n (o extensi�n) no pueden ser null.");
		}
		this.description = description;
		this.extension = extension;
	}

	public boolean accept(File file){
		if(file.isDirectory()) {
			return true;
		}
		String nombreFichero = file.getName().toLowerCase();
		return nombreFichero.endsWith(extension);
	}
	public String getDescription(){
		return description;
	}
}
