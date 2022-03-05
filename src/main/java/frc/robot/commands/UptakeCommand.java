package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.UptakeSubsystem;

public class UptakeCommand extends CommandBase{
    public static class UptakeConfig {
        protected UptakeSubsystem uptake;
        DoubleSupplier motion;

        public UptakeConfig(UptakeSubsystem subsystem, DoubleSupplier _motion){
            uptake = subsystem;
            motion = _motion;
        }
    }

    UptakeConfig cfg;

    public UptakeCommand(UptakeConfig config) {
        cfg = config;
        addRequirements(cfg.uptake);
    }

    @Override
    public void initialize() {
        cfg.uptake.move(cfg.motion.getAsDouble());
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
        cfg.uptake.move(0.);
    }
}
