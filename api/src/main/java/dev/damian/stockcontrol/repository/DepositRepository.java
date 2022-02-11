package dev.damian.stockcontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.damian.stockcontrol.model.Deposit;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

	public Optional<Deposit> findByCode(String code);

}
