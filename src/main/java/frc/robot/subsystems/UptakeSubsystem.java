package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;

public class UptakeSubsystem extends SubsystemBase{
    CANSparkMax uptake;
    
    public UptakeSubsystem() {
        uptake = new CANSparkMax(OI.UPTAKE_CTRL_ID, MotorType.kBrushless);
        uptake.setInverted(true);
    }

    public void move(Double speed){
        uptake.set(speed);
    }
}
