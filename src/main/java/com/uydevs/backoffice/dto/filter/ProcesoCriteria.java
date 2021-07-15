package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.StringFilter;

public class ProcesoCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter tipo;

	private InstantFilter fecha;

	public ProcesoCriteria() {
	}

	public ProcesoCriteria(ProcesoCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.tipo = other.tipo == null ? null : other.tipo.copy();
		this.fecha = other.fecha == null ? null : other.fecha.copy();
	}

	@Override
	public ProcesoCriteria copy() {
		return new ProcesoCriteria(this);
	}

	public StringFilter getTipo() {
		return tipo;
	}

	public void setTipo(StringFilter tipo) {
		this.tipo = tipo;
	}

	public InstantFilter getFecha() {
		return fecha;
	}

	public void setFecha(InstantFilter fecha) {
		this.fecha = fecha;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final ProcesoCriteria that = (ProcesoCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(tipo, that.tipo) && Objects.equals(fecha, that.fecha);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tipo, fecha);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ProcesoCriteria{" + (id != null ? "id=" + id + ", " : "") + (tipo != null ? "tipo=" + tipo + ", " : "")
				+ (fecha != null ? "fecha=" + fecha + ", " : "") + "}";
	}

}
