node {
        
    try {
        stage('Checkout From Scm'){
            git 'https://github.com/rnkoaa/shopping-cart.git'            
        }

        stage('Run unit/integration tests'){
            //sh './gradlew test'
            echo 'Test in this stage'
        }
        
        stage('Build application java classes'){
            sh './gradlew assemble'
        }
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
