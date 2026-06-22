package com.cibertec.auth_service.mapper;

import org.mapstruct.Mapper;

import com.cibertec.auth_service.dto.request.UserRequestDTO;
import com.cibertec.auth_service.dto.response.UserResponseDTO;
import com.cibertec.auth_service.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User entity);
}
