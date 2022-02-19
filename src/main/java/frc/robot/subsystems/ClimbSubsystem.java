package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // left in case - im not sure what motor we're using 

import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class ClimbSubsystem extends SubsystemBase{
    WPI_TalonSRX lift;


    public ClimbSubsystem() {
        lift = new WPI_TalonSRX(3);
    
    }

    public void start(Double x){ // do i leave the 'x' here or replace it with numbers like -1 and 1? 
     

    }
    public void stop(Double x){

    }
}
