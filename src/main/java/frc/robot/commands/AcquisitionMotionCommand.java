package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AcquisitionSubsystem;

public class AcquisitionMotionCommand extends CommandBase{
    public static class AcquisitionMotionConfig {
        protected Joystick joystick;
        protected AcquisitionSubsystem acquisition;

        public AcquisitionMotionConfig(Joystick joy, AcquisitionSubsystem acqSubsystem){
            joystick = joy;
            acquisition = acqSubsystem;
        }
    }

    AcquisitionMotionConfig acqMotionConfig;

    public AcquisitionMotionCommand(AcquisitionMotionConfig sConfig) {
        acqMotionConfig = sConfig;
        addRequirements(acqMotionConfig.acquisition);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        acqMotionConfig.acquisition.motion(-acqMotionConfig.joystick.getY());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acqMotionConfig.acquisition.motion(0.);
    }
}
