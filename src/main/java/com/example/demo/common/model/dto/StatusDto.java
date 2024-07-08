package com.example.demo.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusDto {

    private String code;
    private String message;
    private String description;

}
