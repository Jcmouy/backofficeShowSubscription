package com.uydevs.backoffice.dto.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class CuentaDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String codigo;

	@NotNull
	private String nombre;

	private LocalDate fechaBaja;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CuentaDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((CuentaDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "CuentaDTO{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombre='" + getNombre() + "'"
				+ ", fechaBaja='" + getFechaBaja() + "'" + "}";
	}
}
