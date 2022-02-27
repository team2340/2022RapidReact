package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AcquisitionSubsystem;

public class ClimbCommand extends CommandBase{
    public static class ClimbConfig {
        protected Joystick joystick;
        protected AcquisitionSubsystem acquisition;

        public ClimbConfig(Joystick joy, AcquisitionSubsystem acqSubsystem){
            joystick = joy;
            acquisition = acqSubsystem;
        }
    }

    ClimbConfig acqMotionConfig;

    public ClimbCommand(ClimbConfig sConfig) {
        acqMotionConfig = sConfig;
        addRequirements(acqMotionConfig.acquisition);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        acqMotionConfig.acquisition.motion(-acqMotionConfig.joystick.getTwist());
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
