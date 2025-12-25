package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeGroupDTO {

    public SizeGroupDTO(Long id, String sizeGroup) {
        this.id = id;
        this.sizeGroup = sizeGroup;
    }

    private Long id;

    private String sizeGroup;

    private List<SizeDTO> sizes;

}
