package tslib;

import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Gestiona las tablas de símbolos.
 * 
 * @author Carolina Garza Bravo
 */

public class TS_Gestor {
	/**
	 * Tabla global.
	 */
	private TS global;
	/**
	 * Tabla local.
	 */
	private TS local;
	/**
	 * Tabla de palabras reservadas.
	 */
	private TS palabras_reservadas;
	/**
	 * Número de tablas totales creadas.
	 */
	private int num_tablas;
	/**
	 * Lista de atributos definidos.
	 */
	private HashMap<String,Entry<DescripcionAtributo,TipoDatoAtributo>> atributos_def;
	/**
	 * Si es la primera escritura o no.
	 */
	private boolean primera_escritura;
	/**
	 * Si se muestran mensajes de errores o no.
	 */
	private boolean debug;
	/**
	 * Fichero en el cual se escribirá.
	 */
	private FileWriter	fich;
	
	/**
	 * Tipos de atributos predefinidos de una entrada.
	 */
	public enum DescripcionAtributo {
		/**
		 * Dirección o desplazamiento del identificador.
		 */
		DIR,
		/**
		 * Número de parámetros de una función.
		 */
		NUM_PARAM,
		/**
		 * Tipo de los parámetros de una función.
		 */
		TIPO_PARAM,
		/**
		 * Modo de los parámetros de una función.
		 */
		MODO_PARAM,
		/**
		 * Tipo de retorno de una función.
		 */
		TIPO_RET,
		/**
		 * Nombre secundario de la función.
		 */
		ETIQUETA,
		/**
		 * Si la variable es un parámetro de una función o no.
		 */
		PARAM,
		/**
		 * Cualquier otro tipo de atributo que se quiera definir.
		 */
		OTROS};
	/**
	 * Formas de representar el valor del atributo.
	 */
	public enum TipoDatoAtributo {
		/**
		 * Valor entero de un atributo.
		 */
		ENTERO,
		/**
		 * Valor de cadena de un atributo.
		 */
		CADENA,
		/**
		 * Valor de lista de cadenas de un atributo.
		 */
		LISTA};
	/**
	 * Tipos de tablas de símbolos.
	 */
	public enum Tabla {
		/**
		 * Referencia a la tabla de palabras reservadas.
		 */
		PALRES,
		/**
		 * Referencia a la tabla global.
		 */
		GLOBAL,
		/**
		 * Referencia a la tabla local.
		 */
		LOCAL};
	
	/**
	 * Crea el gestor de tablas de símbolos.
	 * @param nombre_fichero Fichero en el que se escribirán las tablas cuando se necesite.
	 */
	public TS_Gestor(String nombre_fichero) {
		num_tablas=0;
		atributos_def=new HashMap<>();
		primera_escritura=true;
		debug=false;
		try {
			fich=new FileWriter(nombre_fichero);
		} catch (IOException e) {
			System.err.println("Ha habido un error en la entrada-salida del fichero.");
		}
	}
	
	/**
	 * Activa los mensajes de error por pantalla.
	 */
	public void activarDebug() {
		debug=true;
	}
	
	/**
	 * Desactiva los mensajes de error por pantalla.
	 */
	public void desactivarDebug() {
		debug=false;
	}
	
	/**
	 * Crea la tabla de palabras reservadas
	 * @return 0 si todo ha ido bien o 1 si se está intentando crear una tabla de palabras reservadas
	 * cuando ya existe una.
	 */
	public int createTPalabrasReservadas() {
		int res=0;
		if(palabras_reservadas==null) {
			palabras_reservadas=new TS(num_tablas);
			num_tablas++;
		}
		else {
			res=1;
		}
		if(debug) {
			if(res==1) {
				System.out.println("Se ha intentado crear la tabla de palabras reservadas cuando ya existe una.");
			}
		}
		return res;
	}
	
