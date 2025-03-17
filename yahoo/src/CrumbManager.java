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
    private static final String CRUMB_URL = "https://finance.yahoo.com/quote/0388.HK/history";
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
            connection.setInstanceFollowRedirects(false);

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder responseBuilder = new StringBuilder(); // 声明并实例化 responseBuilder
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line); // 累加每一行到 responseBuilder
            }
            reader.close();

            String response = responseBuilder.toString();

            // 定义并使用正则表达式模式
            final Pattern CRUMB_PATTERN = Pattern.compile("\"CrumbStore\":\\{\"crumb\":\"(.*?)\"\\}");
            Matcher matcher = CRUMB_PATTERN.matcher(response);
            if (matcher.find()) {
                String crumbFromResponse = matcher.group(1);
                crumbFromResponse = crumbFromResponse.replace("\\\\u002F", "/"); // 转义字符替换
                System.out.println("get crumb successfully: " + crumbFromResponse);
                return crumbFromResponse;
            } else {
                throw new Exception("No crumb info.");
            }
        } catch (Exception e) {
            System.out.println("Error when getting crumb: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
