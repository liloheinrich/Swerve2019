package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.GyroSwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroSwerveDrive extends Subsystem {
  // length and width between the drivetrain's wheel axles
  public final double L = 21.3;
  public final double W = 21.3;
  SwerveModule swerveModBR;
	SwerveModule swerveModBL;
	SwerveModule swerveModFR;
  SwerveModule swerveModFL;
  
  public void gyroDrive (double strafe, double forward, double rotate) {
    double r = Math.sqrt((L * L) + (W * W));

    double angle = RobotMap.gyro.getAngle();
    double intermediary = forward * Math.cos(angle) + strafe * Math.sin(angle);
    strafe = - forward * Math.sin(angle) + strafe * Math.cos(angle);
    forward = intermediary;

    double a = strafe - rotate * (L / r);
		double b = strafe + rotate * (L / r);
		double c = forward - rotate * (W / r);
    double d = forward + rotate * (W / r);
    
    double speedBR = Math.sqrt ((a * a) + (d * d));
    double speedBL = Math.sqrt ((a * a) + (c * c));
    double speedFR = Math.sqrt ((b * b) + (d * d));
    double speedFL = Math.sqrt ((b * b) + (c * c));
    
    double angleBR = Math.atan2 (a, d) / Math.PI;
    double angleBL = Math.atan2 (a, c) / Math.PI;
    double angleFR = Math.atan2 (b, d) / Math.PI;
    double angleFL = Math.atan2 (b, c) / Math.PI;
    
    double max = speedBR;
    if (speedBL > max) { speedBL = max; } 
    if (speedFR > max) { speedFR = max; } 
    if (speedFL > max) { speedFL = max; }

    if (max > 1) {
      speedBL /= max; 
      speedFR /= max; 
      speedFL /= max;
    }

    swerveModBR.drive (speedBR, angleBR);
    swerveModBL.drive (speedBL, angleBL);
    swerveModFR.drive (speedFR, angleFR);
    swerveModFL.drive (speedFL, angleFL);
  }
  
  public GyroSwerveDrive (SwerveModule swerveModBR, SwerveModule swerveModBL, 
      SwerveModule swerveModFR, SwerveModule swerveModFL){
    this.swerveModBR = swerveModBR;
    this.swerveModBL = swerveModBL;
    this.swerveModFR = swerveModFR;
    this.swerveModFL = swerveModFL;
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new GyroSwerveDriveCommand());
  }
}