package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase{
    public static class ClimbConfig {
        protected DoubleSupplier move;
        protected ClimbSubsystem climb;

        public ClimbConfig(DoubleSupplier moveFunction, ClimbSubsystem climbSubsystem){
            move = moveFunction;
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
        climbConfig.climb.move(climbConfig.move.getAsDouble());
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
