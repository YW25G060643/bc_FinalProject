## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
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
        // 清空 Cookie
        cookieManager.getCookieStore().removeAll();
        URL url = new URL(CRUMB_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // 设置请求头
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer YOUR_ACCESS_TOKEN"); // 替换为实际凭据（如果需要）

        // 调试：打印请求头
        connection.getRequestProperties().forEach((key, value) -> 
            System.out.println(key + ": " + value)
        );

        // 检查响应码
        int responseCode = connection.getResponseCode();
        if (responseCode == 401) {
            System.err.println("未授权：请检查身份验证信息。");
            return null;
        }
        if (responseCode != 200) {
            System.err.println("请求失败：HTTP 响应码 " + responseCode);
            return null;
        }

        // 读取响应
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

        // 提取 crumb
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
