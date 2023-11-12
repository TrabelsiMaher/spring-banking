package org.bnk.exls.dtos;

import java.util.List;

import lombok.Data;

@Data
public class AccountHistoryDTO {
	
	private String accoundId;
	private double balance;
	private List<AccountOperationDTO> accountOperationDTOs;
	private int currentPage;
	private int totalPages;
	private int pageSize; // taille de la page
	private int totalOpearations;
	}
