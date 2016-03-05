package org.fiveware.test.model.entities;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fiveware.test.enumerations.SexoEnum;
import org.fiveware.test.enumerations.StatusCivilEnum;
import org.hibernate.validator.constraints.NotEmpty;
 
@Entity
@Table(name="PESSOA")
public class Pessoa {
 
    @Id
    @Column(name="PESSOA_ID", unique=true, nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
 
    @NotEmpty
    @Column(name="PESSOA_NOME", unique=true, nullable=false)
    private String nome;
     
    @Column(name="PESSOA_STATUS_CIVIL", nullable=false)
    private StatusCivilEnum statuscivil;
         
    @Column(name="PESSOA_DEFICIENTE", nullable=false)
    private boolean deficiente;
 
    @Column(name="PESSOA_SEXO", nullable=false)
    private SexoEnum sexo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deficiente ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((statuscivil == null) ? 0 : statuscivil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (deficiente != other.deficiente)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sexo != other.sexo)
			return false;
		if (statuscivil != other.statuscivil)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", statuscivil=" + statuscivil + ", deficiente=" + deficiente
				+ ", sexo=" + sexo + "]";
	}

}