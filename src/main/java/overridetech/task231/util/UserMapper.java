package overridetech.task231.util;


import org.mapstruct.*;
import overridetech.task231.model.User;
import overridetech.task231.Dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateUserFromDto(UserDto dto, @MappingTarget User user);
}
