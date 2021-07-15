package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class CuentaCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter codigo;

	private StringFilter nombre;

	private LocalDateFilter fechaBaja;

	private LongFilter obrasId;

	private LongFilter personasId;

	public CuentaCriteria() {
	}

	public CuentaCriteria(CuentaCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.codigo = other.codigo == null ? null : other.codigo.copy();
		this.nombre = other.nombre == null ? null : other.nombre.copy();
		this.fechaBaja = other.fechaBaja == null ? null : other.fechaBaja.copy();
		this.obrasId = other.obrasId == null ? null : other.obrasId.copy();
		this.personasId = other.personasId == null ? null : other.personasId.copy();
	}

	@Override
	public CuentaCriteria copy() {
		return new CuentaCriteria(this);
	}

	public StringFilter getCodigo() {
		return codigo;
	}

	public void setCodigo(StringFilter codigo) {
		this.codigo = codigo;
	}

	public StringFilter getNombre() {
		return nombre;
	}

	public void setNombre(StringFilter nombre) {
		this.nombre = nombre;
	}

	public LocalDateFilter getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDateFilter fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public LongFilter getObrasId() {
		return obrasId;
	}

	public void setObrasId(LongFilter obrasId) {
		this.obrasId = obrasId;
	}

	public LongFilter getPersonasId() {
		return personasId;
	}

	public void setPersonasId(LongFilter personasId) {
		this.personasId = personasId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final CuentaCriteria that = (CuentaCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo) && Objects.equals(nombre, that.nombre)
				&& Objects.equals(fechaBaja, that.fechaBaja) && Objects.equals(obrasId, that.obrasId)
				&& Objects.equals(personasId, that.personasId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codigo, nombre, fechaBaja, obrasId, personasId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "CuentaCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (fechaBaja != null ? "fechaBaja=" + fechaBaja + ", " : "")
				+ (obrasId != null ? "obrasId=" + obrasId + ", " : "")
				+ (personasId != null ? "personasId=" + personasId + ", " : "") + "}";
	}

}
