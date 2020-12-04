/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Docs here
 */
public class motorOut {
    public static CANSparkMax Spark;
    public static CANEncoder Encoder;

    public static void motorInit(){
        Spark = new CANSparkMax(4, MotorType.kBrushless);
        Spark.setIdleMode(IdleMode.kBrake);

        Encoder = Spark.getEncoder();
    }

    public static double destination = 0;

    public static void setPosition(double pos){
        destination = pos*0.05;
        SmartDashboard.putNumber("position", getPosition());
    }

    public static double getPosition(){
        return Encoder.getPosition();
    }

    /*
     * A shortcut for finding the quadrant of a point
     */
    public static int getQuadrant(double x, double y){
        int quadrant = 0;
        if(x>=0&&y>0){
            quadrant=1;
        }else if(x>0&&y<=0){
            quadrant=2;
        }else if(x<=0&&y<0){
            quadrant=3;
        }else if(x<0&&y>=0){
            quadrant=4;
        }
        return quadrant;
    }
}