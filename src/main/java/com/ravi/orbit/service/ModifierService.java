package com.ravi.orbit.service;

import com.ravi.orbit.dto.ColorGroupDTO;
import com.ravi.orbit.dto.SizeGroupDTO;
import com.ravi.orbit.entity.Color;
import com.ravi.orbit.entity.ColorGroup;
import com.ravi.orbit.entity.Size;
import com.ravi.orbit.entity.SizeGroup;

import java.util.List;

public interface ModifierService {

    SizeGroupDTO handleSizeGroup(SizeGroupDTO sizeGroupDTO);
    ColorGroupDTO handleColorGroup(ColorGroupDTO colorGroupDTO);
    List<SizeGroupDTO> getAllSizeGroups();
    List<ColorGroupDTO> getAllColorGroups();
    SizeGroupDTO getSizeGroupDTOById(Long id);
    ColorGroupDTO getColorGroupDTOById(Long id);
    SizeGroup getSizeGroupById(Long id);
    ColorGroup getColorGroupById(Long id);
    Size getSizeById(Long id);
    Color getColorById(Long id);

}
