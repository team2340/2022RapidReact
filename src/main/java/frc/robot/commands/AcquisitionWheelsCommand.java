package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AcquisitionSubsystem;

public class AcquisitionWheelsCommand extends CommandBase{
    public static class AcquisitionWheelsConfig {
        DoubleSupplier motion;
        protected AcquisitionSubsystem acquisition;

        public AcquisitionWheelsConfig(AcquisitionSubsystem acqSubsystem, DoubleSupplier _motion){
            acquisition = acqSubsystem;
            motion = _motion;
        }
    }

    AcquisitionWheelsConfig cfg;

    public AcquisitionWheelsCommand(AcquisitionWheelsConfig sConfig) {
        cfg = sConfig;
        // addRequirements(acqWheelsConfig.acquisition);
    }

    @Override
    public void initialize() {
        cfg.acquisition.wheels(cfg.motion.getAsDouble());
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cfg.acquisition.wheels(0.);
    }
}
