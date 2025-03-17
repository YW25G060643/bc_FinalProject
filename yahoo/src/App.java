public class App {
    public static void main(String[] args) {
        try {
            CrumbManager crumbManager = new CrumbManager(); // 实例化 CrumbManager
            System.out.println("Crumb: " + crumbManager.getCrumb()); // 输出 Crumb 信息
        } catch (Exception e) {
            System.err.println("程序执行出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
