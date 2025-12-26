package com.ravi.orbit.service;

import com.ravi.orbit.dto.ColorDTO;
import com.ravi.orbit.dto.ColorGroupDTO;
import com.ravi.orbit.dto.SizeDTO;
import com.ravi.orbit.dto.SizeGroupDTO;
import com.ravi.orbit.entity.Color;
import com.ravi.orbit.entity.ColorGroup;
import com.ravi.orbit.entity.ProductColorGroup;
import com.ravi.orbit.entity.ProductSizeGroup;
import com.ravi.orbit.entity.Size;
import com.ravi.orbit.entity.SizeGroup;

import java.util.List;

public interface ModifierService {

    SizeGroupDTO handleSizeGroup(SizeGroupDTO sizeGroupDTO);
    ColorGroupDTO handleColorGroup(ColorGroupDTO colorGroupDTO);
    String linkProductAndSizeGroup(Long productId, Long sizeGroupId);
    String unlinkProductAndSizeGroup(Long productId, Long sizeGroupId);
    String linkProductAndColorGroup(Long productId, Long colorGroupId);
    String unlinkProductAndColorGroup(Long productId, Long colorGroupId);
    List<SizeGroupDTO> getAllSizeGroups();
    List<ColorGroupDTO> getAllColorGroups();
    SizeGroupDTO getSizeGroupByProductId(Long productId);
    ColorGroupDTO getColorGroupByProductId(Long productId);
    SizeGroupDTO getSizeGroupWithSizes(Long sizeGroupId);
    ColorGroupDTO getColorGroupWithColors(Long colorGroupId);
    List<SizeDTO> getSizesBySizeGroupId(Long sizeGroupId);
    List<ColorDTO> getColorsByColorGroupId(Long colorGroupId);
    SizeGroupDTO getSizeGroupDTOById(Long id);
    ColorGroupDTO getColorGroupDTOById(Long id);
    ProductSizeGroup getProductSizeGroupByProductId(Long productId);
    ProductColorGroup getProductColorGroupByProductId(Long productId);
    ProductSizeGroup getProductSizeGroupByProductIdAndSizeGroupId(Long productId, Long sizeGroupId);
    ProductColorGroup getProductColorGroupByProductIdAndColorGroupId(Long productId, Long colorGroupId);
    SizeGroup getSizeGroupById(Long id);
    ColorGroup getColorGroupById(Long id);
    Size getSizeById(Long id);
    Color getColorById(Long id);

}
