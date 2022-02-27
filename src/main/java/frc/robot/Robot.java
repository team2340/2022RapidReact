// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.SpeedController.SpeedControllerConfig;
import frc.robot.commands.AcquisitionMotionCommand;
import frc.robot.commands.AcquisitionWheelsCommand;
import frc.robot.commands.JoystickDriveCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.AcquisitionMotionCommand.AcquisitionMotionConfig;
import frc.robot.commands.AcquisitionWheelsCommand.AcquisitionWheelsConfig;
import frc.robot.commands.JoystickDriveCommand.JoystickDriveConfig;
import frc.robot.commands.ShooterCommand.ShooterConfig;
import frc.robot.subsystems.AcquisitionSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.util.SmartDashboardKeys;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  private ADXRS450_Gyro gyro;
  private DriveSubsystem driveSubsystem;
  private ShootSubsystem shootSubsystem;
  private AcquisitionSubsystem acquisitionSubsystem;

  private SpeedController driveSpeedController, acqSpeedController;

  private Joystick m_stick;
  private Joystick a_stick;

  @Override
  public void robotInit() {
    gyro = new ADXRS450_Gyro();
    gyro.reset();
    m_stick = new Joystick(OI.DRIVE_PORT);
    a_stick = new Joystick(OI.ACQUISITION_PORT);
    
    driveSubsystem = new DriveSubsystem();
    shootSubsystem = new ShootSubsystem();
    acquisitionSubsystem = new AcquisitionSubsystem();
    
    //Drive Controller
    JoystickDriveConfig jCfg = new JoystickDriveConfig(m_stick, driveSubsystem, gyro);
    driveSubsystem.setDefaultCommand(new JoystickDriveCommand(jCfg));

    SpeedControllerConfig speedControllerConfig = new SpeedControllerConfig(
      SmartDashboardKeys.DRIVE_SPEED_CTRL, 2.0, new JoystickButton(m_stick, OI.BUTTON_10),
      new JoystickButton(m_stick, OI.BUTTON_5), new JoystickButton(m_stick, OI.BUTTON_7),
      new JoystickButton(m_stick, OI.BUTTON_6), new JoystickButton(m_stick, OI.BUTTON_8));
    
    driveSpeedController = new SpeedController(speedControllerConfig);

    //Acquisition Controller
    //Arm Motion
    AcquisitionMotionConfig amCfg = new AcquisitionMotionConfig(a_stick, acquisitionSubsystem);
    acquisitionSubsystem.setDefaultCommand(new AcquisitionMotionCommand(amCfg));

    acqSpeedController = new SpeedController(new SpeedControllerConfig(
      SmartDashboardKeys.ACQ_SPEED_CTRL, 2.0, new JoystickButton(m_stick, OI.BUTTON_10),
      new JoystickButton(m_stick, OI.BUTTON_5), new JoystickButton(m_stick, OI.BUTTON_7),
      new JoystickButton(m_stick, OI.BUTTON_6), new JoystickButton(m_stick, OI.BUTTON_8)));

    //Arm Wheels
    JoystickButton acqWheelButton = new JoystickButton(a_stick, OI.BUTTON_3);
    AcquisitionWheelsConfig awCfg = new AcquisitionWheelsConfig(acquisitionSubsystem);
    acqWheelButton.whileHeld(new AcquisitionWheelsCommand(awCfg));
    
    //Shooting (Uptake and Shooter in 1 command)
    JoystickButton shootButton = new JoystickButton(a_stick, OI.BUTTON_4);
    ShooterConfig sCfg = new ShooterConfig(shootSubsystem);
    shootButton.whileHeld(new ShooterCommand(sCfg));

    //Climbing
    
    
    //Autonomous Stuff
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber(SmartDashboardKeys.GYRO_VAL, gyro.getAngle());
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

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

  @Override
  public void teleopInit() {
    gyro.reset();
    driveSpeedController.reset();
    acqSpeedController.reset();
  }

  @Override
  public void teleopPeriodic() {
    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
