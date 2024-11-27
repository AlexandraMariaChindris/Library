package mapper;

import model.User;
import view.model.EmployeeDTO;
import view.model.builder.EmployeeDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDTO convertEmployeeToEmployeeDTO(User employee){
        return new EmployeeDTOBuilder().setUsername(employee.getUsername()).build();
    }
    public static List<EmployeeDTO> convertEmployeeListToEmployeeDTOList(List<User> employees){
        return employees.parallelStream().map(EmployeeMapper::convertEmployeeToEmployeeDTO).collect(Collectors.toList());

    }
}
