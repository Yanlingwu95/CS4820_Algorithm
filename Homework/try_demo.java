import java.util.Scanner; 
 
public class try_demo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
 
        System.out.println("next：");
		String str1 = new String();
        if (scan.hasNext()) {
            str1 = scan.nextLine();
            System.out.println("outpu：：" + str1);
        }
		
		String[] res = str1.split(" ");
		System.out.println(Integer.parseInt(res[0]));
		System.out.println(res[1]);
		System.out.println(res[2]);
		
		
		if (scan.hasNext()) {
            str1 = scan.nextLine();
            System.out.println("outpu：：" + str1);
        }
		
		res = str1.split(" ");
		System.out.println(Integer.parseInt(res[0]));
		System.out.println(res[1]);
		System.out.println(res[2]);
		
        scan.close();
    }
}
