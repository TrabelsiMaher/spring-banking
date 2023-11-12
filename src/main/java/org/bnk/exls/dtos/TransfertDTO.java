package org.bnk.exls.dtos;

import lombok.Data;

@Data
public class TransfertDTO {
	private String accountIDSource;
	private String accountIdDestination;
	private double ammount;
	private String description;	

}
