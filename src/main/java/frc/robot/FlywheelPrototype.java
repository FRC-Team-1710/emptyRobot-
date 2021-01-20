package frc.robot;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class FlywheelPrototype{
    //the motors are marked with m
    //the encoders are marked with e
    public static CANSparkMax m_flyOne;
    public static CANEncoder e_flyOne;
    public static double kP, kI, kD, kFF, maxOut, minOut, maxRPM, destination;
    public static boolean firstRun;

    public static void Shooter(){
        //Update first parameter to CAN ID of the flywheel's motor controller
        m_flyOne = new CANSparkMax(0, MotorType.kBrushless);
        e_flyOne = m_flyOne.getEncoder();
        m_flyOne.setIdleMode(IdleMode.kCoast);
        //m_flyOne.set(0);

        //setting the variables
        kP = 0.0002;
        kI = 0.000000000001;
        kD = 0;
        kFF = 0;
        maxOut = 1;
        minOut = -1;
        maxRPM = 0;
        destination = 1000;

        //setting the flywheel's speed to the output of the PID loop
        double setFlySpeed = (getOutput(kP, kI, kD, kFF, maxOut, minOut, destination));  
        m_flyOne.set(setFlySpeed); 
    }
public static double getOutput(double p, double i, double d, double f, double maxOut, double minOut, double desOut){
    //setting some more variables
    double actOut = e_flyOne.getVelocity();
    double error = desOut - actOut;
    double errorSum = 0;
    double maxError = 0;
    double lastAct = 0;
    double maxIVal = .1; 
    if(i != 0){
        maxError = maxIVal / i;
    }
    double pVal = p * error; //setting P Value
    double ffVal = f * destination; //setting FF Value
    if(firstRun){ //the first run won't have a last output so we set it to 
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