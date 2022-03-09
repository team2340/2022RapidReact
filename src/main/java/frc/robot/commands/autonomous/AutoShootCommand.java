package frc.robot.commands.autonomous;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.UptakeSubsystem;

public class AutoShootCommand extends CommandBase {
    public static class AutoShootConfig {
        ShootSubsystem shootSubsystem;
        UptakeSubsystem uptakeSubsystem;
        DoubleSupplier percentPower;

        
        public AutoShootConfig(ShootSubsystem shoot, UptakeSubsystem uptake, DoubleSupplier pPower) {
            shootSubsystem = shoot;
            uptakeSubsystem = uptake;
            percentPower = pPower;
        }
    }
    
    AutoShootConfig cfg;
    
    long DELAY = 1000;
    long END_TIME_DELAY = 2000;

    public AutoShootCommand(AutoShootConfig adConfig) {
        cfg = adConfig;
        addRequirements(cfg.shootSubsystem); //This says that this command depends on the subsystem
    }

    long startTime = 0;
     @Override
    public void initialize() {
        startTime = System.currentTimeMillis();
        Double speed = 1.0 * cfg.percentPower.getAsDouble() / 100.0;
        System.out.println("Shoot Auto speed: " + speed + " = " + cfg.percentPower.getAsDouble() + "%");
        cfg.shootSubsystem.move(speed);
    }
    
    @Override
    public void execute() {
        if(System.currentTimeMillis() >= startTime + DELAY)
        {
            cfg.uptakeSubsystem.move(0.3);
        }
    }

    @Override
    public boolean isFinished() {
        if(System.currentTimeMillis() >= startTime + DELAY + END_TIME_DELAY) {
            cfg.uptakeSubsystem.move(0.);
            cfg.shootSubsystem.move(0.);
            return true;
        }
        return false;
    }
}
