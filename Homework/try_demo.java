import java.util.Scanner; 
 
public class try_demo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
 
        System.out.println("next：");

        if (scan.hasNext()) {
            String str1 = scan.next();
            System.out.println("outpu：：" + str1);
        }
        scan.close();
    }
}
