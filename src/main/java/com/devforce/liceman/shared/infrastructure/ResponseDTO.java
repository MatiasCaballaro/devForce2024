package com.devforce.liceman.shared.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private boolean ok;

    private String mensaje;

    private Object contenido;

}
