package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wheel{
    public static CANSparkMax sparky;
    public static CANSparkMax spinny;
    public static CANEncoder sparkoder;
    public static CANEncoder spincoder;
    public static MiniPID pid;
    public Wheel(int angleMotor, int speedMotor){
        sparky = new CANSparkMax(speedMotor, MotorType.kBrushless);
        spinny = new CANSparkMax(angleMotor, MotorType.kBrushless);
        spinny.setSecondaryCurrentLimit(20);
        sparkoder = sparky.getEncoder();
        spincoder = spinny.getEncoder();

        pid = new MiniPID(1,1,0);
    }

    /**
     * Calculate raw destination from input
     * and move towards that destination
     */
    public void rotateTo(double position){


        double destination = position * 0.05;
        double speed = pid.getOutput(getPosition(), destination);
        //double speed = (destination-getPosition())*0.1;
        SmartDashboard.putNumber("Raw Destination", destination);
        SmartDashboard.putNumber("Speed", speed);
        //spinny.set(speed);
    }

    public static double getPosition(){
        SmartDashboard.putNumber("Position", spincoder.getPosition());
        return spincoder.getPosition();
    }
}
