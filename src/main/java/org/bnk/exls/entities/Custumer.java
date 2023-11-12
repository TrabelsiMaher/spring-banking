package org.bnk.exls.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class Custumer {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	@OneToMany(mappedBy = "custumer",fetch = FetchType.EAGER)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<BankAccount> bankAccounts;

}
