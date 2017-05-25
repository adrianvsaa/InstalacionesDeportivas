package paquete;

import java.util.*;
import java.io.*;

public class Personas {
	final static File fichero = new File("ficheros/personas.txt");
	public static void poblar(){
		try{
			Scanner fPersonas = new Scanner(fichero);
			while(fPersonas.hasNextLine()){
				String id = fPersonas.nextLine().trim();
				String tipoUsuario = fPersonas.nextLine().trim();
				String nombre = fPersonas.nextLine().trim();
				String apellidos = fPersonas.nextLine().trim();
				String fNacimiento = fPersonas.nextLine().trim();
				Calendar fNac = Calendar.getInstance();
				fNac.set(Integer.parseInt(fNacimiento.split("/")[2]), 	//Año 
						Integer.parseInt(fNacimiento.split("/")[1])-1, 	//Mes
						Integer.parseInt(fNacimiento.split("/")[0]));	//Dia
				if(tipoUsuario.equals("monitor")){
					String horasAsig = fPersonas.nextLine().trim();
					String gruImpartidos = fPersonas.nextLine().trim();
					String[] aux = gruImpartidos.split(";");
					LinkedList<Grupo> gruposImpartidos = new LinkedList<Grupo>();
					for(int i=0; i<aux.length; i++){
						gruposImpartidos.add(new Grupo(Integer.parseInt(aux[i].split("\\s++")[0], 
													Integer.parseInt(aux[i].split("\\s++")[1]))));
					}
					Persona p = new Monitor(id, nombre, apellidos, fNac, Integer.parseInt(horasAsig));
					((Monitor)p).setGruposImpartidos(gruposImpartidos);
					Gestor.mapaPersonas.put(id, p);
				} else {
					
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
