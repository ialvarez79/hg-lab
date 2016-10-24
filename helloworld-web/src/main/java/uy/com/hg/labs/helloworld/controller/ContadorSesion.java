package uy.com.hg.labs.helloworld.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ContadorSesion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2224331550150251770L;
	
	private Integer cantidadCreados = 0;
	
	private String ultimoUsuarioRegistrado = null;
	
	public Integer getCantidadCreados(){
		return cantidadCreados;
	}
	
	public void setCantidadCreados(Integer valor){
		this.cantidadCreados = valor;
	}

	public String getUltimoUsuarioRegistrado() {
		return ultimoUsuarioRegistrado;
	}

	public void setUltimoUsuarioRegistrado(String ultimoUsuarioRegistrado) {
		this.ultimoUsuarioRegistrado = ultimoUsuarioRegistrado;
	}
}
