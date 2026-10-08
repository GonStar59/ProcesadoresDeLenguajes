package tslib;

/**
 * Representación de un atributo.
 * 
 * @author Carolina Garza Bravo
 */

public class Atributo {
	/**
	 * Nombre del atributo.
	 */
	private String nombre;
	/**
	 * Tipo de dato que el atributo utiliza.
	 */
	private TS_Gestor.TipoDatoAtributo td;
	/**
	 * Descripción del atributo.
	 */
	private TS_Gestor.DescripcionAtributo des;
	/**
	 * Valor entero del atributo.
	 */
	private int valorEnt;
	/**
	 * Valor de cadena del atributo.
	 */
	private String valorCad;
	/**
	 * Valor en forma de lista de cadenas.
	 */
	private String valorLista[];
	/**
	 * Indica si al atributo ya se le ha asignado valor o no.
	 */
	private boolean valor;
	
	/**
	 * Crea un atributo.
	 * @param nombre Nombre del atributo.
	 * @param des Decripción del atributo.
	 * @param td Tipo de dato del atributo.
	 */
	public Atributo(String nombre,TS_Gestor.DescripcionAtributo des,TS_Gestor.TipoDatoAtributo td) {
		this.nombre=nombre;
		this.des=des;
		this.td=td;
		valorEnt=-1;
		valorCad=null;
		valorLista=null;
		valor=false;
	}
	
	/**
	 * Devuelve el nombre del atributo.
	 * @return El nombre del atributo.
	 */
	public String getNombreAtributo() {
		return nombre;
	}
	
	/**
	 * Devuelve el tipo de dato del atributo.
	 * @return El tipo de dato del atributo.
	 */
	public TS_Gestor.TipoDatoAtributo getTipoDatoAtributo() {
		return td;
	}
	
	/**
	 * Devuelve la descripción del atributo.
	 * @return La descripción del atributo.
	 */
	public TS_Gestor.DescripcionAtributo getDescripcionAtributo() {
		return des;
	}
	
	/**
	 * Devuelve si el atributo ya tiene un valor asociado o no.
	 * @return Si tiene un valor asociado o no.
	 */
	public boolean tieneValor() {
		return valor;
	}
	
	/**
	 * Da valor entero al atributo.
	 * @param valor Valor que se le quiere dar.
	 * @return 0 si todo ha salido bien, 8 si el tipo de dato del atributo no es entero o 7 si el atributo
	 * ya tenía valor.
	 */
	public int setValorEnt(int valor) {
		int res=0;
		if(td==TS_Gestor.TipoDatoAtributo.ENTERO) {
			if(!this.valor) {
				valorEnt=valor;
				this.valor=true;
			}
			else {
				res=7;
			}
		}
		else {
			res=8;
		}
		return res;
	}
	
	/**
	 * Devuelve el valor entero del atributo.
	 * @return El valor entero del atributo o -1 si el tipo de dato del atributo no es ENTERO.
	 */
	public int getValorEnt() {
		if(td==TS_Gestor.TipoDatoAtributo.ENTERO) {
			if(valorEnt==-1) {
				System.out.println("-1 es un valor válido.");
			}
			return valorEnt;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Da valor de cadena al atributo.
	 * @param valor Valor que se le quiere dar al atributo.
	 * @return 0 si todo ha salido bien, 8 si el tipo de dato del atributo no es cadena o 7 si el atributo
	 * ya tenía valor.
	 */
	public int setValorCad(String valor) {
		int res=0;
		if(td==TS_Gestor.TipoDatoAtributo.CADENA) {
			if(!this.valor) {
				valorCad=valor;
				this.valor=true;
			}
			else {
				res=7;
			}
		}
		else {
			res=8;
		}
		return res;
	}
	
	/**
	 * Devuelve el valor de cadena del atributo.
	 * @return El valor de cadena del atributo o null si el tipo de dato del atributo no es CADENA.
	 */
	public String getValorCad() {
		if(td==TS_Gestor.TipoDatoAtributo.CADENA) {
			return valorCad;
		}
		else {
			return null;
		}		
	}
	
	/**
	 * Da valor en forma de lista de cadenas al atributo.
	 * @param valor Valor que se le quiere dar al atributo.
	 * @return 0 si todo ha salido bien, 8 si el tipo de dato del atributo no es lista o 7 si el atributo
	 * ya tenía valor.
	 */
	public int setValorLista(String[] valor) {
		int res=0;
		if(td==TS_Gestor.TipoDatoAtributo.LISTA) {
			if(!this.valor) {
				valorLista=valor;
				this.valor=true;
			}
			else {
				res=7;
			}
		}
		else {
			res=8;
		}
		return res;
	}
	
	/**
	 * Devuelve el valor en forma de lista de cadenas del atributo.
	 * @return El valor en forma de lista de cadenas del atributo o null si el tipo de dato del atributo
	 * no es LISTA.
	 */
	public String[] getValorLista() {
		if(td==TS_Gestor.TipoDatoAtributo.LISTA) {
			return valorLista;
		}
		else {
			return null;
		}
	}
	
}
