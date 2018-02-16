package no.skatteetaten.aurora.openshift.reference.springboot

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.GitSource.gitSource
import static com.lesfurets.jenkins.unit.MethodSignature.method

import org.junit.Before
import org.junit.Test

import com.lesfurets.jenkins.unit.BasePipelineTest

//class JenkinsfileTest extends BasePipelineTest {
class JenkinsfileTest extends no.skatteetaten.aurora.openshift.reference.springboot.BasePipelineTest {

  @Before
  void setUp() {
    super.setUp()

    binding.setVariable('env', [PATH : '/path/something', someother : 'somethang'])
    binding.setVariable('scm', [$class: 'GitSCM', userRemoteConfigs: [[url: 'something']]])

    def clonePath = "repo"
    def library = library()
        .name('aurora-pipeline-libraries')
        .retriever(gitSource('https://git.aurora.skead.no/scm/ao/aurora-pipeline-libraries.git'))
        .targetPath(clonePath)
        .defaultVersion("feature/AOS-1731")
        .allowOverride(true)
        .implicit(false)
        .build()
    helper.registerSharedLibrary(library)

    helper.registerAllowedMethod("tool", [String.class], {c -> "mvn"})
    helper.registerAllowedMethod(method("readFile", String.class), { file ->
      //return Files.contentOf(new File(file), Charset.forName("UTF-8"))
      return 'fileContentDummyString'
    })
    helper.registerAllowedMethod("readMavenPom", [LinkedHashMap.class], {c -> [version : "version1"]})
  }

  @Test
  void should_execute_without_errors() throws Exception {
    println("Running Jenkinsfile test")

    runScript("Jenkinsfile")
    printCallStack()
  }
}