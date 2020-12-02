/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Code for the encoder
 */
public class encoder {
    public static AnalogInput Encoder;
    public static double val;
    public static void encoderInit(){
        Encoder = new AnalogInput(0);
        SmartDashboard.putNumber("encoder Position", Encoder.getValue());
    }
    public static double getEncoderVal(){
        SmartDashboard.putNumber("encoder Position", val);
        return val;
    }
}