package org.bnk.exls.repositories;

import org.bnk.exls.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String>{

}
