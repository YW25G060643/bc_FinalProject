public class App {
    public static void main(String[] args) {
        try {
            // ✅ 正确的构造函数调用
            CrumbManager crumbManager = new CrumbManager();
            YahooFinanceManager yahooFinanceManager = new YahooFinanceManager(crumbManager);

            // ✅ 正确的方法调用
            String quote = yahooFinanceManager.getQuote("0388.HK");
            System.out.println("实时报价:\n" + quote);
        } catch (Exception e) {
            System.err.println("程序执行出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}