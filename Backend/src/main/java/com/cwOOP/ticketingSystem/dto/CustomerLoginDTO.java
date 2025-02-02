package com.cwOOP.ticketingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDTO {
    private String email;
    private String password;
}
