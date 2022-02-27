package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;

public class ShooterCommand extends CommandBase{
    public static class ShooterConfig {
        protected ShootSubsystem shoot;

        public ShooterConfig(ShootSubsystem sSubsystem){
            shoot = sSubsystem;
        }
    }

    ShooterConfig shooterConfig;
    long startTime;
    private final static double SHOOTER_SPEED = 1.0;
    private final static double UPTAKE_SPEED = 1.0;

    public ShooterCommand(ShooterConfig sConfig) {
        shooterConfig = sConfig;
        addRequirements(shooterConfig.shoot);
    }

    @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        shooterConfig.shoot.shoot(SHOOTER_SPEED);
    }

    @Override
    public void execute() {
        if(System.currentTimeMillis() - startTime > 1000) {
            shooterConfig.shoot.uptake(UPTAKE_SPEED);
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterConfig.shoot.shoot(0.);
        shooterConfig.shoot.uptake(0.);
    }
}
