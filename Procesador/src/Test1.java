import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tslib.TS_Gestor;
import tslib.TS_Gestor.DescripcionAtributo;
import tslib.TS_Gestor.Tabla;
import tslib.TS_Gestor.TipoDatoAtributo;

public class Test1 {

	public static void main(String[] args) throws IOException {
		TS_Gestor gestor=new TS_Gestor("tabla simbolos");
		if(gestor.createTPalabrasReservadas()==1) {
			System.out.print("Error creando TPR");
			System.exit(1);
		}
		//TODO Habria q ver q no hay errores pero por simplicidad lo hacemos despues
		String[] palabrasReservadas = {
			    "boolean", "break", "case", "echo", "float", 
			    "function", "if", "int", "let", "prompt", 
			    "return", "string", "switch", "void","true","false"
			};
		
		TipoToken[] tokensPR = {
		        TipoToken.BOOL, TipoToken.BREAK, TipoToken.CASE, TipoToken.ECHO, TipoToken.FLOAT,
		        TipoToken.FUNCT, TipoToken.IF, TipoToken.INT, TipoToken.LET, TipoToken.PROMPT,
		        TipoToken.RETURN, TipoToken.STRING, TipoToken.SWITCH, TipoToken.VOID, TipoToken.TRUE, TipoToken.FALSE
		    };
		
		
		Map<Integer,TipoToken> traducionesPRaTT = new HashMap<>();
		
		for(int i=0;i<palabrasReservadas.length;i++) {
			int id = gestor.addEntradaTPalabrasReservadas(palabrasReservadas[i]);
			if(id==0) {
				System.out.print("Error creando PR: "+palabrasReservadas[i]);
				System.exit(1);
			}
			traducionesPRaTT.put(id, tokensPR[i]);
			
		}
		
	
		gestor.createAtributo("lexema", DescripcionAtributo.ETIQUETA,TipoDatoAtributo.CADENA);
		
		
		if(gestor.createTSGlobal()==1) {
			System.out.print("Error creando TSG");
			System.exit(1);
		}
		
		GestorErrores errores = new GestorErrores();
		ALex analizadorLexico = new ALex(gestor,traducionesPRaTT,errores);
		
		TipoToken last=TipoToken.ASIG;
		
		while(last!=TipoToken.EOF) {
			last = (TipoToken) analizadorLexico.nextToken();
			
		}
		gestor.show(Tabla.GLOBAL);
		
	}

}
