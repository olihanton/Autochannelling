//class to take an STL file and return 2 STL files by creating at least one channel. This class will call
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Math.*;

public class Autochannel{

//method to convert .9999 or .00001 numbers to the closest whole
	double rounding(double n){
		n=n*1000;
		n=Math.round(n);
		n=n/1000;
		return(n);
	}

//equalting doubles
	boolean equate(double a, double b){
		if(Math.abs(a-b)<=0.00001){
			return(true);
		}
		return(false);
	}

//method to read through all the triangles in an item and if they have a z-coordinate of 0 then it puts them in a separate item.
	Item groupflatbase(Item origobj){
		Item sealevel = new Item();
		int n = origobj.numberoftriangles();
		int[] store = new int[n];
		for(int i=0;i<n;i++){
			Triangle temp = new Triangle();
			temp=origobj.returntri(i);
			double[] zvalues = new double[3];
			zvalues=temp.returnncoordinate("z");

			if(equate(zvalues[0],0) && equate(zvalues[1],0) && equate(zvalues[2],0)){
				sealevel.insert(temp);
				store[i]=1;
			}
		}
		return(sealevel);
	}

//a method to putthe triangles which ly flat on the xy plane at z=0 into a class "Base" which we can then use!.
//basically converts an item that represents a 2D object into a set of pairs of xy coordinates which also represent a 2D object.
	Base identifyingbase(Item base){
		int n = base.numberoftriangles();
		Base coords = new Base();
		double[] xcoords = new double[(3*n)];
		double[] ycoords = new double[(3*n)];
		for(int i=0;i<n;i++){
			Triangle temp = new Triangle();
			double[] triplex = new double[3];
			double[] tripley = new double[3];
			temp=base.returntri(i);
			triplex = temp.returnncoordinate("x");
			tripley = temp.returnncoordinate("y");
			for(int j=0; j<3; j++){
				Pair pr = new Pair();
				pr.setX(triplex[j]);
				pr.setY(tripley[j]);
				coords.addpair(pr);
			}
		}
		return(coords);
	}

//method which gets rid of duplicate coordinates (where two triangles touch) - currently hard coded for our examples
	Base reducebasesize(Base inputbase){
		int length = inputbase.numvertices();
		int[] duplicates = new int[length];
			inputbase.deletepair(4);
			inputbase.deletepair(3);
		inputbase.printbasecoords();
		return(inputbase);
	}

//method to find optimal center for a contact point (later, this but for multiple contact points) - for now it is just hard coded
	List<Pair> findcontactpoint(){
		List<Pair> contactpoints = new ArrayList<Pair>();
		Pair pr = new Pair();
		pr.setX(15.0);
		pr.setY(15.0);
		contactpoints.add(pr);
		pr.setX(10.0);
		pr.setY(20.0);
		contactpoints.add(pr);
		return(contactpoints);
	}


// method to create channel base from contact point and nchannelsides using some lovely trig. - for now it is just hard coded
	Base createchannelbase(Pair contactpoint, int nchannelsides){
		Base chbs = new Base();
		int nsides = nchannelsides;
		double radius = 5;
		double anglechange=(2*(Math.PI))/nsides;
		int eighth=nchannelsides/8;
		for(int i=(-eighth); i<(nsides-eighth); i++){
			double x = 0;
			double y = 0;
			x = contactpoint.getX() + Math.sin(anglechange*i)*radius;
			y = contactpoint.getY() + Math.cos(anglechange*i)*radius;
			Pair pr = new Pair();
			pr.setX(x);
			pr.setY(y);
			chbs.addpair(pr);
		}
		return(chbs);
	}

// method to recreate objectbase minus channels using contact point and nchannelsides
	Base recreateobjectbase(int nchannelsides, Item origobj, String inputname){
		int numpnts=0;
		Base chbs = new Base();
		Base objbs = new Base();
		Base exobjs = new Base();
		Pair pr = new Pair();
		chbs = createchannelbase(findcontactpoint().get(0), nchannelsides);
		numpnts = chbs.numvertices();
		objbs=reducebasesize(identifyingbase(groupflatbase(origobj)));
		// we want the second part of this function to increase the pairs of coordinates in objbs to 12*numberofchannels 
		// minus x. (x is to be determined), so that there are
		// the same number of points on the objectbase as on all the channel bases minus a crossover factor
		// for now we are just dealing with a single channel
		Base reorderedobjbs = new Base();
		if(inputname=="samplecube"){
			reorderedobjbs.addpair(objbs.getpair(3));
			reorderedobjbs.addpair(objbs.getpair(2));
			reorderedobjbs.addpair(objbs.getpair(0));
			reorderedobjbs.addpair(objbs.getpair(1));
		}
		else{
			reorderedobjbs.addpair(objbs.getpair(2));
			reorderedobjbs.addpair(objbs.getpair(0));
			reorderedobjbs.addpair(objbs.getpair(3));
			reorderedobjbs.addpair(objbs.getpair(1));
		}
		for(int i=0; i<4; i++){
			exobjs.addpair(reorderedobjbs.getpair(i));
		}
		for(int i=0; i<4; i++){
			double x1=reorderedobjbs.getpair(i).getX();
			double y1=reorderedobjbs.getpair(i).getY();
			double x2=reorderedobjbs.getpair((i+1)%4).getX();
			double y2=reorderedobjbs.getpair((i+1)%4).getY();
			for(int j=1; j<4; j++){
				pr = new Pair();
				if(x1<=x2){
					pr.setX(x1 + (j*((x2-x1)/4)));
				}
				else{
					pr.setX(x1 - (j*(Math.abs(x1-x2)/4)));
				}
				if(y1<=y2){
					pr.setY(y1 + (j*(Math.abs(y1-y2)/4)));
				}
				else{
					pr.setY(y1 - (j*(Math.abs(y1-y2)/4)));
				}
				exobjs.slipinpair(pr, (((i)*4)+j));
			}
		}
		System.out.println("Start");
		exobjs.printbasecoords();
		System.out.println("end");
		return(exobjs);
	}


//create top of an object - for now we are assuming that an object has a flat top...
	Ceiling createobjectceiling(Base objectbase, double touchpointz){
		Ceiling objcei = new Ceiling();
		Pair pr = new Pair();
		for(int i=0; i<objectbase.numvertices(); i++){
			pr=objectbase.getpair(i);
			pr.roundpair();
			objcei.addpair(pr);
		}
		objcei.setheight(rounding(touchpointz));
		return(objcei);
	}


//method to calculate the normal of a veritcal rectangle with an edge along the z=o sealevel base.
	Triple calcnorm(Triple point1, Triple point2, Pair center){
		Triple normal = new Triple();
		double x1 = point1.getX();
		double x2 = point2.getX();
		double y1 = point1.getY();
		double y2 = point2.getY();
		double c1 = center.getX();
		double c2 = center.getY();
		double p = Math.abs(c1- (Math.abs(x1-x2)));
		double q = Math.abs(c2- (Math.abs(y1-y2)));
		double h = Math.sqrt((p*p)+(q*q));
		double a = (1/h)*q;
		double b = (1/h)*p;
		normal.setX(a);
		normal.setY(b);
		normal.setZ(0);
		return(normal);
	}

//basically want to take in channelbase and recreate it at elevation touchpointz around touchpointx, touchpointy
//but smaller
	Ceiling createchannelceiling(Base channelbase, double touchpointx, double touchpointy, double touchpointz, int nchannelsides, double rad){
		Ceiling chancei = new Ceiling();
		chancei.setradius(rad); //to give a 3mm top
		double anglechange=(2*(Math.PI))/nchannelsides;
		int eighth=nchannelsides/8;
		for(int i=(-eighth); i<(nchannelsides-eighth); i++){
			double x = 0;
			double y = 0;
			x = touchpointx + Math.sin(anglechange*i)*(chancei.getradius());
			y = touchpointy + Math.cos(anglechange*i)*(chancei.getradius());
			Pair pr = new Pair();
			pr.setX(x);
			pr.setY(y);
			pr.roundpair();
			chancei.addpair(pr);
			chancei.setheight(rounding(touchpointz));
		}
		return(chancei);
	}

// method to finish channels using touch point and createchannelbase
// bin is a binary value. if it is 1 then we want the ends on the channel and the normals pointing outwards 
// from the channel
	Item createchannels(Base channelbase, Ceiling midringbig, Ceiling midringsmall, Ceiling channelceiling, double touchpointx, double touchpointy, double touchpointz, int nchannelsides, int bin){
		Item channels = new Item();
		Triple a1 = new Triple();
		Triple a2 = new Triple();
		Triple b1 = new Triple();
		Triple b2 = new Triple();
		Triple c1 = new Triple();
		Triple c2 = new Triple();
		Triple d1 = new Triple();
		Triple d2 = new Triple();
		Pair center = new Pair();
		Triple sidenormal = new Triple();
		Triple topcenter = new Triple();
		Triple botcenter = new Triple();
		Triple topnorm = new Triple();
		Triple botnorm = new Triple();
		center.setX(touchpointx);
		center.setY(touchpointy);
		topcenter.setX(touchpointx);
		topcenter.setY(touchpointy);
		topcenter.setZ(touchpointz);
		topnorm.setZ(1);
		botcenter.setX(findcontactpoint().get(0).getX());
		botcenter.setY(findcontactpoint().get(0).getY());
		botcenter.setZ(0);
		botnorm.setZ(1);
		for(int i=0; i<nchannelsides; i++){
			a1.setX(channelbase.getpair(i).getX());
			a1.setY(channelbase.getpair(i).getY());
			a1.setZ(0);
			a2.setX(channelbase.getpair((i+1)%nchannelsides).getX());
			a2.setY(channelbase.getpair((i+1)%nchannelsides).getY());
			a2.setZ(0);
			b1.setX(midringbig.getpair(i).getX());
			b1.setY(midringbig.getpair(i).getY());
			b1.setZ(midringbig.getheight());
			b2.setX(midringbig.getpair((i+1)%nchannelsides).getX());
			b2.setY(midringbig.getpair((i+1)%nchannelsides).getY());
			b2.setZ(midringbig.getheight());
			c1.setX(midringsmall.getpair(i).getX());
			c1.setY(midringsmall.getpair(i).getY());
			c1.setZ(midringsmall.getheight());
			c2.setX(midringsmall.getpair((i+1)%nchannelsides).getX());
			c2.setY(midringsmall.getpair((i+1)%nchannelsides).getY());
			c2.setZ(midringsmall.getheight());
			d1.setX(channelceiling.getpair(i).getX());
			d1.setY(channelceiling.getpair(i).getY());
			d1.setZ(channelceiling.getheight());
			d2.setX(channelceiling.getpair((i+1)%nchannelsides).getX());
			d2.setY(channelceiling.getpair((i+1)%nchannelsides).getY());
			d2.setZ(channelceiling.getheight());
			sidenormal = calcnorm(a2, a1, center);
			if(bin==1){
				channels.insert(threepointstotriangle(a1, a2, botcenter, botnorm)); //bottom slice
				insertrhombus(channels, b1, b2, a2, a1, sidenormal); //side1
				insertrhombus(channels, c1, c2, b2, b1, topnorm); //side2
				insertrhombus(channels, d1, d2, c2, c1, sidenormal); //side3
				channels.insert(threepointstotriangle(d2, d1, topcenter, topnorm)); //top slice
			}
			else{	
				sidenormal.setX(-1*sidenormal.getX());
				sidenormal.setY(-1*sidenormal.getY());
				sidenormal.setZ(-1*sidenormal.getZ());
				topnorm.setZ(-1*topnorm.getZ());
				insertrhombus(channels, b1, b2, a2, a1, sidenormal); //side1
				insertrhombus(channels, c1, c2, b2, b1, topnorm); //side2
				insertrhombus(channels, d1, d2, c2, c1, sidenormal); //side3
			}
		}
		//System.out.println("Number of triangles: " + channels.numberoftriangles());
		return(channels);
	}

//takes 3 points in 3D and creates a triangle out of them - N.B. for now the triangle has no normal...
	Triangle threepointstotriangle(Triple alpha, Triple beta, Triple gamma, Triple normal){
		Triangle tri = new Triangle();
		tri.adaptvertex("a", alpha.getX(), alpha.getY(), alpha.getZ());
		tri.adaptvertex("b", beta.getX(), beta.getY(), beta.getZ());
		tri.adaptvertex("c", gamma.getX(), gamma.getY(), gamma.getZ());
		tri.adaptnormal(normal.getX(), normal.getY(), normal.getZ());
		return(tri);
	}

//for a rectangle with 4 vertices labelled anticlockwise from top left. Inserts 2 triangles with the same normal
// so that the rhombus sits in STL format.
	void insertrhombus(Item obj, Triple alpha, Triple beta, Triple gamma, Triple delta, Triple normal){
		Triangle tri1 = new Triangle();
		Triangle tri2 = new Triangle();
		tri1=threepointstotriangle(alpha, beta, gamma, normal);
		tri2=threepointstotriangle(gamma, delta, alpha, normal);
		obj.insert(tri1);
		obj.insert(tri2);
	}

//method to combine all the triangles from two separate items into a single item
	Item combine2items(Item a, Item b){
		Item cong = new Item();
		int aleng = a.numberoftriangles();
		int bleng = b.numberoftriangles();

		for(int i=0; i<aleng; i++){
			cong.insert(a.returntri(i));
		}
		for(int i=0; i<bleng; i++){
			cong.insert(b.returntri(i));
		}
		return(cong);
	}

//method to extract all triangles from an item that arent part of the base or on a level at z=touchpointz. It then
// combines them all to form a new item (the sides of an object)
	Item extractobjwalls(Item origobj, double touchpointz){
		Item objwalls = new Item();
		int n = origobj.numberoftriangles();
		for(int i=0; i<n;i++){
			int flag=0;
			if(equate(origobj.returntri(i).returnncoordinate("z")[0],0) && equate(origobj.returntri(i).returnncoordinate("z")[1],0) && equate(origobj.returntri(i).returnncoordinate("z")[2],0)){
				flag++;
			}
			if(equate(origobj.returntri(i).returnncoordinate("z")[0],touchpointz) && equate(origobj.returntri(i).returnncoordinate("z")[1],touchpointz) && equate(origobj.returntri(i).returnncoordinate("z")[2],touchpointz)){
				flag++;
			}
			if(flag==0){
				objwalls.insert(origobj.returntri(i));
			}
		}
		return(objwalls);
	}

// method to check if the chosen straight line cuts through an triangle defined by three triples (tra, trb, trc)
// needs to run before creating channels or it will clash with them...
// s and e are the coordinates of the start and end of the line...
	boolean collisions(Triple tra, Triple trb, Triple trc , Triple s, Triple e, Triple intersectpoint){
		Triple trp = new Triple();
		Triple se = trp.subtract(e, s);
   		Triple sa = trp.subtract(tra, s);
   		Triple sb = trp.subtract(trb, s);
   		Triple sc = trp.subtract(trc, s);
   		Triple temp = trp.cross(se,sc);
   		double u = trp.dot(sb,temp);      
   		double v = (-1)*(trp.dot(sa,temp));
   		double w = trp.dot(se, trp.cross(sb,sa));
   		if(Math.signum(u)!=Math.signum(v) || Math.signum(u)!=Math.signum(w)){
      		return(false);
   		}
   		double avg = 1.0f / (u + v + w);
   		Triple parta = trp.multiply(tra, u * avg);
  		Triple partb = trp.multiply(trb, v * avg);
  		Triple partc = trp.multiply(trc, w * avg);
  		intersectpoint.setX(rounding(parta.getX() + partb.getX() + partc.getX()));
   		intersectpoint.setY(rounding(parta.getY() + partb.getY() + partc.getY()));
   		intersectpoint.setZ(rounding(parta.getZ() + partb.getZ() + partc.getZ()));
   		return(true);	
	}


//method to run collisions over a whole object.. between start s and end e of a 'ray'.
//returns false if there are no collisions N.B. if a line is on the edge of a triangle it will not recognise that as a collision...
	boolean checkforcoll(Item object, Triple s, Triple e){
		Triple tra = new Triple();
		Triple trb = new Triple();
		Triple trc = new Triple();
		int n = object.numberoftriangles();
		int record = 0;
		List<Triple> recordpoints = new ArrayList<Triple>();
		for(int i=0; i<n; i++){
			tra=object.returntri(i).returntriple(0);
			trb=object.returntri(i).returntriple(1);
			trc=object.returntri(i).returntriple(2);
			System.out.println(".");
			tra.printtriple();
			trb.printtriple();
			trc.printtriple();
			Triple intersectpoint = new Triple();
			if(collisions(tra, trb, trc, s, e, intersectpoint)){
				record++;
				System.out.println("HERE");
				intersectpoint.printtriple();
				recordpoints.add(intersectpoint);
			}
		}
		if(record==0){
			System.out.print("There are no collisions\n");
			return(true);
		}
		else{
			System.out.print("There are " + record + " intersections\n");
		}
		return(false);
	}

//method to finish object - combine the channel walls, the object's sides and create a base and ceiling...
	Item createobject(Base objectbase, Ceiling objceil, Base channelbase, Ceiling chnceil, Item channel, Item objwalls, double touchpointx, double touchpointy, double touchpointz, int nchannelsides){
		Item object = new Item();
		object = combine2items(channel, objwalls);
		Triple a1 = new Triple();
		Triple a2 = new Triple();
		Triple b1 = new Triple();
		Triple b2 = new Triple();
		Triple topnorm = new Triple();
		Triple botnorm = new Triple();
		topnorm.setZ(1);
		botnorm.setZ(-1);
		System.out.println("Object ceiling:");
		objceil.printceilingcoords();
		System.out.println("Channel ceiling:");
		chnceil.printceilingcoords();
		for(int i=0; i<nchannelsides; i++){
			a1.setX(objectbase.getpair((0+i)%nchannelsides).getX());
			a1.setY(objectbase.getpair((0+i)%nchannelsides).getY());
			a1.setZ(0);
			a2.setX(objectbase.getpair((1+i)%nchannelsides).getX());
			a2.setY(objectbase.getpair((1+i)%nchannelsides).getY());
			a2.setZ(0);
			b1.setX(channelbase.getpair((1+i)%nchannelsides).getX());
			b1.setY(channelbase.getpair((1+i)%nchannelsides).getY());
			b1.setZ(0);
			b2.setX(channelbase.getpair((0+i)%nchannelsides).getX());
			b2.setY(channelbase.getpair((0+i)%nchannelsides).getY());
			b2.setZ(0);
			insertrhombus(object, b1, b2, a1, a2, botnorm);
			a1.setX(objceil.getpair((i)%nchannelsides).getX());
			a1.setY(objceil.getpair((i)%nchannelsides).getY());
			a1.setZ(touchpointz);
			a2.setX(objceil.getpair((i+1)%nchannelsides).getX());
			a2.setY(objceil.getpair((i+1)%nchannelsides).getY());
			a2.setZ(touchpointz);
			b1.setX(chnceil.getpair((i+1)%nchannelsides).getX());
			b1.setY(chnceil.getpair((i+1)%nchannelsides).getY());
			b1.setZ(touchpointz);
			b2.setX(chnceil.getpair((i+0)%nchannelsides).getX());
			b2.setY(chnceil.getpair((i+0)%nchannelsides).getY());
			b2.setZ(touchpointz);
			insertrhombus(object, a1, a2, b1, b2, topnorm);
		}	
		return(object);
	}

	void test(){
		System.out.println("testing in progress");
      	assert(false==equate(0.01, 0.02));
      	assert(true==equate(1.00000001, 1.00000002));
    }

    public static void main(String[] args) {
      	boolean testing = false;
        assert(testing = true);
        if(testing){
           	Autochannel ac = new Autochannel();
        	ac.test();
      	}
   	}
}


