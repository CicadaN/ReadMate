package app.ReadMate.mapper;

import app.ReadMate.dto.BookRequestDTO;
import app.ReadMate.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BookMapper {

    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true) // ID игнорируется при создании
    Book toEntity(BookRequestDTO dto);

    BookRequestDTO toDto(Book book);
}
