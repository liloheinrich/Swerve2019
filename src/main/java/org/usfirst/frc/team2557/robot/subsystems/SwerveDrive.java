package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveDrive extends Subsystem {
	// distance between each wheel axle on the length and width.
	public final double L = 21.3;
	public final double W = 21.3;
	
	public void drive (double strafe, double forward, double rotate) {
		double r = Math.sqrt ((L * L) + (W * W));
		
		double a = strafe - rotate * (L / r);
		double b = strafe + rotate * (L / r);
		double c = - forward - rotate * (W / r);
    	double d = - forward + rotate * (W / r);
		
		double speedBR = Math.sqrt ((a * a) + (d * d));
	    double speedBL = Math.sqrt ((a * a) + (c * c));
	    double speedFR = Math.sqrt ((b * b) + (d * d));
	    double speedFL = Math.sqrt ((b * b) + (c * c));
	    
	    double angleBR = Math.atan2 (a, d) / Math.PI;
	    double angleBL = Math.atan2 (a, c) / Math.PI;
	    double angleFR = Math.atan2 (b, d) / Math.PI;
	    double angleFL = Math.atan2 (b, c) / Math.PI;
	    
	    RobotMap.swerveModBR.drive (speedBR, angleBR);
	    RobotMap.swerveModBL.drive (speedBL, angleBL);
	    RobotMap.swerveModFR.drive (speedFR, angleFR);
	    RobotMap.swerveModFL.drive (speedFL, angleFL);
	}

    // Put methods for controlling this subsystem here

    public void initDefaultCommand() {
    	setDefaultCommand(new SwerveDriveCommand());
    }
}