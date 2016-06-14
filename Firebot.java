/**
 * info1103 - assignment 3
 * <Chung Lai Lam>
 * <clam7738>
 */

import java.util.Scanner;

public class Firebot {

	//
	// TODO
	//

    private static Scanner scan = new Scanner(System.in);
    private static Simulation sim;
    
    // HELP FUNCTION
    public static void help(){
    	System.out.println("BYE");
    	System.out.println("HELP");
    	System.out.printf("\n");
    	System.out.println("DATA");
    	System.out.println("STATUS");
    	System.out.printf("\n");
    	System.out.println("NEXT <days>");
    	System.out.println("SHOW <attribute>");
    	System.out.printf("\n");
    	System.out.println("FIRE <region>");
    	System.out.println("WIND <direction>");
    	System.out.println("EXTINGUISH <region>");
    }
    
    // BYE FUNCTION
    public static void bye_Command(){
    	System.out.printf("bye\n");
    	System.exit(0);
    }
    
    public static void show_command(String command){
    	if(command == "fire"){
    		sim.show_fire();
    		System.out.println("test");
    	}
    	if(command=="height"){
    		sim.show_height();
    		System.out.println("test");
    	}
    }
    public static void isInteger(String s){
    	try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            System.out.println("Invalid command");
            return;
        } catch(NullPointerException e) {
        	System.out.println("Invalid command");
            return ;
        }
    }
  
    
	public static void main(String[] args) {

		// Check for input length
		if(args.length != 3){
			System.out.printf("Invalid number of arguments.\n");
			System.exit(0);
		}

		int seed = Integer.parseInt(args[0]); // # of seeds
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		sim = new Simulation(height,width,seed);

		

		System.out.printf("Day: 1\nWind: none\n"); // Starting message

		System.out.printf("\n> ");

		// Start enter command;
		while(scan.hasNextLine()){
			String in = scan.nextLine();
			String[] command = in.split(" ");
			String first_command = command[0].toLowerCase();
			
			switch(first_command){
			default:
				System.out.printf("Invalid command\n");
				
			case "":
				break;
			case "bye":
				bye_Command();
				break;
			case "help":
				help();
				break;
			case "status":
				sim.status();
				break;
			case "data":
				sim.data();
				break;
			case "next":
				if(command.length == 2){
					sim.next_n(Integer.parseInt(command[1])); // n days
					break;
				}else{
					sim.next(); // 1Day
					break;
				}
			case "show":
				if(command.length == 2){
					if(command[1].equals("fire")){
					sim.show_fire();
					}else if(command[1].equals("height")){
						sim.show_height();
					}
					break;
				}
			case "wind":
				if(command.length == 2){
					sim.wind_direct(command[1]);
					break;
				}
			case "fire":
				if(command.length == 5){
					sim.start_fire(Integer.parseInt(command[1]),Integer.parseInt(command[2]),Integer.parseInt(command[3]),Integer.parseInt(command[4]));
					//System.out.println("test1");
					break;
				}else if(command.length == 3){
					if(command[2].equals("apple")||command[1].equals("orange") ){
						System.out.println("Invalid command");
						break;
					}
					sim.start_fire_one(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
					//System.out.println("test2");
					break;

				}
			case "extinguish":
				if(command.length == 3){
					sim.extinguish(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
					//System.out.println("test3");
					break;
				}
				else if(command.length == 5){
					sim.extinguish_area(Integer.parseInt(command[1]),Integer.parseInt(command[2]),Integer.parseInt(command[3]),Integer.parseInt(command[4]));
					//System.out.println("test4");
					break;
				}
				else if(command.length == 2){
						sim.extinguish_all();
					//System.out.println("test5");
					break;
				}				
			}
			System.out.printf("\n> ");
		}
	}
}