	/**
	 * Crea la tabla global.
	 * @return 0 si todo ha ido bien, 1 si se está intentando crear una tabla que ya existe, 2 si no se ha
	 * declarado la tabla de palabras reservadas antes o 3 si no se han definido los atributos.
	 */
	public int createTSGlobal() {
		int res=0;
		if(global==null) {
			if(palabras_reservadas!=null) {
				if(!atributos_def.isEmpty()) {
					global=new TS(num_tablas);
					num_tablas++;
				}
				else {
					res=3;
				}
			}
			else {
				res=2;
			}
		}
		else {
			res=1;
		}
		if(debug) {
			if(res==1) {
				System.out.println("Se ha intentado crear la tabla global cuando ya existe una.");
			}
			if(res==2) {
				System.out.println("Se ha intentado crear la tabla global cuando no se ha creado la tabla de "
						+ "palabras reservadas antes.");
			}
			if(res==3) {
				System.out.println("Se ha intentado crear la tabla global cuando no se han definido los "
						+ "atributos antes.");
			}
		}
		return res;
	}
	
	/**
	 * Crea una tabla local.
	 * @return 0 si todo ha ido bien, 2 si no se ha creado la tabla global o 14 si no se ha creado la tabla
	 * de palabras reservadas antes.
	 */
	public int createTSLocal() {
		int res=0;
		if(global!=null) {
			if(palabras_reservadas!=null) {
				local=new TS(num_tablas);
				num_tablas++;
			}
			else {
				res=2;
			}
		}
		else {
			res=14;
		}
		if(debug) {
			if(res==2) {
				System.out.println("Se ha intentado crear una tabla local cuando no se ha creado "
						+ "la tabla de palabras reservadas.");
			}
			if(res==14) {
				System.out.println("Se ha intentado crear una tabla local cuando no se ha creado la tabla global.");
			}
		}
		return res;
	}
	
