package paquete;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Usuario extends Persona implements Comparable<Usuario>{
	private Calendar fechaAlta;
	private float saldo;

	public LinkedList<Grupo> getActividadesActuales() {
		return actividadesActuales;
	}

	public LinkedList<Integer> getActPrevias() {
		return actPrevias;
	}

	private LinkedList<Grupo> actividadesActuales;
	private LinkedList<Integer> actPrevias;


	public Usuario(String DNI, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaAlta, float saldo){
		super(DNI, nombre, apellidos, fechaNacimiento);
		this.fechaAlta = fechaAlta;
		this.saldo = saldo;
	}

	public String getFechaAlta() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(fechaAlta.getTime());
	}

	public void setFechaAlta(Calendar fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public void setActividadesActuales(LinkedList<Grupo> actividadesActuales){
		this.actividadesActuales = actividadesActuales;
	}

	public void setActPrevias(LinkedList<Integer> actividadesPrevias){
		this.actPrevias = actividadesPrevias;
	}

	public String ActividadesPreviasFichero(){
		String r ="";
		for(int i=0; i<actPrevias.size(); i++){
			r += actPrevias.get(i);
			if((i+1)<actPrevias.size())
				r += ",";
		}

		return r;
	}

	public String ActividadesActualesFichero(){
		String r = "";
		for(int i=0; i<actividadesActuales.size(); i++){
			r += actividadesActuales.get(i).getIdActividad();
			if(actividadesActuales.get(i).getIdGrupo()!=0)
				r += " "+actividadesActuales.get(i).getIdGrupo();
			if((i+1)<actividadesActuales.size())
				r += ";";

		}
		return r;
	}

	public void salidaFichero(BufferedWriter bf) throws IOException{
		bf.write(this.getDNI()+"\n" +
					"usuario" + "\n" +
					this.getNombre()+"\n"+
					this.getApellidos()+"\n"+
					this.getFechaNacimiento()+"\n"+
					this.getFechaAlta()+"\n"+
					ActividadesPreviasFichero()+"\n"+
					this.saldo+"\n"+
					ActividadesActualesFichero()+"\n");
		bf.flush();
	}

	public String toString(){
		String a="";
		for(int i=0; i<actPrevias.size(); i++){
			a += actPrevias.get(i);
		}
		String b="";
		for(int i =0; i<actividadesActuales.size(); i++){
			b += actividadesActuales.get(i).getIdActividad()+" "+actividadesActuales.get(i).getIdGrupo();
		}
		return super.toString()+
				"fechaAlta: "+fechaAlta+"\n" +
				"Saldo: "+saldo+"\n" +
				"Actividades Previas: "+a+"\n" +
				"Actividades Actuales: "+b;
	}

	public boolean isMatriculado(int idActividad){
		boolean comprobacion = false;
		for(int i=0; i<actividadesActuales.size(); i++){
			if(actividadesActuales.get(i).getIdActividad()==idActividad){
				comprobacion = true;
				break;
			}
		}
		return comprobacion;
	}

	public boolean isMatriculable(int idActividad){
		boolean comprobacion = true;
		int cont = 0;
		for(int i=0; i<Gestor.mapaActividades.get(idActividad).getRequisitos().size(); i++){
			for(int j=0; j<actPrevias.size(); j++){
				if(Gestor.mapaActividades.get(idActividad).getRequisitos().get(i)==actPrevias.get(j)){
					cont++;
					break;
				}
			}
		}
		if(cont!=Gestor.mapaActividades.get(idActividad).getRequisitos().size())
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
				horaInicio1 = Gestor.mapaActividades.get(idActividad).getGrupos().get(i).getHoraInicio().trim();
				dia = Gestor.mapaActividades.get(idActividad).getGrupos().get(i).getDia().trim();
				break;
			}
		}
		int horaInicio11 = Integer.parseInt(horaInicio1.split(":")[0]);
		for(int i=0; i<actividadesActuales.size(); i++){
			if(actividadesActuales.get(i).getIdGrupo()==0)
				continue;
			Actividad a = Gestor.mapaActividades.get(actividadesActuales.get(i).getIdActividad());
			LinkedList<Grupo> g = a.getGrupos();
			for(int j=0; j<g.size(); j++){
				if(g.get(j).getIdGrupo()==actividadesActuales.get(i).getIdGrupo()&&g.get(j).getDia().trim().equals(dia)){
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

	public void asignaGrupo(int idActividad, int idGrupo){
		for(int i=0; i<actividadesActuales.size(); i++){
			if(actividadesActuales.get(i).getIdActividad()==idActividad){
				actividadesActuales.get(i).setIdGrupo(idGrupo);
				break;
			}
		}
	}

	public boolean pagar(float precio){
		if((saldo-precio)>=0){
			saldo = saldo - precio;
			return true;
		}
		else{
			return false;
		}
	}

	public boolean hasAsignaciones(){
		boolean comprobacion = false;
		for(int i=0; i<actividadesActuales.size(); i++){
			if(actividadesActuales.get(i).getIdGrupo()!=0) {
				comprobacion = true;
				break;
			}
		}
		return comprobacion;
	}

	@Override
	public int compareTo(Usuario o) {
		if(getSaldo()<o.getSaldo())
			return 1;
		else
			return -1;
	}

}

class comparadorNombre implements Comparator<Usuario>{
	public int compare(Usuario u1, Usuario u2){
		return -(u1.getApellidos()+u1.getNombre()).compareTo((u2.getApellidos()+u2.getNombre()));
	}
}

