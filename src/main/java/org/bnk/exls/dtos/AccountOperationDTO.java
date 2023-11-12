package org.bnk.exls.dtos;

import java.util.Date;

import org.bnk.exls.enums.OperationType;

import lombok.Data;


@Data
public class AccountOperationDTO {
	
	private Long id;
	private Date operationDate;
	private double ammount;
	
	private OperationType type;

	private String description;

}
