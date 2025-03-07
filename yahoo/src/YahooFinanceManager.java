public class YahooFinanceManager {
    private CrumbManager crumbManager;

    public YahooFinanceManager() {
        crumbManager = new CrumbManager();
    }

    public String getQuote(String symbol) {
        String crumb = crumbManager.getCrumb();
    }
}