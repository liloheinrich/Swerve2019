package org.usfirst.frc.team2557.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class WheelDrive extends Subsystem {

	private WPI_TalonSRX angleMotor;
	private CANSparkMax speedMotor;
	private PIDController pidController;
	
	public WheelDrive (int angleMotor, int speedMotor, int encoder) {
	    this.angleMotor = new WPI_TalonSRX(angleMotor);
	    this.speedMotor = new CANSparkMax(speedMotor, MotorType.kBrushless);
	    this.pidController = new PIDController (1, 0, 0, new AnalogInput (encoder), this.angleMotor);

		this.pidController.setInputRange(-1, 1);
	    this.pidController.setOutputRange(-1, 1);
	    this.pidController.setContinuous();
	    this.pidController.enable();
	}
	private final double MAX_VOLTS = 4.95;
	
	public void drive(double speed, double angle) {
	    speedMotor.set (speed);
	    double setpoint = angle*(MAX_VOLTS * 0.5)+(MAX_VOLTS * 0.5);
	    if (setpoint < 0) {
	        setpoint = MAX_VOLTS+setpoint;
	    }
	    if (setpoint > MAX_VOLTS){
	        setpoint = setpoint-MAX_VOLTS;
	    }

	    this.pidController.setSetpoint(setpoint);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

