package com.uydevs.backoffice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.uydevs.backoffice.domain.enumeration.TiposDeObra;

@Entity
@Table(name = "tipo_de_obra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoDeObra extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private TiposDeObra tipo;

	@Column(name = "descripcion")
	private String descripcion;

	@OneToMany(mappedBy = "tipo")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Obra> obras = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public TiposDeObra getTipo() {
		return tipo;
	}

	public TipoDeObra tipo(TiposDeObra tipo) {
		this.tipo = tipo;
		return this;
	}

	public void setTipo(TiposDeObra tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public TipoDeObra descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Obra> getObras() {
		return obras;
	}

	public TipoDeObra obras(Set<Obra> obras) {
		this.obras = obras;
		return this;
	}

	public TipoDeObra addObras(Obra obra) {
		this.obras.add(obra);
		obra.setTipo(this);
		return this;
	}

	public TipoDeObra removeObras(Obra obra) {
		this.obras.remove(obra);
		obra.setTipo(null);
		return this;
	}

	public void setObras(Set<Obra> obras) {
		this.obras = obras;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TipoDeObra)) {
			return false;
		}
		return id != null && id.equals(((TipoDeObra) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "TipoDeObra{" + "id=" + getId() + ", tipo='" + getTipo() + "'" + ", descripcion='" + getDescripcion()
				+ "'" + "}";
	}
}
