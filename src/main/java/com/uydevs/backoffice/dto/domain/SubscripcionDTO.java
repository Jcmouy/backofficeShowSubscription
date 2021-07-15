package com.uydevs.backoffice.dto.domain;

import java.time.Instant;

import com.uydevs.backoffice.domain.enumeration.MetodoPago;
import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class SubscripcionDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	private Instant fecha;

	private MetodoPago metodoPago;

	private Long pagoId;

	private Long funcionId;

	private Long personaId;

	public Instant getFecha() {
		return fecha;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public Long getPagoId() {
		return pagoId;
	}

	public void setPagoId(Long pagoId) {
		this.pagoId = pagoId;
	}

	public Long getFuncionId() {
		return funcionId;
	}

	public void setFuncionId(Long funcionId) {
		this.funcionId = funcionId;
	}

	public Long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SubscripcionDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((SubscripcionDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "SubscripcionDTO{" + "id=" + getId() + ", fecha='" + getFecha() + "'" + ", metodoPago='"
				+ getMetodoPago() + "'" + ", pagoId=" + getPagoId() + ", funcionId=" + getFuncionId() + ", personaId="
				+ getPersonaId() + "}";
	}
}
