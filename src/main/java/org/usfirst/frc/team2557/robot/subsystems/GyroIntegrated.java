package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.GyroCommand;

import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroIntegrated extends Subsystem {

  public final double L = 21.3;
  public final double W = 21.3;
  
  public void gyroDrive(double x1,double y1,double x2) {

    double r = Math.sqrt((L*L) +(W*W));

    double forward = -y1;
    double strafe = x1;
    double rotateCW = x2;

    double intermediary = forward*Math.cos(RobotMap.Gyro1.getAngle()) + strafe*Math.sin(RobotMap.Gyro1.getAngle());
    strafe = -forward* Math.sin(RobotMap.Gyro1.getAngle()) + strafe * Math.cos(RobotMap.Gyro1.getAngle());
    forward = intermediary;

    double a = strafe-rotateCW*(L/r);
		double b = strafe+rotateCW*(L/r);
		double c = forward-rotateCW*(W/r);
    double d = forward+rotateCW*(W/r);
    
    double backRightSpeed = Math.sqrt ((a * a) + (d * d));
	  double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
	  double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
	  double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));
	  
	  double backRightAngle = Math.atan2 (a, d) / Math.PI;
	  double backLeftAngle = Math.atan2 (a, c) / Math.PI;
	  double frontRightAngle = Math.atan2 (b, d) / Math.PI;
    double frontLeftAngle = Math.atan2 (b, c) / Math.PI;
    
    double max = backRightSpeed; if(backLeftSpeed>max){backLeftSpeed=max;} if(frontRightSpeed>max){frontRightSpeed=max;} if(frontLeftSpeed>max){frontLeftSpeed=max;}
    if(max>1){backLeftSpeed/=max; frontRightSpeed/=max; frontLeftSpeed/=max;}

    RobotMap.backRight.drive (backRightSpeed, backRightAngle);
	  RobotMap.backLeft.drive (backLeftSpeed, backLeftAngle);
	  RobotMap.frontRight.drive (frontRightSpeed, frontRightAngle);
	  RobotMap.frontLeft.drive (frontLeftSpeed, frontLeftAngle);
  }
  public GyroIntegrated(WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft) {
		RobotMap.backRight = backRight;
		RobotMap.backLeft = backLeft;
		RobotMap.frontRight = frontRight;
		RobotMap.frontLeft = frontLeft;
	}
  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new GyroCommand());
  }
}
