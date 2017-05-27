package paquete;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Usuario extends Persona {
	private Calendar fechaAlta;
	private float saldo;
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
}
