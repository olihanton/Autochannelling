/*This class is in charge of structuring how the data for any triangle will be dealt with*/

import java.util.*;
import java.io.*;

class Triangle {
	double[] normal = new double[3];
	double[] avertex = new double[3];
	double[] bvertex = new double[3];
	double[] cvertex = new double[3];

	void printtriangle(){
		System.out.print("\nfacet normal " + normal[0] + " " + normal[1] + " " + normal[2] + "\n  outer loop\n");
		System.out.print("    vertex " + avertex[0] + " " + avertex[1] + " " + avertex[2] + "\n");
		System.out.print("    vertex " + bvertex[0] + " " + bvertex[1] + " " + bvertex[2] + "\n");
		System.out.print("    vertex " + cvertex[0] + " " + cvertex[1] + " " + cvertex[2] + "\n");
		System.out.print("  endloop\nendfacet\n\n");
	}

	//method to input a single value out of 12 into a triangle
	void inputvalue(int normvert, int onetothree, double input){
		if(normvert==0){
			normal[onetothree]=input;
		}
		if(normvert==1){
			avertex[onetothree]=input;
		}
		if(normvert==2){
			bvertex[onetothree]=input;
		}
		if(normvert==3){
			cvertex[onetothree]=input;
		}
	}

	//method to convert from a triangle to a triple
	Triple returntriple(int i){
		Triple out = new Triple();
		if(i==0){
			out.setX(avertex[0]);
			out.setY(avertex[1]);
			out.setZ(avertex[2]);
		}
		if(i==1){
			out.setX(bvertex[0]);
			out.setY(bvertex[1]);
			out.setZ(bvertex[2]);
		}
		if(i==2){
			out.setX(cvertex[0]);
			out.setY(cvertex[1]);
			out.setZ(cvertex[2]);
		}
		return(out);
	}

	//method so that we can change a triangles normal
	void adaptnormal(double newvalue0, double newvalue1, double newvalue2){
		normal[0]=newvalue0;
		normal[1]=newvalue1;
		normal[2]=newvalue2;
	}

	//method so that we can change a triangles vertex
	void adaptvertex(String vertex, double newvalue0, double newvalue1, double newvalue2){
		if(vertex=="a"){
			avertex[0]=newvalue0;
			avertex[1]=newvalue1;
			avertex[2]=newvalue2;
		}
		if(vertex=="b"){
			bvertex[0]=newvalue0;
			bvertex[1]=newvalue1;
			bvertex[2]=newvalue2;
		}
		if(vertex=="c"){
			cvertex[0]=newvalue0;
			cvertex[1]=newvalue1;
			cvertex[2]=newvalue2;
		}
	}

	//method so that we can change a triangles z-coordiantes
	void adaptzvalues(double newvalue0, double newvalue1, double newvalue2){
		avertex[2]=newvalue0;
		bvertex[2]=newvalue1;
		cvertex[2]=newvalue2;
	}

	//method to retrieve the x, y or z coordinates of a triangle
	double[] returnncoordinate(String letter){
		double[] output = new double[3];
		if(letter=="x"){
			output[0]=avertex[0];
			output[1]=bvertex[0];
			output[2]=cvertex[0];
		}
		if(letter=="y"){
			output[0]=avertex[1];
			output[1]=bvertex[1];
			output[2]=cvertex[1];
		}
		if(letter=="z"){
			output[0]=avertex[2];
			output[1]=bvertex[2];
			output[2]=cvertex[2];
		}
		return(output);
	}

	//method to return a vertex or the normal of a triangle as an array of doubles
	double[] pick3values(String line){
		double[] output = new double[3];
		if(line=="normal"){
			output=normal;
		}
		if(line=="a"){
			output=avertex;
		}
		if(line=="b"){
			output=bvertex;
		}
		if(line=="c"){
			output=cvertex;
		}
		return(output);
	}

	void test(){
		System.out.println("testing in progress");
		Triangle tri = new Triangle();
		tri.normal[0] = 1;
		tri.adaptvertex("a", 4, 5, 6);
		assert(1==tri.normal[0]);
		assert(0==tri.normal[2]);
		assert(4==tri.avertex[0]);
		assert(6==tri.avertex[2]);
		tri.adaptnormal(7, 8, 9);
		assert(7==tri.normal[0]);
		assert(8==tri.normal[1]);
		tri.inputvalue(2, 2, 5);
		assert(5==tri.bvertex[2]);
		double[] temp = new double[3];
		temp=tri.returnncoordinate("z");
		assert(6==temp[0]);
		assert(5==temp[1]);
		assert(0==temp[2]);
	}	

	public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        Triangle tri = new Triangle();
        if(testing){
        	tri.test();
      	}
    }
}