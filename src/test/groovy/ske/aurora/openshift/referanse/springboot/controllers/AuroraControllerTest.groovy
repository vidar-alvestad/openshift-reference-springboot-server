package ske.aurora.openshift.referanse.springboot.controllers

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.springframework.boot.info.BuildProperties
import org.springframework.http.MediaType

public class AuroraControllerTest extends AbstractControllerTest {

  def buildProperties = new BuildProperties(new Properties().with { put('version', '1.0.0'); return it })

  def controller = new AuroraController(buildProperties, "Aurora Spring Boot Server Referanseapplikasjon")

  def "Aurora endpoint"() {

    expect:
      this.mockMvc.perform(get("/aurora").accept(MediaType.TEXT_HTML))
          .andExpect(status().isOk())
          .andDo(
          document("aurora",
              preprocessResponse(new HtmlPrettyPrinterPreprocessor())
          ))
  }

  @Override
  protected List<Object> getControllersUnderTest() {
    return [controller]
  }
}
