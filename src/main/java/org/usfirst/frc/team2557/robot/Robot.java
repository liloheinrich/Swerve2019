package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static SwerveDrive swerveDrive;
	// public static GyroSwerveDrive gyroIntegrated;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();

		swerveDrive = new SwerveDrive();
		// gyroIntegrated = new GyroSwerveDrive();

		// NOTE: oi MUST be constructed after subsystems
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
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		// NetworkTable contoursTable = NetworkTableInstance.getDefault().getTable("/vision/contours");
		// String[] labels = {"x", "y", "h", "w", "area", "dist", "rot"};
		// double[][] data = new double[7][0];
		// contoursTable.getEntry("centerX").getDoubleArray(data[0]);
		// contoursTable.getEntry("centerY").getDoubleArray(data[1]);
		// contoursTable.getEntry("height").getDoubleArray(data[2]);
		// contoursTable.getEntry("width").getDoubleArray(data[3]);
		// contoursTable.getEntry("area").getDoubleArray(data[4]);
		// contoursTable.getEntry("distance").getDoubleArray(data[5]);
		// contoursTable.getEntry("rotation").getDoubleArray(data[6]);
		// for (int i = 0; i < data.length; i++) {
		// 	System.out.println(labels[i] + ": " + data[i].toString());
		// }
	}

	@Override
	public void teleopInit() {
		// makes autonomous stop running when teleop starts if you want 
		// autonomous to continue until interrupted remove it/comment out
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("encoder FR", RobotMap.swerveModFR.getEncoderCount());
		SmartDashboard.putNumber("encoder FL", RobotMap.swerveModFL.getEncoderCount());
		SmartDashboard.putNumber("encoder BR", RobotMap.swerveModBR.getEncoderCount());
		SmartDashboard.putNumber("encoder BL", RobotMap.swerveModBL.getEncoderCount());

		SmartDashboard.putNumber("setpoint BL", RobotMap.swerveModBL.getSetpoint());
		SmartDashboard.putNumber("setpoint BR", RobotMap.swerveModBR.getSetpoint());
		SmartDashboard.putNumber("setpoint FL", RobotMap.swerveModFL.getSetpoint());
		SmartDashboard.putNumber("setpoint FR", RobotMap.swerveModFR.getSetpoint());

		SmartDashboard.putNumber("error BL", RobotMap.swerveModBL.error);
		SmartDashboard.putNumber("error BR", RobotMap.swerveModBR.error);
		SmartDashboard.putNumber("error FL", RobotMap.swerveModFL.error);
		SmartDashboard.putNumber("error FR", RobotMap.swerveModFR.error);
		
		SmartDashboard.putNumber("output BL", RobotMap.swerveModBL.output);
		SmartDashboard.putNumber("output BR", RobotMap.swerveModBR.output);
		SmartDashboard.putNumber("output FL", RobotMap.swerveModFL.output);
		SmartDashboard.putNumber("output FR", RobotMap.swerveModFR.output);

		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}