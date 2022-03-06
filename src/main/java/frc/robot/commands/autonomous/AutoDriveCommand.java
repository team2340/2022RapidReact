package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase {
    public static class AutoDriveConfig {
        DriveSubsystem driveSubsystem;
        ADXRS450_Gyro gyro;
        Double x;
        Double y;
        Double z;

        public AutoDriveConfig(DriveSubsystem drive) {
            driveSubsystem = drive;
        }
    }

    AutoDriveConfig cfg;

    private Double inchesToEncoderTicks(Double inches) {
        return ((18975.22 * inches) / (6.0 * Math.PI));
    }
    
    public AutoDriveCommand(AutoDriveConfig adConfig) {
        cfg = adConfig;
        addRequirements(cfg.driveSubsystem); //This says that this command depends on the subsystem
    }

    Double desiredTicks = 0.;
    @Override
    public void initialize() {
        SmartDashboard.putNumber("Back Left Encoder Value Start: ", cfg.driveSubsystem.encoderValue());
        Double ticks = inchesToEncoderTicks(6.);
        SmartDashboard.putNumber("Back Left Encoder Ticks: ", ticks);
        SmartDashboard.putNumber("Back Left Desired spot: ", ticks + cfg.driveSubsystem.encoderValue());
        SmartDashboard.putNumber("Back Left Encoder Value Start (2): ", cfg.driveSubsystem.encoderValue());
        // cfg.driveSubsystem.drivePosition(ticks + cfg.driveSubsystem.encoderValue());
        SmartDashboard.putNumber("Back Left Encoder Value Start (3): ", cfg.driveSubsystem.encoderValue());
        desiredTicks = ticks + cfg.driveSubsystem.encoderValue();
    }
    
    Double decreaseSpeed = 0.2;
    @Override
    public void execute() {
        cfg.driveSubsystem.drive(0., -0.5, 0.);
        SmartDashboard.putNumber("Back Left Encoder Value: ", cfg.driveSubsystem.encoderValue());

        if(Math.abs(cfg.driveSubsystem.encoderValue()) - Math.abs(desiredTicks) <= 2000) {
            System.out.println("Almost there");
            cfg.driveSubsystem.drive(0., decreaseSpeed, 0.);
        }
    }

    @Override
    public boolean isFinished() {
        if(Math.abs(cfg.driveSubsystem.encoderValue()) - Math.abs(desiredTicks) <= 0.0) {
            cfg.driveSubsystem.drive(0., 0., 0.);
            return true; //TODO: Should return true when it is done moving
        }
        return false;
    }
}
