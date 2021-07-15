package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class PersonaCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter codigo;

	private StringFilter nombres;

	private StringFilter apellidos;

	private LocalDateFilter fechaNacimiento;

	private StringFilter correoElectronico;

	private StringFilter telefono;

	private LongFilter usuarioId;

	private LongFilter subscripcionesId;

	private LongFilter paisId;

	private LongFilter cuentaId;

	public PersonaCriteria() {
	}

	public PersonaCriteria(PersonaCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.codigo = other.codigo == null ? null : other.codigo.copy();
		this.nombres = other.nombres == null ? null : other.nombres.copy();
		this.apellidos = other.apellidos == null ? null : other.apellidos.copy();
		this.fechaNacimiento = other.fechaNacimiento == null ? null : other.fechaNacimiento.copy();
		this.correoElectronico = other.correoElectronico == null ? null : other.correoElectronico.copy();
		this.telefono = other.telefono == null ? null : other.telefono.copy();
		this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
		this.subscripcionesId = other.subscripcionesId == null ? null : other.subscripcionesId.copy();
		this.paisId = other.paisId == null ? null : other.paisId.copy();
		this.cuentaId = other.cuentaId == null ? null : other.cuentaId.copy();
	}

	@Override
	public PersonaCriteria copy() {
		return new PersonaCriteria(this);
	}

	public StringFilter getCodigo() {
		return codigo;
	}

	public void setCodigo(StringFilter codigo) {
		this.codigo = codigo;
	}

	public StringFilter getNombres() {
		return nombres;
	}

	public void setNombres(StringFilter nombres) {
		this.nombres = nombres;
	}

	public StringFilter getApellidos() {
		return apellidos;
	}

	public void setApellidos(StringFilter apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDateFilter getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public StringFilter getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(StringFilter correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public StringFilter getTelefono() {
		return telefono;
	}

	public void setTelefono(StringFilter telefono) {
		this.telefono = telefono;
	}

	public LongFilter getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(LongFilter usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LongFilter getSubscripcionesId() {
		return subscripcionesId;
	}

	public void setSubscripcionesId(LongFilter subscripcionesId) {
		this.subscripcionesId = subscripcionesId;
	}

	public LongFilter getPaisId() {
		return paisId;
	}

	public void setPaisId(LongFilter paisId) {
		this.paisId = paisId;
	}

	public LongFilter getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(LongFilter cuentaId) {
		this.cuentaId = cuentaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final PersonaCriteria that = (PersonaCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo)
				&& Objects.equals(nombres, that.nombres) && Objects.equals(apellidos, that.apellidos)
				&& Objects.equals(fechaNacimiento, that.fechaNacimiento)
				&& Objects.equals(correoElectronico, that.correoElectronico) && Objects.equals(telefono, that.telefono)
				&& Objects.equals(usuarioId, that.usuarioId) && Objects.equals(subscripcionesId, that.subscripcionesId)
				&& Objects.equals(paisId, that.paisId) && Objects.equals(cuentaId, that.cuentaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codigo, nombres, apellidos, fechaNacimiento, correoElectronico, telefono, usuarioId,
				subscripcionesId, paisId, cuentaId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PersonaCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "")
				+ (nombres != null ? "nombres=" + nombres + ", " : "")
				+ (apellidos != null ? "apellidos=" + apellidos + ", " : "")
				+ (fechaNacimiento != null ? "fechaNacimiento=" + fechaNacimiento + ", " : "")
				+ (correoElectronico != null ? "correoElectronico=" + correoElectronico + ", " : "")
				+ (telefono != null ? "telefono=" + telefono + ", " : "")
				+ (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "")
				+ (subscripcionesId != null ? "subscripcionesId=" + subscripcionesId + ", " : "")
				+ (paisId != null ? "paisId=" + paisId + ", " : "")
				+ (cuentaId != null ? "cuentaId=" + cuentaId + ", " : "") + "}";
	}

}
