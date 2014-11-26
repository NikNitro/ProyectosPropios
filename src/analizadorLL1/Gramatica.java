package analizadorLL1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


//La gramática debe pasarse por texto de la forma S-A$ (sin espacios y a una por línea.
//Aún no es seguro que no queden fallos.
public class Gramatica {
	private File archivo;
	private Map<Character, List<String>> diccionario;
	
	public Gramatica(String s) {
		archivo = new File(s);
		diccionario = new HashMap<Character, List<String>>();
		try {
			Scanner sc = new Scanner(archivo);
			
			while(sc.hasNextLine()) {
				String std = sc.nextLine();
				Scanner linea = new Scanner(std);
				linea.useDelimiter("-");
				char key =linea.next().charAt(0);
				String value = linea.next();
				if(!diccionario.containsKey(key))
					diccionario.put(key, new ArrayList<String>());
				diccionario.get(key).add(value);
				
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error al abrir el fichero");
			e.printStackTrace();
		}
		System.out.println(diccionario.toString());
	}
	
	//Usaremos '1' como epsilon
	public Set<Character> Cabecera(String std, Set<String> used) {
		Set<Character> res = new TreeSet<Character>();
		
		char car = std.charAt(0);
		if(car==Character.toLowerCase(car) || car=='$') {
			res.add(car);
			return res;
		}
		else if(car=='1') {
			res.add(car);
			return res;			
		}
		else {
			if(diccionario.containsKey(car)) {
				for(String str : diccionario.get(car)) {
					if(str!=std) {
						Set<Character> s = Cabecera(str, used);
						if(s.contains('1')) {
							String aux = std.substring(1);
							if(!aux.isEmpty()){
								s.addAll(Cabecera(aux, used));
								s.remove('1');		
							}
						}
						res.addAll(s);
					}
				}
			}
		}
		used.add(std);
		
		return res;
	}

	public Set<Character> Siguiente() {
		return null;
	}
	
	public static void main(String[] unused) {
		Gramatica g = new Gramatica("aa.txt");
		Set<Character> s =g.Cabecera("A", new HashSet<>());
		for(char c : s)
			System.out.println(c);
	}
}
