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
import frc.robot.commands.JoystickDriveCommand;
import frc.robot.commands.JoystickDriveCommand.JoystickDriveConfig;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.SmartDashboardKeys;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  private ADXRS450_Gyro gyro;
  private DriveSubsystem driveSubsystem;
  private SpeedController speedController;
  private Joystick m_stick;

  @Override
  public void robotInit() {
    gyro = new ADXRS450_Gyro();
    gyro.reset();
    m_stick = new Joystick(OI.DRIVE_PORT);
    
    driveSubsystem = new DriveSubsystem();
    
    JoystickDriveConfig jCfg = new JoystickDriveConfig(m_stick, driveSubsystem, gyro);
    driveSubsystem.setDefaultCommand(new JoystickDriveCommand(jCfg));

    speedController = new SpeedController(2.0, new JoystickButton(m_stick, OI.BUTTON_10),
      new JoystickButton(m_stick, OI.BUTTON_5), new JoystickButton(m_stick, OI.BUTTON_7),
      new JoystickButton(m_stick, OI.BUTTON_6), new JoystickButton(m_stick, OI.BUTTON_8));
    
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
    speedController.reset();
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
