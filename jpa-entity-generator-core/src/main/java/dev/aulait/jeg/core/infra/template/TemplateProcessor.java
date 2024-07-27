package dev.aulait.jeg.core.infra.template;

import dev.aulait.jeg.core.infra.formatter.Formattable;
import dev.aulait.jeg.core.infra.textfile.TextFileModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Setter;

public class TemplateProcessor {

  @Setter private Formattable formatter;

  public List<TextFileModel> process(List<? extends TemplateModel> templates) {
    return templates.stream().map(this::template2text).collect(Collectors.toList());
  }

  TextFileModel template2text(TemplateModel templateModel) {
    TextFileModel textFile = new TextFileModel();

    textFile.setPath(templateModel.getFilePath());

    String text = TemplateUtils.process(templateModel.getTemplateName(), templateModel);

    if (formatter != null) {
      text = formatter.format(text);
    }

    textFile.setText(text);

    return textFile;
  }
}
