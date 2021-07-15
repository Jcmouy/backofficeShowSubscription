package com.uydevs.backoffice.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Persona extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "nombres", nullable = false)
	private String nombres;

	@NotNull
	@Column(name = "apellidos", nullable = false)
	private String apellidos;

	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;

	@Column(name = "correo_electronico")
	private String correoElectronico;

	@Column(name = "telefono")
	private String telefono;

	@OneToOne
	@JoinColumn(unique = true)
	private User usuario;

	@OneToMany(mappedBy = "persona")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Subscripcion> subscripciones = new HashSet<>();

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "personas", allowSetters = true)
	private Pais pais;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "personas", allowSetters = true)
	private Cuenta cuenta;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getCodigo() {
		return codigo;
	}

	public Persona codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombres() {
		return nombres;
	}

	public Persona nombres(String nombres) {
		this.nombres = nombres;
		return this;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public Persona apellidos(String apellidos) {
		this.apellidos = apellidos;
		return this;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Persona fechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		return this;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public Persona correoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
		return this;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public Persona telefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public User getUsuario() {
		return usuario;
	}

	public Persona usuario(User user) {
		this.usuario = user;
		return this;
	}

	public void setUsuario(User user) {
		this.usuario = user;
	}

	public Set<Subscripcion> getSubscripciones() {
		return subscripciones;
	}

	public Persona subscripciones(Set<Subscripcion> subscripcions) {
		this.subscripciones = subscripcions;
		return this;
	}

	public Persona addSubscripciones(Subscripcion subscripcion) {
		this.subscripciones.add(subscripcion);
		subscripcion.setPersona(this);
		return this;
	}

	public Persona removeSubscripciones(Subscripcion subscripcion) {
		this.subscripciones.remove(subscripcion);
		subscripcion.setPersona(null);
		return this;
	}

	public void setSubscripciones(Set<Subscripcion> subscripcions) {
		this.subscripciones = subscripcions;
	}

	public Pais getPais() {
		return pais;
	}

	public Persona pais(Pais pais) {
		this.pais = pais;
		return this;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public Persona cuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
		return this;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Persona)) {
			return false;
		}
		return id != null && id.equals(((Persona) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Persona{" + "id=" + getId() + ", codigo='" + getCodigo() + "'" + ", nombres='" + getNombres() + "'"
				+ ", apellidos='" + getApellidos() + "'" + ", fechaNacimiento='" + getFechaNacimiento() + "'"
				+ ", correoElectronico='" + getCorreoElectronico() + "'" + ", telefono='" + getTelefono() + "'" + "}";
	}
}
