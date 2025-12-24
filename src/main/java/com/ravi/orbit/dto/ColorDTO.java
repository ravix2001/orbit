package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorDTO {

    private Long id;

    private String color;

    private boolean isAvailable;

}
