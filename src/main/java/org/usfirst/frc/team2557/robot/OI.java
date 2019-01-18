package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

 /**
 * SOTAbots uses the OI slightly differently. We want the commands to grab the needed info 
 * from the OI instance then pass it on to a subsystem method to be executed. This allows the 
 * OI to be free of commands and just be used to initialize controls. 
 * 
 * We try to avoid use of commands such as 
 * "button1.whilePressed(new ExampleCommmand());" 
 * and rather call these controls from inside of the command's execute method, such as 
 * "Robot.exampleSubsystem.intake(Robot.m_oi.button1.get())"
 */

public class OI {
	public Joystick joystick;
	
	public OI(){
		joystick = new Joystick(0);
	}
}