/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;



public class MotorOut {
public static CANSparkMax CoopersSpark;
public static XboxController Controller;
public static int destination; //setting an interger "destination" that will be used later
    public static void motorInit() {
        CoopersSpark = new CANSparkMax(4, MotorType.kBrushless);

        CoopersSpark.setIdleMode(IdleMode.kBrake);

        Controller = new XboxController(0);
    }

    public static void setPosition(double pos) {
        boolean yB = Controller.getYButtonPressed(); //setting variables for if the button was pressed
        boolean xB = Controller.getXButtonPressed();
        boolean aB = Controller.getAButtonPressed();
        boolean bB = Controller.getBButtonPressed();
        if (xB = true) {
            destination = 0; //the Y button is the top so it is set to 0 degrees
        }
        if (Controller.getXButtonPressed()) {
            destination = 270; //the X button is on the left so it is set to 270 degrees
        }
        if (Controller.getBButtonPressed()) {
            destination = 90; //the B button is on the right so it is set to 90 degrees
        }
        if (Controller.getAButtonPressed()) {
           destination = 180; //the A button is on the bottom so it is set to 180 degrees
        }
        
        /*
        In the eqation below, it is using the destination and subtracting it from the current position.
        This will give the distance the motor is from its target.
        It uses a constant (1/360) so that the speed will be between -1 and 1 (the speed vaules the mortor can use).
        It then uses this distance to determine the speed that the motor should be moving until finally reaching 0.
        This ensures that the motor will stop safely and percisely on the target.
        */
        double speed = 1/360 * (pos - destination); 
        CoopersSpark.set(speed); 

}
public static double getPosition(){
    return encoder.getEncoderVal();
}
}
