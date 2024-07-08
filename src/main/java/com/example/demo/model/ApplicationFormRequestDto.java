package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationFormRequestDto {

    @JsonProperty("prefix")
    @NotNull
    private String prefix;

    @JsonProperty("first_name")
    @NotNull
    private String firstName;

    @JsonProperty("last_name")
    @NotNull
    private String lastName;

    @JsonProperty("address")
    @NotNull
    private String address;

    @JsonProperty("phone_number")
    @NotNull
    private String phoneNumber;

}
