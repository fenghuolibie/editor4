package com.example.dto.department;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DepartmentDTO {

    private String departmentID;

    private String departmentName;
}
