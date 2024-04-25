package com.javaweb.repository.custom.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public void joinTable(CustomerDTO customerDTO, StringBuilder sql) {
		if(customerDTO.getManagementStaff() != null && !customerDTO.getManagementStaff().isEmpty()){
			sql.append(" INNER JOIN assignmentcustomer ac ON ac.customerid = c.id ");
		}
	}

	public void querySpecial(CustomerDTO customerDTO, StringBuilder sql) {
		if(customerDTO.getManagementStaff() != null && !customerDTO.getManagementStaff().isEmpty()){
			sql.append(" AND ac.staffid = " + customerDTO.getManagementStaff());
		}
	}

	public void queryNormal(CustomerDTO customerDTO, StringBuilder sql) {
		if(customerDTO.getName() != null && !customerDTO.getName().isEmpty()){
			sql.append(" AND c.fullname LIKE '%" + customerDTO.getName() + "%' ");
		}
		if(customerDTO.getEmail() != null && !customerDTO.getEmail().isEmpty()){
			sql.append(" AND c.email LIKE '%" + customerDTO.getEmail() + "%' ");
		}
		if(customerDTO.getCustomerPhone() != null && !customerDTO.getCustomerPhone().isEmpty()){
			sql.append(" AND c.phone LIKE '%" + customerDTO.getCustomerPhone() + "%' ");
		}
	}

	@Override
	public List<CustomerEntity> searchCustomer(CustomerDTO customerDTO, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT c.* FROM customer c ");

		joinTable(customerDTO, sql);

		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		querySpecial(customerDTO, where);
		queryNormal(customerDTO, where);

		sql.append(where);

		sql.append(" LIMIT ").append(pageable.getPageSize()).append("\n")
				.append(" OFFSET ").append(pageable.getOffset());


		Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);

		return query.getResultList();
	}

	@Override
	public Integer totalSearchItems(CustomerDTO customerDTO) {
		StringBuilder sql = new StringBuilder("SELECT c.* FROM customer c ");

		joinTable(customerDTO, sql);

		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		querySpecial(customerDTO, where);
		queryNormal(customerDTO, where);

		sql.append(where);

		Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);

		return query.getResultList().size();
	}
}
