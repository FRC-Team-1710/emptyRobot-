/*
THINGS TO KNOW
4 inch flywheel
3750 desired RPM (subject to change)
2 to 1 gear ratio
2 motors (neo) 
1 motor needs to turn in reverse
*/
package frc.robot;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class FlywheelPrototype{
    //the motors are marked with m
    //the encoders are marked with e
    public static CANSparkMax m_flyOne, m_flyTwo;
    public static CANEncoder e_flyOne, e_flyTwo;
    public static double kP, kI, kD, kFF, maxRPM, destination;
    public static boolean firstRun;
    public static XboxController Controller;
    public static PowerDistributionPanel PDP;

    public static void FlywheelInit(){
        //Update first parameter to CAN IDs of the flywheels' motor controller
        m_flyOne = new CANSparkMax(1, MotorType.kBrushless);
        m_flyTwo = new CANSparkMax(2, MotorType.kBrushless);
        e_flyOne = m_flyOne.getEncoder();
        e_flyTwo = m_flyTwo.getEncoder();
        m_flyOne.setIdleMode(IdleMode.kCoast);
        m_flyTwo.setIdleMode(IdleMode.kCoast);
        m_flyOne.setSmartCurrentLimit(40);
        m_flyTwo.setSmartCurrentLimit(40);
        Controller = new XboxController(0);
        PDP = new PowerDistributionPanel(0);
       
        //setting the variables
        kP = 0.00025; //will find better value during testing
        kI = 0; //will find better value during testing
        kD = 0; //not needed
        kFF = 0; //not needed 
    }
public static void setFlySpeed(double desRPM){
    double PDPAmps15 = PDP.getCurrent(15);
    double PDPAmps14 = PDP.getCurrent(14);
    double flyOneOut = e_flyOne.getVelocity();
    double flyTwoOut = e_flyTwo.getVelocity();

    //setting the first flywheel's speed
    double flyOneSpeed = flyWheelPID(kP, kI, kD, kFF, desRPM, flyOneOut);
    //double flyOneSpeed = Controller.getTriggerAxis(Hand.kRight);
    m_flyOne.set(flyOneSpeed);

    //setting the second flywheel's speed (this one is negative (one of the motors needs to spin the other way))
    //double flyTwoSpeed = (flyWheelPID(kP, kI, kD, kFF, desRPM, flyTwoOut));
    //double flyTwoSpeed = -1 * Controller.getTriggerAxis(Hand.kRight);
    m_flyTwo.set(-flyOneSpeed);

    //displays the current output of the PID loop aswell as the destination and current RPM
    SmartDashboard.putNumber("flyone-pid-out", flyOneSpeed);
    //SmartDashboard.putNumber("flytwo-pid-out", flyTwoSpeed);
    SmartDashboard.putNumber("destination", desRPM);
    SmartDashboard.putNumber("flyone-actual", Math.abs(flyOneOut));
    SmartDashboard.putNumber("flytwo-actual", Math.abs(flyTwoOut));
    SmartDashboard.putNumber("PDP-15-Out", PDPAmps15);
    SmartDashboard.putNumber("PDP-14-Out", PDPAmps14);
    //SmartDashboard.putNumber("Trigger-Axis", Controller.getTriggerAxis(Hand.kRight));
}
public static double flyWheelPID(double p, double i, double d, double f, double desOut, double flyOut){
    //setting some more variables
    double actOut = flyOut;
    double error = desOut - actOut;
    double errorSum = 0;
    double maxError = 5500;
    double lastAct = 0;
    double maxIVal = .5; 
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
		errorSum = constrain(errorSum + error, -maxError, maxError);
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
    return (min < value) && (value < max);
}
}