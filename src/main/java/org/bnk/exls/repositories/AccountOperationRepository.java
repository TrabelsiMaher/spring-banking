package org.bnk.exls.repositories;

import java.util.List;

import org.bnk.exls.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

	List<AccountOperation> findByBankAccountId(String accountID);
	Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountID,Pageable pageable );

}
