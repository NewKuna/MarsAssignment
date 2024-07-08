package com.example.demo.controller;

import com.example.demo.common.model.dto.PagingDto;
import com.example.demo.common.model.dto.ResponsePagingDto;
import com.example.demo.model.ApplicationFormRequestDto;
import com.example.demo.model.ApplicationFormResponseDto;
import com.example.demo.service.InsuranceService;
import com.example.demo.common.exception.CommonException;
import com.example.demo.common.model.Status;
import com.example.demo.common.model.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/insurance")
public class InsuranceController {

	private InsuranceService insuranceService;

	@Autowired
	public void setInsuranceService(InsuranceService insuranceService) {
		this.insuranceService = insuranceService;
	}


	@GetMapping
	public ResponseEntity<ResponsePagingDto<ApplicationFormResponseDto>> getApplicationFormList(
			@ParameterObject Pageable pageable
	) throws CommonException {

		Page<ApplicationFormResponseDto> response = insuranceService.getApplicationFormList(pageable);

		return ResponseEntity.ok()
				.body(ResponsePagingDto.<ApplicationFormResponseDto>builder()
						.status(Status.SUCCESS.getStatusDTO())
						.data(response.getContent())
						.paging(PagingDto.builder()
								.itemCount(response.getSize())
								.totalItems(response.getTotalElements())
								.totalPages(response.getTotalPages())
								.currentPage(response.getNumber())
								.build())
						.build());
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseDto<?>> createApplicationForm(
			@RequestPart("prefix") String prefix,
			@RequestPart("first_name") String first_name,
			@RequestPart("last_name") String lastName,
			@RequestPart("address") String address,
			@RequestPart("phone_number") String phoneNumber,
			@RequestPart("image") MultipartFile image
	) throws CommonException {

		if (!isPhoneNumber(phoneNumber)) {
			throw new CommonException(Status.INVALID_REQUEST, "Invalid phone number");
		}
		if (!isImageFile(image)) {
			throw new CommonException(Status.INVALID_REQUEST, "Invalid file type");
		}

		ApplicationFormRequestDto requestDto = new ApplicationFormRequestDto();
		requestDto.setPrefix(prefix);
		requestDto.setFirstName(first_name);
		requestDto.setLastName(lastName);
		requestDto.setAddress(address);
		requestDto.setPhoneNumber(phoneNumber);

		insuranceService.createApplicationForm(requestDto, image);

		return ResponseEntity.ok()
				.body(ResponseDto.builder().status(Status.SUCCESS.getStatusDTO()).build());
	}

	@PutMapping(value  = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ResponseDto<?>> updateApplicationForm(
			@PathVariable String id,
			@RequestPart(value = "prefix", required = false) String prefix,
			@RequestPart(value = "first_name", required = false) String first_name,
			@RequestPart(value = "last_name", required = false) String lastName,
			@RequestPart(value = "address", required = false) String address,
			@RequestPart(value = "phone_number", required = false) String phoneNumber,
			@RequestPart(value = "image", required = false) MultipartFile image
	) throws CommonException {

		if (!StringUtils.isEmpty(phoneNumber) && !isPhoneNumber(phoneNumber)) {
			throw new CommonException(Status.INVALID_REQUEST, "Invalid phone number");
		}
		if (image != null && !image.isEmpty() && !isImageFile(image)) {
			throw new CommonException(Status.INVALID_REQUEST, "Invalid file type");
		}

		ApplicationFormRequestDto requestDto = new ApplicationFormRequestDto();
		requestDto.setPrefix(prefix);
		requestDto.setFirstName(first_name);
		requestDto.setLastName(lastName);
		requestDto.setAddress(address);
		requestDto.setPhoneNumber(phoneNumber);

		insuranceService.updateApplicationForm(id, requestDto, image);

		return ResponseEntity.ok()
				.body(ResponseDto.builder().status(Status.SUCCESS.getStatusDTO()).build());
	}

	@PostMapping(value  = "/cancel/{id}")
	public ResponseEntity<ResponseDto<?>> cancelApplicationForm(
			@PathVariable String id
	) throws CommonException {

		insuranceService.cancelApplicationForm(id);

		return ResponseEntity.ok()
				.body(ResponseDto.builder().status(Status.SUCCESS.getStatusDTO()).build());
	}

	@PostMapping(value  = "/approve/{id}")
	public ResponseEntity<ResponseDto<?>> approveApplicationForm(
			@PathVariable String id
	) throws CommonException {

		insuranceService.approveApplicationForm(id);

		return ResponseEntity.ok()
				.body(ResponseDto.builder().status(Status.SUCCESS.getStatusDTO()).build());
	}

	@PostMapping(value  = "/reject/{id}")
	public ResponseEntity<ResponseDto<?>> rejectApplicationForm(
			@PathVariable String id
	) throws CommonException {

		insuranceService.rejectApplicationForm(id);

		return ResponseEntity.ok()
				.body(ResponseDto.builder().status(Status.SUCCESS.getStatusDTO()).build());
	}


	public static boolean isPhoneNumber(String s)
	{
		Pattern p = Pattern.compile("^\\d{10}$");
		Matcher m = p.matcher(s);
		return (m.matches());
	}

	private boolean isImageFile(MultipartFile file) {
		List<String> imageContentTypes = Arrays.asList(
				MediaType.IMAGE_JPEG_VALUE,
				MediaType.IMAGE_PNG_VALUE
		);
		return imageContentTypes.contains(file.getContentType());
	}

}
