package com.uydevs.backoffice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "etiqueta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etiqueta extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@ManyToMany(mappedBy = "etiquetas")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Obra> obras = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getNombre() {
		return nombre;
	}

	public Etiqueta nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Obra> getObras() {
		return obras;
	}

	public Etiqueta obras(Set<Obra> obras) {
		this.obras = obras;
		return this;
	}

	public Etiqueta addObras(Obra obra) {
		this.obras.add(obra);
		obra.getEtiquetas().add(this);
		return this;
	}

	public Etiqueta removeObras(Obra obra) {
		this.obras.remove(obra);
		obra.getEtiquetas().remove(this);
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
		if (!(o instanceof Etiqueta)) {
			return false;
		}
		return id != null && id.equals(((Etiqueta) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Etiqueta{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + "}";
	}
}
