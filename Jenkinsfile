pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '5'))
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
        timestamps()
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'hml', 'prod'], description: 'Ambiente de destino para execução da pipeline')
        booleanParam(name: 'RUN_FRONTEND_BUILD', defaultValue: true, description: 'Habilita ou desabilita o build do frontend Vue')
        booleanParam(name: 'PUBLISH_DOCKER_IMAGE', defaultValue: false, description: 'Realiza o push da imagem Docker para o registry configurado')
    }

    environment {
        APP_NAME          = 'attd_project'
        DOCKER_IMAGE      = "gamificacao/${env.APP_NAME}"
        DOCKER_TAG        = "${env.BUILD_NUMBER}-${env.GIT_COMMIT ? env.GIT_COMMIT.take(7) : 'latest'}"
        MAVEN_OPTS        = '-Dfile.encoding=UTF-8 -XX:+TieredCompilation -XX:TieredStopAtLevel=1'
    }

    stages {
        stage('Checkout & Environment Info') {
            steps {
                echo "========== INICIANDO CI/CD - EDUCAÇÃO CONTINUADA GAMIFICADA =========="
                echo "Ambiente: ${params.ENVIRONMENT} | Branch: ${env.BRANCH_NAME ?: env.GIT_BRANCH} | Build: #${env.BUILD_NUMBER}"
            }
        }

        stage('Build & Test') {
            parallel {
                stage('Backend Flow') {
                    stages {
                        stage('Backend: Clean & Compile') {
                            steps {
                                echo '--> Compilando o monolito modular Java 17...'
                                script {
                                    if (isUnix()) {
                                        sh 'chmod +x mvnw || true'
                                        sh './mvnw clean compile -B -DskipTests'
                                    } else {
                                        bat '.\\mvnw.cmd clean compile -B -DskipTests'
                                    }
                                }
                            }
                        }

                        stage('Backend: Unit Tests & Coverage') {
                            steps {
                                echo '--> Executando testes (JUnit 5, Mockito) e JaCoCo...'
                                script {
                                    if (isUnix()) {
                                        sh './mvnw test -B'
                                    } else {
                                        bat '.\\mvnw.cmd test -B'
                                    }
                                }
                            }
                            post {
                                always {
                                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                    recordCoverage(
                                        tools: [[toolId: 'jacoco', pattern: 'target/site/jacoco/jacoco.xml']],
                                        qualityGates: [
                                            [threshold: 80.0, metric: 'INSTRUCTION', baseline: 'PROJECT', criticality: 'UNSTABLE']
                                        ]
                                    )
                                    publishHTML(target: [
                                        allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true,
                                        reportDir: 'target/site/jacoco', reportFiles: 'index.html', reportName: 'JaCoCo Report'
                                    ])
                                }
                            }
                        }

                        stage('Backend: Package Artifact') {
                            steps {
                                echo '--> Empacotando aplicação Spring Boot (.jar)...'
                                script {
                                    if (isUnix()) {
                                        sh './mvnw package -B -DskipTests'
                                    } else {
                                        bat '.\\mvnw.cmd package -B -DskipTests'
                                    }
                                }
                                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: false
                            }
                        }
                    }
                }

                stage('Frontend Flow') {
                    when { expression { return params.RUN_FRONTEND_BUILD } }
                    stages {
                        stage('Frontend: Build Vue 3') {
                            steps {
                                echo '--> Instalando dependências e compilando o frontend Vite/Vue...'
                                dir('frontend-vue') {
                                    script {
                                        if (isUnix()) {
                                            sh 'npm ci || npm install'
                                            sh 'npm run build'
                                        } else {
                                            bat 'npm ci || npm install'
                                            bat 'npm run build'
                                        }
                                    }
                                }
                            }
                            post {
                                success {
                                    archiveArtifacts artifacts: 'frontend-vue/dist/**', allowEmptyArchive: true
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('Docker: Build Image') {
            steps {
                echo "--> Construindo imagem Docker: ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}..."
                script {
                    if (isUnix()) {
                        sh "docker build -t ${env.DOCKER_IMAGE}:${env.DOCKER_TAG} -t ${env.DOCKER_IMAGE}:latest ."
                    } else {
                        bat "docker build -t ${env.DOCKER_IMAGE}:${env.DOCKER_TAG} -t ${env.DOCKER_IMAGE}:latest ."
                    }
                }
            }
        }

        stage('Docker: Push to Registry') {
            when { expression { return params.PUBLISH_DOCKER_IMAGE } }
            steps {
                echo "--> Enviando imagem para o container registry..."
                script {
                    echo "Imagem ${env.DOCKER_IMAGE}:${env.DOCKER_TAG} pronta para publicação."
                    // sh 'docker push ...'
                }
            }
        }

        stage('Deploy: Homologation / Production') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                    expression { return params.ENVIRONMENT == 'dev' || params.ENVIRONMENT == 'hml' }
                }
            }
            stages {
                stage('Approval for Production') {
                    when { expression { return params.ENVIRONMENT == 'prod' } }
                    steps {
                        input message: "Confirma a implantação da versão ${env.DOCKER_TAG} em PRODUÇÃO?", ok: 'Aprovar Deploy'
                    }
                }

                stage('Execute Deploy') {
                    steps {
                        echo "--> Executando deploy via Docker Compose em [${params.ENVIRONMENT}]..."
                        script {
                            if (isUnix()) {
                                sh "docker compose down || true"
                                sh "docker compose up -d"
                            } else {
                                bat "docker compose down || true"
                                bat "docker compose up -d"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline concluída com SUCESSO! Build #${env.BUILD_NUMBER} (${env.DOCKER_TAG})."
        }
        failure {
            echo "❌ Pipeline FALHOU na execução do Build #${env.BUILD_NUMBER}."
        }
        always {
            cleanWs deleteDirs: true, notFailBuild: true
        }
    }
}