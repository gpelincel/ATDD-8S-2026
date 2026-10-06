<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const aluno = ref(null)
const erro = ref('')

const BASE_URL = 'http://localhost:8080/api/alunos'

const carregarAluno = async () => {
  try {
    const res = await axios.get(`${BASE_URL}/1`)
    aluno.value = res.data
    erro.value = ''
  } catch (e) {
    if (e.response && e.response.status === 404) {
      criarAlunoInicial()
    } else {
      erro.value = 'Erro de conexão. O Spring Boot está rodando?'
    }
  }
}

const criarAlunoInicial = async () => {
  try {
    await axios.post(BASE_URL, {})
    carregarAluno()
  } catch (e) {
    erro.value = 'Erro ao criar o aluno no banco de dados.'
  }
}

const conquistarCurso = async () => {
  try {
    await axios.post(`${BASE_URL}/1/conquistar-curso`)
    carregarAluno()
  } catch (e) {
    erro.value = 'Erro ao conquistar curso. Limite alcançado ou regra negada.'
  }
}

onMounted(() => {
  carregarAluno()
})
</script>

<template>
  <main class="container">
    <h1>EAD Gamificado</h1>

    <div v-if="erro" class="error">{{ erro }}</div>

    <div v-if="aluno" class="card">
      <h2>Status do Aluno</h2>

      <div class="status-grid">
        <p><strong>Assinatura:</strong> <span class="badge">{{ aluno.assinatura }}</span></p>
        <p><strong>Cursos:</strong> {{ aluno.cursosConquistados }}</p>
        <p><strong>Moedas:</strong> 🪙 {{ aluno.moedas }}</p>
        <p><strong>Vouchers:</strong> 🎟️ {{ aluno.vouchers }}</p>
      </div>

      <button @click="conquistarCurso" class="btn-acao">
        📚 Concluir Novo Curso
      </button>
    </div>
    <div v-else-if="!erro" class="loading">
      Carregando dados da API...
    </div>
  </main>
</template>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  max-width: 500px;
  margin: 40px auto;
  text-align: center;
}
.card {
  border: 2px solid #ddd;
  padding: 20px;
  border-radius: 12px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
.status-grid {
  text-align: left;
  margin: 20px 0;
  padding: 15px;
  background: white;
  border-radius: 8px;
}
.badge {
  background: #ffd700;
  padding: 4px 10px;
  border-radius: 20px;
  font-weight: bold;
  color: #333;
}
.error {
  color: white;
  background: #dc3545;
  padding: 10px;
  border-radius: 8px;
  margin-bottom: 20px;
}
.btn-acao {
  padding: 12px 24px;
  background: #28a745;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: 0.2s;
}
.btn-acao:hover {
  background: #218838;
  transform: scale(1.05);
}
</style>