//package com.ravi.orbit.repository;
//
//import com.ravi.orbit.dto.ColorGroupDTO;
//import com.ravi.orbit.entity.ColorGroup;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ColorGroupRepository extends JpaRepository<ColorGroup, Long> {
//
//    @Query("SELECT NEW com.ravi.orbit.dto.ColorGroupDTO(cg.id, cg.colorGroup) " +
//            " FROM ColorGroup cg ")
//    List<ColorGroupDTO> getAllColorGroups();
//
//    @Query("SELECT NEW com.ravi.orbit.dto.ColorGroupDTO(cg.id, cg.colorGroup) " +
//            " FROM ColorGroup cg " +
//            " WHERE cg.id = :id ")
//    Optional<ColorGroupDTO> getColorGroupDTOById(Long id);
//
//}
