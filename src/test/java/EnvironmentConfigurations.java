import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfigurations {

    private static final Dotenv dotenv = Dotenv.load();

    public static String getAccessToken()
    {
        return dotenv.get("access_token");
    }
}
