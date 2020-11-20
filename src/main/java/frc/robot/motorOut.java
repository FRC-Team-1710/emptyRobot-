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

/**
 * Everything related to the motor
 */
public class motorOut {
    public static CANSparkMax Spark;

    public static void motorInit() {
        Spark = new CANSparkMax(4, MotorType.kBrushless);
        Spark.setIdleMode(IdleMode.kBrake);

    }
    public static void setPosition(double pos){
        //TODO
    }
    public static double getPosition(){

        return 0.0;
    }
}

