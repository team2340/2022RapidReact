package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class ShooterCommand extends CommandBase{
    public static class ShooterConfig {
        protected ShootSubsystem shoot;
        DoubleSupplier motion;

        public ShooterConfig(ShootSubsystem sSubsystem, DoubleSupplier _motion){
            shoot = sSubsystem;
            motion = _motion;
        }
    }

    ShooterConfig cfg;

    public ShooterCommand(ShooterConfig config) {
        cfg = config;
        addRequirements(cfg.shoot);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Double currentSpeed = cfg.motion.getAsDouble();
        SmartDashboard.putNumber("Shoot Speed %", (currentSpeed/1.0) * 100);
        cfg.shoot.move(currentSpeed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        cfg.shoot.move(0.);
    }
}
