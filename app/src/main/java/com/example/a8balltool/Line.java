package com.example.a8balltool;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Line extends Shapes {

    //Constructor
    public Line(Paint shapecolor, float left, float top, float right, float bottom){
        //Assign parameter values to properties of the Line (calls super's get/set methods)
        this.setShapeColor(shapecolor);
        this.setLeft(left);
        this.setTop(top);
        this.setRight(right);
        this.setBottom(bottom);
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint mainColor = this.getShapeColor();
        mainColor.setColor(Color.BLACK);
        canvas.drawLine(this.getLeft(), this.getTop(),this.getRight(),this.getBottom(),mainColor);
        Rect rect = new Rect((int)Math.min(this.getLeft(),(int)this.getRight()) , (int)Math.min(this.getTop(),this.getBottom()), (int)(Math.max(this.getLeft(),this.getRight())) - (int)(Math.min(this.getLeft(),this.getRight())),(int)(Math.max(this.getTop(),this.getBottom())) - (int)(Math.min(this.getTop(),this.getBottom())));
        this.setBoundBox(rect);
    }
    @Override
    public void moveShapes(DrawView dv) {}
}