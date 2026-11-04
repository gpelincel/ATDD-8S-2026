package com.gabrielpelincel.attd_project.modules.cursos.dto;

public class AlunoRequestDTO {
    private String assinaturaBase;

    public AlunoRequestDTO() {
    }

    public AlunoRequestDTO(String assinaturaBase) {
        this.assinaturaBase = assinaturaBase;
    }

    public String getAssinaturaBase() {
        return assinaturaBase;
    }

    public void setAssinaturaBase(String assinaturaBase) {
        this.assinaturaBase = assinaturaBase;
    }
}
