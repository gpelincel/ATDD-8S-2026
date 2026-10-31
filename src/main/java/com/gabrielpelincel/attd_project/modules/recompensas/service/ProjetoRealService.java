package com.gabrielpelincel.attd_project.modules.recompensas.service;

import com.gabrielpelincel.attd_project.modules.recompensas.domain.ProjetoReal;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.dto.ProjetoRealResponseDTO;
import com.gabrielpelincel.attd_project.modules.recompensas.repository.ProjetoRealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoRealService {

    private final ProjetoRealRepository projetoRepository;

    public ProjetoRealService(ProjetoRealRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public ProjetoRealResponseDTO criarProjeto(ProjetoRealRequestDTO request) {
        ProjetoReal projeto = new ProjetoReal(request.getNome());
        ProjetoReal projetoSalvo = projetoRepository.save(projeto);
        return new ProjetoRealResponseDTO(projetoSalvo);
    }

    public List<ProjetoRealResponseDTO> listarProjetos() {
        return projetoRepository.findAll()
                .stream()
                .map(ProjetoRealResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProjetoReal buscarPorIdOuNovo(Long idProjeto) {
        if (idProjeto == null) {
            return new ProjetoReal();
        }
        return projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new RuntimeException("Projeto Real não encontrado com o ID: " + idProjeto));
    }

    public ProjetoReal salvar(ProjetoReal projeto) {
        return projetoRepository.save(projeto);
    }
}
