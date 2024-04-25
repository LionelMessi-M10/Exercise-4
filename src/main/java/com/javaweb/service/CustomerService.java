package com.javaweb.service;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

	void saveCustomer(CustomerDTO customerDTO);

	List<CustomerDTO> searchCustomer(CustomerDTO customerDTO, Pageable pageable);

	Integer totalItems(CustomerDTO customerDTO);

	ResponseDTO listStaff(Long customerId);

	void updateAssignmentCustomer(AssignmentCustomerDTO assignmentCustomerDTO);

	void deleteBuildingByIds(List<Long> ids);

	CustomerDTO findById(Long id);
}
