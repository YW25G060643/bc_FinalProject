import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrumbManager {
    private static final String CRUMB_URL = "https://query1.finance.yahoo.com/v1/test/getcrumb";
    private String crumb;
    private CookieManager cookieManager;

    public CrumbManager() {
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        this.crumb = fetchCrumb();
    }

    public String getCrumb() {
        if (crumb == null || crumb.isEmpty()) {
            crumb = fetchCrumb();
        }
        return crumb;
    }

    private String fetchCrumb() {
        try {
            cookieManager.getCookieStore().removeAll();
            URL url = new URL(CRUMB_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
    
            // Add headers
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            connection.setRequestProperty("Authorization", "Bearer YOUR_ACCESS_TOKEN"); // Replace with actual token
    
            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == 401) {
                System.err.println("Unauthorized: Check your authentication credentials.");
                return null;
            }
    
            // Read response
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
    
            String response = responseBuilder.toString();
            System.out.println("Response: " + response);
    
            // Extract crumb
            final Pattern CRUMB_PATTERN = Pattern.compile("\"CrumbStore\":\\{\"crumb\":\"(.*?)\"\\}");
            Matcher matcher = CRUMB_PATTERN.matcher(response);
            if (matcher.find()) {
                String crumbFromResponse = matcher.group(1).replace("\\\\u002F", "/");
                System.out.println("get crumb successfully: " + crumbFromResponse);
                return crumbFromResponse;
            } else {
                throw new Exception("No crumb info.");
            }
        } catch (Exception e) {
            System.err.println("Error when getting crumb: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
}
