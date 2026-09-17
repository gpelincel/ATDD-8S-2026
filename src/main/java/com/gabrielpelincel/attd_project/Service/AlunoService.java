package com.gabrielpelincel.attd_project.Service;

import com.gabrielpelincel.attd_project.DTO.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.DTO.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.Domain.Aluno;
import com.gabrielpelincel.attd_project.Domain.ProjetoReal;
import com.gabrielpelincel.attd_project.Repository.AlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponseDTO criarAluno(AlunoRequestDTO requestDTO) {
        Aluno aluno = new Aluno();

        Aluno alunoSalvo = alunoRepository.save(aluno);
        return new AlunoResponseDTO(alunoSalvo);
    }

    public AlunoResponseDTO conquistarCurso(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + idAluno));

        aluno.conquistarCurso();

        Aluno alunoAtualizado = alunoRepository.save(aluno);

        return new AlunoResponseDTO(alunoAtualizado);
    }

    public AlunoResponseDTO resgatarVoucher(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + idAluno));

        ProjetoReal projeto = new ProjetoReal();

        aluno.resgatarVoucher(projeto);

        if (projeto.isVoucherAplicado()) {
            Aluno alunoAtualizado = alunoRepository.save(aluno);
            return new AlunoResponseDTO(alunoAtualizado);
        } else {
            throw new RuntimeException("O aluno não possui saldo de vouchers para resgate.");
        }
    }
}
