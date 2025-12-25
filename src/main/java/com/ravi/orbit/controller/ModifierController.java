package com.ravi.orbit.controller;

import com.ravi.orbit.dto.ColorGroupDTO;
import com.ravi.orbit.dto.SizeGroupDTO;
import com.ravi.orbit.service.ModifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/modifier")
@RequiredArgsConstructor
public class ModifierController {

    private final ModifierService modifierService;

    @PostMapping("/handleSizes")
    public ResponseEntity<SizeGroupDTO> handleSizes(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return ResponseEntity.ok(modifierService.handleSizeGroup(sizeGroupDTO));
    }

    @PostMapping("/handleColors")
    public ResponseEntity<ColorGroupDTO> handleColors(@RequestBody ColorGroupDTO colorGroupDTO) {
        return ResponseEntity.ok(modifierService.handleColorGroup(colorGroupDTO));
    }

    @GetMapping("/getSizeGroups")
    public ResponseEntity<List<SizeGroupDTO>> getAllSizeGroups() {
        return ResponseEntity.ok(modifierService.getAllSizeGroups());
    }

    @GetMapping("/getColorGroups")
    public ResponseEntity<List<ColorGroupDTO>> getAllColorGroups() {
        return ResponseEntity.ok(modifierService.getAllColorGroups());
    }

    @GetMapping("/getSizeGroup")
    public ResponseEntity<SizeGroupDTO> getSizeGroupDTOById(Long id) {
        return ResponseEntity.ok(modifierService.getSizeGroupDTOById(id));
    }

    @GetMapping("/getColorGroup")
    public ResponseEntity<ColorGroupDTO> getColorGroupDTOById(Long id) {
        return ResponseEntity.ok(modifierService.getColorGroupDTOById(id));
    }

}