	/**
	 * Define un atributo.
	 * @param nombre_atr Nombre del atributo.
	 * @param tipo_des Descripción del atributo.
	 * @param tipo_valor Tipo de dato del atributo.
	 * @return 0 si todo ha salido bien, 4 si se está definiendo un atributo ya definido, 11 si se quiere utilizar
	 * un nombre para un atributo que ya está asignado a otro atributo o 13 si el nombre del atributo no es válido.
	 */
	public int createAtributo(String nombre_atr,DescripcionAtributo tipo_des,TipoDatoAtributo tipo_valor) {
		int res=0;
		if((normalizar(nombre_atr).equalsIgnoreCase("despl")||normalizar(nombre_atr).equalsIgnoreCase("desplazamiento")||
				normalizar(nombre_atr).equalsIgnoreCase("dir")||normalizar(nombre_atr).equalsIgnoreCase("direccion"))&&
				!tipo_des.equals(TS_Gestor.DescripcionAtributo.DIR)) {
			res=13;
		}
		if((normalizar(nombre_atr).equalsIgnoreCase("numpar")||normalizar(nombre_atr).equalsIgnoreCase("num_par")||
				normalizar(nombre_atr).equalsIgnoreCase("numparam")||normalizar(nombre_atr).equalsIgnoreCase("num_param")||
				normalizar(nombre_atr).equalsIgnoreCase("numerodeparametros")||
				normalizar(nombre_atr).equalsIgnoreCase("numero_de_parametros")||
				normalizar(nombre_atr).equalsIgnoreCase("numero_parametros")||
				normalizar(nombre_atr).equalsIgnoreCase("numeroparametros"))&&
				!tipo_des.equals(TS_Gestor.DescripcionAtributo.NUM_PARAM)) {
			res=13;
		}
		if((normalizar(nombre_atr).equalsIgnoreCase("tipopar")||normalizar(nombre_atr).equalsIgnoreCase("tipo_par")||
				normalizar(nombre_atr).equalsIgnoreCase("tipoparam")||normalizar(nombre_atr).equalsIgnoreCase("tipo_param")||
				normalizar(nombre_atr).equalsIgnoreCase("tipodeparametros")||
				normalizar(nombre_atr).equalsIgnoreCase("tipo_de_parametros")||
				normalizar(nombre_atr).equalsIgnoreCase("tipo_parametros")||
				normalizar(nombre_atr).equalsIgnoreCase("tipoparametros"))&&
				!tipo_des.equals(TS_Gestor.DescripcionAtributo.TIPO_PARAM)) {
			res=13;
		}
		if((normalizar(nombre_atr).equalsIgnoreCase("modopar")||normalizar(nombre_atr).equalsIgnoreCase("modo_par")||
				normalizar(nombre_atr).equalsIgnoreCase("modoparam")||normalizar(nombre_atr).equalsIgnoreCase("modo_param")||
				normalizar(nombre_atr).equalsIgnoreCase("mododeparametros")||
				normalizar(nombre_atr).equalsIgnoreCase("modo_de_parametros")||
				normalizar(nombre_atr).equalsIgnoreCase("modo_parametros")||
				normalizar(nombre_atr).equalsIgnoreCase("modoparametros"))&&
				!tipo_des.equals(TS_Gestor.DescripcionAtributo.MODO_PARAM)) {
			res=13;
		}
		if((normalizar(nombre_atr).equalsIgnoreCase("tiporetorno")||normalizar(nombre_atr).equalsIgnoreCase("tipo_retorno")||
				normalizar(nombre_atr).equalsIgnoreCase("tiporeturn")||normalizar(nombre_atr).equalsIgnoreCase("tipo_return")||
				normalizar(nombre_atr).equalsIgnoreCase("tipo_de_retorno")||normalizar(nombre_atr).equalsIgnoreCase("tipoderetorno")||
				normalizar(nombre_atr).equalsIgnoreCase("tipo_de_return")||normalizar(nombre_atr).equalsIgnoreCase("tipodereturn"))&&
				!tipo_des.equals(TS_Gestor.DescripcionAtributo.TIPO_RET)) {
			res=13;
		}
		if((normalizar(nombre_atr).equalsIgnoreCase("etiqueta")||normalizar(nombre_atr).equalsIgnoreCase("etiqfuncion")||
				normalizar(nombre_atr).equalsIgnoreCase("etiq"))&&!tipo_des.equals(TS_Gestor.DescripcionAtributo.ETIQUETA)) {
			res=13;
		}
		if((normalizar(nombre_atr).equalsIgnoreCase("param")||normalizar(nombre_atr).equalsIgnoreCase("parametro"))&&
				!tipo_des.equals(TS_Gestor.DescripcionAtributo.PARAM)) {
			res=13;
		}
		if(res==0&&!tipo_des.equals(DescripcionAtributo.OTROS)) {
			Iterator<Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>>> it=atributos_def.entrySet().iterator();
			while(it.hasNext()&&res==0) {
				Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>> atributo=it.next();
				DescripcionAtributo des=atributo.getValue().getKey();
				if(des.equals(tipo_des)) {
					res=4;
				}
			}
		}
		if(res==0) {
			if(!atributos_def.containsKey(nombre_atr)) {
				Entry<DescripcionAtributo,TipoDatoAtributo> atrb=new AbstractMap.SimpleEntry<DescripcionAtributo, TipoDatoAtributo>(tipo_des,tipo_valor);
				atributos_def.put(nombre_atr, atrb);
			}
			else {
				res=11;
			}
		}
		if(debug) {
			if(res==4) {
				System.out.println("Se está intentando definir un atributo ya definido.");
			}
			if(res==11) {
				System.out.println("Se está intentando definir un atributo con un nombre que ya está asignado "
						+ "a otro atributo.");
			}
			if(res==13) {
				System.out.println("El nombre del atributo no es válido.");
			}
		}
		return res;
	}
	
