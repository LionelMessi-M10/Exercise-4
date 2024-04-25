package com.javaweb.service;

import com.javaweb.model.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

	List<TransactionDTO> findByCustomerId(Long customerId);
	void saveTransaction(TransactionDTO transactionDTO);
}
