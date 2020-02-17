/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /**
     * Drivetrain Motor IDs
     */
    public static final int
        DRIVE_RIGHT_MASTER_ID = 3, //spark
        DRIVE_RIGHT_SLAVE_ID  = 4, //spark
        DRIVE_LEFT_MASTER_ID  = 1, //spark
        DRIVE_LEFT_SLAVE_ID   = 2; //spark

    /**
     * Turret Motor IDs
     */
    public static final int
        TURRET_YAW_ID      = 8,
        TURRET_PITCH_ID    = 9,
        TURRET_FLYWHEEL_ID = 7; //spark

    /**
     * Climber Motor IDs
     */
    public static final int
        CLIMBER_SCISSOR_ID = 5, //spark
        CLIMBER_WINCH_ID = 6;   //spark

    /**
     * Spinner Motor IDs
     */
    public static final int
        SPINNER_ID = 10;

  /**
   * Intake IDs
   */
    public static final int
        MAININTAKE_ID = 0,
        FEEDINTAKE_ID = 0;
    
    /**
     * Intake Motor IDs
     */
    public static final int
        EATER_ID = 11,
        SLAPPER_ID = 12;

    /**
     * Feeder Motor IDs
     */
    public static final int
        BEATER_ID = 13,
        FEEDER_ID = 14;
    
    /**
     * Drivetrain motor inverts
     */
    public static final boolean
        DRIVE_RIGHT_MASTER_INVERT = false,
        DRIVE_RIGHT_SLAVE_INVERT  = false,
        DRIVE_LEFT_MASTER_INVERT  = true,
        DRIVE_LEFT_SLAVE_INVERT   = true;

    /**
     * Turret Motor Inverts
     */
    public static final boolean
        TURRET_YAW_INVERT      = false,
        TURRET_PITCH_INVERT    = false,
        TURRET_FLYWHEEL_INVERT = false;

    /**
     * Climber Inverts
     */
    public static final boolean
        CLIMBER_SCISSOR_INVERT = false,
        CLIMBER_WINCH_INVERT   = false;

    /**
     * Spinner Inverts
     */
    public static final boolean
        SPINNER_INVERT = false;

    /**
     * Intake Motor Inverts
     */
    public static final boolean
        EATER_INVERT   = false,
        SLAPPER_INVERT = false;

    /**
     * Feeder Motor Inverts
     */
    public static final boolean
        BEATER_INVERT = false,
        FEEDER_INVERT = false;

    /**
     * Braking Values
     */
    public static final boolean
        INTAKE_BRAKING = true,
        FEEDER_BRAKING = true;

    /**
     * Amp Limits
     */
    public static int
        FLYWHEEL_AMP_LIMIT = 50;
    
    /*
    * FORMAT: Red_Min, Green_Min, Blue_Min, Red_Max, Green_Max, Blue_Max.
    */
    public static final int[]
        TARGET_RED    = { 200, 0  , 0  , 255, 25 , 25  },
        TARGET_GREEN  = { 0  , 200, 0  , 25 , 255, 25  },
        TARGET_BLUE   = { 0  , 200, 200, 25 , 255, 255 },
        TARGET_YELLOW = { 200, 200, 0  , 255, 255, 25  };
    
    /**
     * Extraneous values
     */
    public static final int
        SPINNER_SPEED = 1;

    public static final double 
        FLYWHEEL_GEAR_RATIO = 1.6071;

    public static final boolean
        DRIVE_CAMERA_AUTOMATIC_EXPOSURE = true;
}
