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

}
