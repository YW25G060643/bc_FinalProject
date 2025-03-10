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
    private static final String CRUMB_URL =  "https://finance.yahoo.com/quote/0388.HK/history";
    private String crumb;
    private CookieManager cookieManager;
    
    public CrumbManager() {
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        this.crumb = fetchCrumb();
    }
    public String getCrumb() {
        if (crumb == null || crumb.isEmpty()) {
            crumb = fenchCrumb();
        }
        return crumb;
    }

    private String fenchCrumb() {
        try{
            cookieManager.getCookieStore().removeAll();
            URL url = new URL(CRUMB_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(false);

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String Line;
            while ((line = reader.readLine()) != null) {
                rseponseBuilder.append(line);
            }
            reader.close();

            String response = rseponseBuilder.toString();

            Pattern pattern = pattern.compile("\"CrumbStore\":\\{\"crumb\":\"(.*?)\"\\}");
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                String crumbFromResponse = matcher.group(1);
                crumbFromResponse = crumbFromResponse.replace("\\\\u002F", "/");
                System.out.println("get crumb successfully: " + crumbFromResponse);
                return crumbFromResponse;
            }else{
                throw new Exception("no crumb info。");
            }
        } catch (Exception e){
            System.out.println("error when getting crumb : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
