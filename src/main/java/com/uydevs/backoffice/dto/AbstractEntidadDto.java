package com.uydevs.backoffice.dto;

import java.io.Serializable;

public class AbstractEntidadDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	public AbstractEntidadDto() {
	}

	public AbstractEntidadDto(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AbstractEntidadDto [id=" + id + "]";
	}

}
