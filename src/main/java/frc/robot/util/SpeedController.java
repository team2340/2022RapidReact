package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class SpeedController {
    public static class SpeedControllerConfig {
        private static final Double SLOWEST_SPEED = 4.0;
        private static final Double FASTEST_SPEED = 1.0;
        private static final Double INCREMENT_AMOUNT = 0.2;
        
        String key;
        Double defSpeed;
        JoystickButton setDefault;
        JoystickButton slowIncr;
        JoystickButton slowInst;
        JoystickButton fastIncr;
        JoystickButton fastInst;
        Double slowestSpeed;
        Double fastestSpeed;
        Double incrementAmt;

        public SpeedControllerConfig(String key, Double defSpeed, 
            JoystickButton setDefault, JoystickButton slowIncr, JoystickButton fastIncr)
        {
            this(key, defSpeed, setDefault, slowIncr, null, fastIncr, null,
                SLOWEST_SPEED, FASTEST_SPEED);
        }

        public SpeedControllerConfig(String key, Double defSpeed, JoystickButton setDefault,
            JoystickButton slowIncr, JoystickButton slowInst,
            JoystickButton fastIncr, JoystickButton fastInst)
        {
            this(key, defSpeed, setDefault, slowIncr, slowInst, fastIncr, fastInst,
                SLOWEST_SPEED, FASTEST_SPEED);
        }

        public SpeedControllerConfig(String key, Double defSpeed, JoystickButton setDefault,
            JoystickButton slowIncr, JoystickButton slowInst,
            JoystickButton fastIncr, JoystickButton fastInst,
            Double slowestSpeed, Double fastestSpeed)
        {
            this(key, defSpeed, setDefault, slowIncr, slowInst, fastIncr, fastInst,
                slowestSpeed, fastestSpeed, INCREMENT_AMOUNT);
        }

        public SpeedControllerConfig(String key, Double defSpeed, JoystickButton setDefault,
            JoystickButton slowIncr, JoystickButton slowInst,
            JoystickButton fastIncr, JoystickButton fastInst,
            Double slowestSpeed, Double fastestSpeed, Double increment)
        {
            this.key = key;
            this.defSpeed = defSpeed;
            this.setDefault = setDefault;
            this.slowIncr = slowIncr;
            this.slowInst = slowInst;
            this.fastIncr = fastIncr;
            this.fastInst = fastInst;
            this.slowestSpeed = slowestSpeed;
            this.fastestSpeed = fastestSpeed;
            this.incrementAmt = increment;
        }    
    }

    SpeedControllerConfig cfg;

    public SpeedController(SpeedControllerConfig config) {
        cfg = config;
        SmartDashboard.putNumber(cfg.key, cfg.defSpeed);

        if(cfg.setDefault != null) {
            cfg.setDefault.whenPressed(new CommandBase() {
                @Override
                public void initialize() {
                    SmartDashboard.putNumber(cfg.key, cfg.defSpeed);
                }

                @Override
                public boolean isFinished() {
                    return true; 
                }
            });
        }
    
        if(cfg.slowIncr != null) {
            cfg.slowIncr.whenPressed(new CommandBase() {
                @Override
                public void initialize() {
                    Double speedCtrlVal = SmartDashboard.getNumber(cfg.key, cfg.defSpeed);
                    speedCtrlVal = Math.min(speedCtrlVal + cfg.incrementAmt, cfg.slowestSpeed);
                    SmartDashboard.putNumber(cfg.key, speedCtrlVal);
                }

                @Override
                public boolean isFinished() {
                    return true; 
                }
            });
        }

        if(cfg.fastIncr != null) {
            cfg.fastIncr.whenPressed(new CommandBase() {
                @Override
                public void initialize() {
                    Double speedCtrlVal = SmartDashboard.getNumber(cfg.key, cfg.defSpeed);
                    speedCtrlVal = Math.max(speedCtrlVal - cfg.incrementAmt, cfg.fastestSpeed);
                    SmartDashboard.putNumber(cfg.key, speedCtrlVal);
                }

                @Override
                public boolean isFinished() {
                    return true; 
                }
            });
        }
        
        if(cfg.slowInst != null) {
            cfg.slowInst.whenPressed(new CommandBase() {
                @Override
                public void initialize() {
                    SmartDashboard.putNumber(cfg.key, cfg.slowestSpeed);
                }

                @Override
                public boolean isFinished() {
                    return true; 
                }
            });
        }

        if(cfg.fastInst != null) {
            cfg.fastInst.whenPressed(new CommandBase() {
                @Override
                public void initialize() {
                    SmartDashboard.putNumber(cfg.key, cfg.fastestSpeed);
                }

                @Override
                public boolean isFinished() {
                    return true; 
                }
            });
        }
    }
    
    public void reset() {
        SmartDashboard.putNumber(cfg.key, cfg.defSpeed);
    }
}
