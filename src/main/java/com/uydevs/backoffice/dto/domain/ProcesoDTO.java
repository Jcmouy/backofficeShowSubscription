package com.uydevs.backoffice.dto.domain;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class ProcesoDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String tipo;

	@NotNull
	private Instant fecha;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Instant getFecha() {
		return fecha;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ProcesoDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((ProcesoDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ProcesoDTO{" + "id=" + getId() + ", tipo='" + getTipo() + "'" + ", fecha='" + getFecha() + "'" + "}";
	}
}
