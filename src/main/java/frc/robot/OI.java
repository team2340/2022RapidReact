package frc.robot;

public class OI {
//magic numbers~~
	public static final int BUTTON_1 = 1;
	public static final int BUTTON_2 = 2; 
	public static final int BUTTON_3 = 3;
	public static final int BUTTON_4 = 4;
	public static final int BUTTON_5 = 5;
	public static final int BUTTON_6 = 6;
	public static final int BUTTON_7 = 7;
	public static final int BUTTON_8 = 8;
	public static final int BUTTON_9 = 9;
	public static final int BUTTON_10 = 10;
    
/*Controller Ports*/
	public static final int DRIVE_PORT = 0;
	public static final int ACQUISITION_PORT = 1;

/*CAN IDs*/
    /*Talon IDs*/
    //Wheels
    public static final int FRONT_LEFT_WHEEL_TAL_ID = 2;
    public static final int BACK_LEFT_WHEEL_TAL_ID = 3;
    public static final int FRONT_RIGHT_WHEEL_TAL_ID = 4;
    public static final int BACK_RIGHT_WHEEL_TAL_ID = 5;
    //Climb
    public static final int CLIMB_TAL_ID = 6;
    //Shoot
    public static final int SHOOT_TAL_ID = 7;
    //Acquisition
    public static final int ACQ_TAL_ID = 8;

    /*SparkMAX IDs*/
    public static final int UPTAKE_SM_ID = 9;
}