package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;

public class FuncionCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private LocalDateFilter fecha;

	private BigDecimalFilter precio;

	private LongFilter subscripcionesId;

	private LongFilter obraId;

	private LongFilter paisId;

	private LongFilter monedaId;

	public FuncionCriteria() {
	}

	public FuncionCriteria(FuncionCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.fecha = other.fecha == null ? null : other.fecha.copy();
		this.precio = other.precio == null ? null : other.precio.copy();
		this.subscripcionesId = other.subscripcionesId == null ? null : other.subscripcionesId.copy();
		this.obraId = other.obraId == null ? null : other.obraId.copy();
		this.paisId = other.paisId == null ? null : other.paisId.copy();
		this.monedaId = other.monedaId == null ? null : other.monedaId.copy();
	}

	@Override
	public FuncionCriteria copy() {
		return new FuncionCriteria(this);
	}

	public LocalDateFilter getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateFilter fecha) {
		this.fecha = fecha;
	}

	public BigDecimalFilter getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimalFilter precio) {
		this.precio = precio;
	}

	public LongFilter getSubscripcionesId() {
		return subscripcionesId;
	}

	public void setSubscripcionesId(LongFilter subscripcionesId) {
		this.subscripcionesId = subscripcionesId;
	}

	public LongFilter getObraId() {
		return obraId;
	}

	public void setObraId(LongFilter obraId) {
		this.obraId = obraId;
	}

	public LongFilter getPaisId() {
		return paisId;
	}

	public void setPaisId(LongFilter paisId) {
		this.paisId = paisId;
	}

	public LongFilter getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(LongFilter monedaId) {
		this.monedaId = monedaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final FuncionCriteria that = (FuncionCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(fecha, that.fecha) && Objects.equals(precio, that.precio)
				&& Objects.equals(subscripcionesId, that.subscripcionesId) && Objects.equals(obraId, that.obraId)
				&& Objects.equals(paisId, that.paisId) && Objects.equals(monedaId, that.monedaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fecha, precio, subscripcionesId, obraId, paisId, monedaId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "FuncionCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (fecha != null ? "fecha=" + fecha + ", " : "") + (precio != null ? "precio=" + precio + ", " : "")
				+ (subscripcionesId != null ? "subscripcionesId=" + subscripcionesId + ", " : "")
				+ (obraId != null ? "obraId=" + obraId + ", " : "") + (paisId != null ? "paisId=" + paisId + ", " : "")
				+ (monedaId != null ? "monedaId=" + monedaId + ", " : "") + "}";
	}

}
