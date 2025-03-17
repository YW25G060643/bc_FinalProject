import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class YahooFinanceManager {
    private final CrumbManager crumbManager;

    // 构造函数
    public YahooFinanceManager(CrumbManager crumbManager) {
        if (crumbManager == null) {
            throw new IllegalArgumentException("CrumbManager 不能为空");
        }
        this.crumbManager = crumbManager;
    }

    // 获取股票报价
    public String getQuote(String symbol) {
        try {
            // 获取 Crumb
            String crumb = crumbManager.getCrumb();
            if (crumb == null || crumb.isEmpty()) {
                System.err.println("[ERROR] 无法获取有效 crumb");
                return null;
            }

            // 编码请求参数
            String encodedSymbol = URLEncoder.encode(symbol, StandardCharsets.UTF_8.name());
            String encodedCrumb = URLEncoder.encode(crumb, StandardCharsets.UTF_8.name());

            // 构造请求 URL
            String urlStr = String.format(
                    "https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s&crumb=%s",
                    encodedSymbol, 
                    encodedCrumb
            );

            // 发送 HTTP 请求
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 读取响应
            String response = readResponse(connection);
            return response;
        } catch (Exception e) {
            logError(String.format("获取股票 %s 报价失败", symbol), e);
            return null;
        }
    }

    // 读取 HTTP 响应
    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    // 错误日志
    private void logError(String message, Exception e) {
        System.err.printf("[ERROR] %s: %s%n", message, e.getMessage());
        if (e != null) {
            e.printStackTrace();
        }
    }
}
