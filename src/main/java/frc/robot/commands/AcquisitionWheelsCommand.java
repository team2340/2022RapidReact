package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AcquisitionSubsystem;

public class AcquisitionWheelsCommand extends CommandBase{
    public static class AcquisitionWheelsConfig {
        protected AcquisitionSubsystem acquisition;

        public AcquisitionWheelsConfig(AcquisitionSubsystem acqSubsystem){
            acquisition = acqSubsystem;
        }
    }

    AcquisitionWheelsConfig acqWheelsConfig;
    private final static double WHEELS_SPEED = 1.0;

    public AcquisitionWheelsCommand(AcquisitionWheelsConfig sConfig) {
        acqWheelsConfig = sConfig;
        addRequirements(acqWheelsConfig.acquisition);
    }

    @Override
    public void initialize() {
        acqWheelsConfig.acquisition.wheels(WHEELS_SPEED);
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
        acqWheelsConfig.acquisition.wheels(0.);
    }
}
