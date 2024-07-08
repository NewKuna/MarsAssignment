package com.example.demo.service;

import com.example.demo.common.exception.CommonException;
import com.example.demo.model.ApplicationFormRequestDto;
import com.example.demo.model.ApplicationFormResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface InsuranceService {

    Page<ApplicationFormResponseDto> getApplicationFormList(Pageable pageable) throws CommonException;

    void createApplicationForm(ApplicationFormRequestDto applicationFormRequestDto, MultipartFile image) throws CommonException;

    void updateApplicationForm(String id, ApplicationFormRequestDto applicationFormRequestDto, MultipartFile image) throws CommonException;

    void cancelApplicationForm(String id) throws CommonException;

    void approveApplicationForm(String id) throws CommonException;

    void rejectApplicationForm(String id) throws CommonException;

}
