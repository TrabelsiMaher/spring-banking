package org.bnk.exls.dtos;

import lombok.Data;

@Data
public class InitCurrentBankAccount {
	
	private Long custumerId;
	private double initialBalance;
	private double overDraft;
	
}
