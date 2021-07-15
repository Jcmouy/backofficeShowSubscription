package com.uydevs.backoffice.dto.filter;

import java.util.Objects;

import com.uydevs.backoffice.dto.AbstractEntidadCriteria;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

public class ObraCriteria extends AbstractEntidadCriteria {
	private static final long serialVersionUID = 1L;

	private StringFilter nombre;

	private StringFilter descripcion;

	private StringFilter protagonistas;

	private StringFilter direccion;

	private StringFilter autores;

	private LocalDateFilter fecha;

	private StringFilter duracion;

	private LongFilter funcionesId;

	private LongFilter contenidosId;

	private LongFilter etiquetasId;

	private LongFilter tipoId;

	private LongFilter cuentaId;

	public ObraCriteria() {
	}

	public ObraCriteria(ObraCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.nombre = other.nombre == null ? null : other.nombre.copy();
		this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
		this.protagonistas = other.protagonistas == null ? null : other.protagonistas.copy();
		this.direccion = other.direccion == null ? null : other.direccion.copy();
		this.autores = other.autores == null ? null : other.autores.copy();
		this.fecha = other.fecha == null ? null : other.fecha.copy();
		this.duracion = other.duracion == null ? null : other.duracion.copy();
		this.funcionesId = other.funcionesId == null ? null : other.funcionesId.copy();
		this.contenidosId = other.contenidosId == null ? null : other.contenidosId.copy();
		this.etiquetasId = other.etiquetasId == null ? null : other.etiquetasId.copy();
		this.tipoId = other.tipoId == null ? null : other.tipoId.copy();
		this.cuentaId = other.cuentaId == null ? null : other.cuentaId.copy();
	}

	@Override
	public ObraCriteria copy() {
		return new ObraCriteria(this);
	}

	public StringFilter getNombre() {
		return nombre;
	}

	public void setNombre(StringFilter nombre) {
		this.nombre = nombre;
	}

	public StringFilter getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(StringFilter descripcion) {
		this.descripcion = descripcion;
	}

	public StringFilter getProtagonistas() {
		return protagonistas;
	}

	public void setProtagonistas(StringFilter protagonistas) {
		this.protagonistas = protagonistas;
	}

	public StringFilter getDireccion() {
		return direccion;
	}

	public void setDireccion(StringFilter direccion) {
		this.direccion = direccion;
	}

	public StringFilter getAutores() {
		return autores;
	}

	public void setAutores(StringFilter autores) {
		this.autores = autores;
	}

	public LocalDateFilter getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateFilter fecha) {
		this.fecha = fecha;
	}

	public StringFilter getDuracion() {
		return duracion;
	}

	public void setDuracion(StringFilter duracion) {
		this.duracion = duracion;
	}

	public LongFilter getFuncionesId() {
		return funcionesId;
	}

	public void setFuncionesId(LongFilter funcionesId) {
		this.funcionesId = funcionesId;
	}

	public LongFilter getContenidosId() {
		return contenidosId;
	}

	public void setContenidosId(LongFilter contenidosId) {
		this.contenidosId = contenidosId;
	}

	public LongFilter getEtiquetasId() {
		return etiquetasId;
	}

	public void setEtiquetasId(LongFilter etiquetasId) {
		this.etiquetasId = etiquetasId;
	}

	public LongFilter getTipoId() {
		return tipoId;
	}

	public void setTipoId(LongFilter tipoId) {
		this.tipoId = tipoId;
	}

	public LongFilter getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(LongFilter cuentaId) {
		this.cuentaId = cuentaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final ObraCriteria that = (ObraCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre)
				&& Objects.equals(descripcion, that.descripcion) && Objects.equals(protagonistas, that.protagonistas)
				&& Objects.equals(direccion, that.direccion) && Objects.equals(autores, that.autores)
				&& Objects.equals(fecha, that.fecha) && Objects.equals(duracion, that.duracion)
				&& Objects.equals(funcionesId, that.funcionesId) && Objects.equals(contenidosId, that.contenidosId)
				&& Objects.equals(etiquetasId, that.etiquetasId) && Objects.equals(tipoId, that.tipoId)
				&& Objects.equals(cuentaId, that.cuentaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, descripcion, protagonistas, direccion, autores, fecha, duracion, funcionesId,
				contenidosId, etiquetasId, tipoId, cuentaId);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "ObraCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion + ", " : "")
				+ (protagonistas != null ? "protagonistas=" + protagonistas + ", " : "")
				+ (direccion != null ? "direccion=" + direccion + ", " : "")
				+ (autores != null ? "autores=" + autores + ", " : "") + (fecha != null ? "fecha=" + fecha + ", " : "")
				+ (duracion != null ? "duracion=" + duracion + ", " : "")
				+ (funcionesId != null ? "funcionesId=" + funcionesId + ", " : "")
				+ (contenidosId != null ? "contenidosId=" + contenidosId + ", " : "")
				+ (etiquetasId != null ? "etiquetasId=" + etiquetasId + ", " : "")
				+ (tipoId != null ? "tipoId=" + tipoId + ", " : "")
				+ (cuentaId != null ? "cuentaId=" + cuentaId + ", " : "") + "}";
	}

}
