package com.uydevs.backoffice.dto;

import java.time.Instant;

import com.uydevs.backoffice.domain.AbstractEntidadAuditable;

public class AbstractEntidadAuditableDto extends AbstractEntidadDto {

	private static final long serialVersionUID = 1L;

	private String createdBy;
	private Instant createdDate;
	private String lastModifiedBy;
	private Instant lastModifiedDate;

	public AbstractEntidadAuditableDto() {
	}

	public AbstractEntidadAuditableDto(AbstractEntidadAuditable entidad) {
		super();
		this.createdBy = entidad.getCreatedBy();
		this.createdDate = entidad.getCreatedDate();
		this.lastModifiedBy = entidad.getLastModifiedBy();
		this.lastModifiedDate = entidad.getLastModifiedDate();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "AbstractEntidadAuditableDto [createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + ", getId()="
				+ getId() + "]";
	}

}
