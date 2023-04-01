package com.example.a8balltool;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.provider.Settings;
import android.view.*;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class DrawView extends View {
    //This JPanel will serve as our drawing board, our canvas.
    private Timer timer;
    //private static final int BACKGROUND = Color.TRANSPARENT;
    private int xDelta, yDelta, x2, y2;
    private Line lineTopLeft,lineDownLeft,lineTopRight,lineDownRight,lineTop,lineDown,DirLine,TrickLine ;
    private LineDragger MidButton;
    private DirectionDragger Trickshot;


    Paint paint = new Paint();
    //Declaring Rectangle, Line, Line dragger and Direction dragger Objects
    LineDragger TopResize = new LineDragger(paint,50,50);
    LineDragger DownResize = new LineDragger(paint,700,700);

    //Rectangle gets its x, y, width and height from the Line dragger above
    Rectangle Rect = new Rectangle(paint,TopResize.getLeft(),TopResize.getTop(),DownResize.getTop(),DownResize.getLeft());


    Line[] Lines = new Line[6];//Arraylist of lines
    Shapes[] Shape = new Shapes[3];


    public DrawView(Context context) {
        super(context);
        timer = new Timer();

        x2 = (int)((Rect.getLeft()/2)+(Rect.getWidth()/2));
        y2 = (int)((Rect.getTop()/2)+(Rect.getHeight()/2));
        //Top left line
        lineTopLeft = new Line(paint,Rect.getLeft(),Rect.getTop(),x2,y2);
        //Down left line
        lineDownLeft = new Line(paint,Rect.getLeft(),Rect.getHeight(), x2, y2);
        //Top Right line
        lineTopRight = new Line(paint,DownResize.getLeft(), Rect.getTop(), x2, y2);
        //Down Right line
        lineDownRight = new Line(paint,DownResize.getLeft(),DownResize.getTop(), x2, y2);
        //Top line
        lineTop = new Line(paint,(Rect.getLeft()/2)+(Rect.getWidth()/2), Rect.getTop(), x2, y2);
        //Down line
        lineDown = new Line(paint,TopResize.getLeft()/2+(Rect.getWidth()/2), Rect.getHeight(), x2, y2);
        //Direction line
        DirLine = new Line(paint,(Rect.getLeft()/2)+(Rect.getWidth()/2), Rect.getTop(), x2, y2);
        //TrickShot line
        TrickLine = new Line(paint,(Rect.getLeft()/2)+(Rect.getWidth()/2), Rect.getTop(), (Rect.getLeft()/2)+(Rect.getWidth()/2), Rect.getTop() + 200);
        //Line Dragger
        MidButton = new LineDragger(paint,x2,y2);
        //Direction Dragger
        Trickshot = new DirectionDragger(paint,((Rect.getLeft()/2)+Rect.getWidth()/2),Rect.getTop());


        Lines[0] = lineTopLeft;
        Lines[1] = lineDownLeft;
        Lines[2] = lineTopRight;
        Lines[3] = lineDownRight;
        Lines[4] = lineTop;
        Lines[5] = lineDown;

        //Adding Shapes to Array List
        Shape[0] = DirLine;
        Shape[1] = TrickLine;
        Shape[2] = Trickshot;

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int eventaction = event.getAction();
        xDelta = (int) event.getX();
        yDelta = (int) event.getY();

        //Button Distance checks if the mouse or finger is over the button, or if user clicks the button
        double MidButtonDistance = Math.sqrt(Math.pow(xDelta - MidButton.getLeft(), 2) + Math.pow(yDelta - MidButton.getTop(), 2));
        double TrickshotDistance = Math.sqrt(Math.pow(xDelta - Trickshot.getLeft(), 2) + Math.pow(yDelta - Trickshot.getTop(), 2));
        double TopResizeDistance = Math.sqrt(Math.pow(xDelta - TopResize.getLeft(), 2) + Math.pow(yDelta - TopResize.getTop(), 2));
        double DownResizeDistance = Math.sqrt(Math.pow(xDelta - DownResize.getLeft(), 2) + Math.pow(yDelta - DownResize.getTop(), 2));

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
                System.out.println("x position :" + xDelta + "  " +  "y position :" + yDelta);
                System.out.println("Rectangle :" +MidButton.getBoundBox().toString());
                System.out.println("Distance: " + MidButtonDistance);
                invalidate();
                break;

        case MotionEvent.ACTION_MOVE: // touch drag with the ball
            startOrStopMoving();
            if (MidButtonDistance < 40) { // It checks if the mouse is within the radius of 40 to the shape
                Angle();
                for(Line line: Lines){
                    line.setRight(xDelta);
                    line.setBottom(yDelta);
                }
                DirLine.setRight(xDelta);
                DirLine.setBottom(yDelta);
                MidButton.setLeft(xDelta);
                MidButton.setTop(yDelta);
                invalidate();
                break;
            }

            if (TrickshotDistance < 40) {
                Angle();
                for(Shapes Shape: Shape){
                    Shape.setLeft(xDelta);
                    Shape.setTop(yDelta);
                }
                Trickshot.setLeft(xDelta);
                Trickshot.setTop(yDelta);
                invalidate();
                break;
            }
            if (TopResizeDistance < 40) {
                TopResize.setLeft(xDelta);
                TopResize.setTop(yDelta);
                Resize();
                invalidate();
                break;
            }
            if (DownResizeDistance < 40) {
                DownResize.setLeft(xDelta);
                DownResize.setTop(yDelta);
                Resize();
                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping
                break;
        }
        // redraw the canvas
        this.postInvalidate();
        //Toast.makeText(getContext(),"8 Ball Tool", Toast.LENGTH_SHORT).show();
        return true;
    }
        @Override
    public void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        Rect.onDraw(canvas);
        for(Line line: Lines){
            line.onDraw(canvas);
        }
        DirLine.onDraw(canvas);
        TrickLine.onDraw(canvas);
        MidButton.onDraw(canvas);
        Trickshot.onDraw(canvas);
        TopResize.onDraw(canvas);
        DownResize.onDraw(canvas);
        checkcollision();
        
    }
    public void Angle(){
        int length = (int) Math.sqrt(Math.pow(DirLine.getBottom() - DirLine.getTop(), 2) + Math.pow(DirLine.getRight() - DirLine.getLeft(), 2));
        //angle is the degree at which direction line is facing
        int angle = (int) Math.toDegrees(Math.atan2(-(DirLine.getLeft() - DirLine.getRight()), (DirLine.getTop() - DirLine.getBottom())));
        //the angle sets the trickline to its end coordinates
        TrickLine.setRight((int) (TrickLine.getLeft() - (length + 400) * Math.sin(Math.toRadians(angle))));
        TrickLine.setBottom((int) (TrickLine.getTop() - (length + 400) * Math.cos(Math.toRadians(angle))));
        //if the angle is at a degree(63, -63, 116, -116), trickline projects
        if (angle >= 63 && angle <= 116 || angle <= -63 && angle >= -116) {
            angle = (int) Math.toDegrees(Math.atan2((DirLine.getLeft() - DirLine.getRight()), -(DirLine.getTop() - DirLine.getBottom())));
            TrickLine.setRight((int) (TrickLine.getLeft() - (length + 400) * Math.sin(Math.toRadians(angle))));
            TrickLine.setBottom((int) (TrickLine.getTop() - (length + 400) * Math.cos(Math.toRadians(angle))));
        }
    }
    public void Resize(){
        for(Line line: Lines){
            line.setRight((Rect.getLeft()/2)+(Rect.getWidth()/2));
            line.setBottom((Rect.getTop()/2)+(Rect.getHeight()/2));
        }
        DirLine.setRight((Rect.getLeft()/2)+(Rect.getWidth()/2));
        DirLine.setBottom((Rect.getTop()/2)+(Rect.getHeight()/2));
        Rect.setLeft(TopResize.getLeft());
        Rect.setTop(TopResize.getTop());
        Rect.setWidth(DownResize.getLeft());
        Rect.setHeight(DownResize.getTop());
        lineTopLeft.setLeft(Rect.getLeft());
        lineTopLeft.setTop(Rect.getTop());
        lineDownLeft.setLeft(Rect.getLeft());
        lineDownLeft.setTop(Rect.getHeight());
        lineTopRight.setLeft(DownResize.getLeft());
        lineTopRight.setTop(Rect.getTop());
        lineDownRight.setLeft(DownResize.getLeft());
        lineDownRight.setTop(DownResize.getTop());
        lineTop.setLeft((Rect.getLeft()/2)+(Rect.getWidth()/2));
        lineTop.setTop(Rect.getTop());
        lineDown.setLeft(TopResize.getLeft()/2+(Rect.getWidth()/2));
        lineDown.setTop(Rect.getHeight());
        DirLine.setLeft((Rect.getLeft()/2)+(Rect.getWidth()/2));
        DirLine.setTop(Rect.getTop());
        TrickLine.setLeft((Rect.getLeft()/2)+(Rect.getWidth()/2));
        TrickLine.setTop(Rect.getTop());
        TrickLine.setRight((Rect.getLeft()/2)+(Rect.getWidth()/2));
        TrickLine.setBottom(Rect.getTop() + 200);
        MidButton.setLeft((Rect.getLeft()/2)+(Rect.getWidth()/2));
        MidButton.setTop((Rect.getTop()/2)+(Rect.getHeight()/2));
        Trickshot.setLeft((Rect.getLeft()/2)+(Rect.getWidth()/2));
        Trickshot.setTop(Rect.getTop());
    }
    public void checkcollision(){
        //Changes the Left and Top coordinates of the DirectionLine
        DirLine.setLeft(DirLine.getLeft() + DirLine.getxSpeed());
        DirLine.setTop(DirLine.getTop()+ DirLine.getySpeed());
        //if the Left and Top coordinates is less than 0, i.e if it goes outside the Rectangles's x and y axis
        //reset the Left and Top position to zero, i.e stay on the 0 coordinates
        //If the DirLine touches the height(left hand) of the rectangle, it should stay
        if (DirLine.getLeft() <Rect.getLeft()){
            DirLine.setLeft(Trickshot.getLeft());
        }
        //If the DirLine touches the height(right hand) of the rectangle, it should stay
        else if (DirLine.getLeft() > (Rect.getLeft())){
            DirLine.setLeft(Trickshot.getLeft());
        }
        //If the DirLine touches the up width of the rectangle, it should stay
        if (DirLine.getTop() <Rect.getTop()){
            DirLine.setTop(Trickshot.getTop());
        }
        //If the DirLine touches the base of the rectangle, it should stay
        else if (DirLine.getTop() > (Rect.getTop())){
            DirLine.setTop(Trickshot.getTop());
        }
        TrickLine.setLeft(TrickLine.getLeft() + TrickLine.getxSpeed());
        TrickLine.setTop(TrickLine.getTop()+ TrickLine.getySpeed());

        //if the Left and Top coordinates is less than 0, i.e if it goes outside the Rectangles's x and y axis
        //reset the Left and Top position to zero, i.e stay on the 0 coordinates
        //If the trickline touches the height(left hand) of the rectangle, it should stay
        if (TrickLine.getLeft() <Rect.getLeft()){
            TrickLine.setLeft(Trickshot.getLeft());
            //System.out.println("Left HEIGHT ");
        }
        //If the trickline touches the height(right hand) of the rectangle, it should stay
        else if (TrickLine.getLeft() > (Rect.getLeft())){
            TrickLine.setLeft(Trickshot.getLeft());
            //System.out.println("HEIGHT ");
        }
        //If the trickline touches the up width of the rectangle, it should stay
        if (TrickLine.getTop() <Rect.getTop()){
            TrickLine.setTop(Trickshot.getTop());
            //System.out.println("Up WIDTH ");
        }
        //If the trickline touches the base(width) of the rectangle, it should stay
        else if (TrickLine.getTop() > (Rect.getTop())){
            TrickLine.setTop(Trickshot.getTop());
            //System.out.println("WIDTH ");
        }
    }
    public void driveTheShapes() {
        //Call the "move me" method for Direction Dragger
        Trickshot.moveShapes(this);
        //After the move, tell the panel to repaint (which re-calls
        //the paintComponent() method)
        DrawView.this.invalidate();
    }
    private void startOrStopMoving()  {
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
            driveTheShapes();
            invalidate();
        }
    }, 1000, 1000);}
}