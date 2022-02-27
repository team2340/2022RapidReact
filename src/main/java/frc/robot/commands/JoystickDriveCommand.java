package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class JoystickDriveCommand extends CommandBase{
    public static class JoystickDriveConfig {
        protected DriveSubsystem drive;
        DoubleSupplier x;
        DoubleSupplier y;
        DoubleSupplier z;
        DoubleSupplier gyro;

        public JoystickDriveConfig(DriveSubsystem drive,
          DoubleSupplier x, DoubleSupplier y,
          DoubleSupplier z, DoubleSupplier gyro){
            this.drive = drive;
            this.x = x;
            this.y = y;
            this.z = z;
            this.gyro = gyro;
        }
    }

    JoystickDriveConfig cfg;

    public JoystickDriveCommand(JoystickDriveConfig jdConfig) {
        cfg = jdConfig;
        addRequirements(cfg.drive);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        cfg.drive.drive(cfg.x.getAsDouble(), cfg.y.getAsDouble(), 
                        cfg.z.getAsDouble(), cfg.gyro.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
