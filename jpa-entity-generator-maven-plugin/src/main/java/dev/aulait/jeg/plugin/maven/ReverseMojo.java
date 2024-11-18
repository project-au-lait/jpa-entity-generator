package dev.aulait.jeg.plugin.maven;

import dev.aulait.jeg.core.infra.config.RuntimeConfig;
import dev.aulait.jeg.core.infra.util.ModelUtils;
import dev.aulait.jeg.core.interfaces.Main;
import java.nio.file.Path;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "reverse")
@Getter
public class ReverseMojo extends AbstractMojo {

  @Parameter(property = "configFilePath")
  private String configFilePath;

  @Parameter(property = "jdbcUrl")
  private String jdbcUrl;

  @Parameter(property = "jdbcUsername")
  private String jdbcUsername;

  @Parameter(property = "jdbcPassword")
  private String jdbcPassword;

  @Parameter(property = "outputDir")
  private String outputDir;

  @Parameter(property = "metadataOutputDir")
  private String metadataOutputDir;

  @Component private MavenProject project;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    RuntimeConfig config = ModelUtils.map(this, RuntimeConfig.class);

    Main main = new Main();
    main.setConfigFilePath(adjustConfigFilePath());
    main.setRuntimeConfig(config);
    main.execute();
  }

  private String adjustConfigFilePath() {
    if (StringUtils.isEmpty(configFilePath)) {
      return "";
    } else if (Path.of(configFilePath).isAbsolute()) {
      return configFilePath;
    }
    return project.getBasedir().toPath().resolve(Path.of(configFilePath)).toString();
  }
}
