package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase{
    public static class ClimbConfig {
        protected Joystick joystick;
        protected ClimbSubsystem climb;

        public ClimbConfig(Joystick joy, ClimbSubsystem climbSubsystem){
            joystick = joy;
            climb = climbSubsystem;
        }
    }

    ClimbConfig climbConfig;

    public ClimbCommand(ClimbConfig cConfig) {
        climbConfig = cConfig;
        addRequirements(climbConfig.climb);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        climbConfig.climb.move(-climbConfig.joystick.getTwist());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climbConfig.climb.move(0.);
    }
}
