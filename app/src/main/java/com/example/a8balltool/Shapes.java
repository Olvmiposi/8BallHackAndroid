package com.example.a8balltool;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public abstract class Shapes {
    //Properties of Shapes
    private Paint shapeColor;

    private float left;
    private float top;

    private float right;
    private float bottom;

    private float size = 8;

    private float height;        //Height of Shape
    private float width;        //Width of Shape

    private Rect boundBox;

    private float xSpeed = 0;    //Set default speeds for x & y
    private float ySpeed = 0;

    public Rect getBoundBox(){return boundBox;}
    public void setBoundBox(Rect boundBox){this.boundBox = boundBox;}


    //Abstract method to draw the Shapes,
    public abstract void onDraw(Canvas canvas);
    //Abstract method to move the Shapes,
    public abstract void moveShapes(DrawView dv);


    public Paint getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(Paint shapeColor) {
        this.shapeColor = shapeColor;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

}