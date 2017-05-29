package paquete;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
					pagoActividades(comando);
					break;
				case "obtenercalendario":
					obtenerCalendario(comando);
					break;
				case "ordenarmonitoresporcarga":
					ordernarMonitores(comando);
					break;
				case "ordenaractividades":
					ordenarActividadez(comando);
					break;
				case "ordenausuariosxsaldo":
					ordenarUsuarios(comando);
					break;
				default:
					gestorErrores.ComandoIncorrecto();

			}
		}
		//Personas.imprimirFichero();


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

	public static void pagoActividades(String[] comando) throws IOException{
		if(!gestorErrores.pagoActividades(comando))
			return;
	}

	public static void obtenerCalendario(String[] comando) throws IOException{
		if(!gestorErrores.obtenerCalendario(comando))
			return;
		File fichero = new File(comando[3]);
		LinkedList<Grupo> grupos = ((Usuario)mapaPersonas.get(comando[2])).getActividadesActuales();
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, false));
		bw.write("dia; horas; instalacion; idGrupo; actividad; instructor\n");
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getIdGrupo()!=0){
				LinkedList<Grupo> grupos2 = mapaActividades.get(grupos.get(i).getIdActividad()).getGrupos();
				for(int j=0; j<grupos2.size(); j++){
					if(grupos2.get(j).getIdGrupo()==grupos.get(i).getIdGrupo()){
						bw.write(grupos2.get(j).getDia()+"; ");
						bw.write(grupos2.get(j).getHoraInicio()+"; ");
						bw.write(grupos2.get(j).getIdGrupo()+"; ");
						bw.write(mapaActividades.get(grupos2.get(j).getIdActividad()).getNombre()+"; ");
						//Corregir esto; esto es el coordinador no el monitor que la imparte
						if(mapaPersonas.get(mapaActividades.get(grupos2.get(j).getIdActividad()).getCoordinador())!=null) {
							bw.write(mapaPersonas.get(mapaActividades.get(grupos2.get(j).getIdActividad()).getCoordinador()).getNombre() + " ");
							bw.write(mapaPersonas.get(mapaActividades.get(grupos2.get(j).getIdActividad()).getCoordinador()).getApellidos() + "\n");
						}
						else{
							bw.write(" No hay instructor\n");
						}
					}
				}

			}
		}
		bw.flush();
	}

	public static void ordernarMonitores(String[] comando) throws IOException{
		if(!gestorErrores.ordenarMonitores())
			return;
		File f = new File(comando[2]);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
		Set<String> keys = mapaPersonas.keySet();
		LinkedList<Monitor> monitors = new LinkedList<Monitor>();
		for(String key : keys){
			if(mapaPersonas.get(key) instanceof Monitor)
				monitors.add((Monitor)mapaPersonas.get(key));
		}
		Collections.sort(monitors);
		for(int i=0; i<monitors.size(); i++){
			monitors.get(i).salidaFichero(bw);
			if((i+1)<monitors.size())
				bw.write("************************\n");
		}
		bw.flush();
	}

	public static void ordenarActividadez(String[] comando) throws IOException{
		if(!gestorErrores.ordernarActividades())
			return;
		File f = new File(comando[2]);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
		LinkedList<Actividad> a = new LinkedList<Actividad>(mapaActividades.values());
		Collections.sort(a);
		for(int i=0; i<a.size(); i++){
			a.get(i).salidaFichero(bw);
			if((i+1)<a.size())
				bw.write("*************************\n");
		}
		bw.flush();
	}

	public static void ordenarUsuarios(String[] comando) throws IOException{
		gestorErrores.ordenarUsuarios();
		LinkedList<Usuario> users = new LinkedList<Usuario>();
		Set<String> keys = mapaPersonas.keySet();
		for(String key : keys){
			if(mapaPersonas.get(key) instanceof Usuario)
				users.add((Usuario)mapaPersonas.get(key));
		}
		File f = new File(comando[2]);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
		Collections.sort(users);
		for(int i=0; i<users.size(); i++){
			users.get(i).salidaFichero(bw);
			if((i+1)<users.size())
				bw.write("*************************\n");
		}
		bw.flush();
	}
}
