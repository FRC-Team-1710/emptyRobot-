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

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

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

  public Wheel Wheelie;
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

    Wheelie = new Wheel(2,3,1);
    Controller = new XboxController(0);
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
    double angle = getAngle(Controller.getRawAxis(0), Controller.getRawAxis(1));
		SmartDashboard.putBoolean("angle.isNaN", Double.isNaN(angle));
    record();
    if(angle!=0)
      Wheelie.rotateTo(angle);
    else
      Wheelie.stopSpinning();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  /**
   * Shortcut to get angle from a point
   */
  public static double getAngle(double x, double y){
    double a=x, b=x, c=0;
    SmartDashboard.putNumber("Input Quadrant", getQuadrant(x,y));
    switch(getQuadrant(x, y)){
      case 0:return 0;
      case 1:b=y;c=270;break;
      case 2:a=y;c=  0;break;
      case 3:b=y;c= 90;break;
      case 4:a=y;c=180;break;
    }
    double angle = Math.tanh(-(Math.abs(a)/Math.abs(b)))*90-c;
    SmartDashboard.putNumber("Destination",angle);
    return angle;
  }

  /**
   * A shortcut for finding the quadrant of a point
   */
  public static int getQuadrant(double x, double y){
    int quadrant = 0;
    if(x>=0&&y<0){
      quadrant=1;
    }else if(x>0&&y>=0){
      quadrant=2;
    }else if(x<=0&&y>0){
      quadrant=3;
    }else if(x<0&&y<=0){
      quadrant=4;
    }else if(x==0&&y==0){
      quadrant=0;
    }
    return quadrant;
  }

  public static void record(){
    SmartDashboard.putNumber("Left JoyStick Input X", Controller.getRawAxis(0));
    SmartDashboard.putNumber("Left JoyStick Input Y", Controller.getRawAxis(1));

  }
}
