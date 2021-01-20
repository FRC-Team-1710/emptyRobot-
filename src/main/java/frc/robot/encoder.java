/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Docs here
 */
public class encoder {
    public static AnalogInput Encoder;

    public static void initEncoder(){
        Encoder = new AnalogInput(0);
        SmartDashboard.putNumber("Encoder Position", Encoder.getValue());
    }

    public static double getEncoderVal(){
        SmartDashboard.putNumber("Encoder Position", Encoder.getValue());
        return Encoder.getValue();
    }
}
