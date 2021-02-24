// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public Wheel Donkey;
  public Wheel Diddy;
  public Wheel Cranky;
  public Wheel Funky;
  public Wheel[] Wheels = {Donkey, Diddy, Cranky, Funky};

  public static XboxController Controller;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    Donkey = new Wheel(1,2,3, 20);
    Diddy  = new Wheel(4,5,6, 80);
    Cranky = new Wheel(7,8,9, 20);
    Funky  = new Wheel(10,11,12, 200);

    Controller = new XboxController(0);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
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

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double x = Controller.getRawAxis(0);
    double y = Controller.getRawAxis(1);
    double angle = getAngle(x,y) + 360;

    if(x!=0||y!=0){
      Donkey.rotateTo(angle);
      Diddy.rotateTo(angle);
      Cranky.rotateTo(angle);
      Funky.rotateTo(angle);
    }else{
      Donkey.spinBrake();
      Diddy.spinBrake();
      Cranky.spinBrake();
      Funky.spinBrake();
    }

    if(x!=0||y!=0){
      double speed = Math.sqrt(x*x+y*y);
      Donkey.drive(speed);
      Diddy.drive(speed);
      Cranky.drive(speed);
      Funky.drive(speed);
    }else{
      Donkey.drive(0);
      Diddy.drive(0);
      Cranky.drive(0);
      Funky.drive(0);
    }

    record();
  }

  /**
   * Calculates the angle of the input point
   * @param x The x value of the point
   * @param y The y value of the point
   * @return The angle of the point in relation to the origin
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
    return angle;
  }

  /**
   * Returns the quadrant of a point
   * @param x The x value of the point
   * @param y The y value of the point
   * @return Number between 0 and 4 indicating which quadrant the point
   * belongs to on a coordinate plane. A quadrant of 0 means the given
   * point is the origin on a coordinate plane. A point that touches
   * either axis is assigned the next quadrant clockwise.
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

  /**
   * Record information to SmartDashboard
   */
  public void record(){
    SmartDashboard.putNumber("Module 2 Orientation", Donkey.encoder.getAbsolutePosition());
    SmartDashboard.putNumber("Module 2 Orientation", Diddy.encoder.getAbsolutePosition());
    SmartDashboard.putNumber("Module 3 Orientation", Cranky.encoder.getAbsolutePosition());
    SmartDashboard.putNumber("Module 4 Orientation", Funky.encoder.getAbsolutePosition());

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}









  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
