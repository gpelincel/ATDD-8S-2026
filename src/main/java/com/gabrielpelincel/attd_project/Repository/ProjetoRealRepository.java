package com.gabrielpelincel.attd_project.Repository;

import com.gabrielpelincel.attd_project.Domain.ProjetoReal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRealRepository extends JpaRepository<ProjetoReal, Long> {

}
