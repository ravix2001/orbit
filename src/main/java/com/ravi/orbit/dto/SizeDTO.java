package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeDTO {

    private Long id;

    private String size;

    private boolean isAvailable;

}
