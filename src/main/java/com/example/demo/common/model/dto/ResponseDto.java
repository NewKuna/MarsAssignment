package com.example.demo.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {

    private StatusDto status;
    private T data;

}
