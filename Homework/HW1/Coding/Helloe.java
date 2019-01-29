import java.util.*;
class Helloe{
	public static void main(String[] args) {
		System.out.println("Hello World!!");
		System.out.println(args[0] + args[1]);
		LinkedList<Integer> freeE = new LinkedList<>();
		int n = 8, e =-1;
		for(int i = 0; i < n; i++)
			freeE.add(i);
		for(int i = 0; i < n; i++) {
			e = freeE.removeFirst();
			System.out.println(e);
		}
		if(freeE.size() == 0) {
			System.out.println("Congragulations!");
		}
		System.out.println(freeE.removeFirst());
	}
}