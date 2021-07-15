package com.uydevs.backoffice.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "proceso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proceso extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "tipo", nullable = false)
	private String tipo;

	@NotNull
	@Column(name = "fecha", nullable = false)
	private Instant fecha;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getTipo() {
		return tipo;
	}

	public Proceso tipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Instant getFecha() {
		return fecha;
	}

	public Proceso fecha(Instant fecha) {
		this.fecha = fecha;
		return this;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Proceso)) {
			return false;
		}
		return id != null && id.equals(((Proceso) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Proceso{" + "id=" + getId() + ", tipo='" + getTipo() + "'" + ", fecha='" + getFecha() + "'" + "}";
	}
}
