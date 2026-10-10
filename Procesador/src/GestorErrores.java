
public class GestorErrores {
	int lineaActual;

	public GestorErrores() {
		lineaActual=1;
	}


	public void lineaNueva() {
		lineaActual++;
	}

	public void ERROR(int lex, int codERROR) {
		if(codERROR==200) {
			System.out.println("En Linea: "+lineaActual+"; El la constante int : "+lex+" es demasiado grande tamaño maximo: "+ALex.INT_MAX+" codigo de error:"+codERROR);
		}
		else if(codERROR/100==3) {
			System.out.println("Linea: "+lineaActual+"; numero actual: "+lex+" codigo de error:"+codERROR+" esperando digito");
		}else {
			String caracterMalo;
			if(lex==-1) {
				caracterMalo="eof";
			}else {
				caracterMalo=""+(char)lex;
			}
			
			System.out.println("Linea: "+lineaActual+"; caracter recibido: "+caracterMalo+" codigo de error:"+codERROR);
			if(codERROR/100==9) {
				System.out.print("esperando = y recibio "+caracterMalo );
			}else if(codERROR/100==8) {
				System.out.print("esperando \\ o ' y recibio "+caracterMalo );
			}else if(codERROR/100==5) {
				System.out.print("esperando / y recibio "+caracterMalo );
			}
			
		}

	}
	
	
	public void ERROR(double num, int codERROR) {

		System.out.println("En Linea: "+lineaActual+"; El la constante float : "+num+" es demasiado grande tamaño maximo: "+ALex.FLOAT_MAX+" codigo de error:"+codERROR);
	}


	public void ERROR(String lex, int codERROR) {
		if(codERROR==700) {
			System.out.println("En Linea: "+lineaActual+"; la cadena : "+lex+" es demasiado grande tamaño maximo: "+ALex.CADENA_MAX+" codigo de error:"+codERROR);
		}
		System.out.println("Linea: "+lineaActual+"; Lexema actual: "+lex+" codigo de error:"+codERROR);
	}

}
