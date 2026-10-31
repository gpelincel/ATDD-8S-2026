package com.gabrielpelincel.attd_project.modules.recompensas.repository;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRealRepository extends JpaRepository<ProjetoReal, Long> {
}
