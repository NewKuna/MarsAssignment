package com.example.demo.common.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponsePagingDto<T> {

    private StatusDto status;
    private List<T> data;
    private PagingDto paging;

}
