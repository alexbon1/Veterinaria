package pack1;

import javax.persistence.*;
import java.util.List;

@Entity
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fecha;
    private String motivo;
    private String diagnostico;

    @ManyToOne
    @JoinColumn(name = "mascota_id")  // Ajusta el nombre de la columna según tu esquema de base de datos
    private Mascota mascota;
    // Getters and setters
}