package org.bnk.exls.web;

import java.util.List;

import org.bnk.exls.dtos.AccountHistoryDTO;
import org.bnk.exls.dtos.AccountNewBalanceDTO;
import org.bnk.exls.dtos.AccountOperationDTO;
import org.bnk.exls.dtos.BankAccountDTO;
import org.bnk.exls.dtos.CreditDebitDTO;
import org.bnk.exls.dtos.NewTransfertDTO;
import org.bnk.exls.dtos.TransfertDTO;
import org.bnk.exls.exceptions.BalanceNotSufficientException;
import org.bnk.exls.exceptions.BankAccountNotFoundException;
import org.bnk.exls.services.BankAccountService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class BankAccountRestApi {
	private BankAccountService bankAccountService;

	
	@GetMapping("/accounts")
	public List<BankAccountDTO> AllBankAccounts() throws BankAccountNotFoundException {
		return bankAccountService.bankAccountList();
	}

	@GetMapping("/accounts/{id}")
	public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(id);
	}

	@GetMapping("/accounts/{id}/operations")
	public List<AccountOperationDTO> getHistory(@PathVariable(name = "id") String AccountID) {

		return bankAccountService.accountHistory(AccountID);

	}

	@GetMapping("/accounts/{accountId}/pageOperations")
	public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
		
		return bankAccountService.getAccountHistory(accountId, page, size);

	}
	@PostMapping("/accounts/debit")
	public AccountNewBalanceDTO debiteAccount(@RequestBody CreditDebitDTO  creditDebitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		return bankAccountService.debit( creditDebitDTO);
	}
	@PostMapping("/accounts/credit")
	public AccountNewBalanceDTO creditAccount(@RequestBody CreditDebitDTO  creditDebitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		return bankAccountService.credit( creditDebitDTO);
	}
		
	@PostMapping("/accounts/transfert")
	public NewTransfertDTO tranfer(@RequestBody TransfertDTO transfertDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		
		return bankAccountService.transfert(transfertDTO);
	}
}
