package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class JoystickDriveCommand extends CommandBase{
    public static class JoystickDriveConfig {
        protected Joystick joystick;
        protected DriveSubsystem drive;

        public JoystickDriveConfig(Joystick jStick, DriveSubsystem dSubsystem){
            joystick = jStick;
            drive = dSubsystem;
        }
    }

    JoystickDriveConfig joystickDriveConfig;

    public JoystickDriveCommand(JoystickDriveConfig jdConfig) {
        joystickDriveConfig = jdConfig;
        addRequirements(joystickDriveConfig.drive);
    }

    @Override
    public void execute() {
        //TODO: speed control?
        Double x = joystickDriveConfig.joystick.getX();
        Double y = joystickDriveConfig.joystick.getY();
        Double z = joystickDriveConfig.joystick.getZ();
        joystickDriveConfig.drive.drive(x, y, z);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
