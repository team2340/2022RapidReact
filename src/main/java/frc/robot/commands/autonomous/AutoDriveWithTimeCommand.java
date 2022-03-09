package frc.robot.commands.autonomous;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveWithTimeCommand extends CommandBase {
    public static class AutoDriveWithTimeConfig {
        DriveSubsystem driveSubsystem;
        long time;
        Double speed;

        public AutoDriveWithTimeConfig(DriveSubsystem drive, long timeInMilliseconds, Double speedPosForwards) {
            driveSubsystem = drive;
            time = timeInMilliseconds;
            this.speed = speedPosForwards;
        }
    }

    AutoDriveWithTimeConfig cfg;
    
    public AutoDriveWithTimeCommand(AutoDriveWithTimeConfig adConfig) {
        cfg = adConfig;
        addRequirements(cfg.driveSubsystem); //This says that this command depends on the subsystem
    }

    long startTime = 0;
    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        
    }

    @Override
    public void execute() {
        cfg.driveSubsystem.drive(0., -cfg.speed, 0.);
    }

    @Override
    public boolean isFinished() {
        if(System.currentTimeMillis() >= startTime + cfg.time) {
            cfg.driveSubsystem.drive(0.0, 0.0, 0.0);
            return true;
        }
        return false;
    }
}
