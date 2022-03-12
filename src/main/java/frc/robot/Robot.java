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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
import frc.robot.commands.autonomous.AutoArmCommand;
import frc.robot.commands.autonomous.AutoDriveWithTimeCommand;
import frc.robot.commands.autonomous.AutoShootCommand;
import frc.robot.commands.autonomous.AutoArmCommand.AutoArmConfig;
import frc.robot.commands.autonomous.AutoDriveWithTimeCommand.AutoDriveWithTimeConfig;
import frc.robot.commands.autonomous.AutoShootCommand.AutoShootConfig;
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
  private static final String autoNone = "None";
  private static final String auto1 = "Auto 1";
  private static final String auto2 = "Auto 2";
  private static final String auto3 = "Auto 3";
  private final static double DEADBAND = 0.1;
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
        double x = m_stick.getX();
        if (x < 0 - DEADBAND || x > 0 + DEADBAND) {
          return x / speedCtrlVal;
        } else {
          return 0;
        }
      },
      () -> {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.DRIVE_SPEED_CTRL, 1);
        double y = m_stick.getY();
        if (y < 0 - DEADBAND || y > 0 + DEADBAND) {
          return y / speedCtrlVal;
        } else {
          return 0;
        }
      },
      () -> {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.DRIVE_SPEED_CTRL, 1);
        double z = m_stick.getRawAxis(4);
        if (z < 0 - DEADBAND || z > 0 + DEADBAND) {
          return z / speedCtrlVal;
        } else {
          return 0;
        }
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
        return a_stick.getY() / speedCtrlVal;
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

    createButton(a_stick, OI.BUTTON_3, "Set Shoot Speed").whenPressed(
      new ShooterCommand(new ShooterConfig(shootSubsystem, 
      () -> {
        return 0.333;
      })));

    //Climbing
    ClimbConfig cCfg = new ClimbConfig(climbSubsystem, () -> a_stick.getThrottle());
    climbSubsystem.setDefaultCommand(new ClimbCommand(cCfg));
    
    //Autonomous Stuff
    m_chooser.setDefaultOption("None", autoNone);
    m_chooser.addOption("Auto 1", auto1);
    m_chooser.addOption("Auto 2", auto2);
    m_chooser.addOption("Auto 3", auto3);
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
    System.out.println("Auto selected: " + m_autoSelected);
    
    switch (m_autoSelected) {
      case auto1:
      {
        autoCommand = new SequentialCommandGroup(
          new ParallelCommandGroup(
            new SequentialCommandGroup(
              new AutoDriveWithTimeCommand(new AutoDriveWithTimeConfig(driveSubsystem, 3500, -0.2)),
              new AutoShootCommand(
                  new AutoShootConfig(acquisitionSubsystem, shootSubsystem, uptakeSubsystem, () -> 50.0))
            ),
            new AutoArmCommand(new AutoArmConfig(acquisitionSubsystem)) 
          )       
        );
        break; 
      }
      case auto2: //High Goal
      {
        autoCommand = new SequentialCommandGroup(
          new ParallelCommandGroup(
            new SequentialCommandGroup(
              new AutoDriveWithTimeCommand(new AutoDriveWithTimeConfig(driveSubsystem, 3500, -0.2)),
              new AutoShootCommand(
                  new AutoShootConfig(acquisitionSubsystem, shootSubsystem, uptakeSubsystem, () -> 50.0))
            ),
            new AutoArmCommand(new AutoArmConfig(acquisitionSubsystem)) 
          )       
        );
        break;
      }
      case auto3: //Low goal
      {
        autoCommand = new SequentialCommandGroup(
          new ParallelCommandGroup(
            new SequentialCommandGroup(
              new AutoDriveWithTimeCommand(new AutoDriveWithTimeConfig(driveSubsystem, 3500, -0.2)),
              new AutoShootCommand(
                  new AutoShootConfig(acquisitionSubsystem, shootSubsystem, uptakeSubsystem, () -> 35.0))
            ),
            new AutoArmCommand(new AutoArmConfig(acquisitionSubsystem)) 
          )       
        );
        break;
      }
      default:
      {
        autoCommand = null;
        break;
      }
    }
    if(autoCommand != null) autoCommand.schedule();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if(autoCommand != null) autoCommand.cancel();
    gyro.reset();
    driveSpeedController.reset();
    shootSpeedController.reset();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void disabledInit() 
  {
    if(autoCommand != null) autoCommand.cancel();
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
