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

public static void motorInit(){
CoopersSpark = new CANSparkMax(5, MotorType.kBrushless);

    CoopersSpark.setIdleMode(IdleMode.kBrake);

    Controller = new XboxController(0);
}
public static void setPosition(double pos){

}
public static double getPosition(){

    return 0;
}
}
