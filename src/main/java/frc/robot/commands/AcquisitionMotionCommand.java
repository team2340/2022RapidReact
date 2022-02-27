package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AcquisitionSubsystem;

public class AcquisitionMotionCommand extends CommandBase{
    public static class AcquisitionMotionConfig {
        DoubleSupplier motion;
        protected AcquisitionSubsystem acquisition;

        public AcquisitionMotionConfig(DoubleSupplier motionFunction, AcquisitionSubsystem acqSubsystem){
            motion = motionFunction;
            acquisition = acqSubsystem;
        }
    }

    AcquisitionMotionConfig cfg;

    public AcquisitionMotionCommand(AcquisitionMotionConfig cfg) {
        this.cfg = cfg;
        addRequirements(cfg.acquisition);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        cfg.acquisition.motion(cfg.motion.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cfg.acquisition.motion(0.);
    }
}
