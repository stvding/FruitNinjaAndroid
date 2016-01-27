/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery
 */
package com.example.a4;
import com.example.a4complete.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Region.Op;
import android.util.Log;
import android.app.Activity;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit {
    private Path path = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Matrix transform = new Matrix();
    
    public  	boolean			isSliced	 	= false;
    public		boolean 		subPiece		= false;
    public	 	float 			vyreal;
    public 		float 			vy;
    public	 	float 			vx				= 0;
    public 		int 			piece 			= -1;
    //public 		int 			color 			= Color.BLACK;
    //private 	Canvas			can;
    //public 		PointF 			pp1,pp2 			= null ;
    //public 		Path 			linepath 		= null;
    //public 		Region 			line 			= null;
	//private Path savedUpperCut;
//    private Paint bitmapPaint = new Paint(); 
//    private Bitmap fruitBMP;
//    private BitmapShader fruitBMPShader;  
//    public Resources res = null;
    
       

    /**
     * A fruit is represented as Path, typically populated 
     * by a series of points 
     */
    Fruit(double[] points,boolean sub,int piece) {
        init();
        this.path.reset();
        this.path.moveTo((float)points[0], (float)points[1]);
        for (int i = 2; i < points.length; i += 2) {
            this.path.lineTo((float)points[i], (float)points[i + 1]);
        }
        this.path.moveTo((float)points[0], (float)points[1]);
        
    	int rand = 0 + (int)(Math.random() * ((2 - 0) + 1));
    	if(rand == 0){
    		setFillColor(Color.YELLOW);	
    	}else if (rand == 1){
    		setFillColor(Color.CYAN);
    	}else{
    		setFillColor(Color.RED);
    	}
    	
    	
    	if(subPiece){
    		vy=40;
    		vyreal=40;
    	}else{
    		vy=-50;
    		vyreal=-50;
    	}
    	this.isSliced = false;
    	this.piece = piece;
    	this.subPiece = sub;
    }

    Fruit(Region region,boolean sub,int piece) {
        init();
        this.path = region.getBoundaryPath();
        this.subPiece = sub;
        this.piece = piece;
    }

    Fruit(Path path,boolean sub,int piece,int color) {
        init();
        this.path = path;
        this.subPiece = sub;
        this.piece = piece;
        setFillColor(color);
    }

    private void init() {
        //this.paint.setColor(Color.BLUE);
        this.paint.setStrokeWidth(2);
        //linepath = new Path();
    }

    /**
     * The color used to paint the interior of the Fruit.
     */
    public int getFillColor() { return paint.getColor(); }
    public void setFillColor(int color) { paint.setColor(color); }

    /**
     * The width of the outline stroke used when painting.
     */
    public double getOutlineWidth() { return paint.getStrokeWidth(); }
    public void setOutlineWidth(float newWidth) { paint.setStrokeWidth(newWidth); }

    /**
     * Concatenates transforms to the Fruit's affine transform
     */
    public void rotate(float theta) { transform.postRotate(theta); }
    public void scale(float x, float y) { transform.postScale(x, y); }
    public void translate(float tx, float ty) { transform.postTranslate(tx, ty); }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public Matrix getTransform() { return transform; }

    /**
     * The path used to describe the fruit shape.
     */
    public Path getTransformedPath() {
        Path originalPath = new Path(path);
        Path transformedPath = new Path();
        originalPath.transform(transform, transformedPath);
        return transformedPath;
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Canvas canvas) {
        // TODO BEGIN CS349
        // tell the shape to draw itself using the matrix and paint parameters
        // TODO END CS349

        
//        this.fruitBMP = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
//        this.fruitBMPShader = new BitmapShader(fruitBMP, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//
//    	this.bitmapPaint.setColor(0xFFFFFFFF);  
//        this.bitmapPaint.setStyle(Paint.Style.FILL);
//        this.bitmapPaint.setShader(fruitBMPShader);  
        
    	
    	canvas.drawPath(getTransformedPath(), paint);
    	this.paint.setStyle(Paint.Style.STROKE);
    	this.setOutlineWidth(5);
    	int backupColor = this.getFillColor();
    	//Log.d("color","backup = "+backupColor);
    	this.setFillColor(Color.BLACK);
    	canvas.drawPath(getTransformedPath(), this.paint);
    	paint.setStyle(Paint.Style.FILL);
    	this.setFillColor(backupColor);
    	//canvas.drawPath(linepath, paint);

//    	if(pp1!=null && pp2!=null){
//    		//Log.d("drawline", "draw");
//        	setFillColor(Color.RED);
//    		
//        	//canvas.drawPath(savedUpperCut, paint);
//        	//canvas.drawRect(line.getBounds(), paint);
//        	
//        	setFillColor(Color.BLUE);
//    	}
    	
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(PointF p1, PointF p2) {
        // TODO BEGIN CS349
        // calculate angle between points
        // rotate and flatten points passed in 
        // rotate path and create region for comparison
    	//Log.e("slice","Point 1 = "+p1.toString()+" Point 2 = "+p2.toString());
    	if(!this.isSliced && !this.subPiece){
//    		float angle = Graphics2D.findAngle(p1, p2);
//	    	Log.d("angle", "angle = "+ angle);
	    	
//	    	Matrix pointTransform = new Matrix();
//	    	pointTransform.postRotate(angle);
//	    	float[] rotatedPoint1 = {p1.x,p1.y};
//	    	float[] rotatedPoint2 = {p2.x,p2.y};
//			pointTransform.mapPoints(rotatedPoint1);
//			pointTransform.mapPoints(rotatedPoint2);
			
			//Log.d("rotate","before = " + p1.toString() + "   " + p2.toString());
//			Log.d("rotate","after = " + rotatedPoint1[0] + "," + rotatedPoint1[1]+ "   "+
//					rotatedPoint2[0]+","+rotatedPoint2[1]);
			
			
//			Path line = new Path();
//			line.moveTo(rotatedPoint1[0], rotatedPoint1[1]);
//			line.lineTo(rotatedPoint2[0], rotatedPoint2[1]);
//			line.lineTo(rotatedPoint2[0], rotatedPoint2[1]-5);
//			line.lineTo(rotatedPoint1[0], rotatedPoint1[1]-5);
			
//			Region lineRegion = new Region(new Rect(
//					(int)rotatedPoint1[0], (int)rotatedPoint1[1]+5, 
//					(int)rotatedPoint2[0], (int)rotatedPoint1[1]));
//			this.linepath = lineRegion.getBoundaryPath();
			
//			float[] a = {rotatedPoint1[0], rotatedPoint1[1]};
//			float[] b = {rotatedPoint2[0], rotatedPoint2[1]};
//			float[] c = {rotatedPoint2[0], rotatedPoint2[1]-5};
//			float[] d = {rotatedPoint1[0], rotatedPoint1[1]-5};
//			Matrix pointTransform2 = new Matrix();
//	    	pointTransform2.postRotate(-angle);
//	    	pointTransform2.mapPoints(a);
//	    	pointTransform2.mapPoints(b);
//	    	pointTransform2.mapPoints(c);
//	    	pointTransform2.mapPoints(d);
//			
//			this.linepath.moveTo(a[0], a[1]);
//			this.linepath.lineTo(b[0], b[1]);
//			this.linepath.lineTo(c[0], c[1]);
//			this.linepath.lineTo(d[0], d[1]);
			
//			this.rotate(angle);
//			Region fruit = new Region();
//			fruit.setPath(this.getTransformedPath(), new Region(0,0,2000,2000));
//	    	this.rotate(-angle);
	    	
	//    	linepath.moveTo(p1.x, p1.y);
	//    	linepath.lineTo(p2.x,p2.y);
	//    	linepath.lineTo(p2.x-1,p2.y-1);
	//    	linepath.lineTo(p1.x-1, p1.y-2);
    		
    		//worked method starts
    		Path line = new Path();
    		line.moveTo(p1.x, p1.y);
    		line.lineTo(p2.x, p2.y);
    		line.lineTo(p2.x+1, p2.y);
    		line.lineTo(p1.x+1, p1.y);
    		
    		Region lineRegion = new Region();
    		lineRegion.setPath(line, new Region(0,0,2000,2000));
    		
			Region fruit = new Region();
			fruit.setPath(this.getTransformedPath(), new Region(0,0,2000,2000));
    		//worked method ends
    		
	    	if(fruit.op(lineRegion, Op.INTERSECT)){
	    		Log.e("slice","cut!!");
	    		return true;
	    	}
	        // TODO END CS349
    	}
    	
        return false;
    }


	/**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(PointF p1) {
        Region region = new Region();
        boolean valid = region.setPath(getTransformedPath(), new Region());
        return valid && region.contains((int) p1.x, (int) p1.y);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(PointF p1, PointF p2) {
    	Path topPath = null;
    	Path bottomPath = null;
    	// TODO BEGIN CS349
        // calculate angle between points
        // rotate and flatten points passed in
        // rotate region
        // define region masks and use to split region into top and bottom
    	

//rotate method starts
//    	float angle = Graphics2D.findAngle(p1, p2);
//    	Matrix pointTransform = new Matrix();
//    	pointTransform.postRotate(angle);
    	
//    	Path cut = new Path();
//    	cut.moveTo(p1.x, p1.y);
//    	cut.lineTo(p2.x, p2.y);
//    	
//    	cut.transform(pointTransform);
//    	Log.d("cut",cut.toString());
    	
//    	float[] rotatedPoint1 = {p1.x,p1.y};
//    	float[] rotatedPoint2 = {p2.x,p2.y};
//		pointTransform.mapPoints(rotatedPoint1);
//		pointTransform.mapPoints(rotatedPoint2);
//		
//		Log.d("rotate","before = " + p1.toString());
//		Log.d("rotate","after = " + rotatedPoint1[0] + " " + rotatedPoint1[1]);
//		
//		this.rotate(angle);
//    	
//		Path upperRectPath = new Path();
//		upperRectPath.moveTo(rotatedPoint1[0], rotatedPoint1[1]);
//		upperRectPath.lineTo(rotatedPoint2[0], rotatedPoint2[1]);
//		upperRectPath.lineTo(rotatedPoint2[0], 100);
//		upperRectPath.lineTo(rotatedPoint1[0], 100);
//		Region upperRect = new Region();
//		upperRect.setPath(upperRectPath, new Region(0,0,2000,2000));
//		
//		
//		Region upperFruit = new Region();
//		upperFruit.setPath(this.getTransformedPath(), new Region(0,0,2000,2000));
//		upperFruit.op(upperRect, Op.INTERSECT);
//		
//		Region lowerFruit = new Region();
//		lowerFruit.setPath(this.getTransformedPath(), new Region(0,0,2000,2000));
//		lowerFruit.op(upperFruit,Op.DIFFERENCE);
//    	
//		topPath = upperFruit.getBoundaryPath();
//		bottomPath = lowerFruit.getBoundaryPath();
//		
//		pointTransform.postRotate(-angle);
//		pointTransform.postRotate(-angle);
//		topPath.transform(pointTransform);
//		bottomPath.transform(pointTransform);
//		this.rotate(-angle);
    	
//rotate method ends
    	
    	//worked method starts
    	Region fruitRegion = new Region();
    	fruitRegion.setPath(this.getTransformedPath(), new Region(0,0,2000,2000));
    	
    	
    	Path upperCut = new Path();
    	upperCut.moveTo(p1.x, p1.y);
    	upperCut.lineTo(p2.x, p2.y);
    	upperCut.lineTo(p2.x-1000, p2.y-1000);
    	upperCut.lineTo(p1.x-1000, p1.y-1000);
    	
    	Region upperCutRegion = new Region();
    	upperCutRegion.setPath(upperCut, new Region(0,0,2000,2000));
    	upperCutRegion.op(fruitRegion,Op.INTERSECT);
    	
    	Region lowerCutRegion = fruitRegion;
    	lowerCutRegion.op(upperCutRegion, Op.DIFFERENCE);
    	
    	topPath = upperCutRegion.getBoundaryPath();
    	bottomPath = lowerCutRegion.getBoundaryPath();
    	
    	//worked method ends
    	
    	
    	
//    	double tan = Math.tan(angle);
//    	Path upperCut = new Path();
//    	Path lowerCut = new Path();
//    	int width = 720;
//    	int height = 1280;
//    	
//    	
//    	upperCut.moveTo(p1.x,p1.y);
//		upperCut.lineTo(p2.x,p2.y);
//		upperCut.lineTo(p1.x,p2.y);
//    	
//		lowerCut.moveTo(p1.x, p1.y);
//		lowerCut.lineTo(p2.x, p2.y);
//		lowerCut.lineTo(p2.x, p1.y);
		
		
//    	if(p1.x < p2.x){
//    		if(p1.y < p2.y){
//    			upperCut.moveTo(p1.x,p1.y);
//    			upperCut.lineTo(p2.x,p2.y);
//    			upperCut.lineTo(p1.x,p2.y);
//    			//this.savedUpperCut = upperCut;
//    		}else{
//    			
//    		}
//    	}else{
//    		if(p1.y < p2.y){
//    			
//    		}else{
//    			
//    		}
//    	}
    	

    	
//    	Region upperCutRegion = new Region();
//    	upperCutRegion.setPath(upperCut, new Region(0,0,1000,1000));
//    	
//    	Region topPathRegion = new Region();
//    	topPathRegion.setPath(this.getTransformedPath(), new Region(0,0,1000,1000));
//    	topPathRegion.op(upperCutRegion, Op.INTERSECT);
//    	
//    	Region bottomPathRegion = new Region();
//    	bottomPathRegion.setPath(this.getTransformedPath(), new Region(0,0,1000,1000));
//    	bottomPathRegion.op(topPathRegion, Op.DIFFERENCE);
//    	
//    	topPath = new Path();
//    	topPath = topPathRegion.getBoundaryPath();
//    	
//    	bottomPath = new Path();
//    	bottomPath = bottomPathRegion.getBoundaryPath();
		
//		topPath = upperCut;
//		bottomPath = lowerCut;
//		
    	
//    	topPath = new Path();
//    	topPath.moveTo(0, 20);
//    	topPath.lineTo(20, 0);
//    	topPath.lineTo(20,40);
//    	
//    	bottomPath = new Path();
//    	bottomPath.moveTo(20,0);
//    	bottomPath.lineTo(40,20);
//    	bottomPath.lineTo(20,40);
    	
        // TODO END CS349
        if (topPath != null && bottomPath != null) {
        	this.isSliced = true;
        	Log.d("color","color = "+this.getFillColor());
           return new Fruit[] { new Fruit(topPath,true,0,this.getFillColor()), new Fruit(bottomPath,true,1,this.getFillColor()) };
        }
        return new Fruit[0];
    }
}
