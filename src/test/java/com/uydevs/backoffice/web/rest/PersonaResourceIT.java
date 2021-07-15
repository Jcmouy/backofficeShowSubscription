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

import java.time.LocalDate;
import java.time.ZoneId;
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
import com.uydevs.backoffice.domain.Cuenta;
import com.uydevs.backoffice.domain.Pais;
import com.uydevs.backoffice.domain.Persona;
import com.uydevs.backoffice.domain.Subscripcion;
import com.uydevs.backoffice.domain.User;
import com.uydevs.backoffice.dto.domain.PersonaDTO;
import com.uydevs.backoffice.repository.domain.PersonaRepository;
import com.uydevs.backoffice.service.mapper.PersonaMapper;
import com.uydevs.backoffice.web.rest.domain.PersonaResource;

/**
 * Integration tests for the {@link PersonaResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonaResourceIT {

	private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
	private static final String UPDATED_CODIGO = "BBBBBBBBBB";

	private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

	private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
	private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

	private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
	private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

	private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
	private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

	private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
	private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PersonaMapper personaMapper;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restPersonaMockMvc;

	private Persona persona;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Persona createEntity(EntityManager em) {
		Persona persona = new Persona().codigo(DEFAULT_CODIGO).nombres(DEFAULT_NOMBRES).apellidos(DEFAULT_APELLIDOS)
				.fechaNacimiento(DEFAULT_FECHA_NACIMIENTO).correoElectronico(DEFAULT_CORREO_ELECTRONICO)
				.telefono(DEFAULT_TELEFONO);
		// Add required entity
		Pais pais;
		if (TestUtil.findAll(em, Pais.class).isEmpty()) {
			pais = PaisResourceIT.createEntity(em);
			em.persist(pais);
			em.flush();
		} else {
			pais = TestUtil.findAll(em, Pais.class).get(0);
		}
		persona.setPais(pais);
		// Add required entity
		Cuenta cuenta;
		if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
			cuenta = CuentaResourceIT.createEntity(em);
			em.persist(cuenta);
			em.flush();
		} else {
			cuenta = TestUtil.findAll(em, Cuenta.class).get(0);
		}
		persona.setCuenta(cuenta);
		return persona;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Persona createUpdatedEntity(EntityManager em) {
		Persona persona = new Persona().codigo(UPDATED_CODIGO).nombres(UPDATED_NOMBRES).apellidos(UPDATED_APELLIDOS)
				.fechaNacimiento(UPDATED_FECHA_NACIMIENTO).correoElectronico(UPDATED_CORREO_ELECTRONICO)
				.telefono(UPDATED_TELEFONO);
		// Add required entity
		Pais pais;
		if (TestUtil.findAll(em, Pais.class).isEmpty()) {
			pais = PaisResourceIT.createUpdatedEntity(em);
			em.persist(pais);
			em.flush();
		} else {
			pais = TestUtil.findAll(em, Pais.class).get(0);
		}
		persona.setPais(pais);
		// Add required entity
		Cuenta cuenta;
		if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
			cuenta = CuentaResourceIT.createUpdatedEntity(em);
			em.persist(cuenta);
			em.flush();
		} else {
			cuenta = TestUtil.findAll(em, Cuenta.class).get(0);
		}
		persona.setCuenta(cuenta);
		return persona;
	}

	@BeforeEach
	public void initTest() {
		persona = createEntity(em);
	}

	@Test
	@Transactional
	public void createPersona() throws Exception {
		int databaseSizeBeforeCreate = personaRepository.findAll().size();
		// Create the Persona
		PersonaDTO personaDTO = personaMapper.toDto(persona);
		restPersonaMockMvc.perform(post("/api/personas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(personaDTO))).andExpect(status().isCreated());

		// Validate the Persona in the database
		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
		Persona testPersona = personaList.get(personaList.size() - 1);
		assertThat(testPersona.getCodigo()).isEqualTo(DEFAULT_CODIGO);
		assertThat(testPersona.getNombres()).isEqualTo(DEFAULT_NOMBRES);
		assertThat(testPersona.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
		assertThat(testPersona.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
		assertThat(testPersona.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
		assertThat(testPersona.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
	}

	@Test
	@Transactional
	public void createPersonaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = personaRepository.findAll().size();

		// Create the Persona with an existing ID
		persona.setId(1L);
		PersonaDTO personaDTO = personaMapper.toDto(persona);

		// An entity with an existing ID cannot be created, so this API call must fail
		restPersonaMockMvc.perform(post("/api/personas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(personaDTO))).andExpect(status().isBadRequest());

		// Validate the Persona in the database
		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkNombresIsRequired() throws Exception {
		int databaseSizeBeforeTest = personaRepository.findAll().size();
		// set the field null
		persona.setNombres(null);

		// Create the Persona, which fails.
		PersonaDTO personaDTO = personaMapper.toDto(persona);

		restPersonaMockMvc.perform(post("/api/personas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(personaDTO))).andExpect(status().isBadRequest());

		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkApellidosIsRequired() throws Exception {
		int databaseSizeBeforeTest = personaRepository.findAll().size();
		// set the field null
		persona.setApellidos(null);

		// Create the Persona, which fails.
		PersonaDTO personaDTO = personaMapper.toDto(persona);

		restPersonaMockMvc.perform(post("/api/personas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(personaDTO))).andExpect(status().isBadRequest());

		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllPersonas() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList
		restPersonaMockMvc.perform(get("/api/personas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
				.andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
				.andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
				.andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
				.andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));
	}

	@Test
	@Transactional
	public void getPersona() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get the persona
		restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(persona.getId().intValue()))
				.andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
				.andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
				.andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
				.andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
				.andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO))
				.andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO));
	}

	@Test
	@Transactional
	public void getPersonasByIdFiltering() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		Long id = persona.getId();

		defaultPersonaShouldBeFound("id.equals=" + id);
		defaultPersonaShouldNotBeFound("id.notEquals=" + id);

		defaultPersonaShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultPersonaShouldNotBeFound("id.greaterThan=" + id);

		defaultPersonaShouldBeFound("id.lessThanOrEqual=" + id);
		defaultPersonaShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllPersonasByCodigoIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where codigo equals to DEFAULT_CODIGO
		defaultPersonaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

		// Get all the personaList where codigo equals to UPDATED_CODIGO
		defaultPersonaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCodigoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where codigo not equals to DEFAULT_CODIGO
		defaultPersonaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

		// Get all the personaList where codigo not equals to UPDATED_CODIGO
		defaultPersonaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCodigoIsInShouldWork() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
		defaultPersonaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

		// Get all the personaList where codigo equals to UPDATED_CODIGO
		defaultPersonaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCodigoIsNullOrNotNull() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where codigo is not null
		defaultPersonaShouldBeFound("codigo.specified=true");

		// Get all the personaList where codigo is null
		defaultPersonaShouldNotBeFound("codigo.specified=false");
	}

	@Test
	@Transactional
	public void getAllPersonasByCodigoContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where codigo contains DEFAULT_CODIGO
		defaultPersonaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

		// Get all the personaList where codigo contains UPDATED_CODIGO
		defaultPersonaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCodigoNotContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where codigo does not contain DEFAULT_CODIGO
		defaultPersonaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

		// Get all the personaList where codigo does not contain UPDATED_CODIGO
		defaultPersonaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
	}

	@Test
	@Transactional
	public void getAllPersonasByNombresIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where nombres equals to DEFAULT_NOMBRES
		defaultPersonaShouldBeFound("nombres.equals=" + DEFAULT_NOMBRES);

		// Get all the personaList where nombres equals to UPDATED_NOMBRES
		defaultPersonaShouldNotBeFound("nombres.equals=" + UPDATED_NOMBRES);
	}

	@Test
	@Transactional
	public void getAllPersonasByNombresIsNotEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where nombres not equals to DEFAULT_NOMBRES
		defaultPersonaShouldNotBeFound("nombres.notEquals=" + DEFAULT_NOMBRES);

		// Get all the personaList where nombres not equals to UPDATED_NOMBRES
		defaultPersonaShouldBeFound("nombres.notEquals=" + UPDATED_NOMBRES);
	}

	@Test
	@Transactional
	public void getAllPersonasByNombresIsInShouldWork() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where nombres in DEFAULT_NOMBRES or UPDATED_NOMBRES
		defaultPersonaShouldBeFound("nombres.in=" + DEFAULT_NOMBRES + "," + UPDATED_NOMBRES);

		// Get all the personaList where nombres equals to UPDATED_NOMBRES
		defaultPersonaShouldNotBeFound("nombres.in=" + UPDATED_NOMBRES);
	}

	@Test
	@Transactional
	public void getAllPersonasByNombresIsNullOrNotNull() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where nombres is not null
		defaultPersonaShouldBeFound("nombres.specified=true");

		// Get all the personaList where nombres is null
		defaultPersonaShouldNotBeFound("nombres.specified=false");
	}

	@Test
	@Transactional
	public void getAllPersonasByNombresContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where nombres contains DEFAULT_NOMBRES
		defaultPersonaShouldBeFound("nombres.contains=" + DEFAULT_NOMBRES);

		// Get all the personaList where nombres contains UPDATED_NOMBRES
		defaultPersonaShouldNotBeFound("nombres.contains=" + UPDATED_NOMBRES);
	}

	@Test
	@Transactional
	public void getAllPersonasByNombresNotContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where nombres does not contain DEFAULT_NOMBRES
		defaultPersonaShouldNotBeFound("nombres.doesNotContain=" + DEFAULT_NOMBRES);

		// Get all the personaList where nombres does not contain UPDATED_NOMBRES
		defaultPersonaShouldBeFound("nombres.doesNotContain=" + UPDATED_NOMBRES);
	}

	@Test
	@Transactional
	public void getAllPersonasByApellidosIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where apellidos equals to DEFAULT_APELLIDOS
		defaultPersonaShouldBeFound("apellidos.equals=" + DEFAULT_APELLIDOS);

		// Get all the personaList where apellidos equals to UPDATED_APELLIDOS
		defaultPersonaShouldNotBeFound("apellidos.equals=" + UPDATED_APELLIDOS);
	}

	@Test
	@Transactional
	public void getAllPersonasByApellidosIsNotEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where apellidos not equals to DEFAULT_APELLIDOS
		defaultPersonaShouldNotBeFound("apellidos.notEquals=" + DEFAULT_APELLIDOS);

		// Get all the personaList where apellidos not equals to UPDATED_APELLIDOS
		defaultPersonaShouldBeFound("apellidos.notEquals=" + UPDATED_APELLIDOS);
	}

	@Test
	@Transactional
	public void getAllPersonasByApellidosIsInShouldWork() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where apellidos in DEFAULT_APELLIDOS or
		// UPDATED_APELLIDOS
		defaultPersonaShouldBeFound("apellidos.in=" + DEFAULT_APELLIDOS + "," + UPDATED_APELLIDOS);

		// Get all the personaList where apellidos equals to UPDATED_APELLIDOS
		defaultPersonaShouldNotBeFound("apellidos.in=" + UPDATED_APELLIDOS);
	}

	@Test
	@Transactional
	public void getAllPersonasByApellidosIsNullOrNotNull() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where apellidos is not null
		defaultPersonaShouldBeFound("apellidos.specified=true");

		// Get all the personaList where apellidos is null
		defaultPersonaShouldNotBeFound("apellidos.specified=false");
	}

	@Test
	@Transactional
	public void getAllPersonasByApellidosContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where apellidos contains DEFAULT_APELLIDOS
		defaultPersonaShouldBeFound("apellidos.contains=" + DEFAULT_APELLIDOS);

		// Get all the personaList where apellidos contains UPDATED_APELLIDOS
		defaultPersonaShouldNotBeFound("apellidos.contains=" + UPDATED_APELLIDOS);
	}

	@Test
	@Transactional
	public void getAllPersonasByApellidosNotContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where apellidos does not contain DEFAULT_APELLIDOS
		defaultPersonaShouldNotBeFound("apellidos.doesNotContain=" + DEFAULT_APELLIDOS);

		// Get all the personaList where apellidos does not contain UPDATED_APELLIDOS
		defaultPersonaShouldBeFound("apellidos.doesNotContain=" + UPDATED_APELLIDOS);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento equals to
		// DEFAULT_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento equals to
		// UPDATED_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento not equals to
		// DEFAULT_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.notEquals=" + DEFAULT_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento not equals to
		// UPDATED_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.notEquals=" + UPDATED_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsInShouldWork() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento in DEFAULT_FECHA_NACIMIENTO or
		// UPDATED_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento equals to
		// UPDATED_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsNullOrNotNull() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento is not null
		defaultPersonaShouldBeFound("fechaNacimiento.specified=true");

		// Get all the personaList where fechaNacimiento is null
		defaultPersonaShouldNotBeFound("fechaNacimiento.specified=false");
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento is greater than or equal to
		// DEFAULT_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.greaterThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento is greater than or equal to
		// UPDATED_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.greaterThanOrEqual=" + UPDATED_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsLessThanOrEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento is less than or equal to
		// DEFAULT_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.lessThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento is less than or equal to
		// SMALLER_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.lessThanOrEqual=" + SMALLER_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsLessThanSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento is less than
		// DEFAULT_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento is less than
		// UPDATED_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByFechaNacimientoIsGreaterThanSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where fechaNacimiento is greater than
		// DEFAULT_FECHA_NACIMIENTO
		defaultPersonaShouldNotBeFound("fechaNacimiento.greaterThan=" + DEFAULT_FECHA_NACIMIENTO);

		// Get all the personaList where fechaNacimiento is greater than
		// SMALLER_FECHA_NACIMIENTO
		defaultPersonaShouldBeFound("fechaNacimiento.greaterThan=" + SMALLER_FECHA_NACIMIENTO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCorreoElectronicoIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where correoElectronico equals to
		// DEFAULT_CORREO_ELECTRONICO
		defaultPersonaShouldBeFound("correoElectronico.equals=" + DEFAULT_CORREO_ELECTRONICO);

		// Get all the personaList where correoElectronico equals to
		// UPDATED_CORREO_ELECTRONICO
		defaultPersonaShouldNotBeFound("correoElectronico.equals=" + UPDATED_CORREO_ELECTRONICO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCorreoElectronicoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where correoElectronico not equals to
		// DEFAULT_CORREO_ELECTRONICO
		defaultPersonaShouldNotBeFound("correoElectronico.notEquals=" + DEFAULT_CORREO_ELECTRONICO);

		// Get all the personaList where correoElectronico not equals to
		// UPDATED_CORREO_ELECTRONICO
		defaultPersonaShouldBeFound("correoElectronico.notEquals=" + UPDATED_CORREO_ELECTRONICO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCorreoElectronicoIsInShouldWork() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where correoElectronico in DEFAULT_CORREO_ELECTRONICO
		// or UPDATED_CORREO_ELECTRONICO
		defaultPersonaShouldBeFound(
				"correoElectronico.in=" + DEFAULT_CORREO_ELECTRONICO + "," + UPDATED_CORREO_ELECTRONICO);

		// Get all the personaList where correoElectronico equals to
		// UPDATED_CORREO_ELECTRONICO
		defaultPersonaShouldNotBeFound("correoElectronico.in=" + UPDATED_CORREO_ELECTRONICO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCorreoElectronicoIsNullOrNotNull() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where correoElectronico is not null
		defaultPersonaShouldBeFound("correoElectronico.specified=true");

		// Get all the personaList where correoElectronico is null
		defaultPersonaShouldNotBeFound("correoElectronico.specified=false");
	}

	@Test
	@Transactional
	public void getAllPersonasByCorreoElectronicoContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where correoElectronico contains
		// DEFAULT_CORREO_ELECTRONICO
		defaultPersonaShouldBeFound("correoElectronico.contains=" + DEFAULT_CORREO_ELECTRONICO);

		// Get all the personaList where correoElectronico contains
		// UPDATED_CORREO_ELECTRONICO
		defaultPersonaShouldNotBeFound("correoElectronico.contains=" + UPDATED_CORREO_ELECTRONICO);
	}

	@Test
	@Transactional
	public void getAllPersonasByCorreoElectronicoNotContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where correoElectronico does not contain
		// DEFAULT_CORREO_ELECTRONICO
		defaultPersonaShouldNotBeFound("correoElectronico.doesNotContain=" + DEFAULT_CORREO_ELECTRONICO);

		// Get all the personaList where correoElectronico does not contain
		// UPDATED_CORREO_ELECTRONICO
		defaultPersonaShouldBeFound("correoElectronico.doesNotContain=" + UPDATED_CORREO_ELECTRONICO);
	}

	@Test
	@Transactional
	public void getAllPersonasByTelefonoIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where telefono equals to DEFAULT_TELEFONO
		defaultPersonaShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

		// Get all the personaList where telefono equals to UPDATED_TELEFONO
		defaultPersonaShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
	}

	@Test
	@Transactional
	public void getAllPersonasByTelefonoIsNotEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where telefono not equals to DEFAULT_TELEFONO
		defaultPersonaShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

		// Get all the personaList where telefono not equals to UPDATED_TELEFONO
		defaultPersonaShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
	}

	@Test
	@Transactional
	public void getAllPersonasByTelefonoIsInShouldWork() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where telefono in DEFAULT_TELEFONO or
		// UPDATED_TELEFONO
		defaultPersonaShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

		// Get all the personaList where telefono equals to UPDATED_TELEFONO
		defaultPersonaShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
	}

	@Test
	@Transactional
	public void getAllPersonasByTelefonoIsNullOrNotNull() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where telefono is not null
		defaultPersonaShouldBeFound("telefono.specified=true");

		// Get all the personaList where telefono is null
		defaultPersonaShouldNotBeFound("telefono.specified=false");
	}

	@Test
	@Transactional
	public void getAllPersonasByTelefonoContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where telefono contains DEFAULT_TELEFONO
		defaultPersonaShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

		// Get all the personaList where telefono contains UPDATED_TELEFONO
		defaultPersonaShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
	}

	@Test
	@Transactional
	public void getAllPersonasByTelefonoNotContainsSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		// Get all the personaList where telefono does not contain DEFAULT_TELEFONO
		defaultPersonaShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

		// Get all the personaList where telefono does not contain UPDATED_TELEFONO
		defaultPersonaShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
	}

	@Test
	@Transactional
	public void getAllPersonasByUsuarioIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);
		User usuario = UserResourceIT.createEntity(em);
		em.persist(usuario);
		em.flush();
		persona.setUsuario(usuario);
		personaRepository.saveAndFlush(persona);
		Long usuarioId = usuario.getId();

		// Get all the personaList where usuario equals to usuarioId
		defaultPersonaShouldBeFound("usuarioId.equals=" + usuarioId);

		// Get all the personaList where usuario equals to usuarioId + 1
		defaultPersonaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
	}

	@Test
	@Transactional
	public void getAllPersonasBySubscripcionesIsEqualToSomething() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);
		Subscripcion subscripciones = SubscripcionResourceIT.createEntity(em);
		em.persist(subscripciones);
		em.flush();
		persona.addSubscripciones(subscripciones);
		personaRepository.saveAndFlush(persona);
		Long subscripcionesId = subscripciones.getId();

		// Get all the personaList where subscripciones equals to subscripcionesId
		defaultPersonaShouldBeFound("subscripcionesId.equals=" + subscripcionesId);

		// Get all the personaList where subscripciones equals to subscripcionesId + 1
		defaultPersonaShouldNotBeFound("subscripcionesId.equals=" + (subscripcionesId + 1));
	}

	@Test
	@Transactional
	public void getAllPersonasByPaisIsEqualToSomething() throws Exception {
		// Get already existing entity
		Pais pais = persona.getPais();
		personaRepository.saveAndFlush(persona);
		Long paisId = pais.getId();

		// Get all the personaList where pais equals to paisId
		defaultPersonaShouldBeFound("paisId.equals=" + paisId);

		// Get all the personaList where pais equals to paisId + 1
		defaultPersonaShouldNotBeFound("paisId.equals=" + (paisId + 1));
	}

	@Test
	@Transactional
	public void getAllPersonasByCuentaIsEqualToSomething() throws Exception {
		// Get already existing entity
		Cuenta cuenta = persona.getCuenta();
		personaRepository.saveAndFlush(persona);
		Long cuentaId = cuenta.getId();

		// Get all the personaList where cuenta equals to cuentaId
		defaultPersonaShouldBeFound("cuentaId.equals=" + cuentaId);

		// Get all the personaList where cuenta equals to cuentaId + 1
		defaultPersonaShouldNotBeFound("cuentaId.equals=" + (cuentaId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultPersonaShouldBeFound(String filter) throws Exception {
		restPersonaMockMvc.perform(get("/api/personas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
				.andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
				.andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
				.andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
				.andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
				.andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
				.andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));

		// Check, that the count call also returns 1
		restPersonaMockMvc.perform(get("/api/personas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultPersonaShouldNotBeFound(String filter) throws Exception {
		restPersonaMockMvc.perform(get("/api/personas?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restPersonaMockMvc.perform(get("/api/personas/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingPersona() throws Exception {
		// Get the persona
		restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updatePersona() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		int databaseSizeBeforeUpdate = personaRepository.findAll().size();

		// Update the persona
		Persona updatedPersona = personaRepository.findById(persona.getId()).get();
		// Disconnect from session so that the updates on updatedPersona are not
		// directly saved in db
		em.detach(updatedPersona);
		updatedPersona.codigo(UPDATED_CODIGO).nombres(UPDATED_NOMBRES).apellidos(UPDATED_APELLIDOS)
				.fechaNacimiento(UPDATED_FECHA_NACIMIENTO).correoElectronico(UPDATED_CORREO_ELECTRONICO)
				.telefono(UPDATED_TELEFONO);
		PersonaDTO personaDTO = personaMapper.toDto(updatedPersona);

		restPersonaMockMvc.perform(put("/api/personas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(personaDTO))).andExpect(status().isOk());

		// Validate the Persona in the database
		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
		Persona testPersona = personaList.get(personaList.size() - 1);
		assertThat(testPersona.getCodigo()).isEqualTo(UPDATED_CODIGO);
		assertThat(testPersona.getNombres()).isEqualTo(UPDATED_NOMBRES);
		assertThat(testPersona.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
		assertThat(testPersona.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
		assertThat(testPersona.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
		assertThat(testPersona.getTelefono()).isEqualTo(UPDATED_TELEFONO);
	}

	@Test
	@Transactional
	public void updateNonExistingPersona() throws Exception {
		int databaseSizeBeforeUpdate = personaRepository.findAll().size();

		// Create the Persona
		PersonaDTO personaDTO = personaMapper.toDto(persona);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restPersonaMockMvc.perform(put("/api/personas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(personaDTO))).andExpect(status().isBadRequest());

		// Validate the Persona in the database
		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deletePersona() throws Exception {
		// Initialize the database
		personaRepository.saveAndFlush(persona);

		int databaseSizeBeforeDelete = personaRepository.findAll().size();

		// Delete the persona
		restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Persona> personaList = personaRepository.findAll();
		assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
