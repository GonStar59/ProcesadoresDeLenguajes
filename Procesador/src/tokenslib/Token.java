package tokenslib;

public class Token {
	
	private TipoToken tipo;
	
	private Object atributo;
	
	public Token(TipoToken tipo ,Object atributo) {
		this.tipo=tipo;
		this.atributo=atributo;
	}
	
	public TipoToken getTipoToken() {
		return tipo;
	}
	
	public int getTipoInt() {
		return tipo.ordinal();
	}
	
	public Object getAtributo() {
		return atributo; 
	}
	
	public int getAtributoInt() throws Exception {
		if(!(atributo instanceof Integer)) {
			throw new Exception("no se puede retornar un entero no es un entero");
		}
		return (Integer) atributo; 
	}
	
	public double getAtributoFloat() throws Exception {
		if(!(atributo instanceof Double)) {
			throw new Exception("no se puede convertir a double no es instancia de double");
		}
		return (Double) atributo; 
	}
	
	public String getAtributoString() throws Exception {
		if(!(atributo instanceof String)) {
			throw new Exception("no se puede convertir a String no es un String");
		}
		return (String) atributo; 
	}
	
	public String toString() {
		String res = "<"+tipo.name()+" , ";
		if(atributo!=null) {
			res= res+atributo;
		}
		res = res +">";
		return res;
		
	}
}
