package com.cwOOP.ticketingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String password;
    private String email;
    private String name;
    private String phoneNumber;

}
