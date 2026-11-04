pipeline {
    agent any

    options {
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
    }

    stages {
        stage('Testes (Unitários e Integração)') {
            steps {
                echo '--> Executando suíte de testes do projeto...'
                script {
                    if (isUnix()) {
                        sh 'chmod +x mvnw || true'
                        sh './mvnw clean test -B'
                    } else {
                        bat '.\\mvnw.cmd clean test -B'
                    }
                }
            }
        }

        stage('Build API (Package)') {
            steps {
                echo '--> Empacotando aplicação Spring Boot (.jar)...'
                script {
                    if (isUnix()) {
                        sh './mvnw package -B -DskipTests'
                    } else {
                        bat '.\\mvnw.cmd package -B -DskipTests'
                    }
                }
            }
        }

        stage('Deploy Local (Docker Compose)') {
            steps {
                echo '--> Subindo banco de dados e API via Docker Compose...'
                script {
                    if (isUnix()) {
                        sh 'docker compose down || true'
                        sh 'docker compose up -d --build'
                    } else {
                        bat 'docker compose down || true'
                        bat 'docker compose up -d --build'
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline concluída com SUCESSO! Todos os testes passaram."
        }
        failure {
            echo "❌ Pipeline FALHOU. Verifique os logs do estágio de Testes."
        }
        always {
            cleanWs deleteDirs: true, notFailBuild: true
        }
    }
}