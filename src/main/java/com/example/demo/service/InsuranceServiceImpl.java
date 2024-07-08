package com.example.demo.service;

import com.example.demo.common.exception.CommonException;
import com.example.demo.common.model.Status;
import com.example.demo.model.ApplicationFormRequestDto;
import com.example.demo.model.ApplicationFormResponseDto;
import com.example.demo.model.ApplicationFormStatus;
import com.example.demo.model.InsuranceApplication;
import com.example.demo.repository.InsuranceApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class InsuranceServiceImpl implements InsuranceService {

    private InsuranceApplicationRepository insuranceApplicationRepository;

    @Autowired
    public void setInsuranceApplicationRepository(InsuranceApplicationRepository insuranceApplicationRepository) {
        this.insuranceApplicationRepository = insuranceApplicationRepository;
    }

    @Override
    public Page<ApplicationFormResponseDto> getApplicationFormList(Pageable pageable) throws CommonException {
        Page<InsuranceApplication> insuranceApplications = insuranceApplicationRepository.findAll(pageable);

        List<ApplicationFormResponseDto> applicationFormResponse = insuranceApplications.stream()
                .map(this::applicationFormResponseDtoMapper)
                .toList();

        return new PageImpl<>(applicationFormResponse, insuranceApplications.getPageable(), insuranceApplications.getTotalElements());
    }

    @Override
    public void createApplicationForm(ApplicationFormRequestDto applicationFormRequestDto, MultipartFile image) throws CommonException {
        UUID imageId = this.saveImageToFileServer(image);
        InsuranceApplication insuranceApplication = InsuranceApplication.builder()
                .prefix(applicationFormRequestDto.getPrefix())
                .firstName(applicationFormRequestDto.getFirstName())
                .lastName(applicationFormRequestDto.getLastName())
                .address(applicationFormRequestDto.getAddress())
                .phoneNumber(applicationFormRequestDto.getPhoneNumber())
                .status(ApplicationFormStatus.PENDING.toString())
                .image(imageId)
                .build();

        insuranceApplicationRepository.save(insuranceApplication);
    }

    @Override
    public void updateApplicationForm(String id, ApplicationFormRequestDto applicationFormRequestDto, MultipartFile image) throws CommonException {
        InsuranceApplication insuranceApplication = insuranceApplicationRepository.findById(id)
                .orElseThrow(() -> new CommonException(Status.INVALID_REQUEST, "Invalid id"));

        if (!insuranceApplication.getStatus().equals(ApplicationFormStatus.PENDING.toString())) {
            throw new CommonException(Status.INVALID_REQUEST, "Application form status is not PENDING");
        }

        if (!StringUtils.isEmpty(applicationFormRequestDto.getPrefix())) {
            insuranceApplication.setPrefix(applicationFormRequestDto.getPrefix());
        }
        if (!StringUtils.isEmpty(applicationFormRequestDto.getFirstName())) {
            insuranceApplication.setFirstName(applicationFormRequestDto.getFirstName());
        }
        if (!StringUtils.isEmpty(applicationFormRequestDto.getLastName())) {
            insuranceApplication.setLastName(applicationFormRequestDto.getLastName());
        }
        if (!StringUtils.isEmpty(applicationFormRequestDto.getAddress())) {
            insuranceApplication.setAddress(applicationFormRequestDto.getAddress());
        }
        if (!StringUtils.isEmpty(applicationFormRequestDto.getPhoneNumber())) {
            insuranceApplication.setPhoneNumber(applicationFormRequestDto.getPhoneNumber());
        }
        if (image != null && !image.isEmpty()) {
            UUID imageId = this.saveImageToFileServer(image);
            insuranceApplication.setImage(imageId);
        }

        insuranceApplicationRepository.save(insuranceApplication);
    }

    @Override
    public void cancelApplicationForm(String id) throws CommonException {
        InsuranceApplication insuranceApplication = insuranceApplicationRepository.findById(id)
                .orElseThrow(() -> new CommonException(Status.INVALID_REQUEST, "Invalid id"));

        if (!insuranceApplication.getStatus().equals(ApplicationFormStatus.PENDING.toString())) {
            throw new CommonException(Status.INVALID_REQUEST, "Application form status is not PENDING");
        }
        insuranceApplication.setStatus(ApplicationFormStatus.CANCEL.toString());

        insuranceApplicationRepository.save(insuranceApplication);
    }

    @Override
    public void approveApplicationForm(String id) throws CommonException {
        InsuranceApplication insuranceApplication = insuranceApplicationRepository.findById(id)
                .orElseThrow(() -> new CommonException(Status.INVALID_REQUEST, "Invalid id"));

        if (!insuranceApplication.getStatus().equals(ApplicationFormStatus.PENDING.toString())) {
            throw new CommonException(Status.INVALID_REQUEST, "Application form status is not PENDING");
        }
        insuranceApplication.setStatus(ApplicationFormStatus.APPROVED.toString());

        insuranceApplicationRepository.save(insuranceApplication);
    }

    @Override
    public void rejectApplicationForm(String id) throws CommonException {
        InsuranceApplication insuranceApplication = insuranceApplicationRepository.findById(id)
                .orElseThrow(() -> new CommonException(Status.INVALID_REQUEST, "Invalid id"));

        if (!insuranceApplication.getStatus().equals(ApplicationFormStatus.PENDING.toString())) {
            throw new CommonException(Status.INVALID_REQUEST, "Application form status is not PENDING");
        }
        insuranceApplication.setStatus(ApplicationFormStatus.REJECTED.toString());

        insuranceApplicationRepository.save(insuranceApplication);
    }

    private ApplicationFormResponseDto applicationFormResponseDtoMapper(InsuranceApplication insuranceApplication) {
        return ApplicationFormResponseDto.builder()
                .id(insuranceApplication.getId())
                .prefix(insuranceApplication.getPrefix())
                .firstName(insuranceApplication.getFirstName())
                .lastName(insuranceApplication.getLastName())
                .address(insuranceApplication.getAddress())
                .phoneNumber(insuranceApplication.getPhoneNumber())
                .status(insuranceApplication.getStatus())
                .image(this.findImageFromFileServer(insuranceApplication.getImage()))
                .build();
    }

    private UUID saveImageToFileServer(MultipartFile image) {
        // SAVE A IMAGE TO SOMEWHERE ELSE
        return UUID.randomUUID();
    }

    private byte[] findImageFromFileServer(UUID id) {
        // FIND A IMAGE FROM FILE SERVER
//        String filePath = "src/main/resources/sample-image.jpg";
//        Path path = Paths.get(filePath);
//        try {
//            return Files.readAllBytes(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return new byte[0];
    }

}
