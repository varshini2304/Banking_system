package exe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bank.Account;
import bank.AccountType;
import bank.Address;
import bank.CurrentAccount;
import bank.Customer;
import bank.Employee;
import bank.SavingsAccount;

public class Exe {

	private static String role; 

	public static void main(String[] args) {
		List<Employee> emplist = new ArrayList<Employee>();
		List<Customer> cuslist = new ArrayList<Customer>();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to varshini banking private limited");
		System.out.println("hai wat would you like to do 1)open an account\n2)employee section\n3)customer section");
		
		
		/*
		 * The ramaining activities code shall go here for the time being
		 * shall discuss what has to be done
		 */
		
		
		int operation = sc.nextInt();
		sc.nextLine();
		switch (operation) {
		case 1:
			System.out.println("Are you customer or employee(c/e)..?");
			role = sc.nextLine();
			
			if(role.equalsIgnoreCase("e")) {
				String addnew = "y";
				while(addnew.equalsIgnoreCase("y")) {
					System.out.println("Happy working! have a great day.....");
					System.out.println("Please enter your details as in the following pattern:(age,firstName,lastName,email,"
							+ "mobileNumber,userName,password,designation,salary)");
					int age=sc.nextInt();
					sc.nextLine();
					String firstName = sc.nextLine();
					String lastName = sc.nextLine();
					String email = sc.nextLine();
					sc.nextLong();
					Long mobileNumber = sc.nextLong();
					sc.nextLine();
					String userName = sc.nextLine();
					String password = sc.nextLine();
					String designation = sc.nextLine();
					double salary = sc.nextDouble();
					Employee emp = new Employee(age,firstName,lastName,email,mobileNumber,userName,password,designation,salary);
					emplist.add(emp);
					System.out.println("want to add new employee account(y/n)");
					sc.nextLine();
					addnew = sc.nextLine();
				}
			}
			
			else if(role.equalsIgnoreCase("c")) {
				String addnew = "y";
				while(addnew.equalsIgnoreCase("y")) {
					System.out.println("Please enter your details");
					System.out.println("age");int age=sc.nextInt();
					sc.nextLine();
					System.out.println("first name");String firstName = sc.nextLine();
					System.out.println("last name");String lastName = sc.nextLine();
					System.out.println("doorno");int doorno = sc.nextInt();
					sc.nextLine();
					System.out.println("street");String street= sc.nextLine();
					System.out.println("city");String city= sc.nextLine();
					System.out.println("state");String state=sc.nextLine();
					System.out.println("country");String country = sc.nextLine();
					String pincode = sc.nextLine();
					Address address = new Address(doorno,street,city,state,country,pincode);
					System.out.println("email");String email = sc.nextLine();
					sc.nextLong();
					System.out.println("mobileNumber");Long mobileNumber = sc.nextLong();
					Customer cus = new Customer(age,firstName,lastName,address,email,mobileNumber);
					cuslist.add(cus);
					System.out.println("want to add new Customer account(y/n)");
					sc.nextLine();
					addnew = sc.nextLine();
				}
			}
			else
				System.out.println("Please enter a valid choice.......");
			break;
		case 2:{//employee section
			
			System.out.println("the Employee section is under progress you can only "
					+ "view the employees details here as of now\nHere are the employees details\n"
					+ "age\tfirstName\tlastName\t\t\taddress\t"
					+ "				\temail\t\t\tmobileNumber\t\tdesignation\tsalary\tuserName");
			
			for(Employee em : emplist) {
				System.out.println(em.toString());	
			}
			
		}
			
			break;
		case 3:{
					System.out.println("the Employee section is under progress you can only "
						+ "view the employees details here as of now\nHere are the employees details\n"
						+ "age\tfirstName\tlastName\t\t\taddress\t"
						+ "				\temail\t\t\tmobileNumber\t\tusername\tpriviliged\taccount");
				for(Customer cu : cuslist) {
					System.out.println(cu.toString());	
				}
				
			}
		
			
			break;


		default:
			System.out.println("Please enter a valid input......");
			break;
		}
		
		
		
		
		
		
		
		
		
		
		
//		AccountType ac = AccountType.SB;
//		Account acc = new SavingsAccount(ac,2000000);
//		System.out.println(acc.getAccountNumber());
//		
//		AccountType cac = AccountType.CUR;
//		Account cacc = new CurrentAccount(cac,20050000);
//		System.out.println(cacc.getAccountNumber());

	}

}
