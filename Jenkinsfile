node {
        
    try {
        stage 'Checkout From Scm'
        git 'https://github.com/rnkoaa/shopping-cart.git'

        stage 'Run unit/integration tests'
        //sh './gradlew test'
        echo 'Test in this stage'

        stage 'Build application java classes'
        sh './gradlew assemble'

        /*stage 'Create release environment and run acceptance tests'
        sh 'make release'

        stage 'Tag and publish release image'
        sh "make tag latest \$(git rev-parse --short HEAD) \$(git tag --points-at HEAD)"
        sh "make buildtag master \$(git tag --points-at HEAD)"
        withEnv(["DOCKER_USER=${DOCKER_USER}",
                 "DOCKER_PASSWORD=${DOCKER_PASSWORD}",
                 "DOCKER_EMAIL=${DOCKER_EMAIL}"]) {
            sh "make login"
        }
        sh "make publish"

        stage 'Deploy release'
        sh "printf \$(git rev-parse --short HEAD) > tag.tmp"
        def imageTag = readFile 'tag.tmp'
        build job: DEPLOY_JOB, parameters: [[
                                                    $class: 'StringParameterValue',
                                                    name: 'IMAGE_TAG',
                                                    value: 'jmenga/todobackend:' + imageTag
                                            ]]*/
    }
    finally {
        /*stage 'Collect test reports'
        // step([$class: 'JUnitResultArchiver', testResults: '**/reports/*.xml'])

        // stage 'Clean up'
        // sh 'make clean'
        // sh 'make logout'*/
        // println("Done Building")
    }
}
