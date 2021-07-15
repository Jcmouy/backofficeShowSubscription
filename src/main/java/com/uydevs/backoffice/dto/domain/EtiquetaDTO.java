package com.uydevs.backoffice.dto.domain;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class EtiquetaDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String nombre;

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
		if (!(o instanceof EtiquetaDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((EtiquetaDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "EtiquetaDTO{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + "}";
	}
}
