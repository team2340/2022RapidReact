package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;

public class ShootSubsystem extends SubsystemBase{
    CANSparkMax shoot;
    
    public ShootSubsystem() {
        shoot = new CANSparkMax(OI.SHOOT_CTRL_ID, MotorType.kBrushless);
        shoot.setInverted(true);
    }

    public void move(Double speed){
        shoot.set(speed);
    }
}
