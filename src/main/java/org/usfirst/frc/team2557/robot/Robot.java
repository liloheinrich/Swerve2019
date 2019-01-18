package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.SwerveModule;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static SwerveModule swerveModBR, swerveModBL, swerveModFR, swerveModFL;
	public static SwerveDrive swerveDrive;
	public static GyroSwerveDrive gyroIntegrated;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		RobotMap.init();

		swerveModBR = new SwerveModule (RobotMap.angleMotorBR, RobotMap.speedMotorBR, RobotMap.encoderBR);
		swerveModBL = new SwerveModule (RobotMap.angleMotorBL, RobotMap.speedMotorBL, RobotMap.encoderBL);
		swerveModFR = new SwerveModule (RobotMap.angleMotorFR, RobotMap.speedMotorFR, RobotMap.encoderFR);
		swerveModFL = new SwerveModule (RobotMap.angleMotorFL, RobotMap.speedMotorFL, RobotMap.encoderFL);
		swerveDrive = new SwerveDrive(swerveModBR, swerveModBL, swerveModFR, swerveModFL);
		gyroIntegrated = new GyroSwerveDrive(swerveModBR, swerveModBL, swerveModFR, swerveModFL);

		// NOTE: the oi MUST be constructed after subsystems
		m_oi = new OI();

		m_chooser = new SendableChooser<>();
		// m_chooser.addDefault("Default Auto", new ExampleCommand());
		// m_chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}