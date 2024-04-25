package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerConverter customerConverter;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public void saveCustomer(CustomerDTO customerDTO) {
		CustomerEntity customerEntity = this.customerConverter.convertToEntity(customerDTO);
		this.customerRepository.save(customerEntity);
	}

	@Override
	public List<CustomerDTO> searchCustomer(CustomerDTO customerDTO, Pageable pageable) {
		List<CustomerEntity> customerEntities = this.customerRepository.searchCustomer(customerDTO, pageable);

		List<CustomerDTO> customerDTOS = new ArrayList<>();

		for(CustomerEntity customerEntity : customerEntities) {
			customerDTOS.add(this.customerConverter.convertToDto(customerEntity));
		}
		return customerDTOS;
	}

	@Override
	public Integer totalItems(CustomerDTO customerDTO) {
		return this.customerRepository.totalSearchItems(customerDTO);
	}

	@Override
	public ResponseDTO listStaff(Long customerId) {
		ResponseDTO responseDTO = new ResponseDTO();

		CustomerEntity customerEntity = this.customerRepository.findById(customerId).get();

		List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1, "STAFF");

		List<UserEntity> staffAssginment = customerEntity.getStaffs();

		List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();

		for(UserEntity it : staffs) {
			StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
			staffResponseDTO.setFullName(it.getFullName());
			staffResponseDTO.setStaffId(it.getId());
			if(staffAssginment.contains(it)){
				staffResponseDTO.setChecked("checked");
			}
			else{
				staffResponseDTO.setChecked("");
			}
			staffResponseDTOS.add(staffResponseDTO);
		}

		responseDTO.setData(staffResponseDTOS);
		responseDTO.setMessage("success");
		return responseDTO;
	}

	@Transactional
	@Override
	public void updateAssignmentCustomer(AssignmentCustomerDTO assignmentCustomerDTO) {
		CustomerEntity customerEntity = this.customerRepository.findById(assignmentCustomerDTO.getCustomerId()).get();
		List<UserEntity> staffs = this.userRepository.findByIdIn(assignmentCustomerDTO.getStaffs());

		customerEntity.setStaffs(staffs);
		this.customerRepository.save(customerEntity);
	}

	@Transactional
	@Override
	public void deleteBuildingByIds(List<Long> ids) {
		List<UserEntity> userEntities = userRepository.findByStatusAndRoles_Code(1, "STAFF");

		for(Long id : ids){
			CustomerEntity customerEntity = this.customerRepository.findById(id).get();
			for(UserEntity userEntity : userEntities){
				if(userEntity.getCustomerEntities().contains(customerEntity)){
					userEntity.getCustomerEntities().remove(customerEntity);
					userRepository.save(userEntity);
				}
			}
			this.customerRepository.deleteById(id);
		}
	}

	@Override
	public CustomerDTO findById(Long id) {
		CustomerEntity customerEntity = this.customerRepository.findById(id).get();
		return this.customerConverter.convertToDto(customerEntity);
	}
}
