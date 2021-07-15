package com.uydevs.backoffice.dto.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class PersonaDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	private String codigo;

	@NotNull
	private String nombres;

	@NotNull
	private String apellidos;

	private LocalDate fechaNacimiento;

	private String correoElectronico;

	private String telefono;

	private Long usuarioId;

	private Long paisId;

	private Long cuentaId;

	private String cuentaNombre;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long userId) {
		this.usuarioId = userId;
	}

	public Long getPaisId() {
		return paisId;
	}

	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}

	public Long getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Long cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getCuentaNombre() {
		return cuentaNombre;
	}

	public void setCuentaNombre(String cuentaNombre) {
		this.cuentaNombre = cuentaNombre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PersonaDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((PersonaDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PersonaDTO{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombres='" + getNombres() + "'"
				+ ", apellidos='" + getApellidos() + "'" + ", fechaNacimiento='" + getFechaNacimiento() + "'"
				+ ", correoElectronico='" + getCorreoElectronico() + "'" + ", telefono='" + getTelefono() + "'"
				+ ", usuarioId=" + getUsuarioId() + ", paisId=" + getPaisId() + ", cuentaId=" + getCuentaId()
				+ ", cuentaNombre='" + getCuentaNombre() + "'" + "}";
	}
}
