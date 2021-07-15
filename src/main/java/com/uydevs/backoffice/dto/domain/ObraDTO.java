package com.uydevs.backoffice.dto.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.uydevs.backoffice.dto.AbstractEntidadDto;

public class ObraDTO extends AbstractEntidadDto {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String nombre;

	private String descripcion;

	@Lob
	private byte[] imagen;

	private String imagenContentType;

	@Lob
	private byte[] icono;

	private String iconoContentType;
	private String protagonistas;

	private String direccion;

	private String autores;

	@NotNull
	private LocalDate fecha;

	private String duracion;

	private Set<EtiquetaDTO> etiquetas = new HashSet<>();

	private Long tipoId;

	private String tipoTipo;

	private Long cuentaId;

	private String cuentaNombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getImagenContentType() {
		return imagenContentType;
	}

	public void setImagenContentType(String imagenContentType) {
		this.imagenContentType = imagenContentType;
	}

	public byte[] getIcono() {
		return icono;
	}

	public void setIcono(byte[] icono) {
		this.icono = icono;
	}

	public String getIconoContentType() {
		return iconoContentType;
	}

	public void setIconoContentType(String iconoContentType) {
		this.iconoContentType = iconoContentType;
	}

	public String getProtagonistas() {
		return protagonistas;
	}

	public void setProtagonistas(String protagonistas) {
		this.protagonistas = protagonistas;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Set<EtiquetaDTO> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Set<EtiquetaDTO> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoDeObraId) {
		this.tipoId = tipoDeObraId;
	}

	public String getTipoTipo() {
		return tipoTipo;
	}

	public void setTipoTipo(String tipoDeObraTipo) {
		this.tipoTipo = tipoDeObraTipo;
	}

	public Long getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Long cuentaId) {
		this.cuentaId = cuentaId;
	}

	public String getCuentaNombre() {
		return cuentaNombre;
	}

	public void setCuentaNombre(String cuentaNombre) {
		this.cuentaNombre = cuentaNombre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ObraDTO)) {
			return false;
		}

		return getId() != null && getId().equals(((ObraDTO) o).getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ObraDTO{" + "id=" + getId() + ", nombre='" + getNombre() + "'" + ", descripcion='" + getDescripcion()
				+ "'" + ", imagen='" + getImagen() + "'" + ", icono='" + getIcono() + "'" + ", protagonistas='"
				+ getProtagonistas() + "'" + ", direccion='" + getDireccion() + "'" + ", autores='" + getAutores() + "'"
				+ ", fecha='" + getFecha() + "'" + ", duracion='" + getDuracion() + "'" + ", etiquetas='"
				+ getEtiquetas() + "'" + ", tipoId=" + getTipoId() + ", tipoTipo='" + getTipoTipo() + "'"
				+ ", cuentaId=" + getCuentaId() + ", cuentaNombre='" + getCuentaNombre() + "'" + "}";
	}
}
