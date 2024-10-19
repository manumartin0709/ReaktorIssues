package es.iesjandula.reaktor.issues_server.rest;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import es.iesjandula.reaktor.issues_server.entities.Incidencia;
import es.iesjandula.reaktor.issues_server.exception.IncidenciaError;
import es.iesjandula.reaktor.issues_server.utils.Constants;
import es.iesjandula.reaktor.issues_serverer.repository.IncidenciaRepository;


@RequestMapping(value = "/incidencias", produces = {"application/json"})
@RestController
public class RestHandler 
{
	@Autowired
	private IncidenciaRepository incidenciaRepository;
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	Constants constants = new Constants();
	
	@RequestMapping(method = RequestMethod.POST, value = "/subir")
	public ResponseEntity<?> addIncidencia(
										@RequestParam(value="aula", required=true) String aula,
									    @RequestParam(value="nombreProfesor", required=true) String nombreProfesor,
									    @RequestParam(value="fechaIncidencia", required=true) Date fechaIncidencia,
									    @RequestParam(value="descripcion", required=true) String descripcion,
									    @RequestHeader(value="numero1", required=true) final String correo)
	{
		try
		{
			Incidencia incidencia = new Incidencia();
			
			
			incidencia.setAula(aula);
            incidencia.setNombreProfesor(nombreProfesor);
            incidencia.setEstado(constants.STATE_TODO);
            incidencia.setFechaIncidencia(fechaIncidencia);
            incidencia.setDescripcion(descripcion);
			
			this.incidenciaRepository.saveAndFlush(incidencia);
			return ResponseEntity.ok().body("Incidencia notificada correctamente") ;
		}
		catch(Exception exception)
		{
			String message = "No se ha podido subir la incidencia";
			LOGGER.error(message, exception);
			return ResponseEntity.status(400).body(exception) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/actualizar")
	public ResponseEntity<?> actualizarIncidencia(
													@RequestParam(value="id", required=true) Long id,
													@RequestBody(required=true) final String descripcion
			
													)
	{
		try
		{
			Optional<Incidencia> incidencia = this.incidenciaRepository.findById(id);
			
			if(incidencia.isPresent())
			{
				    Incidencia incidencia2 = incidencia.get();
		            // Actualizar la descripción
		            incidencia2.setDescripcion(descripcion);
		            // Guardar los cambios
		            this.incidenciaRepository.saveAndFlush(incidencia2);
		            return ResponseEntity.ok().body("Incidencia notificada correctamente");
			}
			else
			{
				return ResponseEntity.status(400).body("Incidencia no encontrada") ;
			}
			
		}
		catch(Exception exception)
		{
			String message = "No se ha podido subir la incidencia";
			LOGGER.error(message, exception);
			return ResponseEntity.status(400).body(exception) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/cancerlar")
	public ResponseEntity<?> borrarIncidencia(
													@RequestParam(value="id", required=true) Long id,
													@RequestParam(value="nombreProfesor", required=true) String nombreProfesor
											)
	{
		try 
		{
			Optional<Incidencia> incidencia = this.incidenciaRepository.findById(id);
			
			if(incidencia.isPresent())
			{
				    Incidencia incidencia2 = incidencia.get();
				    
		            if(nombreProfesor.equals("TDE"))
		            {
		            	incidencia2.setEstado(constants.STATE_CANCELED_BY_TDE);
		            }
		            incidencia2.setEstado(constants.STATE_CANCELED_BY_USER);
		            // Guardar los cambios
		            this.incidenciaRepository.saveAndFlush(incidencia2);
		            return ResponseEntity.ok().body("Incidencia notificada correctamente");
			}
			else
			{
				return ResponseEntity.status(400).body("Incidencia no encontrada") ;
			}
			
			
			
		} 
		catch (Exception exception) 
		{
			String message = "No se ha podido subir la incidencia";
			LOGGER.error(message, exception);
			return ResponseEntity.status(400).body(exception) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/filtrar", produces = "application/json"	)
	public ResponseEntity<?> filtrarIncidencia( @RequestParam(value="estado", required=false) String estado
									    )
	{
		try 
		{
			
			List<Incidencia>listaFiltrada = new ArrayList<Incidencia>();
			List<Incidencia>listaTotal= this.incidenciaRepository.findAll();
			 if (estado.equals(constants.STATE_CANCELED_BY_TDE) ||
			     estado.equals(constants.STATE_CANCELED_BY_USER) ||
			     estado.equals(constants.STATE_DONE) ||
			     estado.equals(constants.STATE_ERROR) ||
			     estado.equals(constants.STATE_TODO)) 
			 {
				filtrate(estado, listaFiltrada, listaTotal);
		        
		        return ResponseEntity.ok().body(listaFiltrada);
			 }
			 else
			 {
				 return ResponseEntity.status(400).body("No existe ningun estado llamado" + estado) ;
			 }
			
		}
		catch (Exception exception) 
		{
			String message = "No se ha podido subir la incidencia";
			LOGGER.error(message, exception);
			return ResponseEntity.status(400).body(exception) ;
		}
		
	}

	public void filtrate(String estado, List<Incidencia> listaFiltrada, List<Incidencia> listaTotal) 
	{
		
		for(Incidencia incidencia: listaTotal)
		{
				if(incidencia.getEstado().equals(estado))
				{
					listaFiltrada.add(incidencia);
				}
		}
			
		
	}
}
