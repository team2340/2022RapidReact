package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;

public class DriveSubsystem extends SubsystemBase {
    WPI_TalonSRX frontLeft;
    WPI_TalonSRX frontRight;
    WPI_TalonSRX  backLeft;
    WPI_TalonSRX  backRight;
    private MecanumDrive m_robotDrive;

    public DriveSubsystem() {
        frontLeft = new WPI_TalonSRX(OI.FRONT_LEFT_WHEEL_CTRL_ID);
        frontRight = new WPI_TalonSRX(OI.FRONT_RIGHT_WHEEL_CTRL_ID);
        backLeft = new WPI_TalonSRX(OI.BACK_LEFT_WHEEL_CTRL_ID);
        backRight = new WPI_TalonSRX(OI.BACK_RIGHT_WHEEL_CTRL_ID);

        frontRight.setInverted(true);
        backRight.setInverted(true);

        backLeft.setSensorPhase(true);

        setBrakeMode(NeutralMode.Brake);

        // Trying something for creating encoders, and making them a little easier to use
        // EncoderBaseConfig encConfig = new EncoderBaseConfig();
        // encConfig.wheelDiameter = 4.0; //?
        // encConfig.setVal = (Double val) -> frontLeft.set(ControlMode.Position, val);
        // encConfig.getVal = () -> frontLeft.getSelectedSensorPosition(0);
        // encConfig.encoderDef = new AM4027();

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

        printEncoderVal("Front Left", frontLeft);
        printEncoderVal("Front Right", frontRight);
        printEncoderVal("Back Left", backLeft);
        printEncoderVal("Back Right", backRight);
    }

    public void printEncoderVal(String name, WPI_TalonSRX talon) {
        SmartDashboard.putNumber(name + " Enc Val: ", talon.getSelectedSensorPosition(0));
    }
    //Field-Oriented Driving: Using the gyro, forward is always forward, regardless of
    //the robot's orientation
    public void drive(Double x, Double y, Double z, Double gyroAngle){
        m_robotDrive.driveCartesian(-y, x, z, gyroAngle);
    }

    // public double positionP = 5 ; //25% power at 3000 error ((%*1023)/desired error)- increases power or drecers error ( bigger nubmer, closere to one) pos- 15% power at 1024/)
	// public double positionP = 0.127875; // 25% power at 2000 error
	// public double positionP = 0.25575; // 25% power at 1000 error              //3000 should be smaller
//	public double positionP = 2/*0.5115*/; // 50% power at 1000 error
//	public double positionP = 0.1023; // 30% power at 3000 error
//	public double positionP = 0.15345;// 30% power at 2000 error
	// public double positionP = 0.1023;// 30% power at 1000 error
//	public double positionI = 0.00001; 
	//public double positionD = 10.0;
    public double positionP = (.0835 * 1023.) / 2000.;//.0639375;
	//.042966
    //.0424545

	// public double positionI = 0.0001;
	public double positionI = 0.000;
	public double positionD = 0.0;
	public double positionF = 0.0;
	
	public double vBusMaxOutput = 1.0; //An output multiplier
	public double vBusPeakOutputVoltage = 1f; //the peak output (between 0 and 1)

    public Double encoderValue() {
        return backLeft.getSelectedSensorPosition(0);
    }

    public void drivePosition(Double ticks) {
        // backLeft.config_kF(0, positionF);
        // backLeft.config_kP(0, positionP);
        // backLeft.config_kI(0, positionI);
        // backLeft.config_kD(0, positionD);
        backLeft.set(ControlMode.Position, ticks);
    }

    //Default rotate should be to the right
    public void rotate(Double speed) {
        //TODO: whatever logic needed for the robot to rotate to the right
    }
}
