package paquete;

import java.util.*;

public class Usuario extends Persona {
	private Calendar fechaAlta;
	private float saldo;
	private LinkedList<Grupo> actividadesActuales;
	
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
	
	public void setActividadesActuales(LinkedList<Grupo> actividadesActuales){
		this.actividadesActuales = actividadesActuales;
	}
}
