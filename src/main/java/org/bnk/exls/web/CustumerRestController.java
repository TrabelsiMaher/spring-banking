package org.bnk.exls.web;

import java.util.List;

import org.bnk.exls.dtos.CurrentBankAccountDTO;
import org.bnk.exls.dtos.CustumerDTO;
import org.bnk.exls.dtos.InitCurrentBankAccount;
import org.bnk.exls.dtos.InitSaveBankAccount;
import org.bnk.exls.dtos.SavingBankAccountDTO;
import org.bnk.exls.exceptions.BalanceNotSufficientException;
import org.bnk.exls.exceptions.BankAccountNotFoundException;
import org.bnk.exls.exceptions.CustumerNotFoundException;
import org.bnk.exls.services.BankAccountService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustumerRestController {

	private BankAccountService bankAccountService;
	
	@GetMapping("/custumers")
	public List<CustumerDTO> custumers(){
		log.info(" custumers");
		return bankAccountService.listCustumers();
	}
	@GetMapping("/custumers/search")
	public List<CustumerDTO> searchCustumers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
		return bankAccountService.searchCustomerByKeyword(keyword);
	}
	@GetMapping("/custumers/{id}")
	public CustumerDTO getCustumer(@PathVariable(name = "id") Long custumerId) throws CustumerNotFoundException {
		
		
		return bankAccountService.getCustumer(custumerId);
	
	}
	@PostMapping("/custumers")
	public CustumerDTO saveCustumer(@RequestBody CustumerDTO custumerDTO) {
		return bankAccountService.saveCustumer(custumerDTO);
	}
	@PostMapping("/custumers/{custumerId}/newSavingAccount")
	public SavingBankAccountDTO newCustumerSavingAccount(@RequestBody InitSaveBankAccount initSaveBankAccount) throws CustumerNotFoundException {
	
		return bankAccountService.saveSavingBankAccount(initSaveBankAccount.getInitialBalance(), initSaveBankAccount.getSavingRate(), initSaveBankAccount.getCustumerId());
	}
	@PostMapping("/custumers/{custumerId}/newCurrentAccount")
	public CurrentBankAccountDTO newCustumerCurrentAccount(@RequestBody InitCurrentBankAccount initCurrentBankAccount) throws CustumerNotFoundException {
		return bankAccountService.saveCurrentBankAccount(initCurrentBankAccount.getInitialBalance(), initCurrentBankAccount.getOverDraft(), initCurrentBankAccount.getCustumerId());
	}
	@PutMapping("/custumers/{id}")
	public CustumerDTO deleteCustumer(@PathVariable Long id,@RequestBody CustumerDTO custumerDTO ) throws BankAccountNotFoundException, BalanceNotSufficientException{
		custumerDTO.setId(id);
		return bankAccountService.updateCustumer(custumerDTO);
	}
	@DeleteMapping("/custumers/{id}")
	public void deleteCustumer(@PathVariable(name = "id")Long id) {
		
		bankAccountService.deleteCustumer(id);
	}
}
