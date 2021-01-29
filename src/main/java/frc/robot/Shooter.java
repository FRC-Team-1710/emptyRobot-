package frc.robot;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DigitalSource;
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.GenericHID.Hand;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Shooter{
    public static CANSparkMax shooter1, shooter2;
    public static CANEncoder encoder1, encoder2;
    public static MiniPID flywheelPID;
    public static double kP, kI, kD, kFF;
    public static MiniPID flyWheelPID;
    public static double flyWheelSpeed;

    public static void shooterInit(){
        shooter1 = new CANSparkMax(0, MotorType.kBrushless); // will change based on motor
        shooter2 = new CANSparkMax(1, MotorType.kBrushless);
        encoder1 = shooter1.getEncoder();
        encoder2 = shooter2.getEncoder();
        shooter1.setIdleMode(IdleMode.kCoast);
        shooter2.setIdleMode(IdleMode.kCoast);

        kP = 0.0002; 
        kI = 0.000000000001; 
        kD = 0; 
        kFF = 0;

        flyWheelPID = new MiniPID(kP,kI,kD);

        
        
    }
public static void shooterSpeed(double flywheelRPM, boolean go){

    if (go == true) {
        flyWheelSpeed = flywheelPID.getOutput(encoder1.getVelocity());
        shooter1.set(flyWheelSpeed);
        shooter2.set(-flyWheelSpeed);
    } else {
        flyWheelPID.reset();
        flyWheelSpeed = flywheelPID.getOutput(encoder1.getVelocity());
        shooter1.set(0);
        shooter2.set(0);
    }
    
}
}













