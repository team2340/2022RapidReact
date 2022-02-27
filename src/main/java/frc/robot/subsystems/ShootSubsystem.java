package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;

public class ShootSubsystem extends SubsystemBase{
    CANSparkMax uptake;
    CANSparkMax shoot;
    
    public ShootSubsystem() {
        uptake = new CANSparkMax(OI.UPTAKE_CTRL_ID, MotorType.kBrushless);
        shoot = new CANSparkMax(OI.SHOOT_CTRL_ID, MotorType.kBrushless);
    }

    public void shoot(Double speed){
        shoot.set(speed);
    }

    public void uptake(Double speed){
        uptake.set(speed);
    }
}
