package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeDTO {

    public SizeDTO(Long id, String size, boolean isAvailable) {
        this.id = id;
        this.size = size;
        this.isAvailable = isAvailable;
    }

    private Long id;

    private String size;

    private boolean isAvailable;

}