	/**
	 * Añade una entrada a la tabla de palabras reservadas.
	 * @param lex Palabra reservada.
	 * @return La posición en la que se ha añadido, 0 si la tabla de palabras reservadas no existe o
	 * si la palabra reservada a añadir ya está en la tabla de palabras reservadas.
	 */
	public int addEntradaTPalabrasReservadas(String lex) {
		if(palabras_reservadas!=null) {
			return palabras_reservadas.addEntrada(lex);
		}
		else {
			if(debug) {
				System.out.println("Se está intentando añadir una entrada en la tabla de palabras reservadas "
						+ "cuando la tabla no existe o cuando la palabra reservada ya está en la tabla de"
						+ "palabras reservadas.");
			}
			return 0;
		}
	}
	
	/**
	 * Añade una entrada a la tabla global.
	 * @param lex Lexema del identificador.
	 * @return La posición en la que se ha añadido, 0 si la tabla global no existe o si el identificador a añadir
	 * ya está en la tabla de símbolos global.
	 */
	public int addEntradaTSGlobal(String lex) {
		if(global!=null) {
			return global.addEntrada(lex);
		}
		else {
			if(debug) {
				System.out.println("Se está intentando añadir una entrada en la tabla global cuando la tabla "
						+ "no existe o cuando el identificador ya está en la tabla global.");
			}
			return 0;
		}
	}
	
	/**
	 * Añade una entrada a la tabla local.
	 * @param lex Lexema del identificador.
	 * @return La posición en la que se ha añadido, 0 si la tabla local lo existe o si el identificador a añadir ya
	 * está en la tabla de símbolos local.
	 */
	public int addEntradaTSLocal(String lex) {
		if(local!=null) {
			return local.addEntrada(lex);
		}
		else {
			if(debug) {
				System.out.println("Se está intentando añadir una entrada en la tabla local cuando la tabla "
						+ "no existe o cuando el identificador ya está en la tabla local.");
			}
			return 0;
		}
	}
	
	/**
	 * Busca en la tabla de palabras reservadas una palabra reservada.
	 * @param lex Palabra Reservada
	 * @return La posición de la palabra reservada o 0 si no se ha encontrado. También devuelve 0 si la tabla
	 * de palabras reservadas no está creada.
	 */
	public int getEntradaTPalabrasReservadas(String lex) {
		if(palabras_reservadas!=null) {
			return palabras_reservadas.getEntrada(lex);
		}
		else {
			if(debug) {
				System.out.println("Se está intentando obtener una entrada de la tabla de palabras reservadas "
						+ "cuando la tabla no existe.");
			}
			return 0;
		}
	}
	
	/**
	 * Busca en la tabla global un identificador.
	 * @param lex Lexema del identificador.
	 * @return La posición del identificador en la tabla global o 0 si no se ha encontrado. También devuelve 0
	 * si la tabla global no está creada.
	 */
	public int getEntradaTSGlobal(String lex) {
		if(global!=null) {
			return global.getEntrada(lex);
		}
		else {
			if(debug) {
				System.out.println("Se está intentando obtener una entrada de la tabla global cuando la tabla "
						+ "no existe.");
			}
			return 0;
		}
	}
	
	/**
	 * Busca en la tabla local un identificador.
	 * @param lex Lexema del identificador.
	 * @return La posición del identificador en la tabla local o 0 si no se ha encontrado. También devuelve 0 si
	 * la tabla local no está creada.
	 */
	public int getEntradaTSLocal(String lex) {
		if(local!=null) {
			return local.getEntrada(lex);
		}
		else {
			if(debug) {
				System.out.println("Se está intentando obtener una entrada de la tabla local cuando la tabla "
						+ "no existe.");
			}
			return 0;
		}
	}
	
	/**
	 * Busca en la tabla global y local (si existe).
	 * @param lex Lexema del identificador.
	 * @return La posición del identificador en la tabla en la que está o 0 si no se ha encontrado. También
	 * devuelve 0 si la tabla global no está creada.
	 */
	public int getEntradaTS(String lex) {
		if(global!=null) {
			if(local!=null) {
				int pos=local.getEntrada(lex);
				if(pos==0) {
					return global.getEntrada(lex);
				}
				else {
					return pos;
				}
			}
			else {
				return global.getEntrada(lex);
			}
		}
		else {
			if(debug) {
				System.out.println("Se está intentando obtener una entrada de cualquier tabla cuando la tabla"
						+ "global no existe.");
			}
			return 0;
		}
	}
	
