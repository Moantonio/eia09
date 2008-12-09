package eia.model;

import java.math.*;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

/**
 * Clase que implementa la informaci�n para el an�lisis/valoraci�n 
 * cuantitativa de un efecto/impacto ambiental.
 */
public class ValoracionCuantitativa {

	/**
	 * Magnitud del efecto/impacto en unidades heterog�neas.
	 */
	private double indicador;
	
	/**
	 * Mayor valor que podr� tomar el indicador del efecto/impacto.
	 */
	private double mayorValorIndicador;
	
	/**
	 * Menor valor que podr� tomar el indicador del efecto/impacto.
	 */
	private double menorValorIndicador;
	
	/**
	 * Valor del efecto/impacto por valoraci�n cuantitativa.
	 */
	private double magnitudImpacto;

	/**
	 * Constructor por par�metros.
	 * @param indicador Magnitud del efecto/impacto en unidades heterog�neas.
	 * @param mayorValorIndicador Mayor valor que podr� tomar el indicador del efecto/impacto.
	 * @param menorValorIndicador Menor valor que podr� tomar el indicador del efecto/impacto.
	 */
	public ValoracionCuantitativa(double indicador, double mayorValorIndicador, double menorValorIndicador) {
		this.indicador = indicador;
		this.mayorValorIndicador = mayorValorIndicador;
		this.menorValorIndicador = menorValorIndicador;
		this.magnitudImpacto = 0;
	}
	
	/**
	 * Accesor para el atributo 'indicador'.
	 * @return Magnitud del efecto/impacto en unidades heterog�neas.
	 */
	public double getIndicador() {
		return indicador;
	}

	/**
	 * Mutador para el atributo 'indicador'.
	 * @param indicador Magnitud del efecto/impacto a asociar en unidades heterog�neas.
	 */
	public void setIndicador(double indicador) {
		this.indicador = indicador;
	}

	/**
	 * Accesor para el atributo 'mayorValorIndicador'.
	 * @return Mayor valor que podr� tomar el indicador del efecto/impacto.
	 */
	public double getMayorValorIndicador() {
		return mayorValorIndicador;
	}

	/**
	 * Mutador para el atributo 'mayorValorIndicador'.
	 * @param mayorValorIndicador Mayor valor que podr� tomar el indicador del efecto/impacto.
	 */
	public void setMayorValorIndicador(double mayorValorIndicador) {
		this.mayorValorIndicador = mayorValorIndicador;
	}

	/**
	 * Accesor para el atributo 'menorValorIndicador'.
	 * @return Menor valor que podr� tomar el indicador del efecto/impacto.
	 */
	public double getMenorValorIndicador() {
		return menorValorIndicador;
	}

	/**
	 * Mutador para el atributo 'menorValorIndicador'.
	 * @param menorValorIndicador Menor valor que podr� tomar el indicador del efecto/impacto.
	 */
	public void setMenorValorIndicador(double menorValorIndicador) {
		this.menorValorIndicador = menorValorIndicador;
	}

	/**
	 * Accesor para el atributo 'magnitudImpacto'.
	 * @return Valor del efecto/impacto por valoraci�n cuantitativa.
	 */
	public double getMagnitudImpacto() {
		return magnitudImpacto;
	}

	/**
	 * Mutador para el atributo 'magnitudImpacto'.
	 * @param magnitudImpacto Valor del efecto/impacto a asociar por valoraci�n cuantitativa.
	 */
	public void setMagnitudImpacto(double magnitudImpacto) {
		this.magnitudImpacto = magnitudImpacto;
	}
	
	/**
	 * Funci�n de transformaci�n lineal creciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'. 
	 * @param min Valor m�nimo que podr� tomar la variable 'x'. 
	 * @return Resultado de la operaci�n.
	 */
	private double linealCreciente(double x, double max, double min){
		double y = (x - min)/(max-min);
		return y;
	}
	
	/**
	 * Funci�n de transformaci�n lineal decreciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'. 
	 * @param min Valor m�nimo que podr� tomar la variable 'x'. 
	 * @return Resultado de la operaci�n.
	 */
	private double linealDecreciente(double x, double max, double min){
		double y = (max - x)/(max-min);
		return y;	
	}
	
	/**
	 * 
	 * @param x
	 * @param max
	 * @param min
	 * @return
	 */
	private double parabolica1Creciente(double x, double max, double min){
		double num = - (double)Math.pow(x,2)+ 2*max*x + (double)Math.pow(min, 2)- 2* max * min;
		double den = (double)Math.pow(max-min, 2);
		return num/den;		
	}

	/**
	 * 
	 * @param x
	 * @param max
	 * @param min
	 * @return
	 */
	private double parabolica1Decreciente(double x, double max, double min){
		double num = (double)Math.pow(x,2)+ 2*max*x + (double)Math.pow(max, 2);
		double den = (double)Math.pow(max-min, 2);
		return num/den;
	}
	
	/**
	 * 
	 * @param x
	 * @param max
	 * @param min
	 * @return
	 */
	private double parabolica2Creciente(double x, double max, double min){
		double num = (double)Math.pow(x,2)+ 2*min*x + (double)Math.pow(min, 2);
		double den = (double)Math.pow(max-min, 2);
		return num/den;		
	}
	
	/**
	 * 
	 * @param x
	 * @param max
	 * @param min
	 * @return
	 */
	private double parabolica2Decreciente(double x, double max, double min){
		double num = -(double)Math.pow(x, 2)+ 2*min*x + (double)Math.pow(max, 2)-2*min*max;
		double den = (double)Math.pow(max-min, 2);
		return num/den;		
	}
	
	/**
	 * 
	 * @param x
	 * @param max
	 * @param min
	 * @return
	 */
	private double parabolicaDoble1Creciente(double x, double max, double min){
		double num,den;
		if((x>=min)&&(x<= (max+min)/2)){
			num = -2*(double)Math.pow(x, 2)+ 2*(max+min)*x - 2*max*min;
		}
		else{
			num = 2*(double)Math.pow(x, 2)- 2*(max+min*x + 2*max*min;		
		}
		den = (double)Math.pow(max-min,2);
		return num/den;
	}
	
	
	
	
	
	
}
