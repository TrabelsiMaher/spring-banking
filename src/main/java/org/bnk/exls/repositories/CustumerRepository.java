package org.bnk.exls.repositories;

import java.util.List;

import org.bnk.exls.entities.Custumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface CustumerRepository extends JpaRepository<Custumer, Long>{
	
 List<Custumer> findByNameContainsAllIgnoreCase(String keyword);
}
