package com.uydevs.backoffice.dto.domain;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class PaisDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String codigo;

	@NotNull
	private String nombre;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PaisDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((PaisDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PaisDTO{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombre='" + getNombre() + "'" + "}";
	}
}
