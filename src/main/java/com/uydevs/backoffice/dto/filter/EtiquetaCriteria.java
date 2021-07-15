package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class EtiquetaCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter nombre;

	private LongFilter obrasId;

	public EtiquetaCriteria() {
	}

	public EtiquetaCriteria(EtiquetaCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.nombre = other.nombre == null ? null : other.nombre.copy();
		this.obrasId = other.obrasId == null ? null : other.obrasId.copy();
	}

	@Override
	public EtiquetaCriteria copy() {
		return new EtiquetaCriteria(this);
	}

	public StringFilter getNombre() {
		return nombre;
	}

	public void setNombre(StringFilter nombre) {
		this.nombre = nombre;
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
		final EtiquetaCriteria that = (EtiquetaCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre)
				&& Objects.equals(obrasId, that.obrasId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, obrasId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "EtiquetaCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (obrasId != null ? "obrasId=" + obrasId + ", " : "") + "}";
	}

}
