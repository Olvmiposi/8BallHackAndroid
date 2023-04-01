package com.example.a8balltool;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangle extends Shapes {

    //Constructor
    public Rectangle(Paint shapecolor, float left, float top, float height, float width){
        //Assign parameter values to properties of the Line (calls super's get/set methods)
        this.setShapeColor(shapecolor);
        this.setLeft(left);
        this.setTop(top);
        this.setHeight(height);
        this.setWidth(width);
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint mainColor = this.getShapeColor();
        mainColor.setColor(Color.WHITE);
        canvas.drawRect(this.getLeft(),this.getTop(),this.getWidth(),this.getHeight(),mainColor);
        Rect rect = new Rect((int)this.getLeft(), (int)this.getTop(),(int)this.getWidth(),(int)this.getHeight());
        this.setBoundBox(rect);
    }
    @Override
    public void moveShapes(DrawView dv) {}
}