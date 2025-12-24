package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    public CategoryDTO(Long id, String name, EStatus status, String imgUrl) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.imgUrl = imgUrl;
    }

    private Long id;

    private String name;

    private EStatus status;

    private String imgUrl;

    private List<ProductDTO> products;

}
