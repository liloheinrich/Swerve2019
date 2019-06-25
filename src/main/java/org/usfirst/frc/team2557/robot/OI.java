package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public Joystick joystick1;
	public JoystickButton a; 
	public JoystickButton b; 
	public JoystickButton x;
	public JoystickButton y;
	public JoystickButton bl;
	public JoystickButton br;
	public JoystickButton back;
	public JoystickButton start;
	public JoystickButton tl;
	public JoystickButton tr;

	public OI(){
		joystick1 = new Joystick(0); // right = rotate, left = translate
		a = new JoystickButton(joystick1, 1); // fr rocket
		b = new JoystickButton(joystick1, 2); // fl rocket
		x = new JoystickButton(joystick1, 3); // br rocket
		y = new JoystickButton(joystick1, 4); // bl rocket
		bl = new JoystickButton(joystick1, 5); // slow mode
		br = new JoystickButton(joystick1, 6); // FCD global
		back = new JoystickButton(joystick1, 7);
		start = new JoystickButton(joystick1, 8);
		tl = new JoystickButton(joystick1, 9);
		tr = new JoystickButton(joystick1, 10); // X-MODE
	}
}