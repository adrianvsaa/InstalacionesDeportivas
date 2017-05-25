package paquete;

public class Grupo {
	private int idActividad = 0;
	private int idGrupo = 0;
	
	public Grupo(int idActividad, int idGrupo){
		this.idActividad = idActividad;
		this.idGrupo = idGrupo;
	}
	
	public Grupo(int idActividad){
		this.idActividad = 0;
	}
	
	public boolean isAsignado(){
		if(idGrupo == 0)
			return false;
		else
			return true;
	}
}
