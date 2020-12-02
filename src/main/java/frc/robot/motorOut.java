/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
/**
 * Everything related to the motor
 */
public class motorOut {
    public static CANSparkMax Spark;
    private static XboxController xboxController = new XboxController(1);

    public static void motorInit() {
        Spark = new CANSparkMax(4, MotorType.kBrushless);
        Spark.setIdleMode(IdleMode.kBrake);

    }

    public static void setPosition(){
        //TODO: add calculation for shortest route
        if(xboxController.getAButton() && getPosition() != 180){
            Spark.set(.6);
        }
        if(xboxController.getBButton() && getPosition() != 90){
            Spark.set(.6);
        }
        if(xboxController.getXButton() && getPosition() != 270){
            Spark.set(.6);
        }
        if(xboxController.getYButton() && getPosition() != 0){
            Spark.set(.6);
        } //All this looks too simple for the time I spent on it
    }
    
    public static double getPosition(){
        double position;
        position = encoder.getEncoderVal() * 72;
        return position;
    }
}

