//This class represents a way to store 3D coordinates. 

import java.util.*;
import java.io.*;
import java.lang.*;


public class Triple{
	double x = 0;
	double y = 0;
	double z = 0;

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

	double getZ(){
		return(z);
	}

	void setZ(double inz){
		z=inz;
	}

//subtracts one vector 'input' from the main one 
	Triple subtract(Triple input1, Triple input2){
		Triple out = new Triple();
		out.setX(input1.getX()-input2.getX());
		out.setY(input1.getY()-input2.getY());
		out.setZ(input1.getZ()-input2.getZ());
		return(out);
	}

//Performs the dot product of two 3D coordinates
	double dot(Triple input1, Triple input2){
		double out=0;
		out=(input1.getX()*input2.getX())+(input1.getY()*input2.getY())+(input1.getZ()*input2.getZ());
		return(out);
	}

//Performs the cross product of two 3D coordinates
	Triple cross(Triple in1, Triple in2){
		Triple out = new Triple();
		out.setX((in1.getY()*in2.getZ())-(in1.getZ()*in2.getY()));
		out.setY((in1.getZ()*in2.getX())-(in1.getX()*in2.getZ()));
		out.setZ((in1.getX()*in2.getY())-(in1.getY()*in2.getX()));
		return(out);
	}

//Performs the product of a 3D coordinate and a scalar.
	Triple multiply(Triple input, double scale){
		input.setX(input.getX()*scale);
		input.setY(input.getY()*scale);
		input.setZ(input.getZ()*scale);
		return(input);
	}

//Method to print out a set of coordinates
	void printtriple(){
		System.out.print(x + " " + y + " " + z + "\n");
	}

	void test(){
		System.out.println("testing in progress");
		Triple tp1 = new Triple();
		Triple tp2 = new Triple();
		tp1.setX(3);
		tp1.setY(5);
		tp1.setZ(1);
		assert(3==tp1.getX());
		assert(10==multiply(tp1, 2).getY());
		tp2.setX(2);
		tp2.setY(3);
		tp2.setZ(4);
		assert(4==subtract(tp1, tp2).getX());
		multiply(tp1, 0.5);
		assert(25==dot(tp1, tp2));
		assert(-10==cross(tp1, tp2).getY());
    }	

	public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Triple tp = new Triple();
        if(testing){
        	tp.test();
      	}
    }
}

