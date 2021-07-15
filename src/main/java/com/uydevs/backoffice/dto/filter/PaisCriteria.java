package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class PaisCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter codigo;

	private StringFilter nombre;

	private LongFilter personasId;

	private LongFilter funcionesId;

	public PaisCriteria() {
	}

	public PaisCriteria(PaisCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.codigo = other.codigo == null ? null : other.codigo.copy();
		this.nombre = other.nombre == null ? null : other.nombre.copy();
		this.personasId = other.personasId == null ? null : other.personasId.copy();
		this.funcionesId = other.funcionesId == null ? null : other.funcionesId.copy();
	}

	@Override
	public PaisCriteria copy() {
		return new PaisCriteria(this);
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

	public LongFilter getPersonasId() {
		return personasId;
	}

	public void setPersonasId(LongFilter personasId) {
		this.personasId = personasId;
	}

	public LongFilter getFuncionesId() {
		return funcionesId;
	}

	public void setFuncionesId(LongFilter funcionesId) {
		this.funcionesId = funcionesId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final PaisCriteria that = (PaisCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo) && Objects.equals(nombre, that.nombre)
				&& Objects.equals(personasId, that.personasId) && Objects.equals(funcionesId, that.funcionesId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codigo, nombre, personasId, funcionesId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PaisCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (personasId != null ? "personasId=" + personasId + ", " : "")
				+ (funcionesId != null ? "funcionesId=" + funcionesId + ", " : "") + "}";
	}

}
