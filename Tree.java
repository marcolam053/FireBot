/**
 * info1103 - assignment 3
 * <Chung Lai Lam>
 * <clam7738>
 */

public class Tree {

	//
	// Method per Tree :)
	//
	private  int fire = 0; // Fire intensity 0->9
	private  int height; //Tree height 0->9
	private  boolean isBurntDown = false;
	private  boolean onFire = false;
	private int count = 0;
	
	public Tree(int height) {
		this.height = height;
	}
	// Get height of tree
	public int get_height(){
		return height;
	}
	// Get intensity of fire
	public int get_fire(){
		return fire;
	}
	
	public boolean isOnFire(){
		if(onFire == true) return true;
		return false;
	}
	// Check if the tree is burnt down or not
	public boolean get_BurntDown(){
		return isBurntDown;
	}
	
	// Set fire to a tree
	public void set_fire(){
		if(fire > 0){
			onFire = true;
		}
		
		if(height > 0 && fire == 0){
			fire ++;
			onFire = true;
		}
		
		if(height == 0){
			onFire = false;
		}
	}
	
	// Tree continue burning
	public void cont_fire(){
		
		// If fire == 9 -> decrease height
				if(height > 0 && fire == 9){
					height -= 1;
					if(height == 0){
						isBurntDown = true;
						//onFire = false;
						fire = 0;
					}
				}
		
		// Continue to burn if height > 0 & not burnt down
		if(height > 0 && fire > 0 && fire < 9 && isBurntDown == false){
			fire++;
		}
		
	}
	
	// Extinguish fire
	public void extinguish_fire(){
		if(onFire==true){
		fire = 0;
		onFire = false;
		}
	}
	
}

