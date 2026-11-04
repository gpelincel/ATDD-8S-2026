package com.gabrielpelincel.attd_project.modules.cursos.service;

import com.gabrielpelincel.attd_project.modules.cursos.domain.Aluno;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoRequestDTO;
import com.gabrielpelincel.attd_project.modules.cursos.dto.AlunoResponseDTO;
import com.gabrielpelincel.attd_project.modules.cursos.repository.AlunoRepository;
import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.service.RecompensaService;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final RecompensaService recompensaService;

    public AlunoService(AlunoRepository alunoRepository, RecompensaService recompensaService) {
        this.alunoRepository = alunoRepository;
        this.recompensaService = recompensaService;
    }

    public AlunoResponseDTO buscarAluno(Long idAluno) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + idAluno));

        return new AlunoResponseDTO(aluno);
    }

    public AlunoResponseDTO criarAluno(AlunoRequestDTO requestDTO) {
        Aluno aluno = new Aluno();
        if (requestDTO != null && requestDTO.getAssinaturaBase() != null && !requestDTO.getAssinaturaBase().isBlank()) {
            aluno.setAssinatura(requestDTO.getAssinaturaBase());
        }

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

        if (aluno.getVouchers() <= 0) {
            throw new RuntimeException("Aluno não possui saldo de vouchers disponível para resgate.");
        }

        ProjetoReal projeto = new ProjetoReal();
        aluno.resgatarVoucher(projeto);
        recompensaService.resgatarVoucherEmProjeto(1, projeto);

        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return new AlunoResponseDTO(alunoAtualizado);
    }
}
