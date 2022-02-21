package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.util.SmartDashboardKeys;

public class SpeedController {
    private static final Double SLOWEST_SPEED = 4.0;
    private static final Double FASTEST_SPEED = 1.0;
    private Double defaultSpeed = 1.0;

    public SpeedController(Double defSpeed, JoystickButton setDefault,
    JoystickButton slowIncr, JoystickButton slowInst,
    JoystickButton fastIncr, JoystickButton fastInst) {
        defaultSpeed = defSpeed;

        SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, defaultSpeed);

        setDefault.whenPressed(new CommandBase() {
            @Override
            public void initialize() {
                SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, defaultSpeed);
            }

            @Override
            public boolean isFinished() {
                return true; 
            }
        });
    
        slowIncr.whenPressed(new CommandBase() {
            @Override
            public void initialize() {
                Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.SPEED_CTRL, defaultSpeed);
                speedCtrlVal = Math.min(speedCtrlVal + 0.2, SLOWEST_SPEED);
                SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, speedCtrlVal);
            }

            @Override
            public boolean isFinished() {
                return true; 
            }
        });

        
        fastIncr.whenPressed(new CommandBase() {
            @Override
            public void initialize() {
                Double speedCtrlVal = SmartDashboard.getNumber(SmartDashboardKeys.SPEED_CTRL, defaultSpeed);
                speedCtrlVal = Math.max(speedCtrlVal - 0.2, FASTEST_SPEED);
                SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, speedCtrlVal);
            }

            @Override
            public boolean isFinished() {
                return true; 
            }
        });
        
        slowInst.whenPressed(new CommandBase() {
            @Override
            public void initialize() {
                SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, SLOWEST_SPEED);
            }

            @Override
            public boolean isFinished() {
                return true; 
            }
        });

        fastInst.whenPressed(new CommandBase() {
            @Override
            public void initialize() {
                SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, FASTEST_SPEED);
            }

            @Override
            public boolean isFinished() {
                return true; 
            }
        });
    }
    
    public void reset() {
        SmartDashboard.putNumber(SmartDashboardKeys.SPEED_CTRL, defaultSpeed);
    }
}
