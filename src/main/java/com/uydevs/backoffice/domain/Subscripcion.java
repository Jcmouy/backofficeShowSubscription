package com.uydevs.backoffice.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uydevs.backoffice.domain.enumeration.MetodoPago;

@Entity
@Table(name = "subscripcion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Subscripcion extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@Column(name = "fecha")
	private Instant fecha;

	@Enumerated(EnumType.STRING)
	@Column(name = "metodo_pago")
	private MetodoPago metodoPago;

	@OneToOne
	@JoinColumn(unique = true)
	private Pago pago;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "subscripciones", allowSetters = true)
	private Funcion funcion;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "subscripciones", allowSetters = true)
	private Persona persona;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Instant getFecha() {
		return fecha;
	}

	public Subscripcion fecha(Instant fecha) {
		this.fecha = fecha;
		return this;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public Subscripcion metodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
		return this;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Pago getPago() {
		return pago;
	}

	public Subscripcion pago(Pago pago) {
		this.pago = pago;
		return this;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public Subscripcion funcion(Funcion funcion) {
		this.funcion = funcion;
		return this;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Persona getPersona() {
		return persona;
	}

	public Subscripcion persona(Persona persona) {
		this.persona = persona;
		return this;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Subscripcion)) {
			return false;
		}
		return id != null && id.equals(((Subscripcion) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Subscripcion{" + "id=" + getId() + ", fecha='" + getFecha() + "'" + ", metodoPago='" + getMetodoPago()
				+ "'" + "}";
	}
}
