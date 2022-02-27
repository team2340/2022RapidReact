package frc.robot.util;

import static edu.wpi.first.wpilibj.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * A {@link Button} that gets its state from a POV on a {@link GenericHID}.
 *
 * <p>This class is provided by the NewCommands VendorDep
 */
public class AxisButton extends Button {
    private final double AXIS_THRESHOLD = 0.2;
    private final GenericHID m_joystick;
    private final int m_channel;

  /**
   * Creates a Axis button for triggering commands.
   *
   * @param joystick The GenericHID object that has the Axis
   * @param channel The channel of the axis
   */
  public AxisButton(GenericHID joystick, int channel) {
    requireNonNullParam(joystick, "joystick", "AxisButton");

    m_joystick = joystick;
    m_channel = channel;
  }

  /**
   * Checks whether the current value of the axis beyond the threshold.
   *
   * @return Whether the value of the axis is beyond the threshold
   */
  @Override
  public boolean get() {
    return Math.abs(m_joystick.getRawAxis(m_channel)) > AXIS_THRESHOLD;
  }
}
