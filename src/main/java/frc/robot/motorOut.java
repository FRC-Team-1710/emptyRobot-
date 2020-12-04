/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import java.math.BigDecimal;
/**
 * Everything related to the motor
 */
public class motorOut {
    public static CANSparkMax Spark;
    private static XboxController xboxController = new XboxController(1);
    private static BigDecimal Adegree = new BigDecimal(180.0);

    public static void motorInit() {
        Spark = new CANSparkMax(4, MotorType.kBrushless);
        Spark.setIdleMode(IdleMode.kBrake);

    }

    public static void setPosition(){
        BigDecimal Position = new BigDecimal(getPosition());
        BigDecimal Distance = new BigDecimal(0);
        BigDecimal k = new BigDecimal(.01);
        BigDecimal Speed = new BigDecimal(0);
        double speed;
        //This should all work in theory
        if(xboxController.getAButton() && getPosition() != 180){
            Distance = Adegree.subtract(Position);
            Speed = Distance.multiply(k);
            speed = Speed.doubleValue();
            Spark.set(speed);
        }
        else if(xboxController.getBButton() && getPosition() != 90){
            Distance = Adegree.subtract(Position);
            Speed = Distance.multiply(k);
            speed = Speed.doubleValue();
            Spark.set(speed);
        }
        else if(xboxController.getXButton() && getPosition() != 270){
            Distance = Adegree.subtract(Position);
            Speed = Distance.multiply(k);
            speed = Speed.doubleValue();
            Spark.set(speed);
        }
        else if(xboxController.getYButton() && getPosition() != 0){
            Distance = Adegree.subtract(Position);
            Speed = Distance.multiply(k);
            speed = Speed.doubleValue();
            Spark.set(speed);
        }
        else{
            Spark.set(.0);
        } 
    }
    
    public static double getPosition(){
        double position;
        position = encoder.getEncoderVal() * 72;
        return position;
    }
}

