package es.iesjandula.reaktor.issues_server.entities;

import java.util.Date;

import es.iesjandula.reaktor.issues_server.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidencia")
public class Incidencia 
{
	@Id
	@Column(length = 10)
	@GeneratedValue
	private Long id;
	
	@Column(length = 10, nullable = false)
    private String correo;
	
	@Column(length = 10, nullable = false)
    private String aula;
	
	@Column(length = 10, nullable = false)
    private String nombreProfesor;
	
	@Column(length = 10)
	private String estado;
	
	@Column(nullable = false)
	private Date fechaIncidencia;
	
	@Column(length = 10, nullable = false)
    private String descripcion;
}
