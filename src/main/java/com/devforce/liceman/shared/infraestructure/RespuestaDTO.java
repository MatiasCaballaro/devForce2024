package com.devforce.liceman.shared.infraestructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDTO {
    private boolean ok;
    private String mensaje;
    private Object contenido;
}
