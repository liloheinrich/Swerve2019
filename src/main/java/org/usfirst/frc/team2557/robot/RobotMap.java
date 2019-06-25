package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;

public class RobotMap {
	public static double kP = 1;
	public static double SWERVE_ENC_CIRC = 4.927;
	public static final double[] SWERVE_SETPOINT_OFFSET = {2.278, 4.720, 3.283, 3.456};
	public static double[][] SWERVE_PID_CONSTANTS = {{kP, 0, 0}, {kP, 0, 0}, {kP, 0, 0}, {kP, 0, 0}};
	public static boolean[] ANGLE_MOTOR_INVERTED = {true, false, false, false};

	public static double kProt = 0.00265;
	public static double kIrot = 0.000012;
	public static double kDrot = 0.0;
	public static double kProtBig = 0.0024;
	public static double kIrotBig = 0.0000;
	public static double kDrotBig = 0.0;
	public static double tolerancerot = 0.01;
	public static double kPstr = 0.00735;
	public static double kIstr = 0.000000125;
	public static double kDstr = 0.0000028;
	public static double tolerancestr = 0.01;

	public static SwerveModule[] swerveMod;
	public static double MAX_VEL = 10;
    public static double MAX_ACC = 5;
	public static double WHEELBASE_WIDTH = 0.8;
	public static double WHEELBASE_LENGTH = 0.8;
	public static double SWERVE_WHEEL_DIAMETER = 0.05; // in m?
	public static int SWERVE_MAX_CURRENT = 30; // in amps
	public static int SWERVE_CURRENT_DUR = 100; // in ms
	public static double SWERVE_LENGTH = 21.5;
	public static double SWERVE_WIDTH = 21.5;
	public static double SWERVE_RADIUS = Math.sqrt(Math.pow(SWERVE_LENGTH, 2) + Math.pow(SWERVE_WIDTH, 2));
	public static double SWERVE_LOOP_TIME = 0.100; // in ms (50 ms default)
	public static double SWERVE_PID_TOLERANCE = SWERVE_ENC_CIRC / 1000.0; // .1%

	public static double JOYSTICK_DEADBAND = 0.05;
	public static AHRS gyro;

	public static void init() {
		// FR = 0, BR = 1, BL = 2, FL = 3
		swerveMod = new SwerveModule[4];
		for(int i = 0; i < 4; i++) swerveMod[i] = new SwerveModule(i);
		gyro = new AHRS(SPI.Port.kMXP);
	}
}