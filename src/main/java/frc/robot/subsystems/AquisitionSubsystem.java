package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AquisitionSubsystem extends SubsystemBase {
    
        WPI_TalonSRX aq;
        
        public AquisitionSubsystem() {
            
            aq = new WPI_TalonSRX(7);

    
            }
    
        public void start(Double x){

        }
            
        public void stop(Double x){

        }
    }
  
