package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.domain.enumeration.TiposDeObra;
import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class TipoDeObraCriteria extends AbstractEntidadCriteria {
	/**
	 * Class for filtering TiposDeObra
	 */
	public static class TiposDeObraFilter extends Filter<TiposDeObra> {
		private static final long serialVersionUID = 1L;

		public TiposDeObraFilter() {
		}

		public TiposDeObraFilter(TiposDeObraFilter filter) {
			super(filter);
		}

		@Override
		public TiposDeObraFilter copy() {
			return new TiposDeObraFilter(this);
		}

	}

	private static final long serialVersionUID = 1L;

	private TiposDeObraFilter tipo;

	private StringFilter descripcion;

	private LongFilter obrasId;

	public TipoDeObraCriteria() {
	}

	public TipoDeObraCriteria(TipoDeObraCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.tipo = other.tipo == null ? null : other.tipo.copy();
		this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
		this.obrasId = other.obrasId == null ? null : other.obrasId.copy();
	}

	@Override
	public TipoDeObraCriteria copy() {
		return new TipoDeObraCriteria(this);
	}

	public TiposDeObraFilter getTipo() {
		return tipo;
	}

	public void setTipo(TiposDeObraFilter tipo) {
		this.tipo = tipo;
	}

	public StringFilter getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(StringFilter descripcion) {
		this.descripcion = descripcion;
	}

	public LongFilter getObrasId() {
		return obrasId;
	}

	public void setObrasId(LongFilter obrasId) {
		this.obrasId = obrasId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final TipoDeObraCriteria that = (TipoDeObraCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(tipo, that.tipo)
				&& Objects.equals(descripcion, that.descripcion) && Objects.equals(obrasId, that.obrasId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tipo, descripcion, obrasId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "TipoDeObraCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (tipo != null ? "tipo=" + tipo + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion + ", " : "")
				+ (obrasId != null ? "obrasId=" + obrasId + ", " : "") + "}";
	}

}
