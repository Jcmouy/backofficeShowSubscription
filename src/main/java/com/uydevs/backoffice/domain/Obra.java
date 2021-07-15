package com.uydevs.backoffice.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "obra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Obra extends AbstractEntidad {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Lob
	@Column(name = "imagen", nullable = false)
	private byte[] imagen;

	@Column(name = "imagen_content_type", nullable = false)
	private String imagenContentType;

	@Lob
	@Column(name = "icono", nullable = false)
	private byte[] icono;

	@Column(name = "icono_content_type", nullable = false)
	private String iconoContentType;

	@Column(name = "protagonistas")
	private String protagonistas;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "autores")
	private String autores;

	@NotNull
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@Column(name = "duracion")
	private String duracion;

	@OneToMany(mappedBy = "obra")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Funcion> funciones = new HashSet<>();

	@OneToMany(mappedBy = "obra")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Contenido> contenidos = new HashSet<>();

	@ManyToMany
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JoinTable(name = "obra_etiquetas", joinColumns = @JoinColumn(name = "obra_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "etiquetas_id", referencedColumnName = "id"))
	private Set<Etiqueta> etiquetas = new HashSet<>();

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "obras", allowSetters = true)
	private TipoDeObra tipo;

	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnoreProperties(value = "obras", allowSetters = true)
	private Cuenta cuenta;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getNombre() {
		return nombre;
	}

	public Obra nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Obra descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public Obra imagen(byte[] imagen) {
		this.imagen = imagen;
		return this;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getImagenContentType() {
		return imagenContentType;
	}

	public Obra imagenContentType(String imagenContentType) {
		this.imagenContentType = imagenContentType;
		return this;
	}

	public void setImagenContentType(String imagenContentType) {
		this.imagenContentType = imagenContentType;
	}

	public byte[] getIcono() {
		return icono;
	}

	public Obra icono(byte[] icono) {
		this.icono = icono;
		return this;
	}

	public void setIcono(byte[] icono) {
		this.icono = icono;
	}

	public String getIconoContentType() {
		return iconoContentType;
	}

	public Obra iconoContentType(String iconoContentType) {
		this.iconoContentType = iconoContentType;
		return this;
	}

	public void setIconoContentType(String iconoContentType) {
		this.iconoContentType = iconoContentType;
	}

	public String getProtagonistas() {
		return protagonistas;
	}

	public Obra protagonistas(String protagonistas) {
		this.protagonistas = protagonistas;
		return this;
	}

	public void setProtagonistas(String protagonistas) {
		this.protagonistas = protagonistas;
	}

	public String getDireccion() {
		return direccion;
	}

	public Obra direccion(String direccion) {
		this.direccion = direccion;
		return this;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getAutores() {
		return autores;
	}

	public Obra autores(String autores) {
		this.autores = autores;
		return this;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public Obra fecha(LocalDate fecha) {
		this.fecha = fecha;
		return this;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getDuracion() {
		return duracion;
	}

	public Obra duracion(String duracion) {
		this.duracion = duracion;
		return this;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Set<Funcion> getFunciones() {
		return funciones;
	}

	public Obra funciones(Set<Funcion> funcions) {
		this.funciones = funcions;
		return this;
	}

	public Obra addFunciones(Funcion funcion) {
		this.funciones.add(funcion);
		funcion.setObra(this);
		return this;
	}

	public Obra removeFunciones(Funcion funcion) {
		this.funciones.remove(funcion);
		funcion.setObra(null);
		return this;
	}

	public void setFunciones(Set<Funcion> funcions) {
		this.funciones = funcions;
	}

	public Set<Contenido> getContenidos() {
		return contenidos;
	}

	public Obra contenidos(Set<Contenido> contenidos) {
		this.contenidos = contenidos;
		return this;
	}

	public Obra addContenidos(Contenido contenido) {
		this.contenidos.add(contenido);
		contenido.setObra(this);
		return this;
	}

	public Obra removeContenidos(Contenido contenido) {
		this.contenidos.remove(contenido);
		contenido.setObra(null);
		return this;
	}

	public void setContenidos(Set<Contenido> contenidos) {
		this.contenidos = contenidos;
	}

	public Set<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public Obra etiquetas(Set<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
		return this;
	}

	public Obra addEtiquetas(Etiqueta etiqueta) {
		this.etiquetas.add(etiqueta);
		etiqueta.getObras().add(this);
		return this;
	}

	public Obra removeEtiquetas(Etiqueta etiqueta) {
		this.etiquetas.remove(etiqueta);
		etiqueta.getObras().remove(this);
		return this;
	}

	public void setEtiquetas(Set<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public TipoDeObra getTipo() {
		return tipo;
	}

	public Obra tipo(TipoDeObra tipoDeObra) {
		this.tipo = tipoDeObra;
		return this;
	}

	public void setTipo(TipoDeObra tipoDeObra) {
		this.tipo = tipoDeObra;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public Obra cuenta(Cuenta cuenta) {
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
		if (!(o instanceof Obra)) {
			return false;
		}
		return id != null && id.equals(((Obra) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Obra{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", descripcion='" + getDescripcion() + "'"
				+ ", imagen='" + getImagen() + "'" + ", imagenContentType='" + getImagenContentType() + "'"
				+ ", icono='" + getIcono() + "'" + ", iconoContentType='" + getIconoContentType() + "'"
				+ ", protagonistas='" + getProtagonistas() + "'" + ", direccion='" + getDireccion() + "'"
				+ ", autores='" + getAutores() + "'" + ", fecha='" + getFecha() + "'" + ", duracion='" + getDuracion()
				+ "'" + "}";
	}
}
