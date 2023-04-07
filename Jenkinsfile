pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("docker_password")
        GITHUB_TOKEN = credentials("github_token")
        GITHUB_TOKEN = credentials("github_token")
    }

    stages {
        stage('Tag image') {


            steps {
                script {
                sh([script: 'git fetch --tag', returnStdout: true]).trim()
                env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                sh([script: 'git fetch --tag', returnStdout: true]).trim()
                env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }
            }
        }

        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
                sh "docker build -t gabi2/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
                sh "git tag ${env.IMAGE_TAG}"
                sh "git push https://$GITHUB_TOKEN@github.com/AnotherRollyCuzTheClassicOneWasTaken/service.git ${env.IMAGE_TAG}"
            }
        }

        stage('Lab 7') {
            steps {
                "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
                sh './gradlew testE2E'
            }
        }

        stage('Lab 7') {
            steps {
                "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
                sh './gradlew testE2E'
            }
        }
    }
}
