package pe.limatambo.dto;

import java.util.Date;
import lombok.*;

@Data
public class PedidoDTO {

    private Integer codigo;
    private String descripcion;
    private String cliente;
    private Date fechaPedido;
    private Date fechaEntrega;
    private String direccion;

    public PedidoDTO() {
    }
    
}
