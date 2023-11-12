package org.bnk.exls.entities;

import java.util.Date;
import java.util.List;

import org.bnk.exls.enums.AccountStatus;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
@Data @AllArgsConstructor @NoArgsConstructor
public class BankAccount {
	@Id
	private String id;
	private double balance;
	private Date createdAt;
	private String currency;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	@ManyToOne
	private Custumer custumer;
	@OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
	List<AccountOperation> accountOperations;

}
