package org.usfirst.frc.team2557.robot.commands;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class SwerveDriveCommand extends Command {
  public SwerveDriveCommand () {
    requires(Robot.swerveDrive);
  }

  
  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if(Robot.m_oi.joystick1.getPOV() == 180) RobotMap.gyro.reset();

    double axis0 = Robot.m_oi.joystick1.getRawAxis(0);
    double axis1 = Robot.m_oi.joystick1.getRawAxis(1);
    double axis4 = Robot.m_oi.joystick1.getRawAxis(4);
    double axis5 = Robot.m_oi.joystick1.getRawAxis(5);

    // radius circular deadband !
    double rad1 = Math.sqrt(Math.pow(axis0, 2) + Math.pow(axis1, 2));
    double rad2 = Math.sqrt(Math.pow(axis4, 2) + Math.pow(axis5, 2));
    if (rad1 < RobotMap.JOYSTICK_DEADBAND) { axis0 = 0.0; axis1 = 0.0; }
    if (rad2 < RobotMap.JOYSTICK_DEADBAND) { axis4 = 0.0; axis5 = 0.0; }

    // to not go too fast...
    double mult = 0.8;
    double rotMult = 0.45;

    // slow mode
    if(Robot.m_oi.bl.get()) {
      mult = 0.2;
      rotMult = 0.2;
    }

    // the no wheel flippy when released, otherwise go! code. aka important
    if(axis0 != 0 || axis1 != 0 || axis4 != 0) Robot.swerveDrive.gyroDrive(axis0*mult, axis1*mult, axis4*rotMult);
    else Robot.swerveDrive.gyroDriveAngle();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}