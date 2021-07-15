package com.uydevs.backoffice.dto.domain;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.domain.enumeration.TiposDeObra;
import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class TipoDeObraDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private TiposDeObra tipo;

	private String descripcion;

	public TiposDeObra getTipo() {
		return tipo;
	}

	public void setTipo(TiposDeObra tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TipoDeObraDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((TipoDeObraDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "TipoDeObraDTO{" + "id=" + getId() + ", tipo='" + getTipo() + "'" + ", descripcion='" + getDescripcion()
				+ "'" + "}";
	}
}
