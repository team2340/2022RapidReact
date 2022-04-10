package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;

public class AcquisitionSubsystem extends SubsystemBase {
    
        WPI_TalonSRX wheels;
        CANSparkMax upDown;
        
        public AcquisitionSubsystem() {
            wheels = new WPI_TalonSRX(OI.ACQ_WHEEL_CTRL_ID);
            upDown = new CANSparkMax(OI.ACQ_MOTION_CTRL_ID, MotorType.kBrushless);

            upDown.setInverted(false);
        }
    
        public void wheels(Double val){
            wheels.set(val);
        }

        public void motion(Double val) {
            upDown.set(val);
        }
    }
  
