package paquete;

import java.io.IOException;
import java.util.*;
import java.io.File;

public class Gestor {
	static LinkedHashMap<String, Persona> mapaPersonas;
	static LinkedHashMap<Integer, Actividad> mapaActividades;
	private final static File fichero = new File("ficheros/ejecucion.txt");
	private static GestorErrores gestorErrores = new GestorErrores();
	public static void main(String[] args) throws IOException {
		mapaPersonas = new LinkedHashMap<String, Persona>();
		mapaActividades = new LinkedHashMap<Integer, Actividad>();
		Personas.poblar();
		Actividades.poblar();

		Scanner entrada = new Scanner(fichero);
		System.out.println(mapaPersonas.size());
		while(entrada.hasNextLine()){
			String[] comando = entrada.nextLine().trim().split("\\s+");
			if(comando[0].charAt(0)=='*')
				continue;
			switch (comando[1].toLowerCase()){
				case "insertapersona":
					insertaPersona(comando);
					break;
				case "asignarmonitorgrupo":
					asignaMonitorGrupo(comando);
					break;
				case "alta":
					altaUsuario(comando);
					break;
				case "asignagrupo":
					asignaGrupo(comando);
					break;
				case "cobrar":
					break;
				case "obtenercalendario":
					break;
				case "ordenarmonitoresporcarga":
					break;
				case "ordenaractividades":
					break;
				case "ordenausuariosxsaldo":
					break;
				default:
					gestorErrores.ComandoIncorrecto();

			}
		}
		Personas.imprimirFichero();


		//Personas.imprimirPersonas();
		//Actividades.imprimirActividades();
	}

	public static void insertaPersona(String[] comando) throws IOException{
		if(!gestorErrores.insertaPersona(comando))
			return;
		String com = "";
		for(int i=0; i<comando.length; i++)
			com += comando[i] + " ";
		String[] a1 =com.split("\"")[0].trim().split("\\s+");
		String[] a2 = com.split("\"");
		String[] a3 = com.split("\"")[4].trim().split("\\s+");
		int tMapa = mapaPersonas.size();
		if(a1[2].equals("monitor")){
			Calendar fechaNac = Calendar.getInstance();
			fechaNac.set(Integer.parseInt(a3[0].split("/")[2]),
					Integer.parseInt(a3[0].split("/")[1]),
					Integer.parseInt(a3[0].split("/")[0]));
			mapaPersonas.put(Integer.toString(mapaPersonas.size()+1), new Monitor(Integer.toString(mapaPersonas.size()+1), a2[1], a2[3], fechaNac, Integer.parseInt(a3[1])));
		}
		else{
			Calendar fechaNac = Calendar.getInstance();
			fechaNac.set(Integer.parseInt(a3[0].split("/")[2]),
					Integer.parseInt(a3[0].split("/")[1]),
					Integer.parseInt(a3[0].split("/")[0]));
			Calendar fechaAlta = Calendar.getInstance();
			fechaAlta.set(Integer.parseInt(a3[1].split("/")[2]),
					Integer.parseInt(a3[1].split("/")[1]),
					Integer.parseInt(a3[1].split("/")[0]));
			mapaPersonas.put(Integer.toString(mapaPersonas.size()+1),
						new Usuario(Integer.toString(mapaPersonas.size()+1), a2[1], a2[3], fechaNac, fechaAlta, Float.parseFloat(a3[2])));
		}
	}

	public static void asignaMonitorGrupo(String[] comando) throws IOException{
		if(!gestorErrores.asignaMonitorGrupo(comando))
			return;
		((Monitor)mapaPersonas.get(comando[2])).addGrupo(Integer.parseInt(comando[3]), Integer.parseInt(comando[4]));
	}

	public static void altaUsuario(String[] comando) throws IOException{
		if(!gestorErrores.altaUsuario(comando))
			return;
		LinkedList mapa = ((Usuario)mapaPersonas.get(comando[2])).getActividadesActuales();
		mapa.add(new Grupo(Integer.parseInt(comando[3])));
	}

	public static void asignaGrupo(String[] comando) throws IOException{
		if(!gestorErrores.asignaGrupo(comando))
			return;

	}
}
