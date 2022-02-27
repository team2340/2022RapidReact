package frc.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.util.SmartDashboardKeys;

public class JoystickDriveCommand extends CommandBase{
    public static class JoystickDriveConfig {
        protected Joystick joystick;
        protected DriveSubsystem drive;
        ADXRS450_Gyro gyro;

        public JoystickDriveConfig(Joystick jStick, DriveSubsystem dSubsystem, ADXRS450_Gyro gro){
            joystick = jStick;
            gyro = gro;
            drive = dSubsystem;
        }
    }

    JoystickDriveConfig joystickDriveConfig;

    public JoystickDriveCommand(JoystickDriveConfig jdConfig) {
        joystickDriveConfig = jdConfig;
        addRequirements(joystickDriveConfig.drive);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.DRIVE_SPEED_CTRL, 1);
        Double x = joystickDriveConfig.joystick.getX() / speedCtrlVal;
        Double y = joystickDriveConfig.joystick.getY() / speedCtrlVal;
        Double z = joystickDriveConfig.joystick.getZ() / speedCtrlVal;
        joystickDriveConfig.drive.drive(x, y, z, joystickDriveConfig.gyro.getAngle());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
