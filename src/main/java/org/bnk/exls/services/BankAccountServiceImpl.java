package org.bnk.exls.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import org.bnk.exls.entities.AccountOperation;
import org.bnk.exls.entities.BankAccount;
import org.bnk.exls.entities.CurrentAccount;
import org.bnk.exls.entities.Custumer;
import org.bnk.exls.entities.SavingAccount;
import org.bnk.exls.enums.AccountStatus;
import org.bnk.exls.enums.OperationType;
import org.bnk.exls.exceptions.BalanceNotSufficientException;
import org.bnk.exls.exceptions.BankAccountNotFoundException;
import org.bnk.exls.exceptions.CustumerNotFoundException;
import org.bnk.exls.mappers.BankAccountMapperImpl;
import org.bnk.exls.repositories.AccountOperationRepository;
import org.bnk.exls.repositories.BankAccountRepository;
import org.bnk.exls.repositories.CustumerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{
/*
 *  @Autowired est dépricated donc à remplacer par un constructeur avec args
 *  privée pour faire l'injection des dépendances
 *  et donc , à travers lombok @AllArgsConstructor
 * 
 */
	//@Autowired
	private CustumerRepository custumerRepository;
	//@Autowired
	private BankAccountRepository bankAccountRepository;
	//@Autowired
	private AccountOperationRepository accountOperationRepository;
	private BankAccountMapperImpl dtoMapper;
	// Logger log=LoggerFactory.getLogger(this.getClass().getName());
	// remplacer par lombok via ====>>>>  @Slf4j
	@Override
	public CustumerDTO saveCustumer(CustumerDTO custumerDTO) {
		log.info("saving new Custumer");
		Custumer savedCustumer=custumerRepository.save(dtoMapper.fromCustumerDTO(custumerDTO));
		return dtoMapper.fromCustumer(savedCustumer);
	}

	@Override
	public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long custumerId) throws CustumerNotFoundException {
		
		Custumer custumer=custumerRepository.findById(custumerId).orElse(null);
		if(custumer==null) {
			throw new CustumerNotFoundException("Custumer not found ...");
		}
		CurrentAccount bankAccount =new CurrentAccount();
		bankAccount.setCreatedAt(new Date());
		bankAccount.setCurrency("TND");
		bankAccount.setId(UUID.randomUUID().toString());
		bankAccount.setCreatedAt(new Date());
		bankAccount.setBalance(initialBalance);
		bankAccount.setCustumer(custumer);
		bankAccount.setStatus(AccountStatus.CREATED);
		bankAccount.setOverDraft(overDraft);
		CurrentAccount savedCurrentAccount=bankAccountRepository.save(bankAccount);
		return  dtoMapper.fromCurrentBankAccount(savedCurrentAccount);
	}

	@Override
	public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double savingRate,Long custumerId) throws CustumerNotFoundException {
		Custumer custumer=custumerRepository.findById(custumerId).orElse(null);
		if(custumer==null) {
			throw new CustumerNotFoundException("Custumer not found ...");
		}
		SavingAccount bankAccount =new SavingAccount();
		bankAccount.setCreatedAt(new Date());
		bankAccount.setCurrency("TND");
		bankAccount.setId(UUID.randomUUID().toString());
		bankAccount.setCreatedAt(new Date());
		bankAccount.setBalance(initialBalance);
		bankAccount.setCustumer(custumer);
		bankAccount.setSavingRate(savingRate);
		bankAccount.setStatus(AccountStatus.CREATED);

		SavingAccount savedSavingAccount=bankAccountRepository.save(bankAccount);
		return dtoMapper.fromSavingBankAccount(savedSavingAccount);
	}
	@Override
	public List<CustumerDTO> listCustumers() {
		List<CustumerDTO> custumerDTOs=custumerRepository.findAll().stream().map(custumer->dtoMapper.fromCustumer(custumer)).collect(Collectors.toList());
		return custumerDTOs;
	}

	@Override
	public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found !!!"));
		if(bankAccount instanceof CurrentAccount)
			return (BankAccountDTO)dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
		else 
		return (BankAccountDTO) dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
	}

	@Override
	public AccountNewBalanceDTO debit(CreditDebitDTO creditDebitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		BankAccount bankAccount=bankAccountRepository.findById(creditDebitDTO.getAccountId()).orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found !!!"));

		if(bankAccount.getBalance()<creditDebitDTO.getAmmount()) {
			throw new BalanceNotSufficientException("Balance Not Sufficient !!!");
		}
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.DEBIT);
		accountOperation.setDescription(creditDebitDTO.getDescription());
		accountOperation.setOperationDate(new Date());
		accountOperation.setBankAccount(bankAccount);
		accountOperation.setAmmount(creditDebitDTO.getAmmount());
		bankAccount.setBalance(bankAccount.getBalance()-creditDebitDTO.getAmmount());
		bankAccountRepository.save(bankAccount);
		accountOperationRepository.save(accountOperation);
		AccountNewBalanceDTO accountNewBalanceDTO=new AccountNewBalanceDTO();
		accountNewBalanceDTO.setAccountId(creditDebitDTO.getAccountId());
		accountNewBalanceDTO.setType(OperationType.DEBIT);
		accountNewBalanceDTO.setNewBalance(bankAccount.getBalance());
		return accountNewBalanceDTO;
	}

	@Override
	public AccountNewBalanceDTO credit(CreditDebitDTO creditDebitDTO) throws BankAccountNotFoundException {
		BankAccount bankAccount=bankAccountRepository.findById(creditDebitDTO.getAccountId()).orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found !!!"));

	
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.CREDIT);
		accountOperation.setDescription(creditDebitDTO.getDescription());
		accountOperation.setOperationDate(new Date());
		accountOperation.setBankAccount(bankAccount);
		accountOperation.setAmmount(creditDebitDTO.getAmmount());
		bankAccount.setBalance(bankAccount.getBalance()+creditDebitDTO.getAmmount());
		bankAccountRepository.save(bankAccount);
		accountOperationRepository.save(accountOperation);
		AccountNewBalanceDTO accountNewBalanceDTO=new AccountNewBalanceDTO();
		accountNewBalanceDTO.setAccountId(creditDebitDTO.getAccountId());
		accountNewBalanceDTO.setType(OperationType.CREDIT);
		accountNewBalanceDTO.setNewBalance(bankAccount.getBalance());
		return accountNewBalanceDTO;
	}

	@Override
	public NewTransfertDTO transfert(TransfertDTO transfertDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		
		
		CreditDebitDTO creditDebitDTO=new CreditDebitDTO(transfertDTO.getAccountIDSource(),transfertDTO.getAmmount(),"Transfert TO "+transfertDTO.getAccountIdDestination()+" ***"+transfertDTO.getDescription()+" ***");
		CreditDebitDTO creditDebitDTOForCredit=new CreditDebitDTO(transfertDTO.getAccountIdDestination(),transfertDTO.getAmmount(),"Transfert FROM "+transfertDTO.getAccountIDSource()+" ***"+transfertDTO.getDescription()+" ***");

		NewTransfertDTO newTransfertDTO=new NewTransfertDTO();
		
		newTransfertDTO.setAccountNewBalanceDTOSource(debit(creditDebitDTO));
		newTransfertDTO.setAccountNewBalanceDTODestination(credit(creditDebitDTOForCredit));
		
		return newTransfertDTO;
	}

	@Override
	public List<BankAccountDTO> bankAccountList(){
		
		return bankAccountRepository.findAll().stream().map(elt->
		{ if(elt instanceof SavingAccount) {
			return dtoMapper.fromSavingBankAccount((SavingAccount) elt);
		}else return dtoMapper.fromCurrentBankAccount((CurrentAccount) elt);
			
		}).collect(Collectors.toList());
	}
	@Override
	public CustumerDTO getCustumer(Long custumerId) throws CustumerNotFoundException {
		Custumer custumer=custumerRepository.findById(custumerId).orElseThrow(() -> new CustumerNotFoundException("Custumer not Found"));
		return dtoMapper.fromCustumer(custumer);
	}
	@Override
	public CustumerDTO updateCustumer(CustumerDTO custumerDTO) {
		
		Custumer savedCustumer=custumerRepository.save(dtoMapper.fromCustumerDTO(custumerDTO));
		return dtoMapper.fromCustumer(savedCustumer);
	}
	@Override
	public void deleteCustumer(Long CustumerId) {
		
		custumerRepository.deleteById(CustumerId);
	}
