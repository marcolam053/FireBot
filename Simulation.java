/**
 * info1103 - assignment 3
 * <Chung Lai Lam>
 * <clam7738>
 */


import java.util.*;
import java.lang.Math;

public class Simulation {

	//
	// TODO
	//
	
	class Iteration{ // make sure all col adn row input is valid
		int co;
		int ro;
		public Iteration(int co, int ro) throws Exception{
			if(co < 0 || ro < 0 || co >= height || ro >= width){
				throw new Exception();
			}else{
				this.co = co;
				this.ro = ro;
			}
		}
	}

	private static int width;
	private static int height;
	private  Tree[][] trees;
	private  Tree[][] fire;


	private static int day = 1;
	private static String wind = "none";
	private static int pollution = 0; // TODO
	private static float damage; 
	private static int seed;
	int fire_Intensity;
	int total_height;
	
	public Simulation(int height, int width, int seed){
		this.height = height;
		this.width = width;
		this.trees = new Tree[height][width];
		this.generateTerrain(seed);
		this.clone_fire(trees);

	}
	
	public Tree[][] clone_fire(Tree[][] trees){
		fire = new Tree[height][width];
		for(int y = 0; y < trees.length; y++){
			for(int x = 0; x < trees[y].length; x++){
				fire[y][x] = trees[y][x];
			}
		}
		return fire;
	}
	
	// ADD ITEM TO ARRAYLIST (FIRE SPREAD)
	public void toList(ArrayList<Iteration> array,int column, int row){
		try{
			Iteration it = new Iteration(column,row);
			array.add(it);
		}
		catch(Exception e){}
	}
	
	public static void grid(){
		System.out.print("+");
		for(int i = 0; i < 2*width-1;i++){
			System.out.print("-");
		}
		System.out.print("+");
		System.out.println("");
}
	// Calculate damage & pollution
	public void data(){
		
		float burnt_tree;
		float whole_tree ;
		// Calculate Damage
		whole_tree = get_nwhole();
		burnt_tree = get_nburnt();
		float total = whole_tree+burnt_tree;		
		damage = (burnt_tree/total)*100;
		
		// Calculate the pollution
		
		//System.out.println("number of fire intensity: "+ get_intensity());
		//System.out.println("number of tree height: "+ get_total_height());
		//System.out.println("damage number: "+ burnt_tree%whole_tree);
		System.out.printf("Damage: "); System.out.printf("%.2f",damage); System.out.printf("%%\n");
		System.out.println("Pollution: "+ pollution);
	}
	
	
	
	private int get_total_height() {
		int count  = 0;
		for(int i = 0; i < trees.length;i++){
			for(int j = 0; j < trees[i].length;j++){
				if(trees[i][j].get_height()>0){
					count += trees[i][j].get_height();
				}
			}
		}
		return count;
	}

	private int get_intensity() {
		int count  = 0;
		for(int i = 0; i < trees.length;i++){
			for(int j = 0; j < trees[i].length;j++){
				if(trees[i][j].get_fire()>0){
					count += trees[i][j].get_fire();
				}
			}
		}
		return count;
	}
	// Get the pollution per day
	public int pollution_day(){
		return get_intensity()-get_total_height();
	}
	
	// CALCULATE the pollution
	public void cal_pollution(){
		int pollution_day = pollution_day();
		
		if(pollution_day < 0 && pollution > 0 || pollution == 0 || pollution > 0){
			pollution = pollution+pollution_day;
		}
		
		if(pollution < 0){
			pollution = 0;
		}
		
	}
	public float get_nwhole(){
		float count = 0;
		for(int j = 0; j <height; j++){
			for(int i = 0; i < width; i++){
				if(trees[j][i].get_height()>0){
					count++;
				}
			}
		}
		return count;
	}
	
	public float get_nburnt(){
		float count = 0;
		for(int j = 0; j <height; j++){
			for(int i = 0; i < width; i++){
				if(trees[j][i].get_BurntDown()==true){
					count++;
				}
			}
		}
		return count;
	}
	public static void status() {
		System.out.println("Day: "+day);
		System.out.println("Wind: "+wind);
	}

	/////////////////////////////////////
   ///          NEXT DAY(S)          ///
  /////////////////////////////////////	
	
	// Increment day by x
	public void next_n(int ndays) {
		// Increment Day by x
		day = day + ndays;
		for(int iterate = 0; iterate < ndays;iterate++){
			next_fire();
			fire_spread();
			cal_pollution();
	     }
		System.out.println("Day: "+day);
		System.out.println("Wind: "+wind);
	}
	
	// Increment day by 1 
	public  void next() {
		// increment day by 1
		day += 1;
		// increase fire by 1 at the same time
				
		next_fire();
		fire_spread();
		cal_pollution();
		
		System.out.println("Day: "+day);
		System.out.println("Wind: "+wind);
	}
	
	/////////////////////////////////////
   //////        WIND METHOD         ///
  /////////////////////////////////////	
	
	// SET WIND DIRECTION
	public void wind_direct(String direction) {
		wind = direction;
		System.out.printf("Set wind to "+wind +"\n");		
	}
	
