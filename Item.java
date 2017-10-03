/*this class stores the information for a 3D object in an array list of trianglesin a manner that 
File.java will then be able to read to and from it*/

import java.util.*;
import java.io.*;

class Item{
	private List<Triangle> triset = new ArrayList<Triangle>();
	
	//method to insert a triangle into our list of triangles "triset"
	void insert(Triangle tr){           
        triset.add(tr);
    } 

	//method to delete the nth triangle in our list of triangles "triset"
   	void delete(int n){
      	triset.remove(n);
   	}

	//method to pass the nth triangle from an object
	Triangle returntri(int n){
		return(triset.get(n));
	}

	//method to return the size of the arraylist in which the trangles defining the object are stored.
	int numberoftriangles(){
		return(triset.size());
	}

	void test(){
		System.out.println("testing in progress");
		Item itm = new Item();
		Triangle tri = new Triangle();
		tri.inputvalue(2, 2, 7);
		itm.insert(tri);
		assert(tri==itm.returntri(0));
    }	

	public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Item itm = new Item();
        if(testing){
        	itm.test();
      	}
    }
}


