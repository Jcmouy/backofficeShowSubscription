package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class PagoCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter idExterno;

	private InstantFilter fechaExterna;

	private LongFilter subscripcionId;

	public PagoCriteria() {
	}

	public PagoCriteria(PagoCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.idExterno = other.idExterno == null ? null : other.idExterno.copy();
		this.fechaExterna = other.fechaExterna == null ? null : other.fechaExterna.copy();
		this.subscripcionId = other.subscripcionId == null ? null : other.subscripcionId.copy();
	}

	@Override
	public PagoCriteria copy() {
		return new PagoCriteria(this);
	}

	public StringFilter getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(StringFilter idExterno) {
		this.idExterno = idExterno;
	}

	public InstantFilter getFechaExterna() {
		return fechaExterna;
	}

	public void setFechaExterna(InstantFilter fechaExterna) {
		this.fechaExterna = fechaExterna;
	}

	public LongFilter getSubscripcionId() {
		return subscripcionId;
	}

	public void setSubscripcionId(LongFilter subscripcionId) {
		this.subscripcionId = subscripcionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final PagoCriteria that = (PagoCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(idExterno, that.idExterno)
				&& Objects.equals(fechaExterna, that.fechaExterna)
				&& Objects.equals(subscripcionId, that.subscripcionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idExterno, fechaExterna, subscripcionId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PagoCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (idExterno != null ? "idExterno=" + idExterno + ", " : "")
				+ (fechaExterna != null ? "fechaExterna=" + fechaExterna + ", " : "")
				+ (subscripcionId != null ? "subscripcionId=" + subscripcionId + ", " : "") + "}";
	}

}
