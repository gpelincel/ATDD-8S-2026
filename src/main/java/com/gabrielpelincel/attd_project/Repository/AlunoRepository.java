package com.gabrielpelincel.attd_project.Repository;

import com.gabrielpelincel.attd_project.Domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByAssinatura(String assinatura);
    List<Aluno> findByCursosConquistadosGreaterThanEqual(int quantidadeCursos);
}