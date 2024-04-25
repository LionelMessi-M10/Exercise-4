package com.javaweb.entity;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity {

	@Column(name = "code")
	private String code;

	@Column(name = "note")
	private String note;

	@Column(name = "staffid")
	private Integer staffId;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "customerid")
	private CustomerEntity customerEntity;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}
}
