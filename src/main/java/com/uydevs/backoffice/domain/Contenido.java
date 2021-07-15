package com.uydevs.backoffice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "contenido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contenido extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "indice", nullable = false)
	private String indice;

	@Column(name = "subindice")
	private String subindice;

	@NotNull
	@Column(name = "tipo_contenido", nullable = false)
	private String tipoContenido;

	@NotNull
	@Column(name = "valor", nullable = false)
	private String valor;

	@Column(name = "resumen")
	private String resumen;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "contenidos", allowSetters = true)
	private Obra obra;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getIndice() {
		return indice;
	}

	public Contenido indice(String indice) {
		this.indice = indice;
		return this;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getSubindice() {
		return subindice;
	}

	public Contenido subindice(String subindice) {
		this.subindice = subindice;
		return this;
	}

	public void setSubindice(String subindice) {
		this.subindice = subindice;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public Contenido tipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
		return this;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public String getValor() {
		return valor;
	}

	public Contenido valor(String valor) {
		this.valor = valor;
		return this;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getResumen() {
		return resumen;
	}

	public Contenido resumen(String resumen) {
		this.resumen = resumen;
		return this;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public Obra getObra() {
		return obra;
	}

	public Contenido obra(Obra obra) {
		this.obra = obra;
		return this;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Contenido)) {
			return false;
		}
		return id != null && id.equals(((Contenido) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Contenido{" + "id=" + getId() + ", indice='" + getIndice() + "'" + ", subindice='" + getSubindice()
				+ "'" + ", tipoContenido='" + getTipoContenido() + "'" + ", valor='" + getValor() + "'" + ", resumen='"
				+ getResumen() + "'" + "}";
	}
}
