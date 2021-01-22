/*
THINGS TO KNOW
4 inch flywheel
3700 - 4100 desired RPM (subject to change)
2 to 1 gear ratio
2 motors (neo) (built in encoder)
1 motor needs to turn in reverse(?)
*/
package frc.robot;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlywheelPrototype{
    //the motors are marked with m
    //the encoders are marked with e
    public static CANSparkMax m_flyOne, m_flyTwo;
    public static CANEncoder e_flyOne, e_flyTwo;
    public static double kP, kI, kD, kFF, maxRPM, destination;
    public static boolean firstRun;

    public static void Shooter(){
        //Update first parameter to CAN IDs of the flywheels' motor controller
        m_flyOne = new CANSparkMax(0, MotorType.kBrushless);
        m_flyTwo = new CANSparkMax(0, MotorType.kBrushless);
        e_flyOne = m_flyOne.getEncoder();
        e_flyTwo = m_flyTwo.getEncoder();
        m_flyOne.setIdleMode(IdleMode.kCoast);
        m_flyTwo.setIdleMode(IdleMode.kCoast);
        //m_flyOne.set(0);

        //setting the variables
        kP = 0.0002; //will find better value during testing
        kI = 0.000000000001; //will find better value during testing
        kD = 0; //not needed
        kFF = 0; //not needed
        destination = 4100; //desired RPM of the flywheels

        setFlySpeed(destination); //setting the flywheels' speed
    }
public static void setFlySpeed(double desRPM){
    //setting the first flywheel's speed
    double flyOneSpeed = flyWheelPID(kP, kI, kD, kFF, desRPM, e_flyOne.getVelocity());
    m_flyOne.set(flyOneSpeed);
    //setting the second flywheel's speed (this one is negative (one of the motors needs to spin the other way?) may change later)
    double flyTwoSpeed = -1 * flyWheelPID(kP, kI, kD, kFF, desRPM, e_flyTwo.getVelocity());
    m_flyTwo.set(flyTwoSpeed);

    SmartDashboard.putNumber("flyone-pid-out", flyOneSpeed);
    SmartDashboard.putNumber("flytwo-pid-out", flyTwoSpeed);
    SmartDashboard.putNumber("destination", destination);
    SmartDashboard.putNumber("flyone-actual", e_flyOne.getVelocity());
    SmartDashboard.putNumber("flytwo-actual", e_flyTwo.getVelocity());
}
public static double flyWheelPID(double p, double i, double d, double f, double desOut, double flyOut){
    //setting some more variables
    double actOut = flyOut;
    double error = desOut - actOut;
    double errorSum = 0;
    double maxError = 0;
    double lastAct = 0;
    double maxIVal = .1; 
    double maxOut = 1; //max value a .set(speed) command will take is 1
    double minOut = -1; //min value a .set(speed) command will take is -1
    if(i != 0){
        maxError = maxIVal / i;
    }
    double pVal = p * error; //setting P Value
    double ffVal = f * destination; //setting FF Value
    if(firstRun){ //the first run won't have a last output so we set it
        lastAct = actOut;
        firstRun = false;
    }
    double dVal = -d * (actOut - lastAct); //setting D Value
    lastAct = actOut; 

    double iVal = i * errorSum; //setting I Value
    if(maxIVal != 0){ //making sure the I Value is within the limits
        iVal = constrain(iVal ,-maxIVal ,maxIVal); 
    }
    double PIDOutput = pVal + iVal + dVal + ffVal; //adding everthing up to get the output
    
    if(minOut != maxOut && !bounded(actOut, minOut, maxOut) ){
        errorSum = error;
    }
    else if(maxIVal != 0){
		errorSum = constrain(errorSum +error, -maxError, maxError);
    }    
    else{
        errorSum += error;
    }
    if(minOut != maxOut){ 
        PIDOutput = constrain(PIDOutput, minOut, maxOut);
    }
	return PIDOutput;
}
public static double constrain(double value, double min, double max){
    if(value > max){ return max;}
    if(value < min){ return min;}
    return value;
}
public static boolean bounded(double value, double min, double max){
    return (min<value) && (value<max);
}
}