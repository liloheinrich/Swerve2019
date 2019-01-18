package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.GyroSwerveDrive;
import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static SwerveDrive swerveDrive;
	public static GyroSwerveDrive gyroIntegrated;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		// NOTE: RobotMap MUST be initialized before subsystems
		RobotMap.init();

		swerveDrive = new SwerveDrive();
		gyroIntegrated = new GyroSwerveDrive();

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

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		NetworkTable contoursTable = NetworkTableInstance.getDefault().getTable("/vision/contours");
		NetworkTableEntry centerXs = contoursTable.getEntry("centerX");
		NetworkTableEntry centerYs = contoursTable.getEntry("centerY");
		NetworkTableEntry heights = contoursTable.getEntry("height");
		NetworkTableEntry widths = contoursTable.getEntry("width");
		NetworkTableEntry areas = contoursTable.getEntry("area");
		NetworkTableEntry distances = contoursTable.getEntry("distance");
		NetworkTableEntry rotations = contoursTable.getEntry("rotation");

		double[] xs = new double[0];
		double[] ys = new double[0];
		double[] hs = new double[0];
		double[] ws = new double[0];
		double[] as = new double[0];
		double[] ds = new double[0];
		double[] rs = new double[0];

		centerXs.getDoubleArray(xs);
		centerYs.getDoubleArray(ys);
		heights.getDoubleArray(hs);
		widths.getDoubleArray(ws);
		areas.getDoubleArray(as);
		distances.getDoubleArray(ds);
		rotations.getDoubleArray(rs);

		System.out.println("xs: " + xs.toString());
		System.out.println("ys: " + ys.toString());
		System.out.println("hs: " + hs.toString());
		System.out.println("ws: " + ws.toString());
		System.out.println("as: " + as.toString());
		System.out.println("ds: " + ds.toString());
		System.out.println("rs: " + rs.toString());
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