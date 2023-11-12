package org.bnk.exls.dtos;

import org.springframework.boot.context.properties.bind.DefaultValue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditDebitDTO {

	private String accountId;
	private double ammount;
	private String description;
	

}
