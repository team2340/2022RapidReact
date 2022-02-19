package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootSubsystem extends SubsystemBase{
    WPI_TalonSRX uptake;
    WPI_TalonSRX shoot;
    
    public ShootSubsystem() {
        uptake = new WPI_TalonSRX(3);
        shoot = new WPI_TalonSRX(7);
    }

    //For this one, you might want two sets of functions, A start and stop for the uptake
    //and a start and stop for the shoot
    public void start(Double x){
        
    
    }
    public void stop(Double x){

    }
}
