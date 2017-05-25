package paquete;

import java.util.Calendar;


public class Usuario extends Persona {
	private Calendar fechaAlta;
	private float saldo;
	
	public Usuario(String DNI, String nombre, String apellidos, Calendar fechaNacimiento, Calendar fechaAlta, float saldo){
		super(DNI, nombre, apellidos, fechaNacimiento);
		this.fechaAlta = fechaAlta;
		this.saldo = saldo;
	}

	public Calendar getFechaAlta() {
		return fechaAlta;
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
}
