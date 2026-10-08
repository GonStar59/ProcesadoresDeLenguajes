package tslib;

import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Representación de una entrada.
 * 
 * @author Carolina Garza Bravo
 */

public class Entrada {
	/**
	 * Lexema del id
	 */
	private String lex;
	/**
	 * Tipo del lexema. Los posibles valores son: 
	 * función, procedimiento, entero, cadena, real, lógico, puntero y vector.
	 */
	private String tipo;
	/**
	 * Lista de atributos asociados al lexema.
	 */
	private HashMap<String,Atributo> atributos;
	
	/**
	 * Lista de atributos pero ordenada de otra manera. Esta variable de clase ayudará a que la impresión de
	 * la entrada sea más sencilla.
	 */
	private HashMap<TS_Gestor.DescripcionAtributo,Atributo> des_atrib;
	
	/**
	 * Crea una entrada.
	 * @param lex Lexema del id.
	 */
	public Entrada(String lex) {
		this.lex=lex;
		tipo=null;
		atributos=new HashMap<>();
		des_atrib=new HashMap<>();
	}
	
	/**
	 * Devuelve el lexema de la entrada.
	 * @return El lexema de la entrada.
	 */
	public String getLexema() {
		return lex;
	}
	
	/**
	 * Da valor al tipo del id
	 * @param tipo Tipo que se la va a dar al id. Los tipos posibles son: función, procedimiento, entero,
	 * cadena, real, lógico, puntero y vector.
	 * @return 0 si todo ha salido bien, 6 si el tipo no es correcto o 7 si el id ya tenía un tipo asignado.
	 */
	public int setTipo(String tipo) {
		int res=0;
		if(tipo.equals("función")||tipo.equals("procedimiento")||tipo.equals("entero")||tipo.equals("cadena")||
				tipo.equals("real")||tipo.equals("lógico")||tipo.equals("puntero")||tipo.equals("vector")) {
			if(this.tipo==null) {
				this.tipo=tipo;
			}
			else {
				res=7;
			}
		}
		else {
			res=6;
		}
		
		return res;
	}
	
