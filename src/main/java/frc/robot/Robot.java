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
import frc.robot.commands.AcquisitionMotionCommand;
import frc.robot.commands.AcquisitionWheelsCommand;
import frc.robot.commands.CameraCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.JoystickDriveCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.UptakeCommand;
import frc.robot.commands.AcquisitionMotionCommand.AcquisitionMotionConfig;
import frc.robot.commands.AcquisitionWheelsCommand.AcquisitionWheelsConfig;
import frc.robot.commands.ClimbCommand.ClimbConfig;
import frc.robot.commands.JoystickDriveCommand.JoystickDriveConfig;
import frc.robot.commands.ShooterCommand.ShooterConfig;
import frc.robot.commands.UptakeCommand.UptakeConfig;
import frc.robot.commands.autonomous.AutoDriveCommand;
import frc.robot.commands.autonomous.AutoDriveCommand.AutoDriveConfig;
import frc.robot.subsystems.AcquisitionSubsystem;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.UptakeSubsystem;
import frc.robot.util.SmartDashboardKeys;
import frc.robot.util.SpeedController;
import frc.robot.util.SpeedController.SpeedControllerConfig;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private ADXRS450_Gyro gyro;
  private DriveSubsystem driveSubsystem;
  private ShootSubsystem shootSubsystem;
  private AcquisitionSubsystem acquisitionSubsystem;
  private ClimbSubsystem climbSubsystem;
  private UptakeSubsystem uptakeSubsystem;
  private CameraSubsystem cameraSubsystem;

  private SpeedController driveSpeedController, shootSpeedController;

  public static Joystick m_stick;
  private Joystick a_stick;

  public JoystickButton createButton(Joystick stick, int buttonNumber, String useCase)
  {
    String whichButton = "Controller " + stick.getPort() + " - Button " + buttonNumber;
    SmartDashboard.putString(whichButton, useCase);
    return new JoystickButton(stick, buttonNumber);
  }

  @Override
  public void robotInit() {
    gyro = new ADXRS450_Gyro();
    gyro.reset();
    m_stick = new Joystick(OI.DRIVE_PORT);
    a_stick = new Joystick(OI.ACQUISITION_PORT);
    
    driveSubsystem = new DriveSubsystem();
    shootSubsystem = new ShootSubsystem();
    acquisitionSubsystem = new AcquisitionSubsystem();
    climbSubsystem = new ClimbSubsystem();
    uptakeSubsystem = new UptakeSubsystem();
    cameraSubsystem = new CameraSubsystem(2);
    
    //Drive Controller
    driveSpeedController = new SpeedController(new SpeedControllerConfig(
      SmartDashboardKeys.DRIVE_SPEED_CTRL, 2.0, createButton(m_stick, OI.BUTTON_10, "Drive Default Speed"),
      createButton(m_stick, OI.BUTTON_5, "Drive Speed Slow"), createButton(m_stick, OI.BUTTON_7, "Drive Speed Full Slow"),
      createButton(m_stick, OI.BUTTON_6, "Drive Speed Full"), createButton(m_stick, OI.BUTTON_8, "Drive Speed Full Fast")));

    JoystickDriveConfig jCfg = new JoystickDriveConfig(
      driveSubsystem,
      () -> {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.DRIVE_SPEED_CTRL, 1);
        return m_stick.getX() / speedCtrlVal;
      },
      () -> {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.DRIVE_SPEED_CTRL, 1);
        return m_stick.getY() / speedCtrlVal;
      },
      () -> {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.DRIVE_SPEED_CTRL, 1);
        return m_stick.getZ() / speedCtrlVal;
      },
      () -> gyro.getAngle());
    driveSubsystem.setDefaultCommand(new JoystickDriveCommand(jCfg));

    JoystickButton cameraButton = createButton(m_stick, OI.BUTTON_4, "Camera Toggle");
    cameraButton.whenPressed(new CameraCommand(cameraSubsystem));

    JoystickButton gyroButton = createButton(m_stick, OI.BUTTON_9, "Gyro Reset");
    gyroButton.whenPressed(new CommandBase() {
      @Override
      public void initialize() {
        gyro.reset();
      }
    });

    //Acquisition Controller
    //Arm Motion

    AcquisitionMotionConfig amCfg = new AcquisitionMotionConfig(
      acquisitionSubsystem,
      () -> {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.ACQ_SPEED_CTRL, 1);
        return -a_stick.getY() / speedCtrlVal;
      });
    acquisitionSubsystem.setDefaultCommand(new AcquisitionMotionCommand(amCfg));

    //Arm Wheels
    JoystickButton acqWheelButton = createButton(a_stick, OI.BUTTON_1, "Arm Wheel Forward");
    AcquisitionWheelsConfig awCfg = new AcquisitionWheelsConfig(acquisitionSubsystem, () -> 1.0);
    acqWheelButton.whenHeld(new AcquisitionWheelsCommand(awCfg));
    
    // JoystickButton acqWheelRevButton = createButton(a_stick, OI.BUTTON_2, "Arm Wheel Reverse");
    // AcquisitionWheelsConfig awRevCfg = new AcquisitionWheelsConfig(acquisitionSubsystem, () -> -1.0);
    // acqWheelRevButton.whenHeld(new AcquisitionWheelsCommand(awRevCfg));

    //Uptake
    JoystickButton uptakeButton = createButton(a_stick, OI.BUTTON_4, "Uptake");
    UptakeConfig uCfg = new UptakeConfig(uptakeSubsystem, () -> 0.4);
    uptakeButton.toggleWhenPressed(new UptakeCommand(uCfg));

    JoystickButton uptakeRevButton = createButton(a_stick, OI.BUTTON_2, "Uptake Rev");
    UptakeConfig uRCfg = new UptakeConfig(uptakeSubsystem, () -> -0.2);
    uptakeRevButton.toggleWhenPressed(new UptakeCommand(uRCfg));
    

    //Shooting
    shootSpeedController = new SpeedController(new SpeedControllerConfig(
      SmartDashboardKeys.SHOOT_SPEED_CTRL, 1.0, createButton(a_stick, OI.BUTTON_10, "Default Shoot Speed"),
      createButton(a_stick, OI.BUTTON_7, "Shoot Speed Slower"),
      createButton(a_stick, OI.BUTTON_8, "Shoot Speed Faster")));

    JoystickButton shootButton = createButton(a_stick, OI.BUTTON_6, "Shoot");
    ShooterConfig sCfg = new ShooterConfig(shootSubsystem, 
    () -> {
      Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.SHOOT_SPEED_CTRL, 1);
      return 1.0 / speedCtrlVal;
    });
    shootButton.toggleWhenPressed(new ShooterCommand(sCfg));

    //Climbing
    ClimbConfig cCfg = new ClimbConfig(climbSubsystem, () -> -a_stick.getThrottle());
    climbSubsystem.setDefaultCommand(new ClimbCommand(cCfg));
    
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

  Command autoCommand = null;
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    switch (m_autoSelected) {
      default:
        AutoDriveConfig adConfig = new AutoDriveConfig(driveSubsystem);
        autoCommand = new AutoDriveCommand(adConfig);
        break;
    }
  }

  @Override
  public void autonomousPeriodic() {
    autoCommand.schedule();
  }

  @Override
  public void teleopInit() {
    gyro.reset();
    driveSpeedController.reset();
    shootSpeedController.reset();
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
