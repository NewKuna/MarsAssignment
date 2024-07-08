package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Data
@Builder
@Document(indexName = "insurance_application")
public class InsuranceApplication {

    @Id
    private String id;

    private String prefix;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String status;

    private UUID image;

}
