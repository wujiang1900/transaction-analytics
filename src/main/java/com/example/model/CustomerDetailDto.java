package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDetailDto {
    private String id;
    private String email;
    private String name;
    private CATEGORY category;
}
