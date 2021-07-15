package com.uydevs.backoffice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.uydevs.backoffice.PlataformaBackofficeApp;
import com.uydevs.backoffice.domain.Contenido;
import com.uydevs.backoffice.domain.Cuenta;
import com.uydevs.backoffice.domain.Etiqueta;
import com.uydevs.backoffice.domain.Funcion;
import com.uydevs.backoffice.domain.Obra;
import com.uydevs.backoffice.domain.TipoDeObra;
import com.uydevs.backoffice.dto.domain.ObraDTO;
import com.uydevs.backoffice.repository.domain.ObraRepository;
import com.uydevs.backoffice.service.domain.ObraService;
import com.uydevs.backoffice.service.mapper.ObraMapper;
import com.uydevs.backoffice.web.rest.domain.ObraResource;

/**
 * Integration tests for the {@link ObraResource} REST controller.
 */
@SpringBootTest(classes = PlataformaBackofficeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ObraResourceIT {

	private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
	private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

	private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
	private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
	private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
	private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

	private static final byte[] DEFAULT_ICONO = TestUtil.createByteArray(1, "0");
	private static final byte[] UPDATED_ICONO = TestUtil.createByteArray(1, "1");
	private static final String DEFAULT_ICONO_CONTENT_TYPE = "image/jpg";
	private static final String UPDATED_ICONO_CONTENT_TYPE = "image/png";

	private static final String DEFAULT_PROTAGONISTAS = "AAAAAAAAAA";
	private static final String UPDATED_PROTAGONISTAS = "BBBBBBBBBB";

	private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
	private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

	private static final String DEFAULT_AUTORES = "AAAAAAAAAA";
	private static final String UPDATED_AUTORES = "BBBBBBBBBB";

	private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
	private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

	private static final String DEFAULT_DURACION = "AAAAAAAAAA";
	private static final String UPDATED_DURACION = "BBBBBBBBBB";

	@Autowired
	private ObraRepository obraRepository;

	@Mock
	private ObraRepository obraRepositoryMock;

	@Autowired
	private ObraMapper obraMapper;

	@Mock
	private ObraService obraServiceMock;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restObraMockMvc;

	private Obra obra;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Obra createEntity(EntityManager em) {
		Obra obra = new Obra().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION).imagen(DEFAULT_IMAGEN)
				.imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE).icono(DEFAULT_ICONO)
				.iconoContentType(DEFAULT_ICONO_CONTENT_TYPE).protagonistas(DEFAULT_PROTAGONISTAS)
				.direccion(DEFAULT_DIRECCION).autores(DEFAULT_AUTORES).fecha(DEFAULT_FECHA).duracion(DEFAULT_DURACION);
		// Add required entity
		TipoDeObra tipoDeObra;
		if (TestUtil.findAll(em, TipoDeObra.class).isEmpty()) {
			tipoDeObra = TipoDeObraResourceIT.createEntity(em);
			em.persist(tipoDeObra);
			em.flush();
		} else {
			tipoDeObra = TestUtil.findAll(em, TipoDeObra.class).get(0);
		}
		obra.setTipo(tipoDeObra);
		// Add required entity
		Cuenta cuenta;
		if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
			cuenta = CuentaResourceIT.createEntity(em);
			em.persist(cuenta);
			em.flush();
		} else {
			cuenta = TestUtil.findAll(em, Cuenta.class).get(0);
		}
		obra.setCuenta(cuenta);
		return obra;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Obra createUpdatedEntity(EntityManager em) {
		Obra obra = new Obra().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).imagen(UPDATED_IMAGEN)
				.imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE).icono(UPDATED_ICONO)
				.iconoContentType(UPDATED_ICONO_CONTENT_TYPE).protagonistas(UPDATED_PROTAGONISTAS)
				.direccion(UPDATED_DIRECCION).autores(UPDATED_AUTORES).fecha(UPDATED_FECHA).duracion(UPDATED_DURACION);
		// Add required entity
		TipoDeObra tipoDeObra;
		if (TestUtil.findAll(em, TipoDeObra.class).isEmpty()) {
			tipoDeObra = TipoDeObraResourceIT.createUpdatedEntity(em);
			em.persist(tipoDeObra);
			em.flush();
		} else {
			tipoDeObra = TestUtil.findAll(em, TipoDeObra.class).get(0);
		}
		obra.setTipo(tipoDeObra);
		// Add required entity
		Cuenta cuenta;
		if (TestUtil.findAll(em, Cuenta.class).isEmpty()) {
			cuenta = CuentaResourceIT.createUpdatedEntity(em);
			em.persist(cuenta);
			em.flush();
		} else {
			cuenta = TestUtil.findAll(em, Cuenta.class).get(0);
		}
		obra.setCuenta(cuenta);
		return obra;
	}

	@BeforeEach
	public void initTest() {
		obra = createEntity(em);
	}

	@Test
	@Transactional
	public void createObra() throws Exception {
		int databaseSizeBeforeCreate = obraRepository.findAll().size();
		// Create the Obra
		ObraDTO obraDTO = obraMapper.toDto(obra);
		restObraMockMvc.perform(post("/api/obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(obraDTO))).andExpect(status().isCreated());

		// Validate the Obra in the database
		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeCreate + 1);
		Obra testObra = obraList.get(obraList.size() - 1);
		assertThat(testObra.getNombre()).isEqualTo(DEFAULT_NOMBRE);
		assertThat(testObra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
		assertThat(testObra.getImagen()).isEqualTo(DEFAULT_IMAGEN);
		assertThat(testObra.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
		assertThat(testObra.getIcono()).isEqualTo(DEFAULT_ICONO);
		assertThat(testObra.getIconoContentType()).isEqualTo(DEFAULT_ICONO_CONTENT_TYPE);
		assertThat(testObra.getProtagonistas()).isEqualTo(DEFAULT_PROTAGONISTAS);
		assertThat(testObra.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
		assertThat(testObra.getAutores()).isEqualTo(DEFAULT_AUTORES);
		assertThat(testObra.getFecha()).isEqualTo(DEFAULT_FECHA);
		assertThat(testObra.getDuracion()).isEqualTo(DEFAULT_DURACION);
	}

	@Test
	@Transactional
	public void createObraWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = obraRepository.findAll().size();

		// Create the Obra with an existing ID
		obra.setId(1L);
		ObraDTO obraDTO = obraMapper.toDto(obra);

		// An entity with an existing ID cannot be created, so this API call must fail
		restObraMockMvc.perform(post("/api/obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(obraDTO))).andExpect(status().isBadRequest());

		// Validate the Obra in the database
		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkNombreIsRequired() throws Exception {
		int databaseSizeBeforeTest = obraRepository.findAll().size();
		// set the field null
		obra.setNombre(null);

		// Create the Obra, which fails.
		ObraDTO obraDTO = obraMapper.toDto(obra);

		restObraMockMvc.perform(post("/api/obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(obraDTO))).andExpect(status().isBadRequest());

		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkFechaIsRequired() throws Exception {
		int databaseSizeBeforeTest = obraRepository.findAll().size();
		// set the field null
		obra.setFecha(null);

		// Create the Obra, which fails.
		ObraDTO obraDTO = obraMapper.toDto(obra);

		restObraMockMvc.perform(post("/api/obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(obraDTO))).andExpect(status().isBadRequest());

		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllObras() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList
		restObraMockMvc.perform(get("/api/obras?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
				.andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
				.andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
				.andExpect(jsonPath("$.[*].iconoContentType").value(hasItem(DEFAULT_ICONO_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].icono").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICONO))))
				.andExpect(jsonPath("$.[*].protagonistas").value(hasItem(DEFAULT_PROTAGONISTAS)))
				.andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
				.andExpect(jsonPath("$.[*].autores").value(hasItem(DEFAULT_AUTORES)))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
				.andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)));
	}

	public void getAllObrasWithEagerRelationshipsIsEnabled() throws Exception {
		when(obraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl<ObraDTO>(new ArrayList<>()));

		restObraMockMvc.perform(get("/api/obras?eagerload=true")).andExpect(status().isOk());

		verify(obraServiceMock, times(1)).findAllWithEagerRelationships(any());
	}

	public void getAllObrasWithEagerRelationshipsIsNotEnabled() throws Exception {
		when(obraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl<ObraDTO>(new ArrayList<>()));

		restObraMockMvc.perform(get("/api/obras?eagerload=true")).andExpect(status().isOk());

		verify(obraServiceMock, times(1)).findAllWithEagerRelationships(any());
	}

	@Test
	@Transactional
	public void getObra() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get the obra
		restObraMockMvc.perform(get("/api/obras/{id}", obra.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(obra.getId().intValue()))
				.andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
				.andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
				.andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
				.andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
				.andExpect(jsonPath("$.iconoContentType").value(DEFAULT_ICONO_CONTENT_TYPE))
				.andExpect(jsonPath("$.icono").value(Base64Utils.encodeToString(DEFAULT_ICONO)))
				.andExpect(jsonPath("$.protagonistas").value(DEFAULT_PROTAGONISTAS))
				.andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
				.andExpect(jsonPath("$.autores").value(DEFAULT_AUTORES))
				.andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
				.andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION));
	}

	@Test
	@Transactional
	public void getObrasByIdFiltering() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		Long id = obra.getId();

		defaultObraShouldBeFound("id.equals=" + id);
		defaultObraShouldNotBeFound("id.notEquals=" + id);

		defaultObraShouldBeFound("id.greaterThanOrEqual=" + id);
		defaultObraShouldNotBeFound("id.greaterThan=" + id);

		defaultObraShouldBeFound("id.lessThanOrEqual=" + id);
		defaultObraShouldNotBeFound("id.lessThan=" + id);
	}

	@Test
	@Transactional
	public void getAllObrasByNombreIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where nombre equals to DEFAULT_NOMBRE
		defaultObraShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

		// Get all the obraList where nombre equals to UPDATED_NOMBRE
		defaultObraShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllObrasByNombreIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where nombre not equals to DEFAULT_NOMBRE
		defaultObraShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

		// Get all the obraList where nombre not equals to UPDATED_NOMBRE
		defaultObraShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllObrasByNombreIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
		defaultObraShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

		// Get all the obraList where nombre equals to UPDATED_NOMBRE
		defaultObraShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllObrasByNombreIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where nombre is not null
		defaultObraShouldBeFound("nombre.specified=true");

		// Get all the obraList where nombre is null
		defaultObraShouldNotBeFound("nombre.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByNombreContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where nombre contains DEFAULT_NOMBRE
		defaultObraShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

		// Get all the obraList where nombre contains UPDATED_NOMBRE
		defaultObraShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllObrasByNombreNotContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where nombre does not contain DEFAULT_NOMBRE
		defaultObraShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

		// Get all the obraList where nombre does not contain UPDATED_NOMBRE
		defaultObraShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
	}

	@Test
	@Transactional
	public void getAllObrasByDescripcionIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where descripcion equals to DEFAULT_DESCRIPCION
		defaultObraShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

		// Get all the obraList where descripcion equals to UPDATED_DESCRIPCION
		defaultObraShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDescripcionIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where descripcion not equals to DEFAULT_DESCRIPCION
		defaultObraShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

		// Get all the obraList where descripcion not equals to UPDATED_DESCRIPCION
		defaultObraShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDescripcionIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where descripcion in DEFAULT_DESCRIPCION or
		// UPDATED_DESCRIPCION
		defaultObraShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

		// Get all the obraList where descripcion equals to UPDATED_DESCRIPCION
		defaultObraShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDescripcionIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where descripcion is not null
		defaultObraShouldBeFound("descripcion.specified=true");

		// Get all the obraList where descripcion is null
		defaultObraShouldNotBeFound("descripcion.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByDescripcionContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where descripcion contains DEFAULT_DESCRIPCION
		defaultObraShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

		// Get all the obraList where descripcion contains UPDATED_DESCRIPCION
		defaultObraShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDescripcionNotContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where descripcion does not contain DEFAULT_DESCRIPCION
		defaultObraShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

		// Get all the obraList where descripcion does not contain UPDATED_DESCRIPCION
		defaultObraShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
	}

	@Test
	@Transactional
	public void getAllObrasByProtagonistasIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where protagonistas equals to DEFAULT_PROTAGONISTAS
		defaultObraShouldBeFound("protagonistas.equals=" + DEFAULT_PROTAGONISTAS);

		// Get all the obraList where protagonistas equals to UPDATED_PROTAGONISTAS
		defaultObraShouldNotBeFound("protagonistas.equals=" + UPDATED_PROTAGONISTAS);
	}

	@Test
	@Transactional
	public void getAllObrasByProtagonistasIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where protagonistas not equals to DEFAULT_PROTAGONISTAS
		defaultObraShouldNotBeFound("protagonistas.notEquals=" + DEFAULT_PROTAGONISTAS);

		// Get all the obraList where protagonistas not equals to UPDATED_PROTAGONISTAS
		defaultObraShouldBeFound("protagonistas.notEquals=" + UPDATED_PROTAGONISTAS);
	}

	@Test
	@Transactional
	public void getAllObrasByProtagonistasIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where protagonistas in DEFAULT_PROTAGONISTAS or
		// UPDATED_PROTAGONISTAS
		defaultObraShouldBeFound("protagonistas.in=" + DEFAULT_PROTAGONISTAS + "," + UPDATED_PROTAGONISTAS);

		// Get all the obraList where protagonistas equals to UPDATED_PROTAGONISTAS
		defaultObraShouldNotBeFound("protagonistas.in=" + UPDATED_PROTAGONISTAS);
	}

	@Test
	@Transactional
	public void getAllObrasByProtagonistasIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where protagonistas is not null
		defaultObraShouldBeFound("protagonistas.specified=true");

		// Get all the obraList where protagonistas is null
		defaultObraShouldNotBeFound("protagonistas.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByProtagonistasContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where protagonistas contains DEFAULT_PROTAGONISTAS
		defaultObraShouldBeFound("protagonistas.contains=" + DEFAULT_PROTAGONISTAS);

		// Get all the obraList where protagonistas contains UPDATED_PROTAGONISTAS
		defaultObraShouldNotBeFound("protagonistas.contains=" + UPDATED_PROTAGONISTAS);
	}

	@Test
	@Transactional
	public void getAllObrasByProtagonistasNotContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where protagonistas does not contain
		// DEFAULT_PROTAGONISTAS
		defaultObraShouldNotBeFound("protagonistas.doesNotContain=" + DEFAULT_PROTAGONISTAS);

		// Get all the obraList where protagonistas does not contain
		// UPDATED_PROTAGONISTAS
		defaultObraShouldBeFound("protagonistas.doesNotContain=" + UPDATED_PROTAGONISTAS);
	}

	@Test
	@Transactional
	public void getAllObrasByDireccionIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where direccion equals to DEFAULT_DIRECCION
		defaultObraShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

		// Get all the obraList where direccion equals to UPDATED_DIRECCION
		defaultObraShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDireccionIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where direccion not equals to DEFAULT_DIRECCION
		defaultObraShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

		// Get all the obraList where direccion not equals to UPDATED_DIRECCION
		defaultObraShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDireccionIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where direccion in DEFAULT_DIRECCION or
		// UPDATED_DIRECCION
		defaultObraShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

		// Get all the obraList where direccion equals to UPDATED_DIRECCION
		defaultObraShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDireccionIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where direccion is not null
		defaultObraShouldBeFound("direccion.specified=true");

		// Get all the obraList where direccion is null
		defaultObraShouldNotBeFound("direccion.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByDireccionContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where direccion contains DEFAULT_DIRECCION
		defaultObraShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

		// Get all the obraList where direccion contains UPDATED_DIRECCION
		defaultObraShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
	}

	@Test
	@Transactional
	public void getAllObrasByDireccionNotContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where direccion does not contain DEFAULT_DIRECCION
		defaultObraShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

		// Get all the obraList where direccion does not contain UPDATED_DIRECCION
		defaultObraShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
	}

	@Test
	@Transactional
	public void getAllObrasByAutoresIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where autores equals to DEFAULT_AUTORES
		defaultObraShouldBeFound("autores.equals=" + DEFAULT_AUTORES);

		// Get all the obraList where autores equals to UPDATED_AUTORES
		defaultObraShouldNotBeFound("autores.equals=" + UPDATED_AUTORES);
	}

	@Test
	@Transactional
	public void getAllObrasByAutoresIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where autores not equals to DEFAULT_AUTORES
		defaultObraShouldNotBeFound("autores.notEquals=" + DEFAULT_AUTORES);

		// Get all the obraList where autores not equals to UPDATED_AUTORES
		defaultObraShouldBeFound("autores.notEquals=" + UPDATED_AUTORES);
	}

	@Test
	@Transactional
	public void getAllObrasByAutoresIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where autores in DEFAULT_AUTORES or UPDATED_AUTORES
		defaultObraShouldBeFound("autores.in=" + DEFAULT_AUTORES + "," + UPDATED_AUTORES);

		// Get all the obraList where autores equals to UPDATED_AUTORES
		defaultObraShouldNotBeFound("autores.in=" + UPDATED_AUTORES);
	}

	@Test
	@Transactional
	public void getAllObrasByAutoresIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where autores is not null
		defaultObraShouldBeFound("autores.specified=true");

		// Get all the obraList where autores is null
		defaultObraShouldNotBeFound("autores.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByAutoresContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where autores contains DEFAULT_AUTORES
		defaultObraShouldBeFound("autores.contains=" + DEFAULT_AUTORES);

		// Get all the obraList where autores contains UPDATED_AUTORES
		defaultObraShouldNotBeFound("autores.contains=" + UPDATED_AUTORES);
	}

	@Test
	@Transactional
	public void getAllObrasByAutoresNotContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where autores does not contain DEFAULT_AUTORES
		defaultObraShouldNotBeFound("autores.doesNotContain=" + DEFAULT_AUTORES);

		// Get all the obraList where autores does not contain UPDATED_AUTORES
		defaultObraShouldBeFound("autores.doesNotContain=" + UPDATED_AUTORES);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha equals to DEFAULT_FECHA
		defaultObraShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

		// Get all the obraList where fecha equals to UPDATED_FECHA
		defaultObraShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha not equals to DEFAULT_FECHA
		defaultObraShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

		// Get all the obraList where fecha not equals to UPDATED_FECHA
		defaultObraShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha in DEFAULT_FECHA or UPDATED_FECHA
		defaultObraShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

		// Get all the obraList where fecha equals to UPDATED_FECHA
		defaultObraShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha is not null
		defaultObraShouldBeFound("fecha.specified=true");

		// Get all the obraList where fecha is null
		defaultObraShouldNotBeFound("fecha.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha is greater than or equal to DEFAULT_FECHA
		defaultObraShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

		// Get all the obraList where fecha is greater than or equal to UPDATED_FECHA
		defaultObraShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsLessThanOrEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha is less than or equal to DEFAULT_FECHA
		defaultObraShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

		// Get all the obraList where fecha is less than or equal to SMALLER_FECHA
		defaultObraShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsLessThanSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha is less than DEFAULT_FECHA
		defaultObraShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

		// Get all the obraList where fecha is less than UPDATED_FECHA
		defaultObraShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByFechaIsGreaterThanSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where fecha is greater than DEFAULT_FECHA
		defaultObraShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

		// Get all the obraList where fecha is greater than SMALLER_FECHA
		defaultObraShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
	}

	@Test
	@Transactional
	public void getAllObrasByDuracionIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where duracion equals to DEFAULT_DURACION
		defaultObraShouldBeFound("duracion.equals=" + DEFAULT_DURACION);

		// Get all the obraList where duracion equals to UPDATED_DURACION
		defaultObraShouldNotBeFound("duracion.equals=" + UPDATED_DURACION);
	}

	@Test
	@Transactional
	public void getAllObrasByDuracionIsNotEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where duracion not equals to DEFAULT_DURACION
		defaultObraShouldNotBeFound("duracion.notEquals=" + DEFAULT_DURACION);

		// Get all the obraList where duracion not equals to UPDATED_DURACION
		defaultObraShouldBeFound("duracion.notEquals=" + UPDATED_DURACION);
	}

	@Test
	@Transactional
	public void getAllObrasByDuracionIsInShouldWork() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where duracion in DEFAULT_DURACION or UPDATED_DURACION
		defaultObraShouldBeFound("duracion.in=" + DEFAULT_DURACION + "," + UPDATED_DURACION);

		// Get all the obraList where duracion equals to UPDATED_DURACION
		defaultObraShouldNotBeFound("duracion.in=" + UPDATED_DURACION);
	}

	@Test
	@Transactional
	public void getAllObrasByDuracionIsNullOrNotNull() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where duracion is not null
		defaultObraShouldBeFound("duracion.specified=true");

		// Get all the obraList where duracion is null
		defaultObraShouldNotBeFound("duracion.specified=false");
	}

	@Test
	@Transactional
	public void getAllObrasByDuracionContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where duracion contains DEFAULT_DURACION
		defaultObraShouldBeFound("duracion.contains=" + DEFAULT_DURACION);

		// Get all the obraList where duracion contains UPDATED_DURACION
		defaultObraShouldNotBeFound("duracion.contains=" + UPDATED_DURACION);
	}

	@Test
	@Transactional
	public void getAllObrasByDuracionNotContainsSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		// Get all the obraList where duracion does not contain DEFAULT_DURACION
		defaultObraShouldNotBeFound("duracion.doesNotContain=" + DEFAULT_DURACION);

		// Get all the obraList where duracion does not contain UPDATED_DURACION
		defaultObraShouldBeFound("duracion.doesNotContain=" + UPDATED_DURACION);
	}

	@Test
	@Transactional
	public void getAllObrasByFuncionesIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);
		Funcion funciones = FuncionResourceIT.createEntity(em);
		em.persist(funciones);
		em.flush();
		obra.addFunciones(funciones);
		obraRepository.saveAndFlush(obra);
		Long funcionesId = funciones.getId();

		// Get all the obraList where funciones equals to funcionesId
		defaultObraShouldBeFound("funcionesId.equals=" + funcionesId);

		// Get all the obraList where funciones equals to funcionesId + 1
		defaultObraShouldNotBeFound("funcionesId.equals=" + (funcionesId + 1));
	}

	@Test
	@Transactional
	public void getAllObrasByContenidosIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);
		Contenido contenidos = ContenidoResourceIT.createEntity(em);
		em.persist(contenidos);
		em.flush();
		obra.addContenidos(contenidos);
		obraRepository.saveAndFlush(obra);
		Long contenidosId = contenidos.getId();

		// Get all the obraList where contenidos equals to contenidosId
		defaultObraShouldBeFound("contenidosId.equals=" + contenidosId);

		// Get all the obraList where contenidos equals to contenidosId + 1
		defaultObraShouldNotBeFound("contenidosId.equals=" + (contenidosId + 1));
	}

	@Test
	@Transactional
	public void getAllObrasByEtiquetasIsEqualToSomething() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);
		Etiqueta etiquetas = EtiquetaResourceIT.createEntity(em);
		em.persist(etiquetas);
		em.flush();
		obra.addEtiquetas(etiquetas);
		obraRepository.saveAndFlush(obra);
		Long etiquetasId = etiquetas.getId();

		// Get all the obraList where etiquetas equals to etiquetasId
		defaultObraShouldBeFound("etiquetasId.equals=" + etiquetasId);

		// Get all the obraList where etiquetas equals to etiquetasId + 1
		defaultObraShouldNotBeFound("etiquetasId.equals=" + (etiquetasId + 1));
	}

	@Test
	@Transactional
	public void getAllObrasByTipoIsEqualToSomething() throws Exception {
		// Get already existing entity
		TipoDeObra tipo = obra.getTipo();
		obraRepository.saveAndFlush(obra);
		Long tipoId = tipo.getId();

		// Get all the obraList where tipo equals to tipoId
		defaultObraShouldBeFound("tipoId.equals=" + tipoId);

		// Get all the obraList where tipo equals to tipoId + 1
		defaultObraShouldNotBeFound("tipoId.equals=" + (tipoId + 1));
	}

	@Test
	@Transactional
	public void getAllObrasByCuentaIsEqualToSomething() throws Exception {
		// Get already existing entity
		Cuenta cuenta = obra.getCuenta();
		obraRepository.saveAndFlush(obra);
		Long cuentaId = cuenta.getId();

		// Get all the obraList where cuenta equals to cuentaId
		defaultObraShouldBeFound("cuentaId.equals=" + cuentaId);

		// Get all the obraList where cuenta equals to cuentaId + 1
		defaultObraShouldNotBeFound("cuentaId.equals=" + (cuentaId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 */
	private void defaultObraShouldBeFound(String filter) throws Exception {
		restObraMockMvc.perform(get("/api/obras?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
				.andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
				.andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
				.andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
				.andExpect(jsonPath("$.[*].iconoContentType").value(hasItem(DEFAULT_ICONO_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].icono").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICONO))))
				.andExpect(jsonPath("$.[*].protagonistas").value(hasItem(DEFAULT_PROTAGONISTAS)))
				.andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
				.andExpect(jsonPath("$.[*].autores").value(hasItem(DEFAULT_AUTORES)))
				.andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
				.andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)));

		// Check, that the count call also returns 1
		restObraMockMvc.perform(get("/api/obras/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 */
	private void defaultObraShouldNotBeFound(String filter) throws Exception {
		restObraMockMvc.perform(get("/api/obras?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restObraMockMvc.perform(get("/api/obras/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("0"));
	}

	@Test
	@Transactional
	public void getNonExistingObra() throws Exception {
		// Get the obra
		restObraMockMvc.perform(get("/api/obras/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateObra() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		int databaseSizeBeforeUpdate = obraRepository.findAll().size();

		// Update the obra
		Obra updatedObra = obraRepository.findById(obra.getId()).get();
		// Disconnect from session so that the updates on updatedObra are not directly
		// saved in db
		em.detach(updatedObra);
		updatedObra.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).imagen(UPDATED_IMAGEN)
				.imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE).icono(UPDATED_ICONO)
				.iconoContentType(UPDATED_ICONO_CONTENT_TYPE).protagonistas(UPDATED_PROTAGONISTAS)
				.direccion(UPDATED_DIRECCION).autores(UPDATED_AUTORES).fecha(UPDATED_FECHA).duracion(UPDATED_DURACION);
		ObraDTO obraDTO = obraMapper.toDto(updatedObra);

		restObraMockMvc.perform(put("/api/obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(obraDTO))).andExpect(status().isOk());

		// Validate the Obra in the database
		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
		Obra testObra = obraList.get(obraList.size() - 1);
		assertThat(testObra.getNombre()).isEqualTo(UPDATED_NOMBRE);
		assertThat(testObra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
		assertThat(testObra.getImagen()).isEqualTo(UPDATED_IMAGEN);
		assertThat(testObra.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
		assertThat(testObra.getIcono()).isEqualTo(UPDATED_ICONO);
		assertThat(testObra.getIconoContentType()).isEqualTo(UPDATED_ICONO_CONTENT_TYPE);
		assertThat(testObra.getProtagonistas()).isEqualTo(UPDATED_PROTAGONISTAS);
		assertThat(testObra.getDireccion()).isEqualTo(UPDATED_DIRECCION);
		assertThat(testObra.getAutores()).isEqualTo(UPDATED_AUTORES);
		assertThat(testObra.getFecha()).isEqualTo(UPDATED_FECHA);
		assertThat(testObra.getDuracion()).isEqualTo(UPDATED_DURACION);
	}

	@Test
	@Transactional
	public void updateNonExistingObra() throws Exception {
		int databaseSizeBeforeUpdate = obraRepository.findAll().size();

		// Create the Obra
		ObraDTO obraDTO = obraMapper.toDto(obra);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restObraMockMvc.perform(put("/api/obras").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(obraDTO))).andExpect(status().isBadRequest());

		// Validate the Obra in the database
		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteObra() throws Exception {
		// Initialize the database
		obraRepository.saveAndFlush(obra);

		int databaseSizeBeforeDelete = obraRepository.findAll().size();

		// Delete the obra
		restObraMockMvc.perform(delete("/api/obras/{id}", obra.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Obra> obraList = obraRepository.findAll();
		assertThat(obraList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
