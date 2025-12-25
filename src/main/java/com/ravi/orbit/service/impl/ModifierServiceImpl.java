package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.ColorDTO;
import com.ravi.orbit.dto.ColorGroupDTO;
import com.ravi.orbit.dto.SizeDTO;
import com.ravi.orbit.dto.SizeGroupDTO;
import com.ravi.orbit.entity.Color;
import com.ravi.orbit.entity.ColorGroup;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.ProductColorGroup;
import com.ravi.orbit.entity.ProductSizeGroup;
import com.ravi.orbit.entity.Size;
import com.ravi.orbit.entity.SizeGroup;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.ColorGroupRepository;
import com.ravi.orbit.repository.ColorRepository;
import com.ravi.orbit.repository.ProductColorGroupRepository;
import com.ravi.orbit.repository.ProductSizeGroupRepository;
import com.ravi.orbit.repository.SizeGroupRepository;
import com.ravi.orbit.repository.SizeRepository;
import com.ravi.orbit.service.IProductService;
import com.ravi.orbit.service.ModifierService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ModifierServiceImpl implements ModifierService {

    private final SizeGroupRepository sizeGroupRepository;
    private final SizeRepository sizeRepository;
    private final ColorGroupRepository colorGroupRepository;
    private final ColorRepository colorRepository;
    private final IProductService productService;
    private final ProductSizeGroupRepository productSizeGroupRepository;
    private final ProductColorGroupRepository productColorGroupRepository;

    @Override
    public SizeGroupDTO handleSizeGroup(SizeGroupDTO sizeGroupDTO) {

//        Validator.validateUserSignup(sizeGroupDTO);

        SizeGroup sizeGroup = null;

        if(CommonMethods.isEmpty(sizeGroupDTO.getId())){
            sizeGroup = new SizeGroup();
        }
        else{
            sizeGroup = getSizeGroupById(sizeGroupDTO.getId());
        }
        sizeGroup.setSizeGroup(sizeGroupDTO.getSizeGroup());
        sizeGroupRepository.save(sizeGroup);

        List<SizeDTO> sizes = sizeGroupDTO.getSizes();

        for(SizeDTO sizeDTO : sizes){
            Size size = null;
            if(CommonMethods.isEmpty(sizeDTO.getId())){
                size = new Size();
                size.setSizeGroup(sizeGroup);
            }
            else{
                size = getSizeById(sizeDTO.getId());
            }
            size.setSize(sizeDTO.getSize());
            sizeRepository.save(size);
        }

        sizeGroupDTO.setId(sizeGroup.getId());
        return sizeGroupDTO;
    }

    @Override
    public ColorGroupDTO handleColorGroup(ColorGroupDTO colorGroupDTO) {

//        Validator.validateUserSignup(colorGroupDTO);

        ColorGroup colorGroup = null;

        if(CommonMethods.isEmpty(colorGroupDTO.getId())){
            colorGroup = new ColorGroup();
        }
        else{
            colorGroup = getColorGroupById(colorGroupDTO.getId());
        }
        colorGroup.setColorGroup(colorGroupDTO.getColorGroup());
        colorGroupRepository.save(colorGroup);

        List<ColorDTO> colors = colorGroupDTO.getColors();

        for(ColorDTO colorDTO : colors){
            Color color = null;
            if(CommonMethods.isEmpty(colorDTO.getId())){
                color = new Color();
                color.setColorGroup(colorGroup);
            }
            else{
                color = getColorById(colorDTO.getId());
            }
            color.setColor(colorDTO.getColor());
            colorRepository.save(color);
        }

        colorGroupDTO.setId(colorGroup.getId());
        return colorGroupDTO;

    }

    @Override
    public String linkProductAndSizeGroup(Long productId, Long sizeGroupId){

        ProductSizeGroup existingProductSizeGroup = productSizeGroupRepository.findByProductIdAndSizeGroupId(productId, sizeGroupId);
        if(existingProductSizeGroup != null){
            return "Size group already linked to this product";
        }

        Product product = productService.getProductById(productId);
        SizeGroup sizeGroup = getSizeGroupById(sizeGroupId);

        ProductSizeGroup productSizeGroup = new ProductSizeGroup();
        productSizeGroup.setProduct(product);
        productSizeGroup.setSizeGroup(sizeGroup);
        productSizeGroupRepository.save(productSizeGroup);

        return "Size group linked to this product successfully";

    }

    @Override
    public String unlinkProductAndSizeGroup(Long productId, Long sizeGroupId){
        ProductSizeGroup existingProductSizeGroup = productSizeGroupRepository.findByProductIdAndSizeGroupId(productId, sizeGroupId);
        if(existingProductSizeGroup == null){
            return "Size group is not linked to this product";
        }
        productSizeGroupRepository.delete(existingProductSizeGroup);
        return "Size group unlinked from this product successfully";
    }

    @Override
    public String linkProductAndColorGroup(Long productId, Long colorGroupId) {

        ProductColorGroup existingProductColorGroup =
                productColorGroupRepository.findByProductIdAndColorGroupId(productId, colorGroupId);

        if (existingProductColorGroup != null) {
            return "Color group already linked to this product";
        }

        Product product = productService.getProductById(productId);
        ColorGroup colorGroup = getColorGroupById(colorGroupId);

        ProductColorGroup productColorGroup = new ProductColorGroup();
        productColorGroup.setProduct(product);
        productColorGroup.setColorGroup(colorGroup);

        productColorGroupRepository.save(productColorGroup);

        return "Color group linked to this product successfully";
    }

    @Override
    public String unlinkProductAndColorGroup(Long productId, Long colorGroupId) {

        ProductColorGroup existingProductColorGroup =
                productColorGroupRepository.findByProductIdAndColorGroupId(productId, colorGroupId);

        if (existingProductColorGroup == null) {
            return "Color group is not linked to this product";
        }

        productColorGroupRepository.delete(existingProductColorGroup);

        return "Color group unlinked from this product successfully";
    }

    @Override
    public List<SizeGroupDTO> getAllSizeGroups(){
        return sizeGroupRepository.getAllSizeGroups();
    }

    @Override
    public List<ColorGroupDTO> getAllColorGroups(){
        return colorGroupRepository.getAllColorGroups();
    }

    @Override
    public SizeGroupDTO getSizeGroupDTOById(Long id) {
        return sizeGroupRepository.getSizeGroupDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "SizeGroup: " + id));
    }

    @Override
    public ColorGroupDTO getColorGroupDTOById(Long id) {
        return colorGroupRepository.getColorGroupDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "ColorGroup: " + id));
    }

    @Override
    public SizeGroup getSizeGroupById(Long id) {
        return sizeGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "SizeGroup: " + id));
    }

    @Override
    public ColorGroup getColorGroupById(Long id) {
        return colorGroupRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "ColorGroup: " + id));
    }

    @Override
    public Size getSizeById(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Size: " + id));
    }

    @Override
    public Color getColorById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Color: " + id));
    }

}
