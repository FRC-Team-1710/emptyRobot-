/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
/**
 * Add your docs here.
 */
public class Intake {
    public static CANSparkMax intake1, intake2, intake3;
    public boolean follow = true;

    // creates motors
    public static void intakeInit(){
        intake1 = new CANSparkMax(1, MotorType.kBrushless); //neo
        intake2 = new CANSparkMax(2, MotorType.kBrushless); //neo
        intake3 = new CANSparkMax(3, MotorType.kBrushed); //775
        intake1.setIdleMode(IdleMode.kBrake);
        intake2.setIdleMode(IdleMode.kBrake);
        intake3.setIdleMode(IdleMode.kBrake);
    }

   // intake motors 1 & 2 are set in opposite directions
    public static void moveIntake(boolean follow){
        if (follow == true) {
            intake1.set(1);
            intake2.set(-1);
             
           
        }
        
    }


	
    

}
