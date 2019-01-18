/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2557.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

import org.usfirst.frc.team2557.robot.subsystems.GyroIntegrated;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.WheelDrive;

// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import edu.wpi.first.wpilibj.PIDController;
	
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static GyroIntegrated gyroIntegrated;
	public static SwerveDrive swerveDrive;
	public static WheelDrive wheelDrive;
	
	public static WheelDrive backRight;
	public static WheelDrive backLeft;
	public static WheelDrive frontRight;
	public static WheelDrive frontLeft;
//	public static WPI_TalonSRX angleMotor;
//	public static CANSparkMax speedMotor;
//  public static PIDController pidController;
	
	public static AHRS Gyro1;
	
	public static void init() {
		
		WheelDrive backRight = new WheelDrive (4, 0, 0);
		WheelDrive backLeft = new WheelDrive (1, 1, 1);
		WheelDrive frontRight = new WheelDrive (1, 1, 1);
		WheelDrive frontLeft = new WheelDrive (1, 1, 1);

		Gyro1 = new AHRS(SPI.Port.kMXP);
		
//		angleMotor = new WPI_TalonSRX(4);
//		speedMotor = new CANSparkMax(1, MotorType.kBrushless);
		
		swerveDrive = new SwerveDrive(backRight, backLeft, frontRight, frontLeft);
		gyroIntegrated = new GyroIntegrated(backRight, backLeft, frontRight, frontLeft);
	}
}
