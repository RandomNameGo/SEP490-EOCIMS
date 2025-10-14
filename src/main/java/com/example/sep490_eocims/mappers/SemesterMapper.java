package com.example.sep490_eocims.mappers;

import com.example.sep490_eocims.dto.request.SemesterRequest;
import com.example.sep490_eocims.models.Semester;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    SemesterMapper INSTANCE = Mappers.getMapper(SemesterMapper.class);

    Semester semesterRequestToSemester(SemesterRequest semesterRequest);

}
