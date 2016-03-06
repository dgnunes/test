package org.fiveware.test.model.entities.bean;

import org.fiveware.test.enumerations.SexoEnum;
import org.fiveware.test.enumerations.StatusCivilEnum;

public class PessoaBean {
	int id;
	String nome;
	StatusCivilEnum statuscivil;
	boolean deficiente;
	SexoEnum sexo;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public StatusCivilEnum getStatuscivil() {
		return statuscivil;
	}
	public void setStatuscivil(StatusCivilEnum statuscivil) {
		this.statuscivil = statuscivil;
	}
	public boolean isDeficiente() {
		return deficiente;
	}
	public void setDeficiente(boolean deficiente) {
		this.deficiente = deficiente;
	}
	public SexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}
	
}