	/**
	 * Devuelve el tipo del id.
	 * @return El tipo del id.
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Añade un atributo a la entrada.
	 * @param atr Nombre del atributo.
	 * @param des Descripción del atributo.
	 * @param td Tipo de dato del atributo.
	 * @return 0 si todo ha ido bien o 13 si el nombre del atributo no es válido
	 */
	public int setAtributo(String atr,TS_Gestor.DescripcionAtributo des,TS_Gestor.TipoDatoAtributo td) {
		int res=0;
		if((normalizar(atr).equalsIgnoreCase("despl")||normalizar(atr).equalsIgnoreCase("desplazamiento")||
				normalizar(atr).equalsIgnoreCase("dir")||normalizar(atr).equalsIgnoreCase("direccion"))&&
				!des.equals(TS_Gestor.DescripcionAtributo.DIR)) {
			res=13;
		}
		if((normalizar(atr).equalsIgnoreCase("numpar")||normalizar(atr).equalsIgnoreCase("num_par")||
				normalizar(atr).equalsIgnoreCase("numparam")||normalizar(atr).equalsIgnoreCase("num_param")||
				normalizar(atr).equalsIgnoreCase("numerodeparametros")||
				normalizar(atr).equalsIgnoreCase("numero_de_parametros")||
				normalizar(atr).equalsIgnoreCase("numero_parametros")||
				normalizar(atr).equalsIgnoreCase("numeroparametros"))&&
				!des.equals(TS_Gestor.DescripcionAtributo.NUM_PARAM)) {
			res=13;
		}
		if((normalizar(atr).equalsIgnoreCase("tipopar")||normalizar(atr).equalsIgnoreCase("tipo_par")||
				normalizar(atr).equalsIgnoreCase("tipoparam")||normalizar(atr).equalsIgnoreCase("tipo_param")||
				normalizar(atr).equalsIgnoreCase("tipodeparametros")||
				normalizar(atr).equalsIgnoreCase("tipo_de_parametros")||
				normalizar(atr).equalsIgnoreCase("tipo_parametros")||
				normalizar(atr).equalsIgnoreCase("tipoparametros"))&&
				!des.equals(TS_Gestor.DescripcionAtributo.TIPO_PARAM)) {
			res=13;
		}
		if((normalizar(atr).equalsIgnoreCase("modopar")||normalizar(atr).equalsIgnoreCase("modo_par")||
				normalizar(atr).equalsIgnoreCase("modoparam")||normalizar(atr).equalsIgnoreCase("modo_param")||
				normalizar(atr).equalsIgnoreCase("mododeparametros")||
				normalizar(atr).equalsIgnoreCase("modo_de_parametros")||
				normalizar(atr).equalsIgnoreCase("modo_parametros")||
				normalizar(atr).equalsIgnoreCase("modoparametros"))&&
				!des.equals(TS_Gestor.DescripcionAtributo.MODO_PARAM)) {
			res=13;
		}
		if((normalizar(atr).equalsIgnoreCase("tiporetorno")||normalizar(atr).equalsIgnoreCase("tipo_retorno")||
				normalizar(atr).equalsIgnoreCase("tiporeturn")||normalizar(atr).equalsIgnoreCase("tipo_return")||
				normalizar(atr).equalsIgnoreCase("tipo_de_retorno")||normalizar(atr).equalsIgnoreCase("tipoderetorno")||
				normalizar(atr).equalsIgnoreCase("tipo_de_return")||normalizar(atr).equalsIgnoreCase("tipodereturn"))&&
				!des.equals(TS_Gestor.DescripcionAtributo.TIPO_RET)) {
			res=13;
		}
		if((normalizar(atr).equalsIgnoreCase("etiqueta")||normalizar(atr).equalsIgnoreCase("etiqfuncion")||
				normalizar(atr).equalsIgnoreCase("etiq"))&&!des.equals(TS_Gestor.DescripcionAtributo.ETIQUETA)) {
			res=13;
		}
		if((normalizar(atr).equalsIgnoreCase("param")||normalizar(atr).equalsIgnoreCase("parametro"))&&
				!des.equals(TS_Gestor.DescripcionAtributo.PARAM)) {
			res=13;
		}
		if(res==0) {
			Atributo atr_nuevo=new Atributo(atr,des,td);
			atributos.put(atr, atr_nuevo);
			des_atrib.put(des, atr_nuevo);
		}
		return res;
	}
	
	/**
	 * Da valor entero un atributo.
	 * @param atr Nombre del atributo.
	 * @param valor Valor que se le quiere dar al atributo.
	 * @return 0 si todo ha salido bien, 7 si el atributo ya tenía un valor asignado, 8 si el tipo de dato del
	 * atributo no es ENTERO o 9 si el atributo no existe.
	 */
	public int setValorAtributoEnt(String atr,int valor) {
		int res=0;
		if(atributos.containsKey(atr)) {
			res=atributos.get(atr).setValorEnt(valor);
			des_atrib.put(atributos.get(atr).getDescripcionAtributo(), atributos.get(atr));
		}
		else {
			res=9;
		}
		return res;
	}
	
