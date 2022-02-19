package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // left in case - im not sure what motor we're using 
//Philip - Sounds good

import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class ClimbSubsystem extends SubsystemBase{
    WPI_TalonSRX lift;


    public ClimbSubsystem() {
        lift = new WPI_TalonSRX(3);
    }

    //Philip -  you would leave x there, what you did so far is right
    //          the next part would be to do something like:
    //          lift.set(x);
    //          This would send the value over to the lift motor
    //          If you want to get a little fancier, you could add a way to
    //          force the value of x to be between -1 and 1. There are a few
    //          ways of doing it, but it is an 'if' statement to see if 'x'
    //          is bigger than 1, then make x = 1, or if it is smaller than -1
    //          make x = -1. I'll do it in start, and you can do it everywhere else. 
    public void start(Double x){ // do i leave the 'x' here or replace it with numbers like -1 and 1? 
        
        if(x < -1.0) {
            x = -1.0;
        }
        else if(x > 1.0) { //else if only happens if the all the 'if' or 'else if' before it fails the check.
            x = 1.0;
        }
        // There is no need for the else, but I put this here just as a reference.
        // We don't need the else since if x is between -1 and 1, we want it to stay the same,
        // so adding the else doesn't really do anything.
        // else {
        //     x = x;
        // }

        /* (This is another type of comment, a block comment, anything between the / * and * / is a comment
        without having to do the // for every new line like I did above)

        Here are two other ways of doing the clamp to keep the x in range (fancy ways):
        1. x = ((x < -1) ? -1 : ((x > 1) ? 1 : x));
        - This is the exact same if, else if (forced to have the else also), just in one line
          - it is called the 'turnary operator' if you wanted to look it up.
          - it does the same if check for what is in the parenthesis, and then what comes after the '?'
            is what you put if the statement was true, and after the ':' is if is is false
        
        2. x = Math.max(-1, Math.min(1, x));
        - This does the same thing again, but it uses Java's built in 'Math' library functions: max and min
          - Both Max and Min takes 2 parameters, and gives you back which number passes the check
            - So if you use Math.max(1, 3) it will give you 3 since 3 is bigger than 1
            - If you use Max.min(1, 3) it will give you 1 since 1 is smaller than 3
          - What happens here is Java always works its way inside out, so it does the Math.min(1, x), then
            uses the result with Math.max(-1, result)
          - It sounds a little complicated as I write it, but hopefully it makes a little sense 
        */

        //After forcing x to be in range, we will set it to the motor.
        lift.set(x);    
}

    public void stop(Double x){

    }
}
