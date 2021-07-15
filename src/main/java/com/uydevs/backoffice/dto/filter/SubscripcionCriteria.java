package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.domain.enumeration.MetodoPago;
import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;

public class SubscripcionCriteria extends AbstractEntidadCriteria {
	/**
	 * Class for filtering MetodoPago
	 */
	public static class MetodoPagoFilter extends Filter<MetodoPago> {
		private static final long serialVersionUID = 1L;

		public MetodoPagoFilter() {
		}

		public MetodoPagoFilter(MetodoPagoFilter filter) {
			super(filter);
		}

		@Override
		public MetodoPagoFilter copy() {
			return new MetodoPagoFilter(this);
		}

	}

	private static final long serialVersionUID = 1L;

	private InstantFilter fecha;

	private MetodoPagoFilter metodoPago;

	private LongFilter pagoId;

	private LongFilter funcionId;

	private LongFilter personaId;

	public SubscripcionCriteria() {
	}

	public SubscripcionCriteria(SubscripcionCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.fecha = other.fecha == null ? null : other.fecha.copy();
		this.metodoPago = other.metodoPago == null ? null : other.metodoPago.copy();
		this.pagoId = other.pagoId == null ? null : other.pagoId.copy();
		this.funcionId = other.funcionId == null ? null : other.funcionId.copy();
		this.personaId = other.personaId == null ? null : other.personaId.copy();
	}

	@Override
	public SubscripcionCriteria copy() {
		return new SubscripcionCriteria(this);
	}

	public InstantFilter getFecha() {
		return fecha;
	}

	public void setFecha(InstantFilter fecha) {
		this.fecha = fecha;
	}

	public MetodoPagoFilter getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPagoFilter metodoPago) {
		this.metodoPago = metodoPago;
	}

	public LongFilter getPagoId() {
		return pagoId;
	}

	public void setPagoId(LongFilter pagoId) {
		this.pagoId = pagoId;
	}

	public LongFilter getFuncionId() {
		return funcionId;
	}

	public void setFuncionId(LongFilter funcionId) {
		this.funcionId = funcionId;
	}

	public LongFilter getPersonaId() {
		return personaId;
	}

	public void setPersonaId(LongFilter personaId) {
		this.personaId = personaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SubscripcionCriteria that = (SubscripcionCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(fecha, that.fecha)
				&& Objects.equals(metodoPago, that.metodoPago) && Objects.equals(pagoId, that.pagoId)
				&& Objects.equals(funcionId, that.funcionId) && Objects.equals(personaId, that.personaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fecha, metodoPago, pagoId, funcionId, personaId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "SubscripcionCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (fecha != null ? "fecha=" + fecha + ", " : "")
				+ (metodoPago != null ? "metodoPago=" + metodoPago + ", " : "")
				+ (pagoId != null ? "pagoId=" + pagoId + ", " : "")
				+ (funcionId != null ? "funcionId=" + funcionId + ", " : "")
				+ (personaId != null ? "personaId=" + personaId + ", " : "") + "}";
	}

}
