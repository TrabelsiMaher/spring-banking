package org.bnk.exls.dtos;

import java.util.Date;

import org.bnk.exls.enums.AccountStatus;

import lombok.Data;



@Data
public class SavingBankAccountDTO extends BankAccountDTO {
	
	private String id;
	private double balance;
	private Date createdAt;
	private String currency;
	
	private AccountStatus status;
	
	private CustumerDTO custumerDTO;


	private double savingRate;

}