@Override
	public List<AccountOperationDTO> accountHistory(String accountID){
		
	return	accountOperationRepository.findByBankAccountId(accountID).stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
	}

@Override
public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
	
	BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
	if(bankAccount==null)
		throw new BankAccountNotFoundException("Bank account Introuvable");
	Page<AccountOperation> accountOperationPage= accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId,PageRequest.of(page, size));
	List<AccountOperationDTO> accountOperationDTOs=accountOperationPage
													.getContent()
													.stream()
													.map(elt->dtoMapper.fromAccountOperation(elt))
													.collect(Collectors.toList());
	AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
	accountHistoryDTO.setAccoundId(accountId);
	accountHistoryDTO.setCurrentPage(page);
	accountHistoryDTO.setPageSize(size);
	accountHistoryDTO.setAccountOperationDTOs(accountOperationDTOs);
	accountHistoryDTO.setBalance(bankAccount.getBalance());
	accountHistoryDTO.setTotalPages(accountOperationPage.getTotalPages());
	accountHistoryDTO.setTotalOpearations((int) accountOperationPage.getTotalElements());

	return accountHistoryDTO;
}

@Override
public List<CustumerDTO> searchCustomerByKeyword(String keyword) {
	return custumerRepository.findByNameContainsAllIgnoreCase(keyword).stream().map(elt->dtoMapper.fromCustumer(elt)).collect(Collectors.toList());
}
}
