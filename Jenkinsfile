node {
 stage 'Clone the project'
 git 'https://github.com/drone-cloud/ArticlesApp'

 stage("Compilation and Checkstyle") {
  parallel 'Compilation': {
   stage('Compilation') {
    sh 'mvn clean package'
   }
  }, 'Static Analysis': {
   stage("Checkstyle") {
    sh "mvn checkstyle:checkstyle"
   }
  }
 }

 stage("Tests") {
  junit 'target/surefire-reports/**/*.xml'
 }

 stage 'Deployment'{
  sh 'nohup mvn spring-boot:run -Dserver.port=8989 &'
 }

 def pom = readMavenPom file: 'pom.xml'
 // get the current development version
 def targetVersion = getDevVersion()
 print 'target build version...'
 developmentArtifactVersion = "${pom.version}-${targetVersion}"
 print pom.version
}

def getDevVersion() {
 def gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
 def versionNumber;
 if (gitCommit == null) {
  versionNumber = env.BUILD_NUMBER;
 } else {
  versionNumber = gitCommit.take(8);
 }
 print 'build  versions...'
 print versionNumber
 return versionNumber
}
