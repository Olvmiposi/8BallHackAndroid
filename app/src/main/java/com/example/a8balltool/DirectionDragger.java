package com.example.a8balltool;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class DirectionDragger extends Shapes {

    //Constructor
    public DirectionDragger(Paint shapecolor, float left, float top){
        //Assign parameter values to properties of the Line (calls super's get/set methods)
        this.setShapeColor(shapecolor);
        this.setLeft(left);
        this.setTop(top);

    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint mainColor = this.getShapeColor();
        mainColor.setColor(Color.BLUE);
        mainColor.setStyle(Paint.Style.STROKE);
        mainColor.setStrokeWidth(7);
        canvas.drawLine(this.getLeft()-15, this.getTop(),this.getLeft()+15,this.getTop(),mainColor);
        canvas.drawLine(this.getLeft(), this.getTop(),this.getLeft(),this.getTop()+15,mainColor);
        Rect rect = new Rect((int)this.getLeft()-15, (int)this.getTop(),60,60);
        this.setBoundBox(rect);
    }
    @Override
    public void moveShapes(DrawView dv) {
        //To move DirectionDragger, add "speed" to current position x/y coords

        //Changes the x1 and y1 coordinates of the DirectionDragger
        this.setLeft(this.getLeft() + this.getxSpeed());
        this.setTop(this.getTop()+ this.getySpeed());

        //if the x1 and y1 coordinates is less than 0, i.e if it goes outside the Rectangle's x and y axis
        //reset the x1 and y1 position to zero, i.e stay on the 0 coordinates
        if (this.getLeft() < dv.Rect.getLeft()){
            this.setLeft(dv.Rect.getLeft());
        }else if (this.getLeft() > (dv.Rect.getWidth())){
            this.setLeft(dv.Rect.getWidth());
        }
        if (this.getTop() <dv.Rect.getTop()){
            this.setTop(dv.Rect.getTop());
        }else if (this.getTop() > (dv.Rect.getHeight())){
            this.setTop(dv.Rect.getHeight());
        }
    }
}