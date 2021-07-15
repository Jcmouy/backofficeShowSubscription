package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class MonedaCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter codigo;

	private StringFilter nombre;

	private LongFilter funcionesId;

	public MonedaCriteria() {
	}

	public MonedaCriteria(MonedaCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.codigo = other.codigo == null ? null : other.codigo.copy();
		this.nombre = other.nombre == null ? null : other.nombre.copy();
		this.funcionesId = other.funcionesId == null ? null : other.funcionesId.copy();
	}

	@Override
	public MonedaCriteria copy() {
		return new MonedaCriteria(this);
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
		final MonedaCriteria that = (MonedaCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo) && Objects.equals(nombre, that.nombre)
				&& Objects.equals(funcionesId, that.funcionesId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codigo, nombre, funcionesId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "MonedaCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (funcionesId != null ? "funcionesId=" + funcionesId + ", " : "") + "}";
	}

}
