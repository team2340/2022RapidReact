package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    WPI_TalonSRX frontLeft;
    WPI_TalonSRX frontRight;
    CANSparkMax  backLeft;
    CANSparkMax  backRight;
    private MecanumDrive m_robotDrive;

    public DriveSubsystem() {
        frontLeft = new WPI_TalonSRX(3);
        frontRight = new WPI_TalonSRX(7);
        backLeft = new CANSparkMax(11, MotorType.kBrushless); 
        backRight = new CANSparkMax(55, MotorType.kBrushless);

        m_robotDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

    }

    public void drive(Double x, Double y, Double z){
        m_robotDrive.driveCartesian(-y, x, z);
        m_robotDrive.drivePolar(-y, x, z);
    }
}
