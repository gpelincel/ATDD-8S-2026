package com.gabrielpelincel.attd_project.Service;

import com.gabrielpelincel.attd_project.DTO.ProjetoRealRequestDTO;
import com.gabrielpelincel.attd_project.DTO.ProjetoRealResponseDTO;
import com.gabrielpelincel.attd_project.Domain.ProjetoReal;
import com.gabrielpelincel.attd_project.Repository.ProjetoRealRepository;
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
}