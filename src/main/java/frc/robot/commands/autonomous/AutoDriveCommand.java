package frc.robot.commands.autonomous;

import java.util.function.DoubleSupplier;

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
        DoubleSupplier distance;

        public AutoDriveConfig(DriveSubsystem drive, DoubleSupplier distanceInches) {
            driveSubsystem = drive;
            distance = distanceInches;
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
    Boolean forwards = true;
    @Override
    public void initialize() {
        SmartDashboard.putNumber("Encoder Value Start: ", cfg.driveSubsystem.encoderValue());
        System.out.println("Front Right Start: " + cfg.driveSubsystem.encoderValue());
        Double ticks = inchesToEncoderTicks(cfg.distance.getAsDouble());
        desiredTicks = cfg.driveSubsystem.encoderValue() - ticks;
        if(desiredTicks < cfg.driveSubsystem.encoderValue()) {
            forwards = true;
        }
        else
        {
            forwards = false;
        }

        SmartDashboard.putNumber("Encoder Value: ", cfg.driveSubsystem.encoderValue());
        SmartDashboard.putNumber("Desired Value: ", desiredTicks);
    }

    Double speedCap = 0.1;
    public Double calculateSpeed(Double desired, Double current)
    {
        Double percentageRemaining = Math.abs(desired - current) / Math.abs(desired);
        System.out.println("Using speed: " + Math.max(speedCap, percentageRemaining));
        SmartDashboard.putNumber("Using speed", Math.max(speedCap, percentageRemaining));
        return Math.max(speedCap, percentageRemaining);
    }
    
    @Override
    public void execute() {
        SmartDashboard.putNumber("Encoder Value: ", cfg.driveSubsystem.encoderValue());
        SmartDashboard.putNumber("Desired Value: ", desiredTicks);
        System.out.println("Desired Value: " + desiredTicks);
        System.out.println("Current Value: " + cfg.driveSubsystem.encoderValue());
        Double speed = -calculateSpeed(desiredTicks, cfg.driveSubsystem.encoderValue());
        if(!forwards) speed *= -1;
        cfg.driveSubsystem.drive(0., speed, 0.);
    }

    boolean inRange() {
        Double encVal = cfg.driveSubsystem.encoderValue();
        Double range = 100.;
        
        return (encVal - desiredTicks >= -range && encVal - desiredTicks <= range);
    }

    @Override
    public boolean isFinished() {
        if(inRange()) {
            SmartDashboard.putNumber("Encoder Value: ", cfg.driveSubsystem.encoderValue());
        SmartDashboard.putNumber("Desired Value: ", desiredTicks);
            System.out.println("Desired Value: " + desiredTicks);
            System.out.println("Current Value: " + cfg.driveSubsystem.encoderValue());
            System.out.println("Done!");
            cfg.driveSubsystem.drive(0., 0., 0.);
            return true;
        }
        return false;
    }
}
