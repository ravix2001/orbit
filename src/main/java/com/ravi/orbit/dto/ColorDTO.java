package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorDTO {

    public ColorDTO(Long id, String color, boolean isAvailable) {
        this.id = id;
        this.color = color;
        this.isAvailable = isAvailable;
    }

    private Long id;

    private String color;

    private boolean isAvailable;

}
