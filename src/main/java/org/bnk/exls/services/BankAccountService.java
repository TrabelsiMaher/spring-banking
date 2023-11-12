package org.bnk.exls.services;

import java.util.List;

import org.bnk.exls.dtos.AccountHistoryDTO;
import org.bnk.exls.dtos.AccountNewBalanceDTO;
import org.bnk.exls.dtos.AccountOperationDTO;
import org.bnk.exls.dtos.BankAccountDTO;
import org.bnk.exls.dtos.CurrentBankAccountDTO;
import org.bnk.exls.dtos.CustumerDTO;
import org.bnk.exls.dtos.NewTransfertDTO;
import org.bnk.exls.dtos.CreditDebitDTO;
import org.bnk.exls.dtos.SavingBankAccountDTO;
import org.bnk.exls.dtos.TransfertDTO;
import org.bnk.exls.exceptions.BalanceNotSufficientException;
import org.bnk.exls.exceptions.BankAccountNotFoundException;
import org.bnk.exls.exceptions.CustumerNotFoundException;

public interface BankAccountService {
	CustumerDTO saveCustumer(CustumerDTO custumer);
	CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance,double overDraft, Long custumerId) throws CustumerNotFoundException;
	SavingBankAccountDTO saveSavingBankAccount(double initialBalance,double savingRate, Long custumerId) throws CustumerNotFoundException;

	List<CustumerDTO> listCustumers();
	BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
	AccountNewBalanceDTO debit(CreditDebitDTO creditDebitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException;
	AccountNewBalanceDTO credit(CreditDebitDTO creditDebitDTO) throws BankAccountNotFoundException;
	NewTransfertDTO transfert(TransfertDTO transfertDTO) throws BankAccountNotFoundException, BalanceNotSufficientException;
CustumerDTO updateCustumer(CustumerDTO custumerDTO) throws BankAccountNotFoundException, BalanceNotSufficientException;
void deleteCustumer(Long CustumerId);
List<BankAccountDTO> bankAccountList();
List<AccountOperationDTO> accountHistory(String accountID);
AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
CustumerDTO getCustumer(Long custumerId) throws CustumerNotFoundException;
List<CustumerDTO> searchCustomerByKeyword(String keyword);
}
