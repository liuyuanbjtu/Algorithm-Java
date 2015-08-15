package com.stdlib.www;

/******************************************************************************
 *  Compilation:  javac BouncingBall.java
 *  Execution:    java BouncingBall
 *  Dependencies: StdDraw.java
 *
 *  Implementation of a 2-d bouncing ball in the box from (-1, -1) to (1, 1).
 *
 *  % java BouncingBall
 *
 ******************************************************************************/

public class BouncingBall { 
    public static void main(String[] args) {

        // set the scale of the coordinate system
        StdDraw.setXscale(-1.0, 1.0);
        StdDraw.setYscale(-1.0, 1.0);

        // initial values 定义小球的圆心位置、速度和半径
        double rx = 0.480, ry = 0.860;     // position
        double vx = 0.030, vy = 0.023;     // velocity
        double radius = 0.05;              // radius

        // main animation loop
        while (true)  { 

            // bounce off wall according to law of elastic collision
        	//如果小球碰到墙壁，则速度立即更新，变成相反方向的速度
            if (Math.abs(rx + vx) > 1.0 - radius) vx = -vx;
            if (Math.abs(ry + vy) > 1.0 - radius) vy = -vy;

            // update position
            rx = rx + vx; 
            ry = ry + vy; 

            // clear the background
            StdDraw.setPenColor(StdDraw.GRAY);
            //Draw a filled square of side length 2r, centered on (x, y).
            StdDraw.filledSquare(0, 0, 1.0);

            // draw ball on the screen
            StdDraw.setPenColor(StdDraw.BLACK); 
            StdDraw.filledCircle(rx, ry, radius); 

            // display and pause for 20 ms
            //每过20ms迭代一次，画一次球
            StdDraw.show(20); 
        } 
    } 
} 
