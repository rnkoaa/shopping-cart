node {
        
    try {
        stage('Checkout From Scm'){
            git 'https://github.com/rnkoaa/shopping-cart.git'            
        }

        stage('Run unit/integration tests'){
            sh 'make unit-test'

            junit 'build/test-results/test/*.xml'
        }
        
        stage('Build application java classes'){
            sh 'make build'
        } 

        stage('Upload Artifact to Nexus'){
            sh 'make upload'
        }

<<<<<<< Updated upstream

=======
        stage("upload"){
            def jarFileName = "shopping-cart-0.0.${BUILD_NUMBER}-SNAPSHOT.jar"
            def artifactName = "${WORKSPACE}/build/libs/${jarFileName}"
            nexusArtifactUploader credentialsId: 'nexus-credentials',
            type: 'jar',
            groupId: 'com.rnkoaa.shop', 
            nexusUrl: 'nexus:8081/nexus', 
            file: "${artifactName}",
            nexusVersion: 'nexus3', 
            protocol: 'http', 
            repository: 'maven-public', 
            version: '1.0.0-SNAPSHOT'
        }
>>>>>>> Stashed changes
    }
    finally {
        // stage 'Collect test reports'
        // step([$class: 'JUnitResultArchiver', testResults: '**/reports/*.xml'])

        // stage 'Clean up'
        // sh 'make clean'
        // sh 'make logout'*/
        // println("Done Building")
        stage("Done"){
            echo "Done Building."
        }

        // stage('Results') {
        //   junit '**/target/surefire-reports/TEST-*.xml'
        //   archive 'target/*.jar'
        // }
    }
}
