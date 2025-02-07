package app.ReadMate.mapper;

import app.ReadMate.model.User;
import app.ReadMate.dto.UserRequestDto;
import app.ReadMate.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface UserMapper {
 
    @Mapping(target = "id", ignore = true)  // ID is generated by the database
    @Mapping(target = "ratings", ignore = true) // Игнорируем список рейтингов
    @Mapping(target = "authorities", ignore = true) // Authorities are not mapped
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    UserResponseDto toDto(User user);

}
