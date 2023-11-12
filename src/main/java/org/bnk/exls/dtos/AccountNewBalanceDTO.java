package org.bnk.exls.dtos;

import org.bnk.exls.enums.OperationType;

import lombok.Data;

@Data
public class AccountNewBalanceDTO {
	
	private String accountId;
	private double newBalance;
	private OperationType type;
}
