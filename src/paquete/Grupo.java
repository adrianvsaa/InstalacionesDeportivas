package paquete;

public class Grupo {
	private int idActividad = 0;

	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	private int idGrupo = 0;

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}


	private String dia;

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	private String horaInicio;

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	private String instalacion;

	public String getInstalacion() {
		return instalacion;
	}

	public void setInstalacion(String instalacion) {
		this.instalacion = instalacion;
	}

	public Grupo(int idActividad, int idGrupo){
		this.idActividad = idActividad;
		this.idGrupo = idGrupo;
	}

	public Grupo(int idActividad){
		this.idActividad = idActividad;
	}

	public Grupo(int idActividad, int idGrupo, String dia, String horaInicio, String instalacion){
		this.idActividad = idActividad;
		this.idGrupo = idGrupo;
		this.horaInicio = horaInicio;
		this.dia = dia;
		this.instalacion = instalacion;
	}


	public boolean isAsignado(){
		if(idGrupo == 0)
			return false;
		else
			return true;
	}
}