	public void fire_spread(){
		
		ArrayList<Iteration> direction = new ArrayList<Iteration>();
		
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
					if(trees[j][i].get_fire() > 0){
						int c = j;
						int r = i;
						// store col and row of different wind direction
					if(wind.equals("east")){
						r = i+1;
						toList(direction,j,r);
						
					}else if(wind.equals("west")){
						r = i -1;
						toList(direction,j,r);
					}else if(wind.equals("south")){
						c = j+1;
						toList(direction,c,i);
					}else if(wind.equals("north")){
						c = j-1;
						toList(direction,c,i);
					}else if(wind.equals("all")){
						r = i+1;
						toList(direction,j,r);
						r = i -1;
						toList(direction,j,r);
						c = j+1;
						toList(direction,c,i);
						c = j-1;
						toList(direction,c,i);
					}else if(wind.equals("none")){
						
					}	
			   }			
			}
		}
		for(int index = 0; index < direction.size(); index++){
			Iteration direct = direction.get(index);
			trees[direct.co][direct.ro].set_fire();
		}
	
	}
	
	/////////////////////////////////////
   //////        START FIRE          ///
  /////////////////////////////////////	

	// START FIRE AREA
	public void start_fire(int x1, int y1, int x2, int y2) {
		if(x1 < 0 || x2 < 0 || y1 < 0 || y2 <0 || x1 >width ||x2 > width || y1 > height || y2 >height){
			System.out.println("Invalid command");
		}
		int count = 0;
		for(int y = y1 ; y < y1+y2;y++){
			for(int x = x1; x < x1+x2;x++){
				fire[y][x].set_fire();
				count+=1;
			}
		}
		if(count > 0){
			System.out.println("Start a fire");
		}else{
			System.out.println("No fires were started");
		}
	}
	
	// Start fire on single tree
	public void start_fire_one(int x, int y) {
		if(x < 0 || y < 0 || x >= width||y >= height || "".equals(x)||"".equals(y)){
			System.out.println("Invalid command");
		}
		else if(fire[y][x].get_height()==0 || fire[y][x].isOnFire()==true){
			System.out.println("No fires were started");
		}
		else if(fire[y][x].get_height() > 0){
			fire[y][x].set_fire();
			System.out.println("Started a fire");
		}
		
	}
	
	/////////////////////////////////////
   //////     EXTINGUISH FIRE        ///
  /////////////////////////////////////	
	
	// EXTINGUISH A TREE
	public void extinguish(int x, int y) {
		if(trees[y][x].isOnFire()==false){
			System.out.println("No fires to extinguish");
		}
		if(trees[y][x].isOnFire()==true){
			trees[y][x].extinguish_fire();
			System.out.println("Extinguished fires");
		}		
		
	}
	
	// EXTINGUISH AREA
	public void extinguish_area(int x1, int y1, int x2, int y2) {
		if(x1 < 0 || x2 < 0 || y1 < 0 || y2 <0){
			System.out.println("Invalid command");
		}
		int count = 0;
		for(int y = y1 ; y < y1+y2;y++){
			for(int x = x1; x < x1+x2;x++){
				fire[y][x].extinguish_fire();
				count+=1;
			}
		}
		if(count > 0){
			System.out.println("Extinguished fires");
		}else{
			System.out.println("No fires to extinguish");
		}
		
	}
	
	// EXTINGUISH ALL
	public void extinguish_all() {
		for(int col = 0; col < trees.length;col++){
			for(int row = 0; row < trees[col].length;row++){
				trees[col][row].extinguish_fire();
			}
		}
		System.out.println("Extinguished fires");
	}
	
	////////////////////////////////
   //       SHOW FUNCTION        //
  ////////////////////////////////
	
	public void show_fire(){
		grid();
		for(int y = 0; y < height; y++){
			System.out.print("|");
			String s = "";
			for(int x = 0; x < width; x++){
				if(fire[y][x].get_fire() > 0){
					s += fire[y][x].get_fire()+" ";
				}else if(fire[y][x].get_height() > 0){
					s += "." + " ";
				}else if(fire[y][x].get_BurntDown() == true){
					s += "x" + " ";
				}else{
					s += " " + " ";
				}
			}
			System.out.print(s.substring(0,s.length()-1));
			System.out.println("|");
		}
		grid();
	}
	
	public void show_height(){
		grid();
		for(int y = 0; y < height;y++){
			System.out.print("|");
			String s = "";
			for(int x = 0; x < width;x++){
				if(trees[y][x].get_height() > 0){
					s += trees[y][x].get_height() + " ";
				}else if(trees[y][x].get_BurntDown() == true){
					s+="x" + " ";
				}else{
					s+= " " + " ";
				}
			}
			System.out.print(s.substring(0,s.length()-1));
			System.out.println("|");
		}
		grid();
	}
	
	public void next_fire(){
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				trees[i][j].cont_fire();
			}
		}
	}
	
	
	public void generateTerrain(int seed) {

		// ###################################
		// ### DO NOT MODIFY THIS FUNCTION ###
		// ###################################

		Perlin perlin = new Perlin(seed);
		double scale = 10.0 / Math.min(width, height);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double height = perlin.noise(x * scale, y * scale, 0.5);
				height = Math.max(0, height * 1.7 - 0.7) * 10;
				trees[y][x] = new Tree((int) height);
			}
		}
	}




	
}



