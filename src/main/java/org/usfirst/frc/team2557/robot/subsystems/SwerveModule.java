package org.usfirst.frc.team2557.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveModule extends Subsystem {
	private final double MAX_VOLTS = 4.95;
	private final double kP = 1.0;
	private final double kI = 0.0;
	private final double kD = 0.0;
	private final double kS = 0.5;
	private WPI_TalonSRX angleMotor;
	private CANSparkMax speedMotor;
	private PIDController pidController;

	public SwerveModule(int angleMotorIndex, int speedMotorIndex, AnalogInput encoder) {
		this.speedMotor = new CANSparkMax(speedMotorIndex, MotorType.kBrushless);
		this.angleMotor = new WPI_TalonSRX(angleMotorIndex);
		this.pidController = new PIDController(kP, kI, kD, encoder, angleMotor);
		this.pidController.setInputRange(-1, 1);
		this.pidController.setOutputRange(-1, 1);
		this.pidController.setContinuous();
		this.pidController.enable();

		angleMotor.configContinuousCurrentLimit(30, 0);
		angleMotor.configPeakCurrentLimit(30, 0);
		angleMotor.configPeakCurrentDuration(100, 0);
		angleMotor.enableCurrentLimit(true);
	}
	
	public void drive (double speed, double angle) {
		speedMotor.set (speed);
		double setpoint = angle * (MAX_VOLTS * kS) + (MAX_VOLTS * kS);
	    if (setpoint < 0) { setpoint += MAX_VOLTS; }
	    if (setpoint > MAX_VOLTS) { setpoint -= MAX_VOLTS; }
	    this.pidController.setSetpoint(setpoint);
	}

    public void initDefaultCommand() {
		// NOTE: there should be no default command here 
		// unless you plan to run only one swerve module at a time
    }
}