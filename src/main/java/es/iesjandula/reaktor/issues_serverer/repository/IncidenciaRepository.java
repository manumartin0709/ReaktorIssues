package es.iesjandula.reaktor.issues_serverer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.issues_server.entities.Incidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long>
{

}
