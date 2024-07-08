package com.example.demo.repository;

import com.example.demo.model.InsuranceApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceApplicationRepository extends ElasticsearchRepository<InsuranceApplication, String> {
}