	/**
	 * Añade el tipo de un identificador y añade los atributos pertinentes. Si el tipo es entero, cadena,
	 * real, lógico, puntero o vector se añadirán los atributos dirección y param. Si el tipo es función o
	 * procedimiento se añadirán los atributos número de parámetros, tipo de parámetros, modo de parámetros,
	 * tipo de retorno y etiqueta. En cualquier caso, se añadirán los atributos OTROS si se han definido.
	 * @param pos Posición del identificador.
	 * @param tipo_id Tipo que se le quiere poner al identificador. Los tipos posibles son: función,
	 * procedimiento, entero, cadena, real, lógico, puntero y vector.
	 * @return 0 si todo ha salido bien, 5 si la posición no es correcta, 6 si el tipo no es correcto, 7 si
	 * el tipo ya existía o 10 si la tabla en la que estaría el identificador no existe o 12 si se intenta añadir
	 * el tipo función a un identificador de la tabla local.
	 */
	public int setTipo(int pos,String tipo_id) {
		int res=0;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.setTipo(pos,tipo_id);
				if(tipo_id.equals("entero")||tipo_id.equals("cadena")||tipo_id.equals("real")||
						tipo_id.equals("lógico")||tipo_id.equals("puntero")||tipo_id.equals("vector")) {
					Iterator<Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>>> it=atributos_def.entrySet().iterator();
					while(it.hasNext()) {
						Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>> atrb=it.next();
						if(atrb.getValue().getKey().equals(DescripcionAtributo.DIR)||
								atrb.getValue().getKey().equals(DescripcionAtributo.PARAM)||
								atrb.getValue().getKey().equals(DescripcionAtributo.OTROS)) {
							global.setAtributo(pos,atrb.getKey(),atrb.getValue().getKey(),atrb.getValue().getValue());
						}
					}
				}
				else {
					Iterator<Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>>> it=atributos_def.entrySet().iterator();
					while(it.hasNext()) {
						Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>> atrb=it.next();
						if(atrb.getValue().getKey().equals(DescripcionAtributo.NUM_PARAM)||
								atrb.getValue().getKey().equals(DescripcionAtributo.TIPO_PARAM)||
								atrb.getValue().getKey().equals(DescripcionAtributo.MODO_PARAM)||
								atrb.getValue().getKey().equals(DescripcionAtributo.TIPO_RET)||
								atrb.getValue().getKey().equals(DescripcionAtributo.ETIQUETA)||
								atrb.getValue().getKey().equals(DescripcionAtributo.OTROS)) {
							global.setAtributo(pos,atrb.getKey(),atrb.getValue().getKey(),atrb.getValue().getValue());
						}
					}
				}
			}
			else {
				res=10;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				if(tipo_id.equals("función")) {
					res=12;
				}
				else {
					local.setTipo(pos,tipo_id);
					if(tipo_id.equals("entero")||tipo_id.equals("cadena")||tipo_id.equals("real")||
							tipo_id.equals("lógico")||tipo_id.equals("puntero")||tipo_id.equals("vector")) {
						Iterator<Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>>> it=atributos_def.entrySet().iterator();
						while(it.hasNext()) {
							Entry<String,Entry<DescripcionAtributo,TipoDatoAtributo>> atrb=it.next();
							if(atrb.getValue().getKey().equals(DescripcionAtributo.DIR)||
									atrb.getValue().getKey().equals(DescripcionAtributo.PARAM)||
									atrb.getValue().getKey().equals(DescripcionAtributo.OTROS)) {
								local.setAtributo(pos,atrb.getKey(),atrb.getValue().getKey(),atrb.getValue().getValue());
							}
						}
					}
				}
			}
			else {
				res=10;
			}
		}
		if(debug) {
			if(res==12) {
				System.out.println("Esta librería no permite lenguajes con anidación de funciones.");
			}
			if(res==10) {
				System.out.println("La tabla a la que se quiere acceder no existe.");
			}
			if(res==5) {
				System.out.println("La posición no es correcta.");
			}
			if(res==6) {
				System.out.println("El tipo no es correcto.");
			}
			if(res==7) {
				System.out.println("La entrada ya tenía un tipo asignado.");
			}
		}
		return res;
	}
	
	/**
	 * Devuelve el tipo de un identificador.
	 * @param pos Posición del identificador.
	 * @return El tipo de la entrada en la posición pos o null si hay error, que puede ser que la posición
	 * no sea correcta o que la tabla en la que estaría el identificador no existe.
	 */
	public String getTipo(int pos) {
		String res=null;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.getTipo(pos);
			}
			else {
				res=null;
			}
		}
		else if(pos<0) { //Está en la tabla local
			if(local!=null) {
				res=local.getTipo(pos);
			}
			else {
				res=null;
			}
		}
		else {
			res=null;
		}
		if(debug) {
			if(res==null) {
				System.out.println("La posición no es correcta.");
			}
		}
		return res;
	}
	
	/**
	 * Da valor entero a un atributo de un identificador.
	 * @param pos Posición del identificador.
	 * @param atr Nombre del atributo.
	 * @param valor Valor del atributo.
	 * @return 0 si todo ha salido bien, 5 si la posición no es correcta, 7 si el atributo ya tenía un valor
	 * asignado, 8 si el tipo de dato del atributo no está definido como ENTERO, 9 si el atributo no existe o
	 * 10 si la tabla en la que estaría el identificador no existe.
	 */
	public int setValorAtributoEnt(int pos,String atr,int valor) {
		int res=0;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.setValorAtributoEnt(pos, atr, valor);
			}
			else {
				res=10;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				res=local.setValorAtributoEnt(pos, atr, valor);
			}
			else {
				res=10;
			}
		}
		if(debug) {
			if(res==10) {
				System.out.println("La tabla a la que se quiere acceder no existe.");
			}
			if(res==5) {
				System.out.println("La posición no es correcta.");
			}
			if(res==9) {
				System.out.println("El atributo " + atr + " no existe.");
			}
			if(res==8) {
				System.out.println("El tipo de dato con el que el atributo " + atr + " es representado no es "
						+ "entero.");
			}
			if(res==7) {
				System.out.println("El atributo " + atr + " ya tenía un valor asignado.");
			}
		}
		return res;
	}
	
	/**
	 * Devuelve el valor entero de un atributo de un identificador.
	 * @param pos Posición del identificador.
	 * @param atr Nombre del atributo.
	 * @return El valor entero del atributo atr del identificador de la posición pos o -1 si hay error,
	 * que puede ser que el atributo o la posición no sean correctos, que la tabla en la que estaría el
	 * identificador no existe o que el atributo no esté declarado como ENTERO.
	 */
	public int getValorAtributoEnt(int pos,String atr) {
		int res=-1;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.getValorAtributoEnt(pos, atr);
			}
			else {
				res=-1;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				res=local.getValorAtributoEnt(pos, atr);
			}
			else {
				res=-1;
			}
		}
		if(debug) {
			if(res==-1) {
				System.out.println("Ha habido algún error con la posición o con el atributo.");
			}
		}
		return res;
	}
	
	/**
	 * Da valor de cadena a un atributo de un identificador.
	 * @param pos Posición del identificador.
	 * @param atr Nombre del atributo.
	 * @param valor Valor del atributo.
	 * @return 0 si todo ha salido bien, 5 si la posición no es correcta, 7 si el atributo ya tenía un
	 * valor asignado, 8 si el tipo de dato del atributo no está definido como CADENA, 9 si el atributo no
	 * existe o 10 si la tabla en la que estaría el identificador no existe.
	 */
	public int setValorAtributoCad(int pos,String atr,String valor) {
		int res=0;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.setValorAtributoCad(pos, atr, valor);
			}
			else {
				res=10;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				res=local.setValorAtributoCad(pos, atr, valor);
			}
			else {
				res=10;
			}
		}
		if(debug) {
			if(res==10) {
				System.out.println("La tabla a la que se quiere acceder no existe.");
			}
			if(res==5) {
				System.out.println("La posición no es correcta.");
			}
			if(res==9) {
				System.out.println("El atributo " + atr + " no existe.");
			}
			if(res==8) {
				System.out.println("El tipo de dato con el que el atributo " + atr + " es representado no es "
						+ "cadena.");
			}
			if(res==7) {
				System.out.println("El atributo " + atr + " ya tenía un valor asignado.");
			}
		}
		return res;
	}
	
	/**
	 * Devuelve el valor de cadena de un atributo de un identificador.
	 * @param pos Posición del identificador.
	 * @param atr Nombre del atributo.
	 * @return El valor de cadena del atributo atr del identificador de la posición pos o null si hay error,
	 * que puede ser que el atributo o la posición no sean correctos, que la tabla en la que estaría el
	 * identificador no existe o que el atributo no esté declarado como CADENA.
	 */
	public String getValorAtributoCad(int pos,String atr) {
		String res=null;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.getValorAtributoCad(pos, atr);
			}
			else {
				res=null;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				res=local.getValorAtributoCad(pos, atr);
			}
			else {
				res=null;
			}
		}
		if(debug) {
			if(res==null) {
				System.out.println("Ha habido algún error con la posición o con el atributo.");
			}
		}
		return res;
	}
	
	/**
	 * Da valor en forma de lista de cadenas de un atributo de un identificador.
	 * @param pos Posición del identificador.
	 * @param atr Nombre del atributo.
	 * @param valor Valor del atributo.
	 * @return 0 si todo ha salido bien, 5 si la posición no es correcta, 7 si el atributo ya tenía un valor
	 * asignado, 8 si el tipo de dato del atributo no está definido como LISTA, 9 si el atributo no existe o
	 * 10 si la tabla en la que estaría el identificador no existe.
	 */
	public int setValorAtributoLista(int pos,String atr,String[] valor) {
		int res=0;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.setValorAtributoLista(pos, atr, valor);
			}
			else {
				res=10;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				res=local.setValorAtributoLista(pos, atr, valor);
			}
			else {
				res=10;
			}
		}
		if(debug) {
			if(res==10) {
				System.out.println("La tabla a la que se quiere acceder no existe.");
			}
			if(res==5) {
				System.out.println("La posición no es correcta.");
			}
			if(res==9) {
				System.out.println("El atributo " + atr + " no existe.");
			}
			if(res==8) {
				System.out.println("El tipo de dato con el que el atributo " + atr + " es representado no es "
						+ "lista.");
			}
			if(res==7) {
				System.out.println("El atributo " + atr + " ya tenía un valor asignado.");
			}
		}
		return res;
	}
	
	/**
	 * Devuelve el valor en forma de lista de cadenas de un atributo de un identificador.
	 * @param pos Posición del identificador.
	 * @param atr Nombre del atributo.
	 * @return El valor en forma de lista de cadenas del atributo atr del identificador de la posición pos o
	 * null si hay error, que puede ser que el atributo o la posición no sean correctos, que la tabla en la que
	 * estaría el identificador no existe o que el atributo no esté declarado como LISTA.
	 */
	public String[] getValorAtributoLista(int pos,String atr) {
		String[] res=null;
		if(pos>0) { //Está en la tabla global
			if(global!=null) {
				res=global.getValorAtributoLista(pos, atr);
			}
			else {
				res=null;
			}
		}
		else { //Está en la tabla local
			if(local!=null) {
				res=local.getValorAtributoLista(pos, atr);
			}
			else {
				res=null;
			}
		}
		if(debug) {
			if(res==null) {
				System.out.println("Ha habido algún error con la posición o con el atributo.");
			}
		}
		return res;
	}
	
	/**
	 * Muestra por pantalla una tabla de símbolos.
	 * @param tabla Tabla que se quiere mostrar.
	 * @return 0 si todo ha salido bien o 10 si la tabla que se quiere mostrar no existe.
	 */
	public int show(Tabla tabla) {
		int res=0;
		if(tabla.equals(Tabla.GLOBAL)) {
			if(global!=null) {
				global.show();
			}
			else {
				res=10;
			}
		}
		else if(tabla.equals(Tabla.LOCAL)) {
			if(local!=null) {
				local.show();
			}
			else {
				res=10;
			}
		}
		else {
			if(palabras_reservadas!=null) {
				palabras_reservadas.show();
			}
			else {
				res=10;
			}
		}
		if(debug) {
			if(res==10) {
				System.out.println("Se está intentando mostrar una tabla que no existe.");
			}
		}
		return res;
	}
	
	/**
	 * Escribe en un fichero la representación de una tabla de símbolos.
	 * @param tabla Tabla que se quiere escribir.
	 * @return 0 si se ha escrito correctamente o 10 si la tabla que se quiere escribir no existe.
	 */
	public int write(Tabla tabla) {
		int res=0;
		if(tabla.equals(Tabla.GLOBAL)) {
			if(global!=null) {
				global.write(fich,primera_escritura);
			}
			else {
				res=10;
			}
		}
		else if(tabla.equals(Tabla.LOCAL)) {
			if(local!=null) {
				local.write(fich,primera_escritura);
			}
			else {
				res=10;
			}
		}
		else {
			if(palabras_reservadas!=null) {
				palabras_reservadas.write(fich,primera_escritura);
			}
			else {
				res=10;
			}
		}
		if(debug) {
			if(res==10) {
				System.out.println("Se está intentando escribir una tabla que no existe.");
			}
		}
		if(primera_escritura&&res==0) {
			primera_escritura=false;
		}
		return res;
	}
	
	/**
	 * Destruye una tabla de símbolos.
	 * @param tabla Tabla que se quiere destruir.
	 * @return 0 si todo ha salido bien, 10 si la tabla que se quiere destruir no existe, 15 si se está intentando
	 * destruir la tabla global sin haber destruido la tabla local o 16 si se está intentando destruir la tabla de
	 * palabras reservadas sin haber destruido la tabla global.
	 */
	public int destroy(Tabla tabla) {
		int res=0;
		if(tabla.equals(Tabla.GLOBAL)) {
			if(local==null) {
				if(global!=null) {
					global.destroy();
					global=null;
				}
				else {
					res=10;
				}
			}
			else {
				res=15;
			}
		}
		else if(tabla.equals(Tabla.LOCAL)) {
			if(local!=null) {
				local.destroy();
				local=null;
			}
			else {
				res=10;
			}
		}
		else {
			if(global==null) {
				if(palabras_reservadas!=null) {
					palabras_reservadas.destroy();
					palabras_reservadas=null;
				}
				else {
					res=10;
				}
			}
			else {
				res=16;
			}
		}
		if(debug) {
			if(res==10) {
				System.out.println("Se está intentando destruir una tabla que no existe.");
			}
			if(res==15) {
				System.out.println("Se está intentando destruir la tabla global sin haber destruido la tabla"
						+ "local.");
			}
			if(res==16) {
				System.out.println("Se está intentando destruir la tabla de palabras reservadas sin haber destruido"
						+ " la tabla local.");
			}
		}
		if(tabla.equals(Tabla.GLOBAL)) {
			try {
				fich.close();
			} catch (IOException e) {
				System.err.println("Ha habido un error en la entrada-salida del fichero.");
			}
		}
		return res;
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
