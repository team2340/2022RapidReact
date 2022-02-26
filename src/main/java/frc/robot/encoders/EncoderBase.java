package frc.robot.encoders;

import frc.robot.encoders.EncoderDefs.EncoderDefines;

public class EncoderBase {
    public static class EncoderBaseConfig {
        public Double wheelDiameter; //inches
        public SetEncoderValue setVal;
        public GetEncoderValue getVal;
        public EncoderDefines encoderDef;

        public EncoderBaseConfig(){}
    }

    EncoderBaseConfig config;
    Double encoderStartValue = 0.0;
    Double inchesPerRevolution = 0.0;
    Double encoderResolution = 0.0;

    public interface GetEncoderValue {
        public Double get();
    }

    public interface SetEncoderValue {
        public void set(Double val);
    }

    EncoderBase(EncoderBaseConfig cfg) {
        config = cfg;
        if(config.encoderDef.QUAD_ENCODER) {
            encoderResolution = config.encoderDef.RESOLUTION;
        }
        else {
            encoderResolution = config.encoderDef.RESOLUTION;
        }
        inchesPerRevolution = cfg.wheelDiameter * Math.PI;
        
        resetEncoderValue();
    }
    
    private Double inchesToEncoderTicks(Double inches) {
        return ((encoderResolution * inches) / inchesPerRevolution);
    }
    
    private Double encoderTicksToInches(Double ticks) {
        return (inchesPerRevolution * ticks) / encoderResolution;
    }

    public Double getEncoderValue() {
        return config.getVal.get();
    }

    public void setEncoderValue(Double val) {
        config.setVal.set(val);
    }

    public void resetEncoderValue() {
        encoderStartValue = getEncoderValue();
    }

    public Double getEncoderValueInches() {
        return encoderTicksToInches(config.getVal.get());
    }

    public void setEncoderValueInches(Double inches) {
        //TODO make sure this is absolute, not relative, otherwise we don't need encoderStartValue
        resetEncoderValue();
        setEncoderValue(encoderStartValue + inchesToEncoderTicks(inches));
    }
}
