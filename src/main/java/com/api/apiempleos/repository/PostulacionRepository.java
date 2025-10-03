package com.api.apiempleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.apiempleos.entity.Postulacion;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {

}
