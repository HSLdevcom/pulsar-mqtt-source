package fi.hsl.pulsarmqttsource;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import java.io.File;

/** Main reads the configuration, starts logging and passes the baton onwards. */
public class Main {

  public static void main(String[] args) {
    Config config = createConfig(args);
    System.out.println(config);
    // Foo.run(conf);
  }

  /**
   * Create a valid Config from a configuration file and environment variables.
   *
   * <p>If the environment variable CONFIG_PATH is set, it determines the path to the configuration
   * file. Otherwise only the other environment variables affect the configuration.
   *
   * <p>Environment variables override values in the configuration file in case of conflict.
   *
   * @param args Command-line arguments. Currently ignored.
   * @return Complete and valid configuration.
   */
  private static Config createConfig(String[] args) {
    Config fileConfig = parseFileConfig();
    Config envConfig = ConfigFactory.parseResources("environment.conf").resolve();
    Config fullConfig;
    if (fileConfig != null) {
      fullConfig = envConfig.withFallback(fileConfig);
    } else {
      fullConfig = envConfig;
    }
    fullConfig.resolve();
    try {
      fullConfig.checkValid(ConfigFactory.parseResources("application.conf").resolve());
    } catch (ConfigException.ValidationFailed e) {
      System.err.println("Validating the given configuration failed.");
      e.printStackTrace();
      System.exit(1);
    }
    return fullConfig;
  }

  /**
   * Parse a Config from the path given by the environment variable CONFIG_PATH. If CONFIG_PATH is
   * unset, return null.
   *
   * @return Either a configuration parsed from the given path or null.
   */
  private static Config parseFileConfig() {
    Config fileConfig = null;
    String configPath = System.getenv("CONFIG_PATH");
    if (configPath != null) {
      try {
        fileConfig = ConfigFactory.parseFile(new File(configPath)).resolve();
      } catch (ConfigException e) {
        System.err.println("Parsing the configuration file from " + configPath + " failed.");
        e.printStackTrace();
        System.exit(1);
      }
    }
    return fileConfig;
  }
}
