/*This class stores information in pairs for when we are dealing with the ceiling or base of an object 
and do not need a z coordinate*/

import java.util.*;
import java.io.*;
import java.lang.*;


public class Pair{
	double x = 0;
	double y = 0;

	double getX(){
		return(x);
	}

	void setX(double inx){
		x=inx;
	}

	double getY(){
		return(y);
	}

	void setY(double iny){
		y=iny;
	}

	//Method to round the x and y values of any given pair.
	void roundpair(){
		x=x*1000;
		x=Math.round(x);
		x=x/1000;
		y=y*1000;
		y=Math.round(y);
		y=y/1000;
	}

	void test(){
		System.out.println("testing in progress");
		Pair pr = new Pair();
		pr.setX(3.2);
		assert(3.2==pr.getX());
    }	

	public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Pair pr = new Pair();
        if(testing){
        	pr.test();
      	}
    }

}

