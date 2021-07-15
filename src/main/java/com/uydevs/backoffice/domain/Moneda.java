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
@Table(name = "moneda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Moneda extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "codigo", nullable = false)
	private String codigo;

	@NotNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@OneToMany(mappedBy = "moneda")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Funcion> funciones = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getCodigo() {
		return codigo;
	}

	public Moneda codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public Moneda nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Funcion> getFunciones() {
		return funciones;
	}

	public Moneda funciones(Set<Funcion> funcions) {
		this.funciones = funcions;
		return this;
	}

	public Moneda addFunciones(Funcion funcion) {
		this.funciones.add(funcion);
		funcion.setMoneda(this);
		return this;
	}

	public Moneda removeFunciones(Funcion funcion) {
		this.funciones.remove(funcion);
		funcion.setMoneda(null);
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
		if (!(o instanceof Moneda)) {
			return false;
		}
		return id != null && id.equals(((Moneda) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Moneda{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombre='" + getNombre() + "'" + "}";
	}
}
