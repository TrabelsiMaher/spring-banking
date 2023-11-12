package org.bnk.exls;


import org.bnk.exls.services.BankAccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
	
		SpringApplication.run(EbankingBackendApplication.class, args);
	
	}
	@Bean 
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	@Bean
//CommandLineRunner start(AccountOperationRepository accountOperationRepository, CustumerRepository custumerRepository,BankAccountRepository bankAccountRepository,BankAccountRepository accountRepository) {
		CommandLineRunner start(BankAccountServiceImpl bankAccountService) {

	return args->{
		// debut service testing
		/*
		Custumer custumer=new Custumer();
		custumer.setName("Bia 6");
		custumer.setEmail("bia6@gmail.com");
		
		bankAccountService.saveCustumer(custumer);
		BankAccount bankAccount=bankAccountService.saveCurrentBankAccount(9000, 6000, custumer.getId());
		try {
			
		
		bankAccountService.debit(bankAccount.getId(), 10000," debiter le compte" );
		} catch (BalanceNotSufficientException e) {
			e.printStackTrace();
		}
*/// fin service test
		
		
		/// ================================
		
		// debut jpa testing
		/*
Stream.of("Maher","Nada","Zeyd","Amrou").forEach(name->{
	Custumer custumer=new Custumer();
	custumer.setName(name);
	custumer.setEmail(name+"@gmail")
	;
	custumerRepository.save(custumer);
});		
	custumerRepository.findAll().forEach(custumer ->{
	CurrentAccount currentAccount=new CurrentAccount();
	currentAccount.setId(UUID.randomUUID().toString());
	currentAccount.setBalance(Math.random()*1000);
	currentAccount.setCreatedAt(new Date());
	currentAccount.setStatus(AccountStatus.CREATED);
	currentAccount.setCustumer(custumer);
	currentAccount.setOverDraft(8000);
	currentAccount.setCurrency("TND");
	bankAccountRepository.save(currentAccount);
	SavingAccount savingAccount=new SavingAccount();
	savingAccount.setId(UUID.randomUUID().toString());
	savingAccount.setBalance(Math.random()*1000);
	savingAccount.setCreatedAt(new Date());
	savingAccount.setStatus(AccountStatus.CREATED);
	savingAccount.setCustumer(custumer);
	savingAccount.setSavingRate(5.8);
	savingAccount.setCurrency("TND");

	bankAccountRepository.save(savingAccount);
	});	
	
		bankAccountRepository.findAll().forEach(cmp->
		{
			for (int i = 0; i < 11; i++) {
		
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setOperationDate(new Date());
		accountOperation.setAmmount(120000*Math.random());
		accountOperation.setType(Math.random()<0.5?OperationType.CREDIT:OperationType.DEBIT);
		accountOperation.setBankAccount(cmp);
		accountOperationRepository.save(accountOperation);
			}
			});	
		*/ 
	};
}

	//fin commandLineRunner
}
