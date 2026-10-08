import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ALex {
	private int caracter;//guardamos el caracter con su codigo ascii para poder leer correctamente si es un eof 
	private BufferedReader lector;
	
	
	private String ruta = "tokens.txt";
	
	public static final double FLOAT_MAX=117549436.0;
	public static final int INT_MAX=32767;
	public static final int CADENA_MAX=64;
	
	public ALex() throws IOException{
		this.lector = new BufferedReader(new InputStreamReader(System.in));
		this.caracter = this.lector.read(); 
	}
	
	public Object nextToken() throws IOException{
		//colocarnos en el estado inicial
		int estado = 0;
		
		//inicializar las variables
		String lex="";
		int cont=0;
		int num=0;
		int exp=0;
		
		while(true) {
			
			int[] nuevo = matTransALex.estadoYAccion(estado,caracter);
			estado= nuevo[0];
			
			switch (nuevo[1]) {
			
				//leer basico sirve para delimitadores o comentarios
				case 0: caracter = this.lector.read();
						break;
					
				//acsemanticas ID y palabras reservadas
				case 1: lex=lex+(char)caracter;
						caracter = this.lector.read();
						break;
						
				case 2: TipoToken id=TPR.buscar(lex);
						if(id!=null) {return genToken(id);}
						Integer pos = TS.buscar(lex);
						if(pos==null) {pos = TS.insertar(lex);}
						return genToken(TipoToken.ID,pos);
					
				//acsemanticas numeros int	
				
				case 3: num=num*10+Integer.parseInt(""+(char)caracter);
						caracter = this.lector.read();
						break;
				
				case 4: if(num>INT_MAX) {throw new Exception();}
						return genToken(TipoToken.INT, num);
				
				//acsemanticas numeros float
						
				case 5: exp=0;
						caracter = this.lector.read();
						break;
						
				case 6: num=num*10+Integer.parseInt(""+(char)caracter);
						exp++;
						caracter = this.lector.read();
						break;
						
				case 7:	double valor = Math.pow(num, exp);
						if(valor>FLOAT_MAX) {throw new Exception();}
						return genToken(TipoToken.FLOAT, valor);
				
				//acSemanticas Cadenas
				
				case 8: lex = lex + (char)caracter ;
						cont++;
						caracter = this.lector.read();
						break;
						
				case 9: caracter = this.lector.read();
						if(cont>64) {throw new Exception();}
						return genToken(TipoToken.CADENA, lex);
						
				//acSemanticas q son solo Gentoken
				
				case 10: caracter = this.lector.read();
						return genToken(TipoToken.ASIGY);
				
				case 11: caracter = this.lector.read();
						return genToken(TipoToken.ASIG);
				
				case 12: caracter = this.lector.read();
						return genToken(TipoToken.COMA);
				
				case 13: caracter = this.lector.read();
						return genToken(TipoToken.PCOMA);
				
				case 14: caracter = this.lector.read();
						return genToken(TipoToken.DOSP);
				
				case 15: caracter = this.lector.read();
						return genToken(TipoToken.PARIZQ);
				
				case 16: caracter = this.lector.read();
						return genToken(TipoToken.PARDER);
				
				case 17: caracter = this.lector.read();
						return genToken(TipoToken.LLAVEIZQ);
				
				case 18: caracter = this.lector.read();
						return genToken(TipoToken.LLAVEDER);
				
				case 19: caracter = this.lector.read();
						return genToken(TipoToken.SUMA);
				
				case 20: caracter = this.lector.read();
						return genToken(TipoToken.NEG);
				
				case 21: caracter = this.lector.read();
						return genToken(TipoToken.MENOR);
				
				case 22: caracter = this.lector.read();
						return genToken(TipoToken.MAYOR);
				
				case 23: caracter = this.lector.read();
						return genToken(TipoToken.EOF);
				
				default: //si se ha llegado aqui es que ha habido un error y por lo tanto habra q pasarle al gestor de errores q error es 
			}
			
			
			
		}
		
	}
	

	private Object genToken(TipoToken id, double valor) throws IOException {
		try(PrintWriter escribrir = new PrintWriter(new FileWriter(ruta))){
			escribrir.println("<"+id.name()+","+valor+">");
		}
		//crear token
		return null;
	}

	private Object genToken(TipoToken id, String lex) throws IOException {
		try(PrintWriter escribrir = new PrintWriter(new FileWriter(ruta))){
			escribrir.println("<"+id.name()+","+lex+">");
		}
		//crear token
		return null;
	}

	private Object genToken(TipoToken id) throws IOException {
		try(PrintWriter escribrir = new PrintWriter(new FileWriter(ruta))){
			escribrir.println("<"+id.name()+", >");
		}
		//crear token
		return null;
	}
	
	
	

}
