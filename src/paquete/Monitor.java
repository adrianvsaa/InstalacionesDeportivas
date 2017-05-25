package paquete;

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
	
}
