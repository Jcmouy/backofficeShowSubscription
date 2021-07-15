package com.uydevs.backoffice.dto.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class FuncionDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private LocalDate fecha;

	@NotNull
	private BigDecimal precio;

	private Long obraId;

	private String obraNombre;

	private Long paisId;

	private String paisNombre;

	private Long monedaId;

	private String monedaNombre;

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Long getObraId() {
		return obraId;
	}

	public void setObraId(Long obraId) {
		this.obraId = obraId;
	}

	public String getObraNombre() {
		return obraNombre;
	}

	public void setObraNombre(String obraNombre) {
		this.obraNombre = obraNombre;
	}

	public Long getPaisId() {
		return paisId;
	}

	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}

	public String getPaisNombre() {
		return paisNombre;
	}

	public void setPaisNombre(String paisNombre) {
		this.paisNombre = paisNombre;
	}

	public Long getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}

	public String getMonedaNombre() {
		return monedaNombre;
	}

	public void setMonedaNombre(String monedaNombre) {
		this.monedaNombre = monedaNombre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FuncionDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((FuncionDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "FuncionDTO{" + "id=" + getId() + ", fecha='" + getFecha() + "'" + ", precio=" + getPrecio()
				+ ", obraId=" + getObraId() + ", obraNombre='" + getObraNombre() + "'" + ", paisId=" + getPaisId()
				+ ", paisNombre='" + getPaisNombre() + "'" + ", monedaId=" + getMonedaId() + ", monedaNombre='"
				+ getMonedaNombre() + "'" + "}";
	}
}
