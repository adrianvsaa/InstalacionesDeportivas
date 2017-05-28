package paquete;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class Monitor extends Persona {
	private int horasAsignables;
	private LinkedList<Grupo> gruposImpartidos;

	public Monitor(String DNI, String nombre, String apellidos, Calendar fechaNacimiento, int horasAsignables){
		super(DNI, nombre, apellidos, fechaNacimiento);
		this.horasAsignables = horasAsignables;
		gruposImpartidos = new LinkedList<Grupo>();
	}
	
	public void setGruposImpartidos(LinkedList<Grupo> gruposImpartidos){
		this.gruposImpartidos = gruposImpartidos;
	}

	public String toString(){
		return super.toString()+
				"horas asig: "+horasAsignables;
	}

	public String gruposImpartidosFichero(){
		String r = "";
		for(int i =0; i<gruposImpartidos.size(); i++){
			r = gruposImpartidos.get(i).getIdActividad()+" "+gruposImpartidos.get(i).getIdGrupo();

			if(i+1>gruposImpartidos.size())
				r += ";";
		}
		return r;
	}

	public void salidaFichero(BufferedWriter bf) throws IOException{
		bf.write(this.getDNI()+"\n"+
					"monitor" +"\n" +
					this.getNombre()+"\n"+
					this.getApellidos()+"\n" +
					this.getFechaNacimiento()+"\n"+
					this.horasAsignables + "\n" +
					gruposImpartidosFichero() +"\n");
		bf.flush();
	}

	public boolean isGrupoAsignado(int idActividad, int idGrupo){
		boolean comprobacion = false;
		for(int i=0; i<gruposImpartidos.size(); i++){
			if(gruposImpartidos.get(i).getIdActividad()==idActividad&&gruposImpartidos.get(i).getIdGrupo()==idGrupo)
				comprobacion = true;
		}
		return comprobacion;
	}

	public boolean isAsignable(int idActividad, int idGrupo){
		boolean comprobacion = true;
		int horas = 0;
		for(int i=0; i<gruposImpartidos.size(); i++){
			horas += Gestor.mapaActividades.get(gruposImpartidos.get(i).getIdActividad()).getDuracion();
		}
		horas += Gestor.mapaActividades.get(idActividad).getDuracion();
		if(horas>this.horasAsignables)
			comprobacion = false;
		return comprobacion;
	}

	public boolean generaSolape(int idActividad, int idGrupo){
		boolean comprobacion = false;
		String horaInicio1 = "";
		String dia = "";
		int duracion1 = Gestor.mapaActividades.get(idActividad).getDuracion();
		for(int i=0; i<Gestor.mapaActividades.get(idActividad).getGrupos().size(); i++){
			if(Gestor.mapaActividades.get(idActividad).getGrupos().get(i).getIdGrupo()==idGrupo){
				horaInicio1 = Gestor.mapaActividades.get(idActividad).getGrupos().get(i).getHoraInicio();
				dia = Gestor.mapaActividades.get(idActividad).getGrupos().get(i).getDia();
				break;
			}
		}
		int horaInicio11 = Integer.parseInt(horaInicio1.split(":")[0]);
		for(int i=0; i<gruposImpartidos.size(); i++){
			Actividad a = Gestor.mapaActividades.get(gruposImpartidos.get(i).getIdActividad());
			LinkedList<Grupo> g = a.getGrupos();
			for(int j=0; j<g.size(); j++){
				if(g.get(j).getIdGrupo()==idGrupo&&g.get(j).getDia().equals(dia)){
					String horaInicio = g.get(j).getHoraInicio();
					int duracion = a.getDuracion();
					int horaInicioI = Integer.parseInt(horaInicio.split(":")[0]);
					if(horaInicio11==horaInicioI||Math.abs(horaInicio11-horaInicioI)<duracion1||Math.abs(horaInicioI-horaInicio11)<duracion){
						comprobacion = true;
						break;
					}
				}
			}
		}

		return comprobacion;
	}

	public void addGrupo(int idActividad, int idGrupo){
		gruposImpartidos.add(new Grupo(idActividad, idGrupo));
	}
}
