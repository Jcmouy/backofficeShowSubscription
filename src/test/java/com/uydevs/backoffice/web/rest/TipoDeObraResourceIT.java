package com.uydevs.backoffice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.uydevs.backoffice.PlataformaBackofficeApp;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.domain.TipoDeObra;
import com.uydevs.backoffice.domain.enumeration.TiposDeObra;
import com.uydevs.backoffice.dto.domain.TipoDeObraDTO;
import com.uydevs.backoffice.repository.domain.TipoDeObraRepository;
import com.uydevs.backoffice.service.mapper.TipoDeObraMapper;
import com.uydevs.backoffice.web.rest.domain.TipoDeObraResource;

/**
 * Integration tests for the {@link TipoDeObraResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoDeObraResourceIT {

	private static final TiposDeObra DEFAULT_TIPO = TiposDeObra.CORTO;
	private static final TiposDeObra UPDATED_TIPO = TiposDeObra.NOVELA;

	private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

	@Autowired
	private TipoDeObraRepository tipoDeObraRepository;

	@Autowired
	private TipoDeObraMapper tipoDeObraMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restTipoDeObraMockMvc;

	private TipoDeObra tipoDeObra;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static TipoDeObra createEntity(EntityManager em) {
		TipoDeObra tipoDeObra = new TipoDeObra().tipo(DEFAULT_TIPO).descripcion(DEFAULT_DESCRIPCION);
		return tipoDeObra;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static TipoDeObra createUpdatedEntity(EntityManager em) {
		TipoDeObra tipoDeObra = new TipoDeObra().tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION);
		return tipoDeObra;
	}

	@BeforeEach
	public void initTest() {
		tipoDeObra = createEntity(em);
	}

	@Test
	@Transactional
	public void createTipoDeObra() throws Exception {
		int databaseSizeBeforeCreate = tipoDeObraRepository.findAll().size();
		// Create the TipoDeObra
		TipoDeObraDTO tipoDeObraDTO = tipoDeObraMapper.toDto(tipoDeObra);
		restTipoDeObraMockMvc.perform(post("/api/tipo-de-obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tipoDeObraDTO))).andExpect(status().isCreated());

		// Validate the TipoDeObra in the database
		List<TipoDeObra> tipoDeObraList = tipoDeObraRepository.findAll();
		assertThat(tipoDeObraList).hasSize(databaseSizeBeforeCreate + 1);
		TipoDeObra testTipoDeObra = tipoDeObraList.get(tipoDeObraList.size() - 1);
		assertThat(testTipoDeObra.getTipo()).isEqualTo(DEFAULT_TIPO);
		assertThat(testTipoDeObra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
	}

	@Test
	@Transactional
	public void createTipoDeObraWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = tipoDeObraRepository.findAll().size();

		// Create the TipoDeObra with an existing ID
		tipoDeObra.setId(1L);
		TipoDeObraDTO tipoDeObraDTO = tipoDeObraMapper.toDto(tipoDeObra);

		// An entity with an existing ID cannot be created, so this API call must fail
		restTipoDeObraMockMvc.perform(post("/api/tipo-de-obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tipoDeObraDTO))).andExpect(status().isBadRequest());

		// Validate the TipoDeObra in the database
		List<TipoDeObra> tipoDeObraList = tipoDeObraRepository.findAll();
		assertThat(tipoDeObraList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkTipoIsRequired() throws Exception {
		int databaseSizeBeforeTest = tipoDeObraRepository.findAll().size();
		// set the field null
		tipoDeObra.setTipo(null);

		// Create the TipoDeObra, which fails.
		TipoDeObraDTO tipoDeObraDTO = tipoDeObraMapper.toDto(tipoDeObra);

		restTipoDeObraMockMvc.perform(post("/api/tipo-de-obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tipoDeObraDTO))).andExpect(status().isBadRequest());

		List<TipoDeObra> tipoDeObraList = tipoDeObraRepository.findAll();
		assertThat(tipoDeObraList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllTipoDeObras() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeObra.getId().intValue())))
				.andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
				.andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
	}

	@Test
	@Transactional
	public void getTipoDeObra() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get the tipoDeObra
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras/{id}", tipoDeObra.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(tipoDeObra.getId().intValue()))
				.andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
				.andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
	}

	@Test
	@Transactional
	public void getTipoDeObrasByIdFiltering() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		Long id = tipoDeObra.getId();

		defaultTipoDeObraShouldBeFound("id.equals=" + id);
		defaultTipoDeObraShouldNotBeFound("id.notEquals=" + id);

		defaultTipoDeObraShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultTipoDeObraShouldNotBeFound("id.greaterThan=" + id);

		defaultTipoDeObraShouldBeFound("id.lessThanOrEqual=" + id);
		defaultTipoDeObraShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByTipoIsEqualToSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where tipo equals to DEFAULT_TIPO
		defaultTipoDeObraShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

		// Get all the tipoDeObraList where tipo equals to UPDATED_TIPO
		defaultTipoDeObraShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByTipoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where tipo not equals to DEFAULT_TIPO
		defaultTipoDeObraShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

		// Get all the tipoDeObraList where tipo not equals to UPDATED_TIPO
		defaultTipoDeObraShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByTipoIsInShouldWork() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where tipo in DEFAULT_TIPO or UPDATED_TIPO
		defaultTipoDeObraShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

		// Get all the tipoDeObraList where tipo equals to UPDATED_TIPO
		defaultTipoDeObraShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByTipoIsNullOrNotNull() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where tipo is not null
		defaultTipoDeObraShouldBeFound("tipo.specified=true");

		// Get all the tipoDeObraList where tipo is null
		defaultTipoDeObraShouldNotBeFound("tipo.specified=false");
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByDescripcionIsEqualToSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where descripcion equals to DEFAULT_DESCRIPCION
		defaultTipoDeObraShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

		// Get all the tipoDeObraList where descripcion equals to UPDATED_DESCRIPCION
		defaultTipoDeObraShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByDescripcionIsNotEqualToSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where descripcion not equals to
		// DEFAULT_DESCRIPCION
		defaultTipoDeObraShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

		// Get all the tipoDeObraList where descripcion not equals to
		// UPDATED_DESCRIPCION
		defaultTipoDeObraShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByDescripcionIsInShouldWork() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where descripcion in DEFAULT_DESCRIPCION or
		// UPDATED_DESCRIPCION
		defaultTipoDeObraShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

		// Get all the tipoDeObraList where descripcion equals to UPDATED_DESCRIPCION
		defaultTipoDeObraShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByDescripcionIsNullOrNotNull() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where descripcion is not null
		defaultTipoDeObraShouldBeFound("descripcion.specified=true");

		// Get all the tipoDeObraList where descripcion is null
		defaultTipoDeObraShouldNotBeFound("descripcion.specified=false");
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByDescripcionContainsSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where descripcion contains DEFAULT_DESCRIPCION
		defaultTipoDeObraShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

		// Get all the tipoDeObraList where descripcion contains UPDATED_DESCRIPCION
		defaultTipoDeObraShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByDescripcionNotContainsSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		// Get all the tipoDeObraList where descripcion does not contain
		// DEFAULT_DESCRIPCION
		defaultTipoDeObraShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

		// Get all the tipoDeObraList where descripcion does not contain
		// UPDATED_DESCRIPCION
		defaultTipoDeObraShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllTipoDeObrasByObrasIsEqualToSomething() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);
		Obra obras = ObraResourceIT.createEntity(em);
		em.persist(obras);
		em.flush();
		tipoDeObra.addObras(obras);
		tipoDeObraRepository.saveAndFlush(tipoDeObra);
		Long obrasId = obras.getId();

		// Get all the tipoDeObraList where obras equals to obrasId
		defaultTipoDeObraShouldBeFound("obrasId.equals=" + obrasId);

		// Get all the tipoDeObraList where obras equals to obrasId + 1
		defaultTipoDeObraShouldNotBeFound("obrasId.equals=" + (obrasId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultTipoDeObraShouldBeFound(String filter) throws Exception {
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeObra.getId().intValue())))
				.andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
				.andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

		// Check, that the count call also returns 1
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultTipoDeObraShouldNotBeFound(String filter) throws Exception {
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingTipoDeObra() throws Exception {
		// Get the tipoDeObra
		restTipoDeObraMockMvc.perform(get("/api/tipo-de-obras/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateTipoDeObra() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		int databaseSizeBeforeUpdate = tipoDeObraRepository.findAll().size();

		// Update the tipoDeObra
		TipoDeObra updatedTipoDeObra = tipoDeObraRepository.findById(tipoDeObra.getId()).get();
		// Disconnect from session so that the updates on updatedTipoDeObra are not
		// directly saved in db
		em.detach(updatedTipoDeObra);
		updatedTipoDeObra.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION);
		TipoDeObraDTO tipoDeObraDTO = tipoDeObraMapper.toDto(updatedTipoDeObra);

		restTipoDeObraMockMvc.perform(put("/api/tipo-de-obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tipoDeObraDTO))).andExpect(status().isOk());

		// Validate the TipoDeObra in the database
		List<TipoDeObra> tipoDeObraList = tipoDeObraRepository.findAll();
		assertThat(tipoDeObraList).hasSize(databaseSizeBeforeUpdate);
		TipoDeObra testTipoDeObra = tipoDeObraList.get(tipoDeObraList.size() - 1);
		assertThat(testTipoDeObra.getTipo()).isEqualTo(UPDATED_TIPO);
		assertThat(testTipoDeObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void updateNonExistingTipoDeObra() throws Exception {
		int databaseSizeBeforeUpdate = tipoDeObraRepository.findAll().size();

		// Create the TipoDeObra
		TipoDeObraDTO tipoDeObraDTO = tipoDeObraMapper.toDto(tipoDeObra);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restTipoDeObraMockMvc.perform(put("/api/tipo-de-obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tipoDeObraDTO))).andExpect(status().isBadRequest());

		// Validate the TipoDeObra in the database
		List<TipoDeObra> tipoDeObraList = tipoDeObraRepository.findAll();
		assertThat(tipoDeObraList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteTipoDeObra() throws Exception {
		// Initialize the database
		tipoDeObraRepository.saveAndFlush(tipoDeObra);

		int databaseSizeBeforeDelete = tipoDeObraRepository.findAll().size();

		// Delete the tipoDeObra
		restTipoDeObraMockMvc
				.perform(delete("/api/tipo-de-obras/{id}", tipoDeObra.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<TipoDeObra> tipoDeObraList = tipoDeObraRepository.findAll();
		assertThat(tipoDeObraList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
