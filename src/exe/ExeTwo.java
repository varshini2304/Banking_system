package exe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bank.Customer;
import bank.Employee;

public class ExeTwo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		List<Object> list = new ArrayList<Object>();
//		List<Object> list2 = new ArrayList<Object>();
//		list.add("c");
//		list.add("Chilly");
//		list.add(new Customer());
//		list.add(new Employee());
//		list2.addAll(list);
//		System.out.print(list);
//		System.out.println(list2);
//		System.out.println("size is "+list.size());
//		list.add(4,20393030L);
//		System.out.print(list.get(0));
//		System.out.print(list.get(3));
		List<Integer> list = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();
		
		
		list.add(11);
		list.add(2000);
		list.add(5000);
		list.add(80220);
		list.add(-59303);
		System.out.print(list);
		System.out.println("size is "+list.size());
		list.add(4,20393030);
		System.out.print(list+"\n");
		System.out.print(list.get(3));
		list.remove(new Integer(2000));
		Collections.sort(list);
		System.out.println("\n"+list);

	}

}
