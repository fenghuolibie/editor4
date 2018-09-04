package com.example.dto.userinfo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginResponseDTO {
    private String userLevel;
}
