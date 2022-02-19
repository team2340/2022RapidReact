package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoRotation extends CommandBase {
    class AutoRotationConfig {
        DriveSubsystem drive;
        ADXRS450_Gyro gyro;
        Double rotationAngle;
        Double x;
        Double y;
        Double z;
    }

    AutoRotationConfig rotationConfig;
    boolean rotateRight;
    Double turnAngle;
    
    public AutoRotation(AutoRotationConfig rConfig) {
        rotationConfig = rConfig;
        addRequirements(rotationConfig.drive); //This says that this command depends on the subsystem
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("Initial angle: ", rotationConfig.gyro.getAngle());
		turnAngle = rotationConfig.rotationAngle - rotationConfig.gyro.getAngle();
		rotationConfig.gyro.reset();
		if (turnAngle > 0) {
			rotateRight = true;
		}
		else {
			rotateRight = false;
		}
		turnAngle = Math.abs(turnAngle);
    }

    @Override
    public void execute() {
        Double currentAngle = Math.abs(rotationConfig.gyro.getAngle());
		SmartDashboard.putNumber("Current angle: ", currentAngle);

		if (currentAngle >= turnAngle) {
			rotationConfig.drive.rotate(0.0);
		}
		else {
            // Logic to slow down as we get closer to the angle, with a clamp at 900
			double t = 1023*((turnAngle - currentAngle)/turnAngle);
			if (t < 900) t = 900;
			double rotateSpeed = t;
			if (rotateRight) {
				rotationConfig.drive.rotate(rotateSpeed);
			}
			else {
				rotationConfig.drive.rotate(-rotateSpeed);
			}
		}
    }

    @Override
    public boolean isFinished() {
        Double currentAngle = Math.abs(rotationConfig.gyro.getAngle());
		if (currentAngle >= turnAngle) {
			rotationConfig.drive.rotate(0.0);
			rotationConfig.gyro.reset();
			return true;
		}
		else {
			return false;
		}
    }
}
