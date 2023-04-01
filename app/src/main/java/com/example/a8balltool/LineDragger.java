package com.example.a8balltool;
import android.graphics.*;

public class LineDragger extends Shapes {



    //Constructor
    public LineDragger(Paint shapecolor, float left, float top){
        //Assign parameter values to properties of the Line (calls super's get/set methods)
        this.setShapeColor(shapecolor);
        this.setLeft(left);
        this.setTop(top);


    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint mainColor = this.getShapeColor();
        mainColor.setColor(Color.YELLOW);
        mainColor.setStyle(Paint.Style.STROKE);
        mainColor.setStrokeWidth(7);
        canvas.drawLine(this.getLeft()-15, this.getTop(),this.getLeft()+15,this.getTop(),mainColor);
        canvas.drawLine(this.getLeft(), this.getTop()-15,this.getLeft(),this.getTop()+15,mainColor);
        Rect rect = new Rect((int)this.getLeft()-15, (int)this.getTop()-15,60,60);
        this.setBoundBox(rect);
    }
    @Override
    public void moveShapes(DrawView dv) {}
}