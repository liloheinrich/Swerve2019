package org.usfirst.frc.team2557.robot;

import org.usfirst.frc.team2557.robot.subsystems.SwerveDrive;
import org.usfirst.frc.team2557.robot.commands.VisionWithGyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI m_oi;
	public static SwerveDrive swerveDrive;
	VisionWithGyro vwg;
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser;

	@Override
	public void robotInit() {
		RobotMap.init();

		swerveDrive = new SwerveDrive();

		m_oi = new OI();
		m_chooser = new SendableChooser<>();

		vwg = new VisionWithGyro();

		m_chooser.addOption("Default Auto", null);
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
		swerveDrive.drive(0,0,0);
		RobotMap.gyro.reset();
		for(int i = 0; i < 4; i++) RobotMap.swerveMod[i].speedMotor.getEncoder();
		
		m_autonomousCommand = m_chooser.getSelected();
		if (m_autonomousCommand != null) m_autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		driverVision();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		swerveDrive.drive(0,0,0);
		if (m_autonomousCommand != null) m_autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic() {
		driverVision();

		SmartDashboard.putNumber("Gyro", RobotMap.gyro.getAngle());
		for (int i = 0; i < 4; i++) {
			SmartDashboard.putNumber("swervemod pidget: " + i, RobotMap.swerveMod[i].encoder.pidGet());
			SmartDashboard.putNumber("swervemod angle: " + i, swerveDrive.angle[i]);
      		SmartDashboard.putNumber("swervemod speed: " + i, swerveDrive.speed[i]);
		}
		Scheduler.getInstance().run();
	}

	public void driverVision(){
		if(m_oi.joystick1.getRawAxis(2) > 0.5 || m_oi.joystick1.getRawAxis(3) > 0.5 || 
				m_oi.a.get() || m_oi.b.get() || m_oi.x.get() || m_oi.y.get()) vwg.start();
		else vwg.cancel();
	}

	@Override
	public void testPeriodic() {
	}
}