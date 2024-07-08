package com.example.demo.common.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingDto {

    @JsonProperty("item_count")
    private Integer itemCount;
    @JsonProperty("total_items")
    private Long totalItems;
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("current_page")
    private Integer currentPage;

}
