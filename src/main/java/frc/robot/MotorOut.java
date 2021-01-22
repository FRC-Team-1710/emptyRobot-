/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/



package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;


public class MotorOut {
public static CANSparkMax CoopersSpark;
public static XboxController Controller;
public static double destination; //setting a double "destination" that will be used later
public static double speed;
public static double closestQuad;
    public static void motorInit() {
        CoopersSpark = new CANSparkMax(4, MotorType.kBrushless);

        CoopersSpark.setIdleMode(IdleMode.kBrake);

        Controller = new XboxController(0);
    }
    public static void setPosition(double pos) {
        boolean xButtonPress = Controller.getXButtonPressed();
        boolean yButtonPress = Controller.getYButtonPressed();
        boolean aButtonPress = Controller.getAButtonPressed();
        boolean bButtonPress = Controller.getBButtonPressed();

        if (yButtonPress == true) {
            destination = 0; //the Y button is the top so it is set to 0 degrees
            speed = (pos - destination) / 360; 
            CoopersSpark.set(speed); 
        }
        if (xButtonPress == true) {
            destination = 270; //the X button is on the left so it is set to 270 degrees
            speed = (pos - destination) / 360;
            CoopersSpark.set(speed); 
        }
        if (bButtonPress == true) {
            destination = 90; //the B button is on the right so it is set to 90 degrees
            speed = (pos - destination) / 360;
            CoopersSpark.set(speed); 
        }
        if (aButtonPress == true) {
           destination = 180; //the A button is on the bottom so it is set to 180 degrees
           speed = (pos - destination) / 360;
           CoopersSpark.set(speed); 
        }


        //double xAxis = Controller.getX(Hand.kLeft);
        //double yAxis = Controller.getY(Hand.kLeft);
        //double offsetDistance = Math.tan(xAxis / yAxis);
        if(pos >= 315){
        closestQuad = 0;
        }
        if(pos <= 45){
        closestQuad = 0;
        }
        if(pos > 45){
        closestQuad = 90;
        }
        if(pos < 135){
        closestQuad = 90;
        }
        if(pos >= 135){
        closestQuad = 180;
        }
        if(pos <= 225){
        closestQuad = 180;
        }
        if(pos > 225){
        closestQuad = 270;
        }
        if(pos < 315){
        closestQuad = 270;
        }
        //destination = closestQuad + offsetDistance;
        speed = (pos - destination) / 360;
        CoopersSpark.set(speed);

}
public static double getPosition(){
    return encoder.getEncoderVal();
}
}
