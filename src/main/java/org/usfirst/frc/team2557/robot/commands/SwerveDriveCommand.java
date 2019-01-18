package org.usfirst.frc.team2557.robot.commands;

import org.usfirst.frc.team2557.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class SwerveDriveCommand extends Command {

    public SwerveDriveCommand () {
    	requires(Robot.swerveDrive);
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.swerveDrive.drive(Robot.m_oi.joystick.getRawAxis(0), 
                Robot.m_oi.joystick.getRawAxis(1),  Robot.m_oi.joystick.getRawAxis(4));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}