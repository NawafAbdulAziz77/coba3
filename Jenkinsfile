pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
        }
        stage('Run') {
            steps {
                sh './gradlew bootRun &'
                sh 'sleep 10'
            }
        }
        stage('Test Endpoint') {
            steps {
                sh 'curl "http://localhost:8081/sum?a=2&b=3"'
            }
        }
      stage('Show Tables') {
            steps {
                sh '''
                    echo "Menampilkan daftar tabel di H2 Database:"
                    java -cp build/libs/*.jar:~/.m2/repository/com/h2database/h2/1.4.200/h2-1.4.200.jar \\
                        org.h2.tools.Shell -url "jdbc:h2:file:./tmp/calculator" -user sa -password "" -sql "SHOW TABLES;"
                '''
            }
        }
    }
}
