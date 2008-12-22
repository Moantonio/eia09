package eia.vista;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FiltreSimple extends FileFilter{
	   // descripci�n y extensi�n aceptada pr el filto
	   private String description;
	   private String extension;

	   // contructor a partir de la descripvi�n y la extensi�n aceptada
	   public FiltreSimple(String description, String extension){
	      if(description == null || extension ==null){
	         throw new NullPointerException("La descripci�n (o extensi�n) no pueden ser null.");
	      }
	      this.description = description;
	      this.extension = extension;
	   }

	   // Implementaci�n del FileFilter
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
