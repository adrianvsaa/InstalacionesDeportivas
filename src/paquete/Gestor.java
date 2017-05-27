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

		while(entrada.hasNextLine()){
			String[] comando = entrada.nextLine().trim().split("\\s+");

			switch (comando[1].toLowerCase()){
				case "insertapersona":
					insertaPersona(comando);
					break;
				case "asignarmonitorgrupo":
					break;
				case "alta":
					break;
				case "asignagrupo":
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
					if(comando[1].charAt(0)=='*')
						break;
					gestorErrores.ComandoIncorrecto();

			}
		}

		Personas.imprimirPersonas();
		Actividades.imprimirActividades();
	}

	public static void insertaPersona(String[] comando){
		if(!gestorErrores.insertaPersona(comando))
			return;

	}
}
