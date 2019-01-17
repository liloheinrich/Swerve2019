package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SwerveDrive extends Subsystem {
	//the distance between each wheel axle on the length and width.
	public final double L = 21.3;
	public final double W = 21.3;
	
	public void drive(double x1,double y1,double x2) {
		double r = Math.sqrt((L*L) +(W*W));
		y1*=-1;
		
		double a = x1-x2*(L/r);
		double b = x1+x2*(L/r);
		double c = y1-x2*(W/r);
		double d = y1+x2*(W/r);
		
		double backRightSpeed = Math.sqrt ((a * a) + (d * d));
	    double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
	    double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
	    double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));
	    
	    double backRightAngle = Math.atan2 (a, d) / Math.PI;
	    double backLeftAngle = Math.atan2 (a, c) / Math.PI;
	    double frontRightAngle = Math.atan2 (b, d) / Math.PI;
	    double frontLeftAngle = Math.atan2 (b, c) / Math.PI;
	    
	    
	    RobotMap.backRight.drive (backRightSpeed, backRightAngle);
	    RobotMap.backLeft.drive (backLeftSpeed, backLeftAngle);
	    RobotMap.frontRight.drive (frontRightSpeed, frontRightAngle);
	    RobotMap.frontLeft.drive (frontLeftSpeed, frontLeftAngle);
	}
	

	public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft) {
		RobotMap.backRight = backRight;
		RobotMap.backLeft = backLeft;
		RobotMap.frontRight = frontRight;
		RobotMap.frontLeft = frontLeft;
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new SwerveCommand());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

