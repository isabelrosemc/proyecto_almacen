import com.ms.proveedores.dto.ProveedorRequestDTO;
import com.ms.proveedores.dto.ProveedorResponseDTO;
import com.ms.proveedores.model.Proveedor;

public class ProveedorMapper {

    public static Proveedor toEntity(ProveedorRequestDTO dto) {

        return Proveedor.builder()
                .razonSocial(dto.getRazonSocial())
                .rut(dto.getRut())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .estado(dto.getEstado())
                .build();
    }

    public static ProveedorResponseDTO toDTO(Proveedor proveedor) {

        return ProveedorResponseDTO.builder()
                .id(proveedor.getId())
                .razonSocial(proveedor.getRazonSocial())
                .rut(proveedor.getRut())
                .email(proveedor.getEmail())
                .telefono(proveedor.getTelefono())
                .direccion(proveedor.getDireccion())
                .estado(proveedor.getEstado())
                .fechaCreacion(proveedor.getFechaCreacion())
                .build();
    }
}