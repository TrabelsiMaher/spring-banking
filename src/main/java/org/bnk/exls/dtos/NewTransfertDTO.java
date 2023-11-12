package org.bnk.exls.dtos;

import lombok.Data;

@Data
public class NewTransfertDTO {
	private AccountNewBalanceDTO accountNewBalanceDTOSource;
	private AccountNewBalanceDTO accountNewBalanceDTODestination;


}
