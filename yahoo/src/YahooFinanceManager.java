import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YahooFinanceManager {
    private CrumbManager crumbManager;

    public YahooFinanceManager() {
        crumbManager = new CrumbManager();
    }


    public String getQuote(String symbol) {
        String crumb = crumbManager.getCrumb();
        if (crumb == null) {
            System.out.println("no crumb found");
            return null;
        }
        String urlStr = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" 
                        + symbol + "&crumb=" + crumb;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            SprringBuilder rseponseBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                rseponseBuilder.append(line);
            }
            StringBuilder responseBuilder = new StringBuilder();
            reader.close();
            String response = rseponseBuilder.toString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error happened when requiring " + symbol + "'s price " + e.getMessage());
        }
        return null;
    }
}