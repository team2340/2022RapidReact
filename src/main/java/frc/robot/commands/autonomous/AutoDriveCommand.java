package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDriveCommand extends CommandBase {
    class AutoDriveConfig {
        DriveSubsystem driveSubsystem;
        ADXRS450_Gyro gyro;
        Double x;
        Double y;
        Double z;
    }

    AutoDriveConfig autoDriveConfig;
    
    public AutoDriveCommand(AutoDriveConfig adConfig) {
        autoDriveConfig = adConfig;
        addRequirements(autoDriveConfig.driveSubsystem); //This says that this command depends on the subsystem
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true; //TODO: Should return true when it is done moving
    }
}
