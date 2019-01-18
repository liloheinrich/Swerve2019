package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;
	
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static AHRS gyro;
	public static WPI_TalonSRX angleMotorFR, angleMotorFL, angleMotorBR, angleMotorBL;
	public static CANSparkMax speedMotorFR, speedMotorFL, speedMotorBR, speedMotorBL;
	public static AnalogInput encoderFR, encoderFL, encoderBR, encoderBL;
	public static SwerveModule swerveModBR, swerveModBL, swerveModFR, swerveModFL;
	
	public static void init() {
		gyro = new AHRS(SPI.Port.kMXP);
		
		angleMotorFR = new WPI_TalonSRX(0);
		angleMotorFL = new WPI_TalonSRX(1);
		angleMotorBR = new WPI_TalonSRX(2);
		angleMotorBL = new WPI_TalonSRX(3);

		speedMotorFR = new CANSparkMax(0, MotorType.kBrushless);
		speedMotorFL = new CANSparkMax(1, MotorType.kBrushless);
		speedMotorBR = new CANSparkMax(2, MotorType.kBrushless);
		speedMotorBL = new CANSparkMax(3, MotorType.kBrushless);

		encoderFR = new AnalogInput(0);
		encoderFL = new AnalogInput(1);
		encoderBR = new AnalogInput(2);
		encoderBL = new AnalogInput(3);

		swerveModBR = new SwerveModule (angleMotorBR, speedMotorBR, encoderBR);
		swerveModBL = new SwerveModule (angleMotorBL, speedMotorBL, encoderBL);
		swerveModFR = new SwerveModule (angleMotorFR, speedMotorFR, encoderFR);
		swerveModFL = new SwerveModule (angleMotorFL, speedMotorFL, encoderFL);
	}
}