package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.RobotMap;
import org.usfirst.frc.team2557.robot.commands.SwerveDriveCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveDrive extends Subsystem {
	
	public void drive (double str, double fwd, double rot) {
		double r = Math.sqrt ((RobotMap.L * RobotMap.L) + (RobotMap.W * RobotMap.W));
		
		double a = str - rot * (RobotMap.L / r);
		double b = str + rot * (RobotMap.L / r);
		double c = - fwd - rot * (RobotMap.W / r);
    	double d = - fwd + rot * (RobotMap.W / r);
		
		double speedBR = Math.sqrt ((a * a) + (d * d));
	    double speedBL = Math.sqrt ((a * a) + (c * c));
	    double speedFR = Math.sqrt ((b * b) + (d * d));
	    double speedFL = Math.sqrt ((b * b) + (c * c));
	    
	    double angleBR = Math.atan2 (a, d) / Math.PI;
	    double angleBL = Math.atan2 (a, c) / Math.PI;
	    double angleFR = Math.atan2 (b, d) / Math.PI;
		double angleFL = Math.atan2 (b, c) / Math.PI;
		
		if (speedBL < RobotMap.deadband && speedBL > -RobotMap.deadband) { speedBL = 0.0; }
		if (speedBR < RobotMap.deadband && speedBR > -RobotMap.deadband) { speedBR = 0.0; }
		if (speedFL < RobotMap.deadband && speedFL > -RobotMap.deadband) { speedFL = 0.0; }
		if (speedFR < RobotMap.deadband && speedFR > -RobotMap.deadband) { speedFR = 0.0; }
	    
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