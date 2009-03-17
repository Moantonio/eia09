package eia.model;

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
public class ValoracionCuantitativa implements Cloneable {

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
	 * N�mero de la funci�n de transformaci�n aplicada para el c�culo de la magnitud.
	 */
	private int numFuncion;

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
		this.numFuncion = 0;
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
	 * Accesor para el atributo 'numFuncion'.
	 * @return N�mero de la funci�n de transformaci�n aplicada.
	 */
	public int getNumFuncion() {
		return numFuncion;
	}

	/**
	 * Mutador para el atributo 'numFuncion'.
	 * @param numFuncion N�mero de la funci�n de transformaci�n a asociar.
	 */
	public void setNumFuncion(int numFuncion) {
		this.numFuncion = numFuncion;
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
	 * Funci�n de transformaci�n parab�lica creciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolica1Creciente(double x, double max, double min){
		double num = -(double)Math.pow(x,2)+ 2*max*x + (double)Math.pow(min, 2)- 2* max * min;
		double den = (double)Math.pow(max-min, 2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n parab�lica decreciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolica1Decreciente(double x, double max, double min){
		double num = (double)Math.pow(x,2)- 2*max*x + (double)Math.pow(max, 2);
		double den = (double)Math.pow(max-min, 2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n parab�lica creciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolica2Creciente(double x, double max, double min){
		double num = (double)Math.pow(x,2)- 2*min*x + (double)Math.pow(min, 2);
		double den = (double)Math.pow(max-min, 2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n parab�lica decreciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolica2Decreciente(double x, double max, double min){
		double num = -(double)Math.pow(x, 2)+ 2*min*x + (double)Math.pow(max, 2)-2*min*max;
		double den = (double)Math.pow(max-min, 2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n parab�lica doble creciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolicaDoble1Creciente(double x, double max, double min){
		double num,den;
		if((x>=min)&&(x<= (max+min)/2)){
			num = -2*(double)Math.pow(x, 2)+ 2*(max+min)*x - 2*max*min;
		}
		else{
			num = 2*(double)Math.pow(x, 2)- 2*(max+min)*x + 2*max*min;
		}
		den = (double)Math.pow(max-min,2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n parab�lica doble decreciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolicaDoble1DeCreciente(double x, double max, double min){
		double num,den,y;
		den = (double)Math.pow(max-min,2);
		if((x>=min)&&(x<= (max+min)/2)){
			num = 2*(double)Math.pow(x, 2)- 2*(max+min)*x + 2*max*min;
			y = num/den +1;
		}
		else{
			num = -2*(double)Math.pow(x, 2)+ 2*(max+min)*x - 2*max*min;
			y = num/den;
		}
		return y;
	}

	/**
	 * Funci�n de transformaci�n parab�lica doble creciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolicaDoble2Creciente(double x, double max, double min){
		double num,den,y;
		den = (double)Math.pow(max-min,2);
		if((x>=min)&&(x<= (max+min)/2)){
			num = 2*(double)Math.pow(x, 2)- 4*min*x + 2*(double)Math.pow(min, 2);
			y = num/den;
		}
		else{
			num = -2*(double)Math.pow(x, 2)+ 4*max*x - 2*(double)Math.pow(max, 2);
			y = num/den +1;
		}
		return y;
	}

	/**
	 * Funci�n de transformaci�n parab�lica doble decreciente.
	 * @param x Variable independiente de la funci�n.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double parabolicaDoble2DeCreciente(double x, double max, double min){
		double num,den,y;
		den = (double)Math.pow(max-min,2);
		if((x>=min)&&(x<= (max+min)/2)){
			num = -2*(double)Math.pow(x, 2)+ 4*min*x - 2*(double)Math.pow(min, 2);
			y = num/den +1;
		}
		else{
			num = 2*(double)Math.pow(x, 2)- 4*max*x + 2*(double)Math.pow(max, 2);
			y = num/den;
		}
		return y;
	}

	/**
	 * Funci�n de transformaci�n m�ximo intermedio.
	 * @param x Variable independiente de la funci�n.
	 * @param a Valor de alcance m�ximo.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double maximoIntermedio(double x, double a, double max, double min){
		double num = -(double)Math.pow(x, 2) + 2*a*x + (double)Math.pow(min, 2) - 2*a*min;
		double den = (double)Math.pow(a-min, 2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n m�nimo intermedio.
	 * @param x Variable independiente de la funci�n.
	 * @param a Valor de alcance m�nimo.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double minimoIntermedio(double x, double a, double max, double min){
		double num = (double)Math.pow(x, 2)- 2*a*x + (double)Math.pow(a, 2);
		double den = (double)Math.pow(a-min, 2);
		return num/den;
	}

	/**
	 * Funci�n de transformaci�n con valor umbral creciente.
	 * @param x Variable independiente de la funci�n.
	 * @param umbral Valor del umbral.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double umbralCreciente(double x, double umbral, double max, double min){
		double y;
		if ((x>=min)&&(x<umbral)){
			y=0;
		}
		else{
			y=1;
		}
		return y;
	}

	/**
	 * Funci�n de transformaci�n con valor umbral decreciente.
	 * @param x Variable independiente de la funci�n.
	 * @param umbral Valor del umbral.
	 * @param max Valor m�ximo que podr� tomar la variable 'x'.
	 * @param min Valor m�nimo que podr� tomar la variable 'x'.
	 * @return Resultado de la operaci�n.
	 */
	private double umbralDecreciente(double x, double umbral, double max, double min){
		double y;
		if ((x>=min)&&(x<umbral)){
			y=1;
		}
		else{
			y=0;
		}
		return y;
	}

	/**
	 * Funci�n para calcular el valor de impacto del efecto seg�n an�lisis
	 * cuantitativo, estableciendo dicho valor en el atributo 'magnitudImpacto'.
	 * @param numFuncion N�mero de funci�n de transformaci�n a aplicar.
	 * @param opc Par�metro opcional ('a', 'umbral') para las funciones que lo demanden.
	 */
	public void calcularValoracion(int numFuncion, double opc){
		magnitudImpacto = redondear(calcularFuncion(numFuncion,indicador,mayorValorIndicador,menorValorIndicador,opc),3);
		this.numFuncion = numFuncion;
	}

	/**
	 * Funci�n para el c�lculo aplicado de la funci�n indicada por par�metro.
	 * @param numFuncion N�mero de funci�n a aplicar.
	 * @param x Valor de 'x' para el c�lculo de la funci�n.
	 * @param max Valor de 'max' para el c�lculo de la funci�n.
	 * @param min Valor de 'min' para el c�lculo de la funci�n.
	 * @param opc ar�metro opcional ('a', 'umbral') para las funciones que lo demanden.
	 * @return Resultado 'y' al aplicar la funci�n.
	 */
	public double calcularFuncion(int numFuncion, double x, double max, double min, double opc){
		double resultado = 0;
		switch (numFuncion){
			case 0: {resultado = linealCreciente(x,max,min);
					break;}
			case 1: {resultado = linealDecreciente(x,max,min);
					break;}
			case 2: {resultado = parabolica1Creciente(x,max,min);
					break;}
			case 3: {resultado = parabolica1Decreciente(x,max,min);
					break;}
			case 4: {resultado = parabolica2Creciente(x,max,min);
					break;}
			case 5: {resultado = parabolica2Decreciente(x,max,min);
					break;}
			case 6: {resultado = parabolicaDoble1Creciente(x,max,min);
					break;}
			case 7: {resultado = parabolicaDoble1DeCreciente(x,max,min);
					break;}
			case 8: {resultado = parabolicaDoble2Creciente(x,max,min);
					break;}
			case 9: {resultado = parabolicaDoble2DeCreciente(x,max,min);
					break;}
			case 10: {resultado = maximoIntermedio(x,opc,max,min);
					break;}
			case 11: {resultado = minimoIntermedio(x,opc,max,min);
					break;}
			case 12: {resultado = umbralCreciente(x,opc,max,min);
					break;}
			case 13: {resultado = umbralDecreciente(x,opc,max,min);
					 break;}
		}
		return resultado;
	}

	/**
	 * Funci�n para redondear un n�mero de tipo double al n�mero de cifras
	 * decimales indicadas por par�metro.
	 * @param nD N�mero a redondear.
	 * @param nDec N�mero de cifras decimales a redondear.
	 * @return N�mero redondeado.
	 */
	private double redondear(double nD, int nDec)
	{
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);
	}

	/**
	 * Funci�n para clonar el objeto ValoracionCuantitativa.
	 * @return Copia del objeto ValoracionCuantitativa.
	 */
    public Object clone(){
        Object copia = null;
        try{
            copia = super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println("Imposible duplicar");
        }
        return copia;
    }

    /**
     * Funci�n para obtener el n�mero de referencia de la funci�n de transformaci�n
     * a partir del nombre de la misma.
     * @param nombreFuncion Nombre de la funci�n de transformaci�n.
     * @return N�mero de referencia de la funci�n de transformaci�n.
     */
    public int numFuncionTransformacion(String nombreFuncion){
    	int num = 0;
    	if(nombreFuncion.equals("Lineal creciente")){
    		num = 0;
    	}else if(nombreFuncion.equals("Lineal decreciente")){
			num = 1;
    	}else if(nombreFuncion.equals("Parab�lica creciente I")){
			num = 2;
    	}else if(nombreFuncion.equals("Parab�lica decreciente I")){
			num = 3;
    	}else if(nombreFuncion.equals("Parab�lica creciente II")){
			num = 4;
    	}else if(nombreFuncion.equals("Parab�lica decreciente II")){
			num = 5;
    	}else if(nombreFuncion.equals("Parab�lica doble creciente I")){
			num = 6;
    	}else if(nombreFuncion.equals("Parab�lica doble decreciente I")){
			num = 7;
    	}else if(nombreFuncion.equals("Parab�lica doble creciente II")){
			num = 8;
    	}else if(nombreFuncion.equals("Parab�lica doble decreciente II")){
			num = 9;
    	}else if(nombreFuncion.equals("M�ximo intermedio")){
			num = 10;
    	}else if(nombreFuncion.equals("M�nimo intermedio")){
			num = 11;
    	}else if(nombreFuncion.equals("Umbral creciente")){
			num = 12;
    	}else if(nombreFuncion.equals("Umbral decreciente")){
			num = 13;
    	}
    	return num;
    }

    /**
     * Funci�n para obtener el nombre la funci�n de transformaci�n
     * a partir del n�mero de referencia de la misma.
     * @param numFuncion N�mero de referencia de la funci�n de transformaci�n.
     * @return Nombre de la funci�n de transformaci�n.
     */
    public String nombreFuncionTransformacion(int numFuncion){
    	String nombreFuncion = "";
		switch (numFuncion){
			case 0: {nombreFuncion = "Lineal creciente";
					break;}
			case 1: {nombreFuncion = "Lineal decreciente";
					break;}
			case 2: {nombreFuncion = "Parab�lica creciente I";
					break;}
			case 3: {nombreFuncion = "Parab�lica decreciente I";
					break;}
			case 4: {nombreFuncion = "Parab�lica creciente II";
					break;}
			case 5: {nombreFuncion = "Parab�lica decreciente II";
					break;}
			case 6: {nombreFuncion = "Parab�lica doble creciente I";
					break;}
			case 7: {nombreFuncion = "Parab�lica doble decreciente I";
					break;}
			case 8: {nombreFuncion = "Parab�lica doble creciente II";
					break;}
			case 9: {nombreFuncion = "Parab�lica doble decreciente II";
					break;}
			case 10: {nombreFuncion = "M�ximo intermedio";
					break;}
			case 11: {nombreFuncion = "M�nimo intermedio";
					break;}
			case 12: {nombreFuncion = "Umbral creciente";
					break;}
			case 13: {nombreFuncion = "Umbral decreciente";
					 break;}
		}
		return nombreFuncion;
    }
}
