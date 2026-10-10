
public class matTransALex {
	
	private static int[][][] matrizTrans=
		{//{estado nuevo,accionsemantica}
			//	 letra_ 0   digtos 1   del 2      CR 3      . 4      / 5         ' 6       \ 7       & 8      = 9      , 10        ; 11      : 12      ( 13      ) 14      { 15      } 16      + 17      ! 18      < 19      > 20     eof 21    o.c 22     
		/*S 0 */{{ 1 , 1 },{ 2 , 3 },{ 0 , 0 },{ 0 , 0 },{ 0 ,104},{ 5 , 0 },{ 7 , 0 },{ 0 ,107},{ 9 , 0 },{-1 , 11},{-1 , 12},{-1 , 13},{-1 , 14},{-1 , 15},{-1 , 16},{-1 , 17},{-1 , 18},{-1 , 19},{-1 , 20},{-1 , 21},{-1 , 22},{-1 , 23},{ 0 ,122}},
		/*A 1 */{{ 1 , 1 },{ 1 , 1 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 },{-1 , 2 }},
		/*B 2 */{{-1 , 4 },{ 2 , 3 },{-1 , 4 },{-1 , 4 },{ 3 , 5 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 },{-1 , 4 }},
		/*C 3 */{{ 0 ,300},{ 4 , 6 },{ 0 ,302},{ 0 ,303},{ 0 ,304},{ 0 ,305},{ 0 ,306},{ 0 ,307},{ 0 ,308},{ 0 ,309},{ 0 ,310},{ 0 ,311},{ 0 ,312},{ 0 ,313},{ 0 ,314},{ 0 ,315},{ 0 ,316},{ 0 ,317},{ 0 ,318},{ 0 ,319},{ 0 ,320},{ 0 ,321},{ 0 ,322}},
		/*D 4 */{{-1 , 7 },{ 4 , 6 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 },{-1 , 7 }},
		/*E 5 */{{ 0 ,500},{ 0 ,501},{ 0 ,502},{ 0 ,503},{ 0 ,504},{ 6 , 0 },{ 0 ,506},{ 0 ,507},{ 0 ,508},{ 0 ,509},{ 0 ,510},{ 0 ,511},{ 0 ,512},{ 0 ,513},{ 0 ,514},{ 0 ,515},{ 0 ,516},{ 0 ,517},{ 0 ,518},{ 0 ,519},{ 0 ,520},{ 0 ,521},{ 0 ,522}},
		/*F 6 */{{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 0 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 },{ 6 , 0 }},
		/*G 7 */{{ 7 , 8 },{ 7 , 0 },{ 7 , 0 },{ 0 ,703},{ 7 , 0 },{ 7 , 0 },{-1 , 9 },{ 8 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 7 , 0 },{ 0 ,721},{ 7 , 0 }},
		/*L 8 */{{ 0 ,800},{ 0 ,801},{ 0 ,802},{ 0 ,803},{ 0 ,804},{ 0 ,805},{ 7 , 8 },{ 7 , 8 },{ 0 ,808},{ 0 ,809},{ 0 ,810},{ 0 ,811},{ 0 ,812},{ 0 ,813},{ 0 ,814},{ 0 ,815},{ 0 ,816},{ 0 ,817},{ 0 ,818},{ 0 ,819},{ 0 ,820},{ 0 ,821},{ 0 ,822}},
		/*H 9 */{{ 0 ,900},{ 0 ,901},{ 0 ,902},{ 0 ,903},{ 0 ,904},{ 0 ,905},{ 0 ,906},{ 0 ,907},{ 0 ,908},{-1 , 10},{ 0 ,910},{ 0 ,911},{ 0 ,912},{ 0 ,913},{ 0 ,914},{ 0 ,915},{ 0 ,916},{ 0 ,917},{ 0 ,918},{ 0 ,919},{ 0 ,920},{ 0 ,921},{ 0 ,922}},
		/*-1 es el esatdo final nunca se llega*/
		//si hay un error vamos a ir a 0 por ahora
		//Sintaxis de errores primer digito esatdo en el q estabamos 
		//segundo y tercer digito la columna q hemos recibido
		//los errores en S se llaman diferente por q sino colisionan con las acc semanticas se les suma 100 simplemente ya que A no tiene codigos de error,
		//ej estado C y recibimos letra C es el 3 y la letra es la columna 0 entonces cod error 300
		//error 700 cadena muy grande 200 int muy grande  400 float muy grande
		};


	public static int[] estadoYAccion(int estado, int caracter) {
		int col;
		if(caracter==-1) {
			col = 21;
		}else {
			col = getColumna((char)caracter);
		}
		
		
		
		return matrizTrans[estado][col] ;
	}
	
	
	private static int getColumna(char caracter) {//el eof ya esta comprobado antes
		
		if(Character.isLetter(caracter)|| caracter == '_') {
			return 0;
			
		}else if(Character.isDigit(caracter)) {
			return 1;
			
		}else if(caracter != '\n' && Character.isWhitespace(caracter)) {
			return 2;
		
		}else if(caracter == '\n') {
			return 3;
			
		}else if(caracter=='.') {
			return 4;
			
		}else if(caracter == '/') {
			return 5;
					
		}else if(caracter == '\'') {
			return 6;
			
		}else if(caracter == '\\') {
			return 7;
			
		}else if(caracter == '&') {
			return 8;
			
		}else if(caracter == '=') {
			return 9;
			
		}else if (caracter == ',') {
			return 10;
			
		} else if (caracter == ';') {
			return 11;
			
		} else if (caracter == ':') {
			return 12;
			
		} else if (caracter == '(') {
			return 13;
			
		} else if (caracter == ')') {
			return 14;
			
		} else if (caracter == '{') {
			return 15;
			
		} else if (caracter == '}') {
			return 16;
			
		} else if (caracter == '+') {
			return 17;
			
		} else if (caracter == '!') {
			return 18;
			
		} else if (caracter == '<') {
			return 19;
			
		} else if (caracter == '>') {
			return 20;
			//21 para el eof
		} else { //o.c
			//se usa en los comentarios
			return 22; 
		}
		
	}

}
