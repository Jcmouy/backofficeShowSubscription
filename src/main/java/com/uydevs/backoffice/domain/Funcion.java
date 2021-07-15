package com.uydevs.backoffice.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "funcion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Funcion extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@NotNull
	@Column(name = "precio", precision = 21, scale = 2, nullable = false)
	private BigDecimal precio;

	@OneToMany(mappedBy = "funcion")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Subscripcion> subscripciones = new HashSet<>();

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "funciones", allowSetters = true)
	private Obra obra;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "funciones", allowSetters = true)
	private Pais pais;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "funciones", allowSetters = true)
	private Moneda moneda;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public LocalDate getFecha() {
		return fecha;
	}

	public Funcion fecha(LocalDate fecha) {
		this.fecha = fecha;
		return this;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public Funcion precio(BigDecimal precio) {
		this.precio = precio;
		return this;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Set<Subscripcion> getSubscripciones() {
		return subscripciones;
	}

	public Funcion subscripciones(Set<Subscripcion> subscripcions) {
		this.subscripciones = subscripcions;
		return this;
	}

	public Funcion addSubscripciones(Subscripcion subscripcion) {
		this.subscripciones.add(subscripcion);
		subscripcion.setFuncion(this);
		return this;
	}

	public Funcion removeSubscripciones(Subscripcion subscripcion) {
		this.subscripciones.remove(subscripcion);
		subscripcion.setFuncion(null);
		return this;
	}

	public void setSubscripciones(Set<Subscripcion> subscripcions) {
		this.subscripciones = subscripcions;
	}

	public Obra getObra() {
		return obra;
	}

	public Funcion obra(Obra obra) {
		this.obra = obra;
		return this;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public Pais getPais() {
		return pais;
	}

	public Funcion pais(Pais pais) {
		this.pais = pais;
		return this;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public Funcion moneda(Moneda moneda) {
		this.moneda = moneda;
		return this;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Funcion)) {
			return false;
		}
		return id != null && id.equals(((Funcion) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Funcion{" + "id=" + getId() + ", fecha='" + getFecha() + "'" + ", precio=" + getPrecio() + "}";
	}
}
