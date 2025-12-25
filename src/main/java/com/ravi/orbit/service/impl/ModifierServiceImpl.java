package com.ravi.orbit.service.impl;

import com.ravi.orbit.repository.ColorGroupRepository;
import com.ravi.orbit.repository.ColorRepository;
import com.ravi.orbit.repository.SizeGroupRepository;
import com.ravi.orbit.repository.SizeRepository;
import com.ravi.orbit.service.ModifierService;
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

    public void handleSizeGroup(){

    }


}
