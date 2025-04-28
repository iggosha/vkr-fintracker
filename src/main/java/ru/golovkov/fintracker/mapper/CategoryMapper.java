package ru.golovkov.fintracker.mapper;

import org.mapstruct.Mapper;
import ru.golovkov.fintracker.dto.CategoryDto;
import ru.golovkov.fintracker.model.Category;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = IGNORE)
public interface CategoryMapper {

    CategoryDto mapToDtoFromEntity(Category entity);

    List<CategoryDto> mapToDtosFromEntities(List<Category> entities);
}
