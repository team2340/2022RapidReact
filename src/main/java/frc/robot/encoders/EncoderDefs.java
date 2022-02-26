package frc.robot.encoders;

public class EncoderDefs {
    public static abstract class EncoderDefines {       
        public Double RESOLUTION = 0.;
        public Integer CHANNELS = 0;
        public Boolean QUAD_ENCODER = false;
    }
    
    public static class AM4027 extends EncoderDefines {
        {
            RESOLUTION = 256.0;
            CHANNELS = 2;
            QUAD_ENCODER = true;
        }
    }
}
