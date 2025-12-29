//package com.ravi.orbit.repository;
//
//import com.ravi.orbit.dto.SizeGroupDTO;
//import com.ravi.orbit.entity.SizeGroup;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface SizeGroupRepository extends JpaRepository<SizeGroup, Long> {
//
//    @Query("SELECT NEW com.ravi.orbit.dto.SizeGroupDTO(sg.id, sg.sizeGroup) " +
//            " FROM SizeGroup sg ")
//    List<SizeGroupDTO> getAllSizeGroups();
//
//    @Query("SELECT NEW com.ravi.orbit.dto.SizeGroupDTO(sg.id, sg.sizeGroup) " +
//            " FROM SizeGroup sg " +
//            " WHERE sg.id = :id ")
//    Optional<SizeGroupDTO> getSizeGroupDTOById(Long id);
//
//}
