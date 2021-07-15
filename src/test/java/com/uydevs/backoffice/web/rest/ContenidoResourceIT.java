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
import com.uydevs.backoffice.domain.Contenido;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.dto.domain.ContenidoDTO;
import com.uydevs.backoffice.repository.domain.ContenidoRepository;
import com.uydevs.backoffice.service.mapper.ContenidoMapper;
import com.uydevs.backoffice.web.rest.domain.ContenidoResource;

/**
 * Integration tests for the {@link ContenidoResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContenidoResourceIT {

	private static final String DEFAULT_INDICE = "AAAAAAAAAA";
	private static final String UPDATED_INDICE = "BBBBBBBBBB";

	private static final String DEFAULT_SUBINDICE = "AAAAAAAAAA";
	private static final String UPDATED_SUBINDICE = "BBBBBBBBBB";

	private static final String DEFAULT_TIPO_CONTENIDO = "AAAAAAAAAA";
	private static final String UPDATED_TIPO_CONTENIDO = "BBBBBBBBBB";

	private static final String DEFAULT_VALOR = "AAAAAAAAAA";
	private static final String UPDATED_VALOR = "BBBBBBBBBB";

	private static final String DEFAULT_RESUMEN = "AAAAAAAAAA";
	private static final String UPDATED_RESUMEN = "BBBBBBBBBB";

	@Autowired
	private ContenidoRepository contenidoRepository;

	@Autowired
	private ContenidoMapper contenidoMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restContenidoMockMvc;

	private Contenido contenido;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Contenido createEntity(EntityManager em) {
		Contenido contenido = new Contenido().indice(DEFAULT_INDICE).subindice(DEFAULT_SUBINDICE)
				.tipoContenido(DEFAULT_TIPO_CONTENIDO).valor(DEFAULT_VALOR).resumen(DEFAULT_RESUMEN);
		// Add required entity
		Obra obra;
		if (TestUtil.findAll(em, Obra.class).isEmpty()) {
			obra = ObraResourceIT.createEntity(em);
			em.persist(obra);
			em.flush();
		} else {
			obra = TestUtil.findAll(em, Obra.class).get(0);
		}
		contenido.setObra(obra);
		return contenido;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Contenido createUpdatedEntity(EntityManager em) {
		Contenido contenido = new Contenido().indice(UPDATED_INDICE).subindice(UPDATED_SUBINDICE)
				.tipoContenido(UPDATED_TIPO_CONTENIDO).valor(UPDATED_VALOR).resumen(UPDATED_RESUMEN);
		// Add required entity
		Obra obra;
		if (TestUtil.findAll(em, Obra.class).isEmpty()) {
			obra = ObraResourceIT.createUpdatedEntity(em);
			em.persist(obra);
			em.flush();
		} else {
			obra = TestUtil.findAll(em, Obra.class).get(0);
		}
		contenido.setObra(obra);
		return contenido;
	}

	@BeforeEach
	public void initTest() {
		contenido = createEntity(em);
	}

	@Test
	@Transactional
	public void createContenido() throws Exception {
		int databaseSizeBeforeCreate = contenidoRepository.findAll().size();
		// Create the Contenido
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);
		restContenidoMockMvc.perform(post("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isCreated());

		// Validate the Contenido in the database
		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeCreate + 1);
		Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
		assertThat(testContenido.getIndice()).isEqualTo(DEFAULT_INDICE);
		assertThat(testContenido.getSubindice()).isEqualTo(DEFAULT_SUBINDICE);
		assertThat(testContenido.getTipoContenido()).isEqualTo(DEFAULT_TIPO_CONTENIDO);
		assertThat(testContenido.getValor()).isEqualTo(DEFAULT_VALOR);
		assertThat(testContenido.getResumen()).isEqualTo(DEFAULT_RESUMEN);
	}

	@Test
	@Transactional
	public void createContenidoWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

		// Create the Contenido with an existing ID
		contenido.setId(1L);
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);

		// An entity with an existing ID cannot be created, so this API call must fail
		restContenidoMockMvc.perform(post("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isBadRequest());

		// Validate the Contenido in the database
		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkIndiceIsRequired() throws Exception {
		int databaseSizeBeforeTest = contenidoRepository.findAll().size();
		// set the field null
		contenido.setIndice(null);

		// Create the Contenido, which fails.
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);

		restContenidoMockMvc.perform(post("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isBadRequest());

		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkTipoContenidoIsRequired() throws Exception {
		int databaseSizeBeforeTest = contenidoRepository.findAll().size();
		// set the field null
		contenido.setTipoContenido(null);

		// Create the Contenido, which fails.
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);

		restContenidoMockMvc.perform(post("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isBadRequest());

		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkValorIsRequired() throws Exception {
		int databaseSizeBeforeTest = contenidoRepository.findAll().size();
		// set the field null
		contenido.setValor(null);

		// Create the Contenido, which fails.
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);

		restContenidoMockMvc.perform(post("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isBadRequest());

		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllContenidos() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList
		restContenidoMockMvc.perform(get("/api/contenidos?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(contenido.getId().intValue())))
				.andExpect(jsonPath("$.[*].indice").value(hasItem(DEFAULT_INDICE)))
				.andExpect(jsonPath("$.[*].subindice").value(hasItem(DEFAULT_SUBINDICE)))
				.andExpect(jsonPath("$.[*].tipoContenido").value(hasItem(DEFAULT_TIPO_CONTENIDO)))
				.andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
				.andExpect(jsonPath("$.[*].resumen").value(hasItem(DEFAULT_RESUMEN)));
	}

	@Test
	@Transactional
	public void getContenido() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get the contenido
		restContenidoMockMvc.perform(get("/api/contenidos/{id}", contenido.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(contenido.getId().intValue()))
				.andExpect(jsonPath("$.indice").value(DEFAULT_INDICE))
				.andExpect(jsonPath("$.subindice").value(DEFAULT_SUBINDICE))
				.andExpect(jsonPath("$.tipoContenido").value(DEFAULT_TIPO_CONTENIDO))
				.andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
				.andExpect(jsonPath("$.resumen").value(DEFAULT_RESUMEN));
	}

	@Test
	@Transactional
	public void getContenidosByIdFiltering() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		Long id = contenido.getId();

		defaultContenidoShouldBeFound("id.equals=" + id);
		defaultContenidoShouldNotBeFound("id.notEquals=" + id);

		defaultContenidoShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultContenidoShouldNotBeFound("id.greaterThan=" + id);

		defaultContenidoShouldBeFound("id.lessThanOrEqual=" + id);
		defaultContenidoShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllContenidosByIndiceIsEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where indice equals to DEFAULT_INDICE
		defaultContenidoShouldBeFound("indice.equals=" + DEFAULT_INDICE);

		// Get all the contenidoList where indice equals to UPDATED_INDICE
		defaultContenidoShouldNotBeFound("indice.equals=" + UPDATED_INDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosByIndiceIsNotEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where indice not equals to DEFAULT_INDICE
		defaultContenidoShouldNotBeFound("indice.notEquals=" + DEFAULT_INDICE);

		// Get all the contenidoList where indice not equals to UPDATED_INDICE
		defaultContenidoShouldBeFound("indice.notEquals=" + UPDATED_INDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosByIndiceIsInShouldWork() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where indice in DEFAULT_INDICE or UPDATED_INDICE
		defaultContenidoShouldBeFound("indice.in=" + DEFAULT_INDICE + "," + UPDATED_INDICE);

		// Get all the contenidoList where indice equals to UPDATED_INDICE
		defaultContenidoShouldNotBeFound("indice.in=" + UPDATED_INDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosByIndiceIsNullOrNotNull() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where indice is not null
		defaultContenidoShouldBeFound("indice.specified=true");

		// Get all the contenidoList where indice is null
		defaultContenidoShouldNotBeFound("indice.specified=false");
	}

	@Test
	@Transactional
	public void getAllContenidosByIndiceContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where indice contains DEFAULT_INDICE
		defaultContenidoShouldBeFound("indice.contains=" + DEFAULT_INDICE);

		// Get all the contenidoList where indice contains UPDATED_INDICE
		defaultContenidoShouldNotBeFound("indice.contains=" + UPDATED_INDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosByIndiceNotContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where indice does not contain DEFAULT_INDICE
		defaultContenidoShouldNotBeFound("indice.doesNotContain=" + DEFAULT_INDICE);

		// Get all the contenidoList where indice does not contain UPDATED_INDICE
		defaultContenidoShouldBeFound("indice.doesNotContain=" + UPDATED_INDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosBySubindiceIsEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where subindice equals to DEFAULT_SUBINDICE
		defaultContenidoShouldBeFound("subindice.equals=" + DEFAULT_SUBINDICE);

		// Get all the contenidoList where subindice equals to UPDATED_SUBINDICE
		defaultContenidoShouldNotBeFound("subindice.equals=" + UPDATED_SUBINDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosBySubindiceIsNotEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where subindice not equals to DEFAULT_SUBINDICE
		defaultContenidoShouldNotBeFound("subindice.notEquals=" + DEFAULT_SUBINDICE);

		// Get all the contenidoList where subindice not equals to UPDATED_SUBINDICE
		defaultContenidoShouldBeFound("subindice.notEquals=" + UPDATED_SUBINDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosBySubindiceIsInShouldWork() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where subindice in DEFAULT_SUBINDICE or
		// UPDATED_SUBINDICE
		defaultContenidoShouldBeFound("subindice.in=" + DEFAULT_SUBINDICE + "," + UPDATED_SUBINDICE);

		// Get all the contenidoList where subindice equals to UPDATED_SUBINDICE
		defaultContenidoShouldNotBeFound("subindice.in=" + UPDATED_SUBINDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosBySubindiceIsNullOrNotNull() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where subindice is not null
		defaultContenidoShouldBeFound("subindice.specified=true");

		// Get all the contenidoList where subindice is null
		defaultContenidoShouldNotBeFound("subindice.specified=false");
	}

	@Test
	@Transactional
	public void getAllContenidosBySubindiceContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where subindice contains DEFAULT_SUBINDICE
		defaultContenidoShouldBeFound("subindice.contains=" + DEFAULT_SUBINDICE);

		// Get all the contenidoList where subindice contains UPDATED_SUBINDICE
		defaultContenidoShouldNotBeFound("subindice.contains=" + UPDATED_SUBINDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosBySubindiceNotContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where subindice does not contain DEFAULT_SUBINDICE
		defaultContenidoShouldNotBeFound("subindice.doesNotContain=" + DEFAULT_SUBINDICE);

		// Get all the contenidoList where subindice does not contain UPDATED_SUBINDICE
		defaultContenidoShouldBeFound("subindice.doesNotContain=" + UPDATED_SUBINDICE);
	}

	@Test
	@Transactional
	public void getAllContenidosByTipoContenidoIsEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where tipoContenido equals to
		// DEFAULT_TIPO_CONTENIDO
		defaultContenidoShouldBeFound("tipoContenido.equals=" + DEFAULT_TIPO_CONTENIDO);

		// Get all the contenidoList where tipoContenido equals to
		// UPDATED_TIPO_CONTENIDO
		defaultContenidoShouldNotBeFound("tipoContenido.equals=" + UPDATED_TIPO_CONTENIDO);
	}

	@Test
	@Transactional
	public void getAllContenidosByTipoContenidoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where tipoContenido not equals to
		// DEFAULT_TIPO_CONTENIDO
		defaultContenidoShouldNotBeFound("tipoContenido.notEquals=" + DEFAULT_TIPO_CONTENIDO);

		// Get all the contenidoList where tipoContenido not equals to
		// UPDATED_TIPO_CONTENIDO
		defaultContenidoShouldBeFound("tipoContenido.notEquals=" + UPDATED_TIPO_CONTENIDO);
	}

	@Test
	@Transactional
	public void getAllContenidosByTipoContenidoIsInShouldWork() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where tipoContenido in DEFAULT_TIPO_CONTENIDO or
		// UPDATED_TIPO_CONTENIDO
		defaultContenidoShouldBeFound("tipoContenido.in=" + DEFAULT_TIPO_CONTENIDO + "," + UPDATED_TIPO_CONTENIDO);

		// Get all the contenidoList where tipoContenido equals to
		// UPDATED_TIPO_CONTENIDO
		defaultContenidoShouldNotBeFound("tipoContenido.in=" + UPDATED_TIPO_CONTENIDO);
	}

	@Test
	@Transactional
	public void getAllContenidosByTipoContenidoIsNullOrNotNull() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where tipoContenido is not null
		defaultContenidoShouldBeFound("tipoContenido.specified=true");

		// Get all the contenidoList where tipoContenido is null
		defaultContenidoShouldNotBeFound("tipoContenido.specified=false");
	}

	@Test
	@Transactional
	public void getAllContenidosByTipoContenidoContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where tipoContenido contains DEFAULT_TIPO_CONTENIDO
		defaultContenidoShouldBeFound("tipoContenido.contains=" + DEFAULT_TIPO_CONTENIDO);

		// Get all the contenidoList where tipoContenido contains UPDATED_TIPO_CONTENIDO
		defaultContenidoShouldNotBeFound("tipoContenido.contains=" + UPDATED_TIPO_CONTENIDO);
	}

	@Test
	@Transactional
	public void getAllContenidosByTipoContenidoNotContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where tipoContenido does not contain
		// DEFAULT_TIPO_CONTENIDO
		defaultContenidoShouldNotBeFound("tipoContenido.doesNotContain=" + DEFAULT_TIPO_CONTENIDO);

		// Get all the contenidoList where tipoContenido does not contain
		// UPDATED_TIPO_CONTENIDO
		defaultContenidoShouldBeFound("tipoContenido.doesNotContain=" + UPDATED_TIPO_CONTENIDO);
	}

	@Test
	@Transactional
	public void getAllContenidosByValorIsEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where valor equals to DEFAULT_VALOR
		defaultContenidoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

		// Get all the contenidoList where valor equals to UPDATED_VALOR
		defaultContenidoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
	}

	@Test
	@Transactional
	public void getAllContenidosByValorIsNotEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where valor not equals to DEFAULT_VALOR
		defaultContenidoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

		// Get all the contenidoList where valor not equals to UPDATED_VALOR
		defaultContenidoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
	}

	@Test
	@Transactional
	public void getAllContenidosByValorIsInShouldWork() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where valor in DEFAULT_VALOR or UPDATED_VALOR
		defaultContenidoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

		// Get all the contenidoList where valor equals to UPDATED_VALOR
		defaultContenidoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
	}

	@Test
	@Transactional
	public void getAllContenidosByValorIsNullOrNotNull() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where valor is not null
		defaultContenidoShouldBeFound("valor.specified=true");

		// Get all the contenidoList where valor is null
		defaultContenidoShouldNotBeFound("valor.specified=false");
	}

	@Test
	@Transactional
	public void getAllContenidosByValorContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where valor contains DEFAULT_VALOR
		defaultContenidoShouldBeFound("valor.contains=" + DEFAULT_VALOR);

		// Get all the contenidoList where valor contains UPDATED_VALOR
		defaultContenidoShouldNotBeFound("valor.contains=" + UPDATED_VALOR);
	}

	@Test
	@Transactional
	public void getAllContenidosByValorNotContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where valor does not contain DEFAULT_VALOR
		defaultContenidoShouldNotBeFound("valor.doesNotContain=" + DEFAULT_VALOR);

		// Get all the contenidoList where valor does not contain UPDATED_VALOR
		defaultContenidoShouldBeFound("valor.doesNotContain=" + UPDATED_VALOR);
	}

	@Test
	@Transactional
	public void getAllContenidosByResumenIsEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where resumen equals to DEFAULT_RESUMEN
		defaultContenidoShouldBeFound("resumen.equals=" + DEFAULT_RESUMEN);

		// Get all the contenidoList where resumen equals to UPDATED_RESUMEN
		defaultContenidoShouldNotBeFound("resumen.equals=" + UPDATED_RESUMEN);
	}

	@Test
	@Transactional
	public void getAllContenidosByResumenIsNotEqualToSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where resumen not equals to DEFAULT_RESUMEN
		defaultContenidoShouldNotBeFound("resumen.notEquals=" + DEFAULT_RESUMEN);

		// Get all the contenidoList where resumen not equals to UPDATED_RESUMEN
		defaultContenidoShouldBeFound("resumen.notEquals=" + UPDATED_RESUMEN);
	}

	@Test
	@Transactional
	public void getAllContenidosByResumenIsInShouldWork() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where resumen in DEFAULT_RESUMEN or UPDATED_RESUMEN
		defaultContenidoShouldBeFound("resumen.in=" + DEFAULT_RESUMEN + "," + UPDATED_RESUMEN);

		// Get all the contenidoList where resumen equals to UPDATED_RESUMEN
		defaultContenidoShouldNotBeFound("resumen.in=" + UPDATED_RESUMEN);
	}

	@Test
	@Transactional
	public void getAllContenidosByResumenIsNullOrNotNull() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where resumen is not null
		defaultContenidoShouldBeFound("resumen.specified=true");

		// Get all the contenidoList where resumen is null
		defaultContenidoShouldNotBeFound("resumen.specified=false");
	}

	@Test
	@Transactional
	public void getAllContenidosByResumenContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where resumen contains DEFAULT_RESUMEN
		defaultContenidoShouldBeFound("resumen.contains=" + DEFAULT_RESUMEN);

		// Get all the contenidoList where resumen contains UPDATED_RESUMEN
		defaultContenidoShouldNotBeFound("resumen.contains=" + UPDATED_RESUMEN);
	}

	@Test
	@Transactional
	public void getAllContenidosByResumenNotContainsSomething() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		// Get all the contenidoList where resumen does not contain DEFAULT_RESUMEN
		defaultContenidoShouldNotBeFound("resumen.doesNotContain=" + DEFAULT_RESUMEN);

		// Get all the contenidoList where resumen does not contain UPDATED_RESUMEN
		defaultContenidoShouldBeFound("resumen.doesNotContain=" + UPDATED_RESUMEN);
	}

	@Test
	@Transactional
	public void getAllContenidosByObraIsEqualToSomething() throws Exception {
		// Get already existing entity
		Obra obra = contenido.getObra();
		contenidoRepository.saveAndFlush(contenido);
		Long obraId = obra.getId();

		// Get all the contenidoList where obra equals to obraId
		defaultContenidoShouldBeFound("obraId.equals=" + obraId);

		// Get all the contenidoList where obra equals to obraId + 1
		defaultContenidoShouldNotBeFound("obraId.equals=" + (obraId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultContenidoShouldBeFound(String filter) throws Exception {
		restContenidoMockMvc.perform(get("/api/contenidos?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(contenido.getId().intValue())))
				.andExpect(jsonPath("$.[*].indice").value(hasItem(DEFAULT_INDICE)))
				.andExpect(jsonPath("$.[*].subindice").value(hasItem(DEFAULT_SUBINDICE)))
				.andExpect(jsonPath("$.[*].tipoContenido").value(hasItem(DEFAULT_TIPO_CONTENIDO)))
				.andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
				.andExpect(jsonPath("$.[*].resumen").value(hasItem(DEFAULT_RESUMEN)));

		// Check, that the count call also returns 1
		restContenidoMockMvc.perform(get("/api/contenidos/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultContenidoShouldNotBeFound(String filter) throws Exception {
		restContenidoMockMvc.perform(get("/api/contenidos?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restContenidoMockMvc.perform(get("/api/contenidos/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingContenido() throws Exception {
		// Get the contenido
		restContenidoMockMvc.perform(get("/api/contenidos/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateContenido() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

		// Update the contenido
		Contenido updatedContenido = contenidoRepository.findById(contenido.getId()).get();
		// Disconnect from session so that the updates on updatedContenido are not
		// directly saved in db
		em.detach(updatedContenido);
		updatedContenido.indice(UPDATED_INDICE).subindice(UPDATED_SUBINDICE).tipoContenido(UPDATED_TIPO_CONTENIDO)
				.valor(UPDATED_VALOR).resumen(UPDATED_RESUMEN);
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(updatedContenido);

		restContenidoMockMvc.perform(put("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isOk());

		// Validate the Contenido in the database
		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
		Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
		assertThat(testContenido.getIndice()).isEqualTo(UPDATED_INDICE);
		assertThat(testContenido.getSubindice()).isEqualTo(UPDATED_SUBINDICE);
		assertThat(testContenido.getTipoContenido()).isEqualTo(UPDATED_TIPO_CONTENIDO);
		assertThat(testContenido.getValor()).isEqualTo(UPDATED_VALOR);
		assertThat(testContenido.getResumen()).isEqualTo(UPDATED_RESUMEN);
	}

	@Test
	@Transactional
	public void updateNonExistingContenido() throws Exception {
		int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

		// Create the Contenido
		ContenidoDTO contenidoDTO = contenidoMapper.toDto(contenido);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restContenidoMockMvc.perform(put("/api/contenidos").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(contenidoDTO))).andExpect(status().isBadRequest());

		// Validate the Contenido in the database
		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteContenido() throws Exception {
		// Initialize the database
		contenidoRepository.saveAndFlush(contenido);

		int databaseSizeBeforeDelete = contenidoRepository.findAll().size();

		// Delete the contenido
		restContenidoMockMvc
				.perform(delete("/api/contenidos/{id}", contenido.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Contenido> contenidoList = contenidoRepository.findAll();
		assertThat(contenidoList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
