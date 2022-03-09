package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;
public class ClimbSubsystem extends SubsystemBase {
    WPI_TalonSRX lift;
    WPI_TalonSRX lift2;

    public ClimbSubsystem() {
        lift = new WPI_TalonSRX(OI.CLIMB_CTRL_ID);
        lift2 = new WPI_TalonSRX(OI.CLIMB2_CTRL_ID);
        lift2.follow(lift);
    }

    public void move(Double val){
        lift.set(val);
    }
}
