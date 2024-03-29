package eia.util.xml;

/**
 * @author SI: EIA'09
 * 		   Vicente Cruz M�nguez.
 *         Enrique Gallego Mart�n.
 *         Luis Gonz�lez de Paula.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eia.util.CaracterEfecto;
import eia.util.TipoProyecto;
import eia.util.ValorJuicio;

import eia.modelo.Accion;
import eia.modelo.Alternativa;
import eia.modelo.Efecto;
import eia.modelo.Factor;
import eia.modelo.InfoProyecto;
import eia.modelo.Proyecto;
import eia.modelo.ValoracionCualitativa;
import eia.modelo.ValoracionCuantitativa;

/**
 * Clase que implementa el manejo de los ficheros XML con la informaci�n
 * de un proyecto EIA.
 */
public class XMLProyecto extends XMLTools{

	/**
	 * Constructor por par�metros.
	 * @param dtd DTD a asignar.
	 */
	public XMLProyecto(String dtd){
		this.setDtd(dtd);
	}

	 /**
     * El m�todo leer recupera un proyecto con la informaci�n almacenada en el documento XML.
     * @param filename Ruta del fichero con el proyecto a leer.
     * @return Proyecto de EIA con la informaci�n contenida en el fichero.
     */
    public Proyecto leer(String filename) {

        //Se obtiene el objeto Document que representa al archivo xml
        Document doc= readFile(filename);

        //Se recupera el objeto Element principal del objeto Document
        Element elemento=(Element)doc. getDocumentElement();

        Proyecto proy = new Proyecto();

        //Obtenemos la fecha, tenemos que parsearla para darle formato y guardarla en el objeto.
        Element elemFecha = (Element)elemento.getElementsByTagName("fecha").item(0);
        StringBuffer stringFecha = new StringBuffer().append(elemFecha.getElementsByTagName("dia").item(0).getTextContent()).append('-')
		        									 .append(elemFecha.getElementsByTagName("mes").item(0).getTextContent()).append('-')
		        									 .append(elemFecha.getElementsByTagName("anio").item(0).getTextContent());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = Calendar.getInstance().getTime(); //Ponemos por defecto la fecha de hoy.
		try {
			fecha = format.parse(stringFecha.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

        InfoProyecto info = new InfoProyecto(elemento.getElementsByTagName("nombre").item(0).getTextContent(),
        									 elemento.getElementsByTagName("descripcion").item(0).getTextContent(),
        									 elemento.getElementsByTagName("promotor").item(0).getTextContent(),
        									 elemento.getElementsByTagName("redactor").item(0).getTextContent(),
        									 elemento.getElementsByTagName("poblacion").item(0).getTextContent(),
        									 elemento.getElementsByTagName("provincia").item(0).getTextContent(),
        									 elemento.getElementsByTagName("pais").item(0).getTextContent(),
        									 fecha,
        									 Integer.valueOf(elemento.getElementsByTagName("vidaUtil").item(0).getTextContent()).intValue());
        proy.setInformacion(info);

        //Cogemos el tipo del proyecto
        String textoTipo = elemento.getElementsByTagName("tipo").item(0).getTextContent();
        TipoProyecto tipo;
        if (textoTipo.compareTo("AUTOV�A") == 0){
        	tipo = TipoProyecto.AUTOV�A;
        } else if (textoTipo.compareTo("VERTEDERO") == 0){
        	tipo = TipoProyecto.VERTEDERO;
        } else if (textoTipo.compareTo("PUERTODEPORTIVO") == 0){
        	tipo = TipoProyecto.PUERTODEPORTIVO;
        } else if (textoTipo.compareTo("PRESA") == 0){
        	tipo = TipoProyecto.PRESA;
        } else {
        	tipo = TipoProyecto.PERSONALIZADO;
        }
        proy.setTipo(tipo);

        //Cogemos la lista principal de factores.
        Element listaFactores = (Element)elemento.getElementsByTagName("listaFactores").item(0);
        NodeList factores = listaFactores.getElementsByTagName("factor");

        //Para cada factor, vamos recorriendo los subfactores y creando el arbol.
        DefaultMutableTreeNode arbolFact = new DefaultMutableTreeNode();
        int index = 0;
        for(int i = 0; i < factores.getLength(); i++){
        	DefaultMutableTreeNode arbolI = recorrerFactores((Element)factores.item(i));
        	if(!esSubArbol(arbolI,arbolFact))
        		arbolFact.insert(arbolI,index++);
        }
        proy.setFactores(new DefaultTreeModel(arbolFact));

      //Cogemos la lista de alternativas.
        Element listaAlternativas = (Element)elemento.getElementsByTagName("listaAlternativas").item(0);
        NodeList alternativas = listaAlternativas.getElementsByTagName("alternativaProyEIA");
        ArrayList<Alternativa> listaAlt = new ArrayList<Alternativa>();
        for(int k=0; k<alternativas.getLength(); k++){
            Alternativa alt = new Alternativa();

            alt.setId(elemento.getElementsByTagName("nombreAlt").item(k).getTextContent());

            //Cogemos la lista principal de acciones.
            Element listaAcciones = (Element)elemento.getElementsByTagName("listaPrincipalAcciones").item(k);
            NodeList acciones = listaAcciones.getElementsByTagName("accion");

            //Para cada accion, vamos recorriendo las subacciones y creando el arbol.
            DefaultMutableTreeNode arbolAcc = new DefaultMutableTreeNode();
            int index2 = 0;
            for(int i = 0; i < acciones.getLength(); i++){
            	DefaultMutableTreeNode arbolI = recorrerAcciones((Element)acciones.item(i));
            	if(!esSubArbol(arbolI,arbolAcc))
            		arbolAcc.insert(arbolI,index2++);
            }
            alt.setAcciones(new DefaultTreeModel(arbolAcc));

            //Cargamos la lista de efectos.
            //Necesitamos el arbol de factores del proyecto para guardar los objetos.
            ArrayList<Efecto> listaEf = new ArrayList<Efecto>();
            DefaultTreeModel arbFact = proy.getFactores();
            Element listaEfectos = (Element)elemento.getElementsByTagName("listaEfectos").item(k);
            if(listaEfectos != null){
    	        NodeList efectos = listaEfectos.getElementsByTagName("efecto");
    	        for(int i=0; i < efectos.getLength(); i++){
    	        	Element efectoElem =(Element)efectos.item(i);
    	        	Efecto ef = new Efecto();
    	        	ef.setId(efectoElem.getElementsByTagName("idEfecto").item(0).getTextContent());
    	        	ef.setDescripcion(efectoElem.getElementsByTagName("descripcion").item(0).getTextContent());
    	        	String idAcc = efectoElem.getElementsByTagName("idAccion").item(0).getTextContent();
    	        	ef.setAccion((Accion)buscarElemento(alt.getAcciones(),idAcc));
    	        	ef.setFactor((Factor)buscarElemento(arbFact,efectoElem.getElementsByTagName("idFactor").item(0).getTextContent()));
    	        	ef.setJuicio(ValorJuicio.valueOf(efectoElem.getElementsByTagName("valorSimpleEnjuiciamiento").item(0).getTextContent()));
    	        	Element caracter = (Element)efectoElem.getElementsByTagName("caracter").item(0);
    	        	if (caracter != null){
    	        		ef.setCaracter(CaracterEfecto.valueOf(efectoElem.getElementsByTagName("caracter").item(0).getTextContent()));
    	        	}

    	        	Element valoracion = (Element)efectoElem.getElementsByTagName("valorCuantitativo").item(0);
    	        	if(valoracion != null){
    		        	ValoracionCuantitativa valCuant = new ValoracionCuantitativa(Double.valueOf(valoracion.getElementsByTagName("indicador").item(0).getTextContent()),
    							 													 Double.valueOf(valoracion.getElementsByTagName("maxVal").item(0).getTextContent()),
    							 													 Double.valueOf(valoracion.getElementsByTagName("minVal").item(0).getTextContent()));
    		        	String nombreFuncion = valoracion.getElementsByTagName("funcionTransformacion").item(0).getTextContent();
    		        	valCuant.setNumFuncion(valCuant.numFuncionTransformacion(nombreFuncion));
    		        	valCuant.setMagnitudImpacto(Double.valueOf(valoracion.getElementsByTagName("magnitud").item(0).getTextContent()));
    		        	ef.setValCuantitativa(valCuant);
    	        	}

    	        	valoracion = (Element)efectoElem.getElementsByTagName("valorCualitativo").item(0);
    	        	if(valoracion != null){
    	        		ValoracionCualitativa valCual = new ValoracionCualitativa(Integer.valueOf(valoracion.getElementsByTagName("signo").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("acumulacion").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("extension").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("extensionCritica").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("intensidad").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("persistencia").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("reversibilidad").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("recuperabilidad").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("periodicidad").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("momento").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("momentoCritico").item(0).getTextContent()),
    	        																  Integer.valueOf(valoracion.getElementsByTagName("efectoImp").item(0).getTextContent()));

    	        		valCual.setIncidencia(Double.valueOf(valoracion.getElementsByTagName("incidencia").item(0).getTextContent()));;
    	        		ef.setValCualitativa(valCual);
    	        	}

    	        	Element elemValorTotal = (Element)efectoElem.getElementsByTagName("valorTotal").item(0);
    	        	if(elemValorTotal != null)
    	        		ef.setValorTotal(Double.valueOf(elemValorTotal.getTextContent()));
    	        	listaEf.add(ef);
    	        }
            }
            alt.setEfectos(listaEf);
            if(elemento.getElementsByTagName("valorTotalAlternativa").item(k)!=null)
            	alt.setValorTotal(Double.valueOf(elemento.getElementsByTagName("valorTotalAlternativa").item(k).getTextContent()));

            String valoradaTexto = elemento.getElementsByTagName("valorada").item(k).getTextContent();
            if (valoradaTexto.compareTo("true")==0){
                alt.setValorada(true);
            }else{
                alt.setValorada(false);
            }
        	listaAlt.add(alt);
        }
        proy.setAlternativas(listaAlt);
        return proy;
    }

    @SuppressWarnings("unchecked")
	private boolean esSubArbol(DefaultMutableTreeNode arbolH, DefaultMutableTreeNode arbolP){
    	boolean esHijo = false;
    	Enumeration eHijos = arbolP.children();
    	while(eHijos.hasMoreElements()){
    		Enumeration eP = ((DefaultMutableTreeNode)eHijos.nextElement()).breadthFirstEnumeration();
	    	while(eP.hasMoreElements() && !esHijo){
	    		Enumeration eH = arbolH.breadthFirstEnumeration();
	    		DefaultMutableTreeNode nodoP = (DefaultMutableTreeNode)eP.nextElement();
	    		while(eH.hasMoreElements() && !esHijo){
	    			DefaultMutableTreeNode nodoH = (DefaultMutableTreeNode)eH.nextElement();
	    			if(nodoP.getUserObject()!= null && nodoH.getUserObject()!= null)
	    				if(nodoP.getUserObject().toString().equals(nodoH.getUserObject().toString()))
	    					esHijo = true;
	    		}

	    	}
    	}
    	return esHijo;
    }

    /**
     * M�todo privado que dado un factor recorre su lista de subfactores.
     * @param factor Factor que puede tener una lista de subfactores.
     * @return Estructura de �rbol con factores y subfactores.
     */
    private DefaultMutableTreeNode recorrerFactores(Element factor){
    	//Caso base
    	if(factor.getElementsByTagName("listaFactores").getLength() == 0){
    		Factor f = new Factor (factor.getElementsByTagName("nombreFactor").item(0).getTextContent(),
					   Integer.valueOf(factor.getElementsByTagName("peso").item(0).getTextContent()));
    		DefaultMutableTreeNode arb = new DefaultMutableTreeNode();
    		arb.setUserObject(f);
    		return arb;
    	}
    	//Caso recursivo
    	else{
    		//Cogemos la lista de subfactores.
    		Element listaFactores = (Element)factor.getElementsByTagName("listaFactores").item(0);
    		NodeList factores = listaFactores.getElementsByTagName("factor");
    		//Creamos el nodo padre del arbol.
    		Factor f = new Factor (factor.getElementsByTagName("nombreFactor").item(0).getTextContent(),
    				Integer.valueOf(factor.getElementsByTagName("peso").item(0).getTextContent()));
    		DefaultMutableTreeNode padre = new DefaultMutableTreeNode();
    		padre.setUserObject(f);
    		int index = 0;
    		//Recorremos cada subfactor.
    		for(int i = 0; i < factores.getLength(); i++){
    			DefaultMutableTreeNode arb = recorrerFactores((Element)factores.item(i));
    			if(!esSubArbol(arb,padre))
    				padre.insert(arb,index++);
    		}
    		return padre;
    	}
    }

    /**
     * M�todo que alamacena informacion en un fichero XML.
     * @param o Informaci�n del proyecto a almacenar.
     * @param archivo Ruta del fichero XML donde queremos almacenar la informaci�n.
     */
	@SuppressWarnings("unchecked")
	public void escribir(Object o, String archivo) {
        Document document=null;
        Proyecto proy = (Proyecto)o;

        try {
            /*Creaci�n del documento*/
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            /*Creaci�n del elemento r�iz*/
            Element root = (Element) document.createElement("proyectoEIA");
            document.appendChild (root);

            Element nombre = (Element)document.createElement("nombre");
            nombre.setTextContent(proy.getInformacion().getNombre());
            root.appendChild(nombre);

            Element descripcion = (Element)document.createElement("descripcion");
            descripcion.setTextContent(proy.getInformacion().getDescripcion());
            root.appendChild(descripcion);

            Element promotor = (Element)document.createElement("promotor");
            promotor.setTextContent(proy.getInformacion().getCompania());
            root.appendChild(promotor);

            Element redactor = (Element)document.createElement("redactor");
            redactor.setTextContent(proy.getInformacion().getAutor());
            root.appendChild(redactor);

            //Convertimos la fecha almacenada en los elementos necesarios en el XML.
            Element fecha = (Element)document.createElement("fecha");
            Element dia = (Element)document.createElement("dia");
            Element mes = (Element)document.createElement("mes");
            Element anio = (Element)document.createElement("anio");

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String fechaStr = format.format(proy.getInformacion().getFecha());
            StringTokenizer tokens = new StringTokenizer(fechaStr, "-");

            dia.setTextContent(tokens.nextToken());
            mes.setTextContent(tokens.nextToken());
            anio.setTextContent(tokens.nextToken());

            fecha.appendChild(dia);
            fecha.appendChild(mes);
            fecha.appendChild(anio);
            root.appendChild(fecha);

            Element vidaUtil = (Element)document.createElement("vidaUtil");
            vidaUtil.setTextContent(Integer.toString(proy.getInformacion().getVidaUtil()));
            root.appendChild(vidaUtil);

            Element poblacion = (Element)document.createElement("poblacion");
            poblacion.setTextContent(proy.getInformacion().getPoblacion());
            root.appendChild(poblacion);

            Element provincia = (Element)document.createElement("provincia");
            provincia.setTextContent(proy.getInformacion().getProvincia());
            root.appendChild(provincia);

            Element pais = (Element)document.createElement("pais");
            pais.setTextContent(proy.getInformacion().getPais());
            root.appendChild(pais);

            Element tipo = (Element)document.createElement("tipo");
            tipo.setTextContent(proy.getTipo().toString());
            root.appendChild(tipo);

            //Creamos la lista de alternativas.
            ArrayList<Alternativa> listaAlt = proy.getAlternativas();

            Element listaAlternativas = (Element)document.createElement("listaAlternativas");
            for(int k=0; k<listaAlt.size(); k++){
                /*Creaci�n del elemento r�iz*/
                Element rootAlt = (Element) document.createElement("alternativaProyEIA");

                Element nombreAlt = (Element)document.createElement("nombreAlt");
                nombreAlt.setTextContent(listaAlt.get(k).getId());
                rootAlt.appendChild(nombreAlt);

                //Creamos la lista de acciones.
                Element listaAcciones = (Element)document.createElement("listaPrincipalAcciones");

                DefaultTreeModel acciones = listaAlt.get(k).getAcciones();
                DefaultMutableTreeNode raiz = (DefaultMutableTreeNode)acciones.getRoot();

                //int numHijos = acciones.getHijos().size();
                int numHijos = raiz.getChildCount();
                Enumeration children = raiz.children();

                for(int i=0; i< numHijos; i++){
                	int cont = 1;

                	DefaultMutableTreeNode nodoI = (DefaultMutableTreeNode)children.nextElement();
                	Element accion = recorrerAcciones(nodoI,document,cont);
                	listaAcciones.appendChild(accion);
                }
                rootAlt.appendChild(listaAcciones);
	            Element listaEfectos = (Element)document.createElement("listaEfectos");
                if(listaAlt.get(k).getEfectos().size() > 0){
    	           // Element listaEfectos = (Element)document.createElement("listaEfectos");
    	            for(int i=0; i<listaAlt.get(k).getEfectos().size(); i++){
    	            	Element elemEfecto = (Element)document.createElement("efecto");
    	            	Efecto ef = listaAlt.get(k).getEfectos().get(i);

    	            	Element idEfecto = document.createElement("idEfecto");
    	            	idEfecto.setTextContent(ef.getId());
    	            	elemEfecto.appendChild(idEfecto);

    	            	Element descripcionAlt = document.createElement("descripcion");
    	            	descripcionAlt.setTextContent(ef.getDescripcion());
    	            	elemEfecto.appendChild(descripcionAlt);

    	            	// El caracter no es obligatorio
    	            	if (ef.getCaracter() != null){
    	            		Element caracter = document.createElement("caracter");
    	            		caracter.setTextContent(ef.getCaracter().toString());
    	            		elemEfecto.appendChild(caracter);
    	            	}

    	            	Element idAccion = document.createElement("idAccion");
    	            	idAccion.setTextContent(ef.getAccion().getId());
    	            	elemEfecto.appendChild(idAccion);

    	            	Element idFactor = document.createElement("idFactor");
    	            	idFactor.setTextContent(ef.getFactor().getId());
    	            	elemEfecto.appendChild(idFactor);

    	            	Element valorSimpleEnjuiciamiento = document.createElement("valorSimpleEnjuiciamiento");
    	            	valorSimpleEnjuiciamiento.setTextContent(ef.getJuicio().toString());
    	            	elemEfecto.appendChild(valorSimpleEnjuiciamiento);

    	            	if (ef.getValCuantitativa() !=null){
	    	            	Element valorCuantitativo = document.createElement("valorCuantitativo");

	    	            	Element indicador = document.createElement("indicador");
	    	            	indicador.setTextContent(Double.toString(ef.getValCuantitativa().getIndicador()));
	    	            	valorCuantitativo.appendChild(indicador);
	    	            	Element maxVal = document.createElement("maxVal");
	    	            	maxVal.setTextContent(Double.toString(ef.getValCuantitativa().getMayorValorIndicador()));
	    	            	valorCuantitativo.appendChild(maxVal);
	    	            	Element minVal = document.createElement("minVal");
	    	            	minVal.setTextContent(Double.toString(ef.getValCuantitativa().getMenorValorIndicador()));
	    	            	valorCuantitativo.appendChild(minVal);
	    	            	Element funcionTransformacion = document.createElement("funcionTransformacion");
	    	            	funcionTransformacion.setTextContent(ef.getValCuantitativa().nombreFuncionTransformacion(ef.getValCuantitativa().getNumFuncion()));
	    	            	valorCuantitativo.appendChild(funcionTransformacion);
	    	            	Element magnitud = document.createElement("magnitud");
	    	            	magnitud.setTextContent(Double.toString(ef.getValCuantitativa().getMagnitudImpacto()));
	    	            	valorCuantitativo.appendChild(magnitud);

	    	            	elemEfecto.appendChild(valorCuantitativo);
    	            	}

    	            	if (ef.getValCualitativa() !=null){
	    	            	Element valorCualitativo = document.createElement("valorCualitativo");

	    	            	Element signo = document.createElement("signo");
	    	            	signo.setTextContent(Integer.toString(ef.getValCualitativa().getSigno()));
	    	            	valorCualitativo.appendChild(signo);

	    	            	Element acumulacion = document.createElement("acumulacion");
	    	            	acumulacion.setTextContent(Integer.toString(ef.getValCualitativa().getAcumulacion()));
	    	            	valorCualitativo.appendChild(acumulacion);

	    	            	Element extension = document.createElement("extension");
	    	            	extension.setTextContent(Integer.toString(ef.getValCualitativa().getExtension()));
	    	            	valorCualitativo.appendChild(extension);

	    	            	Element extensionCritica = document.createElement("extensionCritica");
	    	            	extensionCritica.setTextContent(Integer.toString(ef.getValCualitativa().getExtensionCritica()));
	    	            	valorCualitativo.appendChild(extensionCritica);

	    	            	Element intensidad = document.createElement("intensidad");
	    	            	intensidad.setTextContent(Integer.toString(ef.getValCualitativa().getIntensidad()));
	    	            	valorCualitativo.appendChild(intensidad);

	    	            	Element persistencia = document.createElement("persistencia");
	    	            	persistencia.setTextContent(Integer.toString(ef.getValCualitativa().getPersistencia()));
	    	            	valorCualitativo.appendChild(persistencia);

	    	            	Element reversibilidad = document.createElement("reversibilidad");
	    	            	reversibilidad.setTextContent(Integer.toString(ef.getValCualitativa().getReversibilidad()));
	    	            	valorCualitativo.appendChild(reversibilidad);

	    	            	Element recuperabilidad = document.createElement("recuperabilidad");
	    	            	recuperabilidad.setTextContent(Integer.toString(ef.getValCualitativa().getRecuperabilidad()));
	    	            	valorCualitativo.appendChild(recuperabilidad);

	    	            	Element periodicidad = document.createElement("periodicidad");
	    	            	periodicidad.setTextContent(Integer.toString(ef.getValCualitativa().getPeriodicidad()));
	    	            	valorCualitativo.appendChild(periodicidad);

	    	            	Element momento = document.createElement("momento");
	    	            	momento.setTextContent(Integer.toString(ef.getValCualitativa().getMomento()));
	    	            	valorCualitativo.appendChild(momento);

	    	            	Element momentoCritico = document.createElement("momentoCritico");
	    	            	momentoCritico.setTextContent(Integer.toString(ef.getValCualitativa().getMomentoCritico()));
	    	            	valorCualitativo.appendChild(momentoCritico);

	    	            	Element efectoImp = document.createElement("efectoImp");
	    	            	efectoImp.setTextContent(Integer.toString(ef.getValCualitativa().getEfecto()));
	    	            	valorCualitativo.appendChild(efectoImp);

	    	            	Element incidencia = document.createElement("incidencia");
	    	            	incidencia.setTextContent(Double.toString(ef.getValCualitativa().getIncidencia()));
	    	            	valorCualitativo.appendChild(incidencia);

	    	            	elemEfecto.appendChild(valorCualitativo);
    	            	}
    	            	if ((ef.getValCualitativa() !=null)&&(ef.getValCuantitativa()!=null)){
	    	            	Element valorTotal = document.createElement("valorTotal");
	    	            	valorTotal.setTextContent(String.valueOf(ef.getValorTotal()));
	    	            	elemEfecto.appendChild(valorTotal);
    	            	}

    	            	listaEfectos.appendChild(elemEfecto);
    	            }

                }
                rootAlt.appendChild(listaEfectos);
                if(listaAlt.get(k).getValorada()){
                	Element valorTotalAlternativa= document.createElement("valorTotalAlternativa");
                	valorTotalAlternativa.setTextContent(String.valueOf(listaAlt.get(k).getValorTotal()));
                	rootAlt.appendChild(valorTotalAlternativa);
                }

                Element valorada= document.createElement("valorada");
                valorada.setTextContent(String.valueOf(listaAlt.get(k).getValorada()));
                rootAlt.appendChild(valorada);

                listaAlternativas.appendChild(rootAlt);
            }
            root.appendChild(listaAlternativas);

            //Creamos la lista de factores.
            Element listaFactores = (Element)document.createElement("listaFactores");

            DefaultTreeModel factores = proy.getFactores();
            DefaultMutableTreeNode raiz = (DefaultMutableTreeNode)factores.getRoot();

            int numHijos = raiz.getChildCount();
            Enumeration children = raiz.children();

            for(int i=0; i< numHijos; i++){
            	int cont = 1;
            	DefaultMutableTreeNode nodoI = (DefaultMutableTreeNode)children.nextElement();
            	Element factor = recorrerFactores(nodoI,document,cont);
            	listaFactores.appendChild(factor);
            }
            root.appendChild(listaFactores);

            //Finalizado el archivo XML se almacena f�sicamente
            writeFile(document, archivo);

        }
        catch (java.lang.Exception e) {
            System.out.println(e.getMessage());
        }
   }
    /**
     * M�todo privado que dado un arbol de factores lo recorre y crea el factor correspondiente.
     * @param factor Arbol de factores y subfactores.
     * @return El elemento factor con factores y subfactores.
     */
    @SuppressWarnings("unchecked")
	private Element recorrerFactores(DefaultMutableTreeNode factor, Document document, int cont){

    	Element elemFactor = (Element)document.createElement("factor");
    	Factor f = (Factor)factor.getUserObject();

    	Element nombreFactor = (Element)document.createElement("nombreFactor");
    	nombreFactor.setTextContent(f.getId());

    	Element peso = (Element)document.createElement("peso");
    	peso.setTextContent(Integer.toString(f.getPeso()));

    	elemFactor.appendChild(nombreFactor);
    	elemFactor.appendChild(peso);

    	//Caso base: es un factor hijo, no tiene subfactores.
    	if(factor.getChildCount() == 0){
        	return elemFactor;
    	}
    	//Caso recursivo: Tiene una lista de subfactores asociada.
    	else{
    		Element listaSubFactores = document.createElement("listaFactores");
    		Enumeration e = factor.children();
    		for(int i=0; i<factor.getChildCount(); i++){
    			DefaultMutableTreeNode nodoI = (DefaultMutableTreeNode)e.nextElement();
    			Element elemSubFactor = recorrerFactores(nodoI, document, cont++);
    			listaSubFactores.appendChild(elemSubFactor);
    		}
    		elemFactor.appendChild(listaSubFactores);
    		return elemFactor;
    	}
    }

    /**
     * M�todo privado que dado un �rbol de acciones lo recorre y crea la acci�n correspondiente.
     * @param factor �rbol de acciones y subacciones.
     * @return El elemento acci�n con acciones y subacciones.
     */
    @SuppressWarnings("unchecked")
	private Element recorrerAcciones(DefaultMutableTreeNode accion, Document document, int cont){

    	Element elemAccion = (Element)document.createElement("accion");
    	Accion a = (Accion)accion.getUserObject();

    	Element nombreAccion = (Element)document.createElement("nombreAccion");
    	nombreAccion.setTextContent(a.getId());

    	elemAccion.appendChild(nombreAccion);

    	//Caso base: es un factor hijo, no tiene subfactores.
    	if(accion.getChildCount() == 0){
        	return elemAccion;
    	}
    	//Caso recursivo: Tiene una lista de subfactores asociada.
    	else{
    		Element listaSubAcciones = document.createElement("listaAcciones");
    		Enumeration e = accion.children();
    		for(int i=0; i<accion.getChildCount(); i++){
    			DefaultMutableTreeNode nodoI = (DefaultMutableTreeNode)e.nextElement();
    			Element elemSubAccion = recorrerAcciones(nodoI, document, cont++);
    			listaSubAcciones.appendChild(elemSubAccion);
    		}
    		elemAccion.appendChild(listaSubAcciones);
    		return elemAccion;
    	}
    }

	@SuppressWarnings("unchecked")
	private Object buscarElemento(DefaultTreeModel arbol, String id){
		Object elem = null;
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)arbol.getRoot();
		Enumeration e = root.breadthFirstEnumeration();
		boolean encontrado = false;
		while(e.hasMoreElements() && !encontrado){
			DefaultMutableTreeNode obj = (DefaultMutableTreeNode)e.nextElement();
			if(obj.getUserObject()!=null && id.equals(obj.getUserObject().toString())){
				encontrado = true;
				elem = obj.getUserObject();
			}
		}
		return elem;
	}

	/**
     * M�todo privado que dado una acci�n recorre su lista de subacciones.
     * @param accion Acci�n que puede tener una lista de subacciones.
     * @return Estructura de �rbol con acciones y subacciones.
     */
    private DefaultMutableTreeNode recorrerAcciones(Element accion){
    	//Caso base
    	if(accion.getElementsByTagName("listaAcciones").getLength() == 0){
    		Accion a = new Accion (accion.getElementsByTagName("nombreAccion").item(0).getTextContent());
    		DefaultMutableTreeNode arb = new DefaultMutableTreeNode();
    		arb.setUserObject(a);
    		return arb;
    	}
    	//Caso recursivo
    	else{
    		//Cogemos la lista de subfactores.
    		Element listaAcciones = (Element)accion.getElementsByTagName("listaAcciones").item(0);
    		NodeList acciones = listaAcciones.getElementsByTagName("accion");
    		//Creamos el nodo padre del arbol.
    		Accion a = new Accion (accion.getElementsByTagName("nombreAccion").item(0).getTextContent());
    		DefaultMutableTreeNode padre = new DefaultMutableTreeNode();
    		padre.setUserObject(a);
    		int index=0;
    		//Recorremos cada subfactor.
    		for(int i = 0; i < acciones.getLength(); i++){
    			DefaultMutableTreeNode arb = recorrerAcciones((Element)acciones.item(i));
    			if(!esSubArbol(arb,padre))
    				padre.insert(arb,index++);
    		}
    		return padre;
    	}
    }
}
