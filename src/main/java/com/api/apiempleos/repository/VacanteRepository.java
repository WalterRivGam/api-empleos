package com.api.apiempleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.apiempleos.entity.Vacante;

public interface VacanteRepository extends JpaRepository<Vacante, Integer> {

}