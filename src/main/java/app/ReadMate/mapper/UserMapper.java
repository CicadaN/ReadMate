package app.ReadMate.mapper;

import app.ReadMate.dto.UserRequestDto;
import app.ReadMate.dto.UserResponseDto;
import app.ReadMate.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true) // Игнорируем поле id
    @Mapping(target = "age", ignore = true) // Игнорируем поле age
    @Mapping(target = "authorities", ignore = true) // Игнорируем поле authorities
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    UserResponseDto toDto(User user);
}
