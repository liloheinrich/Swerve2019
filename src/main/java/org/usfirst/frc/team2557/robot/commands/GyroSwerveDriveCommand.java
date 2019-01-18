package org.usfirst.frc.team2557.robot.commands;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class GyroSwerveDriveCommand extends Command {
  public GyroSwerveDriveCommand () {
    requires(Robot.gyroIntegrated);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.swerveDrive.drive(Robot.m_oi.joystick.getRawAxis(0), 
        Robot.m_oi.joystick.getRawAxis(1), Robot.m_oi.joystick.getRawAxis(4));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}