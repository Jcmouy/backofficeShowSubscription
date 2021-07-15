package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class ContenidoCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter indice;

	private StringFilter subindice;

	private StringFilter tipoContenido;

	private StringFilter valor;

	private StringFilter resumen;

	private LongFilter obraId;

	public ContenidoCriteria() {
	}

	public ContenidoCriteria(ContenidoCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.indice = other.indice == null ? null : other.indice.copy();
		this.subindice = other.subindice == null ? null : other.subindice.copy();
		this.tipoContenido = other.tipoContenido == null ? null : other.tipoContenido.copy();
		this.valor = other.valor == null ? null : other.valor.copy();
		this.resumen = other.resumen == null ? null : other.resumen.copy();
		this.obraId = other.obraId == null ? null : other.obraId.copy();
	}

	@Override
	public ContenidoCriteria copy() {
		return new ContenidoCriteria(this);
	}

	public StringFilter getIndice() {
		return indice;
	}

	public void setIndice(StringFilter indice) {
		this.indice = indice;
	}

	public StringFilter getSubindice() {
		return subindice;
	}

	public void setSubindice(StringFilter subindice) {
		this.subindice = subindice;
	}

	public StringFilter getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(StringFilter tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public StringFilter getValor() {
		return valor;
	}

	public void setValor(StringFilter valor) {
		this.valor = valor;
	}

	public StringFilter getResumen() {
		return resumen;
	}

	public void setResumen(StringFilter resumen) {
		this.resumen = resumen;
	}

	public LongFilter getObraId() {
		return obraId;
	}

	public void setObraId(LongFilter obraId) {
		this.obraId = obraId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final ContenidoCriteria that = (ContenidoCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(indice, that.indice)
				&& Objects.equals(subindice, that.subindice) && Objects.equals(tipoContenido, that.tipoContenido)
				&& Objects.equals(valor, that.valor) && Objects.equals(resumen, that.resumen)
				&& Objects.equals(obraId, that.obraId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, indice, subindice, tipoContenido, valor, resumen, obraId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ContenidoCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (indice != null ? "indice=" + indice + ", " : "")
				+ (subindice != null ? "subindice=" + subindice + ", " : "")
				+ (tipoContenido != null ? "tipoContenido=" + tipoContenido + ", " : "")
				+ (valor != null ? "valor=" + valor + ", " : "") + (resumen != null ? "resumen=" + resumen + ", " : "")
				+ (obraId != null ? "obraId=" + obraId + ", " : "") + "}";
	}

}
