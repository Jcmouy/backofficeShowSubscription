package com.uydevs.backoffice.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "cuenta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cuenta extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "codigo", nullable = false)
	private String codigo;

	@NotNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "fecha_baja")
	private LocalDate fechaBaja;

	@OneToMany(mappedBy = "cuenta")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Obra> obras = new HashSet<>();

	@OneToMany(mappedBy = "cuenta")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Persona> personas = new HashSet<>();

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getCodigo() {
		return codigo;
	}

	public Cuenta codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public Cuenta nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public Cuenta fechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
		return this;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Set<Obra> getObras() {
		return obras;
	}

	public Cuenta obras(Set<Obra> obras) {
		this.obras = obras;
		return this;
	}

	public Cuenta addObras(Obra obra) {
		this.obras.add(obra);
		obra.setCuenta(this);
		return this;
	}

	public Cuenta removeObras(Obra obra) {
		this.obras.remove(obra);
		obra.setCuenta(null);
		return this;
	}

	public void setObras(Set<Obra> obras) {
		this.obras = obras;
	}

	public Set<Persona> getPersonas() {
		return personas;
	}

	public Cuenta personas(Set<Persona> personas) {
		this.personas = personas;
		return this;
	}

	public Cuenta addPersonas(Persona persona) {
		this.personas.add(persona);
		persona.setCuenta(this);
		return this;
	}

	public Cuenta removePersonas(Persona persona) {
		this.personas.remove(persona);
		persona.setCuenta(null);
		return this;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Cuenta)) {
			return false;
		}
		return id != null && id.equals(((Cuenta) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Cuenta{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombre='" + getNombre() + "'"
				+ ", fechaBaja='" + getFechaBaja() + "'" + "}";
	}
}
