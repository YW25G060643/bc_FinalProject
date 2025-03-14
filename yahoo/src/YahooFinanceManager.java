import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class YahooFinanceManager {
    private final CrumbManager crumbManager;

    // ✅ 修复构造函数（关键点）
    public YahooFinanceManager(CrumbManager crumbManager) {
        if (crumbManager == null) {
            throw new IllegalArgumentException("CrumbManager 不能为空");
        }
        this.crumbManager = crumbManager; // 注意变量名拼写
    }

    // ✅ 修复方法签名（关键点）
    public String getQuote(String symbol) {
        try {
            // 获取crumb令牌
            String crumb = crumbManager.getCrumb();
            if (crumb == null || crumb.isEmpty()) {
                System.err.println("[ERROR] 无法获取有效crumb");
                return null;
            }

            // 编码股票代码
            String encodedSymbol = URLEncoder.encode(symbol, StandardCharsets.UTF_8.name());
            
            // 构建请求URL
            String urlStr = String.format(
                "https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s&crumb=%s",
                encodedSymbol, 
                crumb
            );
            
            // 发送请求
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            
            // 读取响应
            try (BufferedReader reader = new BufferedReader(
                     new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                
                return response.toString();
            }
        } catch (Exception e) {
            System.err.printf("[ERROR] 获取股票 %s 报价失败：%s%n", symbol, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}