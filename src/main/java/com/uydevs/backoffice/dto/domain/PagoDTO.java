package com.uydevs.backoffice.dto.domain;

import java.time.Instant;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class PagoDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	private String idExterno;

	private Instant fechaExterna;

	public String getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}

	public Instant getFechaExterna() {
		return fechaExterna;
	}

	public void setFechaExterna(Instant fechaExterna) {
		this.fechaExterna = fechaExterna;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PagoDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((PagoDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "PagoDTO{" + "id=" + getId() + ", idExterno='" + getIdExterno() + "'" + ", fechaExterna='"
				+ getFechaExterna() + "'" + "}";
	}
}
