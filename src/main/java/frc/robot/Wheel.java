package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wheel{
    public static TalonFX sparky;
    public static TalonFX spinny;
    public static CANCoder encoder;
    public static MiniPID pid;
    public static CANPIDController PID;


    public Wheel(int angleMotor, int speedMotor, int encoderPort){
        sparky = new TalonFX(speedMotor);
        spinny = new TalonFX(angleMotor);
        encoder = new CANCoder(encoderPort);

        pid = new MiniPID(0.1,0,0,0);
    }

    /**
     * Calculate raw destination from input
     * and move towards that destination
     */
    public void rotateTo(double position){
        position = getNearest(getPosition()*20, position);

        double destination = position * 0.05;
        double speed = pid.getOutput(getPosition(), destination);
        //double speed = (destination-getPosition())*0.1;
        SmartDashboard.putNumber("Raw Destination", destination);
        SmartDashboard.putNumber("Speed", speed);
        spinny.set(TalonFXControlMode.Velocity, speed);
    }

    public static double getPosition(){
        SmartDashboard.putNumber("Position", encoder.getPosition());
        return encoder.getPosition();
    }

    public void stopSpinning(){
        spinny.set(TalonFXControlMode.Velocity, 0);
    }


    /**
     * A method to find the nearest number that represents
     * the same physical orientation as the input angle
     * @param actual The current position of the wheel
     * @param setPoint The controller input, should be between -360 and 0
     * @return The nearest angle that represents the same position as the input
     */
    public static double getNearest(double actual, double setPoint){
        int level = (int)actual/360;
        int mod=-1;
        if(level!=0){mod = level/Math.abs(level);}
        setPoint += 360*level;
        if(Math.abs((actual)-(setPoint))>180){setPoint+=360*mod;}
        return setPoint;
    }
}
