package org.bnk.exls.mappers;

import org.bnk.exls.dtos.AccountOperationDTO;
import org.bnk.exls.dtos.CurrentBankAccountDTO;
import org.bnk.exls.dtos.CustumerDTO;
import org.bnk.exls.dtos.SavingBankAccountDTO;
import org.bnk.exls.entities.AccountOperation;
import org.bnk.exls.entities.CurrentAccount;
import org.bnk.exls.entities.Custumer;
import org.bnk.exls.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

	public CustumerDTO fromCustumer(Custumer custumer) {
		CustumerDTO custumerDTO = new CustumerDTO();
		BeanUtils.copyProperties(custumer, custumerDTO);
		return custumerDTO;
	}

	public Custumer fromCustumerDTO(CustumerDTO custumerDTO) {
		Custumer custumer = new Custumer();
		BeanUtils.copyProperties(custumerDTO, custumer);
		return custumer;
	}

	public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
		
		SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
		BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
		savingBankAccountDTO.setCustumerDTO(fromCustumer(savingAccount.getCustumer()));
		savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
		return savingBankAccountDTO;
	}

	public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {

		SavingAccount savingAccount=new SavingAccount();
		BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
		savingAccount.setCustumer(fromCustumerDTO(savingBankAccountDTO.getCustumerDTO()));
		
		return savingAccount;
	}

	public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
		
		CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
		BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
		currentBankAccountDTO.setCustumerDTO(fromCustumer(currentAccount.getCustumer()));
		currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
		return currentBankAccountDTO;
	}

	public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
		
		CurrentAccount currentAccount=new CurrentAccount();
		BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
		currentAccount.setCustumer(fromCustumerDTO(currentBankAccountDTO.getCustumerDTO()));
		
		return currentAccount;
	}
	
	public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO) {
		AccountOperation accountOperation=new AccountOperation();
		BeanUtils.copyProperties(accountOperationDTO, accountOperation);
		return accountOperation;
	}
	public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation ) {
		AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
		BeanUtils.copyProperties(accountOperation, accountOperationDTO);
		return accountOperationDTO;
		
	}
}
