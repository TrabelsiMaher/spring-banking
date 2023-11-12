package org.bnk.exls.dtos;

import lombok.Data;

@Data
public class InitSaveBankAccount {
	
	private Long custumerId;
	private double initialBalance;
	private double savingRate;
	
}
