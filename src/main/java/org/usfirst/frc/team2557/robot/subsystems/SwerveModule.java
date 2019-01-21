package org.usfirst.frc.team2557.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveModule extends Subsystem {
	private final double kP = 0.1;
	private final double kI = 0.0;
	private final double kD = 0.0;

	private WPI_TalonSRX angleMotor;
	private CANSparkMax speedMotor;
	private PIDController pidController;
	private AnalogInput encoder;

	private double setpoint;
	public double error;
	public double output;
	
	public SwerveModule(int swerveModIndex, boolean inverted) {
		speedMotor = new CANSparkMax(swerveModIndex, MotorType.kBrushless);
		angleMotor = new WPI_TalonSRX(swerveModIndex);
		angleMotor.setInverted(inverted);
		encoder = new AnalogInput(swerveModIndex);
		
		pidController = new PIDController(kP, kI, kD, encoder, angleMotor);
		pidController.setInputRange(0, 4095);
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous(true);
		pidController.setAbsoluteTolerance(RobotMap.toleranceAnglePID);
		pidController.enable();

		angleMotor.configContinuousCurrentLimit(30, 0);
		angleMotor.configPeakCurrentLimit(30, 0);
		angleMotor.configPeakCurrentDuration(100, 0);
		angleMotor.enableCurrentLimit(true);
	}

	public double getEncoderCount(){
		return encoder.getValue();
	}

	public double getSetpoint(){
		return setpoint;
	}
	
	public void drive (double speed, double angle) {
		speedMotor.set (speed);

		setpoint = ((angle + 1) * RobotMap.circumference / 2) % RobotMap.circumference;
		pidController.setSetpoint(setpoint);
		error = pidController.getError();
		output = pidController.get();
	}

    public void initDefaultCommand() {
		// NOTE: there should be no default command here 
		// unless you plan to run only one swerve module at a time
    }
}