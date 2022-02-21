package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;

public class DriveSubsystem extends SubsystemBase {
    WPI_TalonSRX frontLeft;
    WPI_TalonSRX frontRight;
    WPI_TalonSRX  backLeft;
    WPI_TalonSRX  backRight;
    private MecanumDrive m_robotDrive;

    public DriveSubsystem() {
        frontLeft = new WPI_TalonSRX(OI.FRONT_LEFT_WHEEL_TAL_ID);
        frontRight = new WPI_TalonSRX(OI.FRONT_RIGHT_WHEEL_TAL_ID);
        backLeft = new WPI_TalonSRX(OI.BACK_LEFT_WHEEL_TAL_ID);
        backRight = new WPI_TalonSRX(OI.BACK_RIGHT_WHEEL_TAL_ID);

        frontRight.setInverted(true);
        backRight.setInverted(true);

        setBrakeMode(NeutralMode.Brake);

        m_robotDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
    }

    private void setBrakeMode(NeutralMode mode) {
        frontLeft.setNeutralMode(mode);
        frontRight.setNeutralMode(mode);
        backLeft.setNeutralMode(mode);
        backRight.setNeutralMode(mode);
    }

    public void drive(Double x, Double y, Double z) {
        drive(x, y, z, 0.0);
    }
    //Field-Oriented Driving: Using the gyro, forward is always forward, regardless of
    //the robot's orientation
    public void drive(Double x, Double y, Double z, Double gyroAngle){
        m_robotDrive.driveCartesian(-y, x, z, gyroAngle);
        // m_robotDrive.drivePolar(-y, x, z);
    }

    //Default rotate should be to the right
    public void rotate(Double speed) {
        //TODO: whatever logic needed for the robot to rotate to the right
    }
}
