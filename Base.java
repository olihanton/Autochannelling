//this classs sets up the coordinates we have for the base of an object.

import java.util.*;
import java.io.*;
import java.lang.*;

public class Base{
	List<Pair> coords = new ArrayList<Pair>();
	
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

	void printbasecoords(){
		for(int i=0;i<(coords.size());i++){
			System.out.print(coords.get(i).getX() + " ");
			System.out.println(coords.get(i).getY());
		}
	}

//Unit testing using asserts (trigger using "javac Base.java" followed by "java -ea Base" in order to enable asserts)
	void test(){
		System.out.println("testing in progress");
		Pair pr1 = new Pair();
		pr1.setX(3.4);
		pr1.setY(5.2);
		Pair pr2 = new Pair();
		pr2.setX(2);
		pr2.setY(93);
		Base bs = new Base();
		bs.addpair(pr1);
		assert(pr1==bs.getpair(0));
		assert(1==bs.numvertices());
		bs.addpair(pr2);
		assert(pr2==bs.getpair(1));
		assert(2==bs.numvertices());
		assert(93==bs.getpair(1).getY());
		assert(3.4==bs.getpair(0).getX());
    }

    public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Base bs = new Base();
        if(testing){
        	bs.test();
      	}
    }
}
