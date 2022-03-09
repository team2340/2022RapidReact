package frc.robot.commands.autonomous;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AcquisitionSubsystem;

public class AutoArmCommand extends CommandBase {
    public static class AutoArmConfig {
        AcquisitionSubsystem acqSubsystem;
        
        public AutoArmConfig(AcquisitionSubsystem acq) {
            acqSubsystem = acq;
        }
    }
    
    AutoArmConfig cfg;
    
    public AutoArmCommand(AutoArmConfig adConfig) {
        cfg = adConfig;
        addRequirements(cfg.acqSubsystem); //This says that this command depends on the subsystem
    }

    long startTime = 0;
    long END_TIME_DELAY = 5000;
     @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        cfg.acqSubsystem.motion(1.);
    }
    
    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        if(System.currentTimeMillis() >= startTime + END_TIME_DELAY) {
            cfg.acqSubsystem.motion(0.);
            return true;
        }
        return false;
    }
}
