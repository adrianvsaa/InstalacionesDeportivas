package paquete;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class Actividad implements Comparable<Actividad>{
	private int identificador;
	private String nombre;
	private String siglas;
	private int coordinador;
	private int duracion;
	private float coste;
	private LinkedList<Grupo> grupos;
	private LinkedList<Integer> requisitos;

	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	public int getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(int coordinador) {
		this.coordinador = coordinador;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public float getCoste() {
		return coste;
	}

	public void setCoste(float coste) {
		this.coste = coste;
	}

	public LinkedList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(LinkedList<Grupo> grupos) {
		this.grupos = grupos;
	}

	public LinkedList<Integer> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(LinkedList<Integer> requisitos) {
		this.requisitos = requisitos;
	}


	public Actividad(int identificador, String nombre, String siglas, int coordinador, int duracion, float coste){
		this.identificador = identificador;
		this.nombre = nombre;
		this.siglas = siglas;
		this.coordinador = coordinador;
		this.duracion = duracion;
		this.coste = coste;
	}

	public String requistosFichero(){
		String r = "";
		for(int i =0; i<requisitos.size(); i++){
			r += requisitos.get(i);
			if((i+1)<requisitos.size())
				r+= ", ";
		}
		return  r;
	}

	public String gruposFichero(){
		String r = "";
		for(int i=0; i<grupos.size(); i++){
			r+= grupos.get(i).getIdGrupo()+" "+grupos.get(i).getDia()+" "+grupos.get(i).getHoraInicio()+" "+grupos.get(i).getInstalacion()+";";
		}
		return r;
	}

	public void salidaFichero(BufferedWriter bw) throws IOException{
		if(coordinador==0)
			bw.write(this.identificador+"\n" +
				this.nombre+"\n" +
				this.siglas+"\n" +
				"\n" +
				requistosFichero()+"\n"+
				this.duracion+"\n"+
				this.coste+"\n"+
				gruposFichero()+"\n");
		else
			bw.write(this.identificador+"\n" +
					this.nombre+"\n" +
					this.siglas+"\n" +
					this.coordinador + "\n" +
					requistosFichero()+"\n"+
					this.duracion+"\n"+
					this.coste+"\n"+
					gruposFichero()+"\n");
		bw.flush();
	}

	public boolean existsGrupo(int idGrupo){
		boolean comproacion = false;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getIdGrupo()==idGrupo){
				comproacion = true;
				break;
			}
		}
		return comproacion;
	}

	public boolean isGrupoAsignado(int idGrupo){
		boolean comprobacion = false;
		Set<String> keys = Gestor.mapaPersonas.keySet();
		for(String key : keys){
			if(Gestor.mapaPersonas.get(key) instanceof Monitor){
				if(((Monitor)Gestor.mapaPersonas.get(key)).isGrupoAsignado(this.identificador, idGrupo)){
					comprobacion = true;
				}
			}
		}
		return comprobacion;
	}

	public boolean hasGrupo(int idGrupo){
		boolean comprobacion = false;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getIdGrupo()==idGrupo){
				comprobacion = true;
				break;
			}
		}
		return comprobacion;
	}

	public int calcularUsuarios(){
		int x =0;
		Set<String> keys = Gestor.mapaPersonas.keySet();
		for(String key : keys){
			if(Gestor.mapaPersonas.get(key) instanceof Usuario) {
				LinkedList<Grupo> actividades = ((Usuario) Gestor.mapaPersonas.get(key)).getActividadesActuales();
				for(int i=0; i<actividades.size(); i++){
					if(actividades.get(i).getIdActividad() == this.identificador)
						x++;
				}
			}
		}

		return x;
	}

	@Override
	public int compareTo(Actividad o) {
		if(calcularUsuarios()<o.calcularUsuarios())
			return 1;
		else
			return -1;
	}
}
