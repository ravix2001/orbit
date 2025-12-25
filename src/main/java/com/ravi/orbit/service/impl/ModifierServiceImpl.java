package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.ColorGroupDTO;
import com.ravi.orbit.dto.SizeGroupDTO;
import com.ravi.orbit.entity.ColorGroup;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.Size;
import com.ravi.orbit.entity.SizeGroup;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.ColorGroupRepository;
import com.ravi.orbit.repository.ColorRepository;
import com.ravi.orbit.repository.SizeGroupRepository;
import com.ravi.orbit.repository.SizeRepository;
import com.ravi.orbit.service.IProductService;
import com.ravi.orbit.service.ModifierService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ModifierServiceImpl implements ModifierService {

    private final SizeGroupRepository sizeGroupRepository;
    private final SizeRepository sizeRepository;
    private final ColorGroupRepository colorGroupRepository;
    private final ColorRepository colorRepository;
    private final IProductService productService;

    public SizeGroupDTO handleSizeGroup(SizeGroupDTO sizeGroupDTO) {

//        Validator.validateUserSignup(sizeGroupDTO);

        Product product = productService.getProductById(sizeGroupDTO.getProductId());

        SizeGroup sizeGroup = null;

        if(CommonMethods.isEmpty(sizeGroupDTO.getId())){
            sizeGroup = new SizeGroup();
            sizeGroup.setProduct(product);
        }
        else{
            sizeGroup = getSizeGroupById(sizeGroupDTO.getId());
        }
        sizeGroup.setSizeGroup(sizeGroupDTO.getSizeGroup());
        sizeGroupRepository.save(sizeGroup);

        sizeGroupDTO.setId(sizeGroup.getId());
        return sizeGroupDTO;
    }

    public ColorGroupDTO handleColorGroup(ColorGroupDTO colorGroupDTO) {

//        Validator.validateUserSignup(colorGroupDTO);

        Product product = productService.getProductById(colorGroupDTO.getProductId());

        ColorGroup colorGroup = null;

        if(CommonMethods.isEmpty(colorGroupDTO.getId())){
            colorGroup = new ColorGroup();
            colorGroup.setProduct(product);
        }
        else{
            colorGroup = getColorGroupById(colorGroupDTO.getId());
        }
        colorGroup.setColorGroup(colorGroupDTO.getColorGroup());
        colorGroupRepository.save(colorGroup);

        colorGroupDTO.setId(colorGroup.getId());
        return colorGroupDTO;

    }

//    @Override
    public SizeGroup getSizeGroupById(Long id) {
        return sizeGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "SizeGroup: " + id));
    }

    //    @Override
    public ColorGroup getColorGroupById(Long id) {
        return colorGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "ColorGroup: " + id));
    }


}
