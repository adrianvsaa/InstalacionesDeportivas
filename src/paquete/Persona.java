package paquete;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class Persona {
	private String DNI;
	private String nombre;
	private String apellidos;
	private Calendar fechaNacimiento;
	
	
	public Persona(String DNI, String nombre, String apellidos, Calendar fechaNacimiento){
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getDNI() {
		return DNI;
	}


	public void setDNI(String dNI) {
		DNI = dNI;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getFechaNacimiento() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(fechaNacimiento.getTime());
	}


	public void setFechaNacimiento(Calendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String toString(){
		return "Identificador: "+DNI+"\n" +
				"Tipo: " + this.getClass() +
				"Nombre: "+nombre+"\n" +
				"Apellidos: "+apellidos+"\n"+
				"fechaNacimiento: "+getFechaNacimiento()+"\n";
	}

	public abstract void salidaFichero(BufferedWriter bf) throws IOException;

	
}
