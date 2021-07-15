package com.uydevs.backoffice.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pago")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pago extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@Column(name = "id_externo")
	private String idExterno;

	@Column(name = "fecha_externa")
	private Instant fechaExterna;

	@OneToOne(mappedBy = "pago")
	@JsonIgnore
	private Subscripcion subscripcion;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getIdExterno() {
		return idExterno;
	}

	public Pago idExterno(String idExterno) {
		this.idExterno = idExterno;
		return this;
	}

	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}

	public Instant getFechaExterna() {
		return fechaExterna;
	}

	public Pago fechaExterna(Instant fechaExterna) {
		this.fechaExterna = fechaExterna;
		return this;
	}

	public void setFechaExterna(Instant fechaExterna) {
		this.fechaExterna = fechaExterna;
	}

	public Subscripcion getSubscripcion() {
		return subscripcion;
	}

	public Pago subscripcion(Subscripcion subscripcion) {
		this.subscripcion = subscripcion;
		return this;
	}

	public void setSubscripcion(Subscripcion subscripcion) {
		this.subscripcion = subscripcion;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pago)) {
			return false;
		}
		return id != null && id.equals(((Pago) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Pago{" + "id=" + getId() + ", idExterno='" + getIdExterno() + "'" + ", fechaExterna='"
				+ getFechaExterna() + "'" + "}";
	}
}
