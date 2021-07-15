package com.uydevs.backoffice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "pais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pais extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "codigo", nullable = false)
	private String codigo;

	@NotNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@OneToMany(mappedBy = "pais")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Persona> personas = new HashSet<>();

	@OneToMany(mappedBy = "pais")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Funcion> funciones = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getCodigo() {
		return codigo;
	}

	public Pais codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public Pais nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Persona> getPersonas() {
		return personas;
	}

	public Pais personas(Set<Persona> personas) {
		this.personas = personas;
		return this;
	}

	public Pais addPersonas(Persona persona) {
		this.personas.add(persona);
		persona.setPais(this);
		return this;
	}

	public Pais removePersonas(Persona persona) {
		this.personas.remove(persona);
		persona.setPais(null);
		return this;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}

	public Set<Funcion> getFunciones() {
		return funciones;
	}

	public Pais funciones(Set<Funcion> funcions) {
		this.funciones = funcions;
		return this;
	}

	public Pais addFunciones(Funcion funcion) {
		this.funciones.add(funcion);
		funcion.setPais(this);
		return this;
	}

	public Pais removeFunciones(Funcion funcion) {
		this.funciones.remove(funcion);
		funcion.setPais(null);
		return this;
	}

	public void setFunciones(Set<Funcion> funcions) {
		this.funciones = funcions;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pais)) {
			return false;
		}
		return id != null && id.equals(((Pais) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Pais{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombre='" + getNombre() + "'" + "}";
	}
}
