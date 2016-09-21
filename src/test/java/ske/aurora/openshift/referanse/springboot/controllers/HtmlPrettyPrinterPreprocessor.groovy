package ske.aurora.openshift.referanse.springboot.controllers

import org.springframework.http.MediaType
import org.springframework.restdocs.operation.preprocess.ContentModifier
import org.springframework.restdocs.operation.preprocess.ContentModifyingOperationPreprocessor
import org.w3c.dom.Document
import org.w3c.tidy.Tidy

class HtmlPrettyPrinterPreprocessor extends ContentModifyingOperationPreprocessor {
  HtmlPrettyPrinterPreprocessor() {
    super(new HtmlPrettyPrinter())
  }

  private static class HtmlPrettyPrinter implements ContentModifier {

    @Override
    byte[] modifyContent(byte[] bytes, MediaType mediaType) {
      new Tidy().with {
        XHTML = true
        indentContent = true
        Document htmlDOM = parseDOM(new ByteArrayInputStream(bytes), null)
        OutputStream out = new ByteArrayOutputStream()
        pprint(htmlDOM, out)
        out.toByteArray()
      }
    }
  }
}
