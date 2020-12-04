/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;



import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public static CANSparkMax Spark;
  public static XboxController Controller;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    encoder.initEncoder();
    motorOut.motorInit();
    Spark = new CANSparkMax(5, MotorType.kBrushless);
    Controller = new XboxController(0);

    Spark.setIdleMode(IdleMode.kBrake);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //Spark.set(Controller.getRawAxis(5));
    
    /*if(Controller.getRawButtonPressed(1)){
      motorOut.setPosition(90);
    }else if(Controller.getRawButtonPressed(2)){
      motorOut.setPosition(0);
    }else if(Controller.getRawButtonPressed(3)){
      motorOut.setPosition(180);
    }else if(Controller.getRawButtonPressed(4)){
      motorOut.setPosition(270);
    }*/
    switch(motorOut.getQuadrant(Controller.getRawAxis(0), Controller.getRawAxis(1))){
      case 1:
      motorOut.setPosition(Math.tanh(Controller.getRawAxis(0)/Controller.getRawAxis(1))+270);
      SmartDashboard.putNumber("Directional Input", Math.tanh(Controller.getRawAxis(0)/Controller.getRawAxis(1))+270);
      break;
      case 2:
      motorOut.setPosition(Math.tanh(Controller.getRawAxis(1)/Controller.getRawAxis(0)));
      SmartDashboard.putNumber("Directional Input", Math.tanh(Controller.getRawAxis(1)/Controller.getRawAxis(0)));
      break;
      case 3:
      motorOut.setPosition(Math.tanh(Controller.getRawAxis(0)/Controller.getRawAxis(1))+90);
      SmartDashboard.putNumber("Directional Input", Math.tanh(Controller.getRawAxis(0)/Controller.getRawAxis(1))+90);
      break;
      case 4:
      motorOut.setPosition(Math.tanh(Controller.getRawAxis(1)/Controller.getRawAxis(0))+180);
      SmartDashboard.putNumber("Directional Input", Math.tanh(Controller.getRawAxis(1)/Controller.getRawAxis(0))+180);
      break;
    }

    motorOut.Spark.set((motorOut.destination-motorOut.getPosition())*0.1);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
