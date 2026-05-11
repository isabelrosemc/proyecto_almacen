
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorRequestDTO {

    @NotBlank(message = "La razon social es obligatoria")
    @Size(min = 3, max = 150)
    private String razonSocial;

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 20)
    private String rut;

    @Email(message = "Formato email invalido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    private Boolean estado;
}