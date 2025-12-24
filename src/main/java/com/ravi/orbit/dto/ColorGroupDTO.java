package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorGroupDTO {

    private Long id;

    private String colorGroup;

    private List<ColorDTO> colors;

}
