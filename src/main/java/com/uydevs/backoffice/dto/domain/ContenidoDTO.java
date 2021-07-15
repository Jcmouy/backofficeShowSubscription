package com.uydevs.backoffice.dto.domain;

import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class ContenidoDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String indice;

	private String subindice;

	@NotNull
	private String tipoContenido;

	@NotNull
	private String valor;

	private String resumen;

	private Long obraId;

	private String obraNombre;

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getSubindice() {
		return subindice;
	}

	public void setSubindice(String subindice) {
		this.subindice = subindice;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ContenidoDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((ContenidoDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ContenidoDTO{" + "id=" + getId() + ", indice='" + getIndice() + "'" + ", subindice='" + getSubindice()
				+ "'" + ", tipoContenido='" + getTipoContenido() + "'" + ", valor='" + getValor() + "'" + ", resumen='"
				+ getResumen() + "'" + ", obraId=" + getObraId() + ", obraNombre='" + getObraNombre() + "'" + "}";
	}
}
