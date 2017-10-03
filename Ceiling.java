//this classs sets up the coordinates we have for the horizontally level Top of an object.

import java.util.*;
import java.io.*;
import java.lang.*;

public class Ceiling{
	List<Pair> coords = new ArrayList<Pair>();
	double height;
	double radius;

	double getradius(){
		return(radius); 
	}

	void setradius(double r){
		radius=r;
	}

	double getheight(){
		return(height);
	}

	void setheight(double h){
		height=h;
	}
	
	Pair getpair(int n){
		return(coords.get(n));
	}

	void addpair(Pair input){
		coords.add(input);
	}

	void slipinpair(Pair input, int n){
		coords.add(n, input);
	}

	void deletepair(int n){
		coords.remove(n);
	}

	int numvertices(){
		return(coords.size());
	}

	void printceilingcoords(){
		for(int i=0;i<(coords.size());i++){
			System.out.print(coords.get(i).getX() + " ");
			System.out.println(coords.get(i).getY());
		}
	}

//Unit testing using asserts (trigger using "javac Ceiling.java" followed by "java -ea Ceiling" in order to enable asserts)
	void test(){
		System.out.println("testing in progress");
		Pair pr1 = new Pair();
		pr1.setX(2.67);
		pr1.setY(3.1);
		Pair pr2 = new Pair();
		pr2.setX(4);
		pr2.setY(7);
		Ceiling cei = new Ceiling();
		cei.addpair(pr1);
		assert(pr1==cei.getpair(0));
		assert(1==cei.numvertices());
		cei.addpair(pr2);
		assert(pr2==cei.getpair(1));
		assert(2==cei.numvertices());
		assert(7==cei.getpair(1).getY());
		assert(2.67==cei.getpair(0).getX());
		cei.setheight(43);
		assert(43==cei.getheight());
    }

    public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Ceiling cei = new Ceiling();
        if(testing){
        	cei.test();
      	}
    }
}
