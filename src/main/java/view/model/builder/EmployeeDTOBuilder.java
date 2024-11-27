package view.model.builder;

import view.model.EmployeeDTO;

public class EmployeeDTOBuilder {

    private EmployeeDTO employeeDTO;

    public EmployeeDTOBuilder() {
        employeeDTO = new EmployeeDTO();
    }

    public EmployeeDTOBuilder setUsername(String username) {
        employeeDTO.setUsername(username);
        return this;
    }

    public EmployeeDTOBuilder setPassword(String password) {
        employeeDTO.setPassword(password);
        return this;
    }

    public EmployeeDTO build() {
        return employeeDTO;
    }
}
