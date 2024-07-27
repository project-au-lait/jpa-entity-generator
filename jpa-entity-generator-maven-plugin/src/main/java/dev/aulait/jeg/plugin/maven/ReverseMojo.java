package dev.aulait.jeg.plugin.maven;

import dev.aulait.jeg.core.interfaces.Main;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "reverse")
public class ReverseMojo extends AbstractMojo {

  @Parameter(property = "configFilePath")
  private String configFilePath;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    Main main = new Main();
    main.execute(configFilePath);
  }
}
