package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;
	
public class RobotMap {
	public static AHRS gyro;
	public static AnalogInput encoderFR, encoderFL, encoderBR, encoderBL;
	public static SwerveModule swerveModBR, swerveModBL, swerveModFR, swerveModFL;
	
	public static void init() {
		gyro = new AHRS(SPI.Port.kMXP);

		encoderFR = new AnalogInput(0);
		encoderFL = new AnalogInput(1);
		encoderBR = new AnalogInput(2);
		encoderBL = new AnalogInput(3);

		swerveModBR = new SwerveModule (0, 0, encoderBR);
		swerveModBL = new SwerveModule (1, 1, encoderBL);
		swerveModFR = new SwerveModule (2, 2, encoderFR);
		swerveModFL = new SwerveModule (3, 3, encoderFL);
	}
}