	/**
	 * Devuelve el valor entero de un atributo.
	 * @param atr Nombre del atributo del que se quiere saber su valor.
	 * @return El valor entero de un atributo o -1 si hay error, que puede ser que el tipo de dato del atributo
	 * no sea ENTERO o que el atributo no sea correcto.
	 */
	public int getValorAtributoEnt(String atr) {
		if(atributos.containsKey(atr)) {
			return atributos.get(atr).getValorEnt();
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Da valor de cadena a un atributo.
	 * @param atr Nombre del atributo.
	 * @param valor Valor que se le quiere dar al atributo.
	 * @return 0 si todo ha salido bien, 7 si el atributo ya tenía un valor asignado, 8 si el tipo de dato del
	 * atributo no es CADENA o 9 si el atributo no existe.
	 */
	public int setValorAtributoCad(String atr,String valor) {
		int res=0;
		if(atributos.containsKey(atr)) {
			res=atributos.get(atr).setValorCad(valor);
			des_atrib.put(atributos.get(atr).getDescripcionAtributo(), atributos.get(atr));
		}
		else {
			res=9;
		}
		return res;
	}
	
	/**
	 * Devuelve el valor de cadena de un atributo.
	 * @param atr Nombre del atributo del que se quere saber su valor.
	 * @return El valor entero de un atributo o null si hay error, que puede ser que el tipo de dato del atributo
	 * no sea CADENA o que el atributo no sea correcto.
	 */
	public String getValorAtributoCad(String atr) {
		if(atributos.containsKey(atr)) {
			return atributos.get(atr).getValorCad();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Da valor en forma de lista de cadenas a un atributo.
	 * @param atr Nombre del atributo.
	 * @param valor Valor que se le quiere dar al atributo.
	 * @return 0 si todo ha salido bien, 7 si el atributo ya tenía un valor asignado, 8 si el tipo de dato del
	 * atributo no es LISTA o 9 si el atributo no existe.
	 */
	public int setValorAtributoLista(String atr,String[] valor) {
		int res=0;
		if(atributos.containsKey(atr)) {
			res=atributos.get(atr).setValorLista(valor);
			des_atrib.put(atributos.get(atr).getDescripcionAtributo(), atributos.get(atr));
		}
		else {
			res=9;
		}
		return res;
	}
	
	/**
	 * Devuelve el valor en forma de lista de cadenas de un atributo.
	 * @param atr Nombre del atributo del que se quiere saber su valor.
	 * @return El valor entero de un atributo o null si hay error, que puede ser que el tipo de dato del atributo
	 * no sea LISTA o que el atributo no sea correcto.
	 */
	public String[] getValorAtributoLista(String atr) {
		if(atributos.containsKey(atr)) {
			return atributos.get(atr).getValorLista();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Imprime por pantalla la representación de la entrada.
	 */
	public void show() {
		System.out.println("*LEXEMA: '" + lex + "'");
		System.out.println("	Atributos:");
		if(tipo==null) {
			System.out.println("	+ Tipo: '-'");
		}
		else {
			System.out.println("	+ Tipo: '" + normalizar(tipo) + "'");
		}
		TreeMap<TS_Gestor.DescripcionAtributo,Atributo> tree=new TreeMap<>(des_atrib);
		Iterator<Entry<TS_Gestor.DescripcionAtributo,Atributo>> it=tree.entrySet().iterator();
		while(it.hasNext()) {
			Entry<TS_Gestor.DescripcionAtributo,Atributo> entrada=it.next();
			Atributo atr=entrada.getValue();
			TS_Gestor.DescripcionAtributo des=entrada.getKey();
			switch(des) {
			case DIR:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ Despl: 0");
					}
					else {
						System.out.println("	+ Despl: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ Despl: '-'");
					}
					else {
						System.out.println("	+ Despl: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ Despl: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						System.out.println("	+ Despl: '" + valor[0] + "'");
					}
				}
				break;
			case NUM_PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ numParam: 0");
					}
					else {
						System.out.println("	+ numParam: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ numParam: '-'");
					}
					else {
						System.out.println("	+ numParam: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ numParam: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						System.out.println("	+ numParam: '" + valor[0] + "'");
					}
				}
				break;
			case TIPO_PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ TipoParam: 0");
					}
					else {
						System.out.println("	+ TipoParam: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ TipoParam: '-'");
					}
					else {
						System.out.println("	+ TipoParam: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ TipoParam: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						for(int i=0;i<valor.length;i++) {
							System.out.println("	+ TipoParam" + (i+1) +": '" + valor[i] + "'");
						}
					}
				}
				break;
			case MODO_PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ ModoParam: 0");
					}
					else {
						System.out.println("	+ ModoParam: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ ModoParam: '-'");
					}
					else {
						System.out.println("	+ ModoParam: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ ModoParam: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						for(int i=0;i<valor.length;i++) {
							System.out.println("	+ ModoParam" + (i+1) +": '" + valor[i] + "'");
						}
					}
				}
				break;
			case TIPO_RET:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ TipoRetorno: 0");
					}
					else {
						System.out.println("	+ TipoRetorno: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ TipoRetorno: '-'");
					}
					else {
						System.out.println("	+ TipoRetorno: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ TipoRetorno: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						System.out.println("	+ TipoRetorno: '" + valor[0] + "'");
					}
				}
				break;
			case ETIQUETA:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ EtiqFuncion: 0");
					}
					else {
						System.out.println("	+ EtiqFuncion: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ EtiqFuncion: '-'");
					}
					else {
						System.out.println("	+ EtiqFuncion: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ EtiqFuncion: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						System.out.println("	+ EtiqFuncion: '" + valor[0] + "'");
					}
				}
				break;
			case PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ Param: 0");
					}
					else {
						System.out.println("	+ Param: " + atr.getValorEnt());
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						System.out.println("	+ Param: '-'");
					}
					else {
						System.out.println("	+ Param: '" + atr.getValorCad() + "'");
					}
				}
				else {
					if(!atr.tieneValor()) {
						System.out.println("	+ Param: '-'");
					}
					else {
						String[] valor=atr.getValorLista();
						System.out.println("	+ Param: '" + valor[0] + "'");
					}
				}
				break;
			default:
			}
		}
		Iterator<Entry<String,Atributo>> it2=atributos.entrySet().iterator();
		while(it2.hasNext()) {
			Entry<String,Atributo> entrada2=it2.next();
			Atributo atr2=entrada2.getValue();
			if(atr2.getDescripcionAtributo().equals(TS_Gestor.DescripcionAtributo.OTROS)) {
				if(atr2.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr2.tieneValor()) {
						//no se imprime
					}
					else {
						System.out.println("	+ " + normalizar(atr2.getNombreAtributo()) + ": " + atr2.getValorEnt());
					}
				}
				else if(atr2.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr2.tieneValor()) {
						//System.out.println("	+ " + atr2.getNombreAtributo() + ": '-'");
					}
					else {
						System.out.println("	+ " + normalizar(atr2.getNombreAtributo()) + ": '" + atr2.getValorCad() + "'");
					}
				}
				else {
					if(!atr2.tieneValor()) {
						//System.out.println("	+ " + atr2.getNombreAtributo() + ": '-'");
					}
					else {
						String[] valor=atr2.getValorLista();
						for(int i=0;i<valor.length;i++) {
							System.out.println("	+ " + normalizar(atr2.getNombreAtributo()) + (i+1) +": '" + valor[i] + "'");
						}
					}
				}
			}
		}
	}
	
	/**
	 * Escribe en un fichero la representación de la entrada.
	 * @param fich Fichero en el que se quiere escribir.
	 * @param primera_escritura Si es la primera escritura o no.
	 */
	public void write(FileWriter fich,boolean primera_escritura) {
		escribirFichero(fich,"*LEXEMA: '" + lex + "'\n",primera_escritura);
		escribirFichero(fich,"	Atributos:\n",primera_escritura);
		if(tipo==null) {
			escribirFichero(fich,"	+ Tipo: '-'\n",primera_escritura);
		}
		else {
			escribirFichero(fich,"	+ Tipo: '" + normalizar(tipo) + "'\n",primera_escritura);
		}
		TreeMap<TS_Gestor.DescripcionAtributo,Atributo> tree=new TreeMap<>(des_atrib);
		Iterator<Entry<TS_Gestor.DescripcionAtributo,Atributo>> it=tree.entrySet().iterator();
		while(it.hasNext()) {
			Entry<TS_Gestor.DescripcionAtributo,Atributo> entrada=it.next();
			Atributo atr=entrada.getValue();
			TS_Gestor.DescripcionAtributo des=entrada.getKey();
			switch(des) {
			case DIR:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ Despl: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ Despl: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ Despl: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ Despl: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ Despl: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						escribirFichero(fich,"	+ Despl: '" + valor[0] + "'\n",primera_escritura);
					}
				}
				break;
			case NUM_PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ numParam: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ numParam: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ numParam: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ numParam: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ numParam: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						escribirFichero(fich,"	+ numParam: '" + valor[0] + "'\n",primera_escritura);
					}
				}
				break;
			case TIPO_PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ TipoParam: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ TipoParam: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ TipoParam: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ TipoParam: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ TipoParam: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						for(int i=0;i<valor.length;i++) {
							escribirFichero(fich,"	+ TipoParam" + (i+1) +": '" + valor[i] + "'\n",primera_escritura);
						}
					}
				}
				break;
			case MODO_PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ ModoParam: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ ModoParam: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ ModoParam: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ ModoParam: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ ModoParam: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						for(int i=0;i<valor.length;i++) {
							escribirFichero(fich,"	+ ModoParam" + (i+1) +": '" + valor[i] + "'\n",primera_escritura);
						}
					}
				}
				break;
			case TIPO_RET:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ TipoRetorno: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ TipoRetorno: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ TipoRetorno: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ TipoRetorno: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ TipoRetorno: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						escribirFichero(fich,"	+ TipoRetorno: '" + valor[0] + "'\n",primera_escritura);
					}
				}
				break;
			case ETIQUETA:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ EtiqFuncion: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ EtiqFuncion: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ EtiqFuncion: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ EtiqFuncion: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ EtiqFuncion: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						escribirFichero(fich,"	+ EtiqFuncion: '" + valor[0] + "'\n",primera_escritura);
					}
				}
				break;
			case PARAM:
				if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ Param: 0\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ Param: " + atr.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ Param: '-'\n",primera_escritura);
					}
					else {
						escribirFichero(fich,"	+ Param: '" + atr.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr.tieneValor()) {
						escribirFichero(fich,"	+ Param: '-'\n",primera_escritura);
					}
					else {
						String[] valor=atr.getValorLista();
						escribirFichero(fich,"	+ Param: '" + valor[0] + "'\n",primera_escritura);
					}
				}
				break;
			default:
			}
		}
		Iterator<Entry<String,Atributo>> it2=atributos.entrySet().iterator();
		while(it2.hasNext()) {
			Entry<String,Atributo> entrada2=it2.next();
			Atributo atr2=entrada2.getValue();
			if(atr2.getDescripcionAtributo().equals(TS_Gestor.DescripcionAtributo.OTROS)) {
				if(atr2.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.ENTERO)) {
					if(!atr2.tieneValor()) {
						//no se escribe
					}
					else {
						escribirFichero(fich,"	+ " + normalizar(atr2.getNombreAtributo()) + ": " +
					atr2.getValorEnt() + "\n",primera_escritura);
					}
				}
				else if(atr2.getTipoDatoAtributo().equals(TS_Gestor.TipoDatoAtributo.CADENA)) {
					if(!atr2.tieneValor()) {
						//no se escribe
					}
					else {
						escribirFichero(fich,"	+ " + normalizar(atr2.getNombreAtributo()) + ": '" +
					atr2.getValorCad() + "'\n",primera_escritura);
					}
				}
				else {
					if(!atr2.tieneValor()) {
						//no se escribe
					}
					else {
						String[] valor=atr2.getValorLista();
						for(int i=0;i<valor.length;i++) {
							escribirFichero(fich,"	+ " + normalizar(atr2.getNombreAtributo()) + (i+1) +
									": '" + valor[i] + "'\n",primera_escritura);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Encapsulamiento de la función write de la clase FileWriter.
	 * @param fich Fichero en el que se va a escribir.
	 * @param texto Texto que se va a escribir.
	 * @param primera_escritura Si es la primera escritura o no.
	 */
	private void escribirFichero(FileWriter fich,String texto,boolean primera_escritura) {
		if(!primera_escritura) {
			try {
				fich.write(texto);
			} catch (IOException e) {
				System.err.println("Ha habido un error en la entrada-salida del fichero.");
			}
		}
		else {
			try {
				fich.append(texto);
			} catch (IOException e) {
				System.err.println("Ha habido un error en la entrada-salida del fichero.");
			}
		}
	}
	
	/**
	 * Normaliza un nombre de atributo.
	 * @param nombre Nombre de atributo a normalizar.
	 * @return El nombre normalizado.
	 */
	private String normalizar(String nombre) {
		if (nombre == null || nombre.isEmpty()) {
            return nombre; //Devuelve la entrada original si está vacía o es nula
        }

        // Quitar los acentos usando Normalizer
        String sinAcentos = Normalizer.normalize(nombre, Normalizer.Form.NFD)
                                      .replaceAll("\\p{M}", "");

        // Quitar los espacios
        String sinEspacios = sinAcentos.replaceAll("\\s+", "_");

        //Agregar una letra si comienza con un número
        if (Character.isDigit(sinEspacios.charAt(0))||sinEspacios.charAt(0)=='_') {
            sinEspacios = "A_" + sinEspacios;
        }

        return sinEspacios;
	}
}
