package no.skatteetaten.aurora.openshift.reference.springboot

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.GitSource.gitSource

import org.junit.Before
import org.junit.Test

import com.lesfurets.jenkins.unit.BasePipelineTest

class JenkinsfileTest extends BasePipelineTest {

  @Before
  void setUp() {
    super.setUp()

    def clonePath = "repo"
    def library = library()
        .name('aurora-pipeline-libraries')
        .retriever(gitSource('https://git.aurora.skead.no/scm/ao/aurora-pipeline-libraries.git'))
        .targetPath(clonePath)
        .defaultVersion("feature/AOS-1731")
        .allowOverride(true)
        .implicit(false)
        .build()
    //helper.registerSharedLibrary(library)

    helper.registerAllowedMethod("modernSCM", [LinkedHashMap.class], {c -> library})
    helper.registerAllowedMethod("library", [LinkedHashMap.class], {c -> library})
  }

  @Test
  void should_execute_without_errors() throws Exception {
    runScript("Jenkinsfile")
    printCallStack()
  }
}