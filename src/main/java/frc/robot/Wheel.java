package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

public class Wheel {
    public TalonFX sparky;
    public TalonFX spinny;
    public CANCoder encoder;
    public MiniPID pid;

    public double offset;
    public double driveModifier = 1;

    public Wheel(int driveMotor, int encoderPort, int steerMotor, double offset){
        sparky = new TalonFX(driveMotor);
        spinny = new TalonFX(steerMotor);
        encoder = new CANCoder(encoderPort);
        
        pid = new MiniPID(0.005,0,0.0001,0);
        
        CANCoderConfiguration canCoderConfiguration = new CANCoderConfiguration();
        canCoderConfiguration.magnetOffsetDegrees = offset;
        encoder.configAllSettings(canCoderConfiguration);
    }

    /**
     * Set the speed of the steering motor using the PID controller
     * @param degrees The target position in degrees
     */
    public void rotateTo(double degrees){
        double target = getNearest(getPosition(), degrees);
        double speed = pid.getOutput(getPosition(), target);

        SmartDashboard.putNumber("Target Orientation", target);
        SmartDashboard.putNumber("Rotation Speed", speed);

        spinny.set(TalonFXControlMode.PercentOutput, speed);
    }

    /**
     * Update orientation on the SmartDashboard
     * @return The position of the angular motor in degrees
     */
    public double getPosition(){
        SmartDashboard.putNumber("Orientation", encoder.getAbsolutePosition());
        return encoder.getAbsolutePosition();
    }

    public void spinBrake(){
        spinny.set(TalonFXControlMode.PercentOutput, 0);
    }

    /**
     * Method to calculate the most efficient way to move
     * @param current The current orientation of the wheel
     * @param target  The target orientation of the wheel
     * @return The raw numerical target for the PID controller to shoot for
     */
    public double getNearest(double current, double target){
        double difference = current - target;

        int modifier = 1;
        if(difference!=0)
            modifier = (int)(difference/Math.abs(difference));

        if(Math.abs(difference)>90){
            target+=180*modifier;
            driveModifier*=-1;
        }
        return target;
    }

    /**
     * Set the wheel's drive speed
     * @param speed The speed to drive at
     */
    public void drive(double speed){
        //speed = speed * driveModifier;
        SmartDashboard.putNumber("Drive Speed", speed);
        sparky.set(TalonFXControlMode.PercentOutput, speed);
    }
}
