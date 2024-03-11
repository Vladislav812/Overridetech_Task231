package overridetech.task231.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import overridetech.task231.model.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String name;

    private int age;

    private String password;

    private String email;

    private String address;

    private Set<Role> currentRoles;
}
