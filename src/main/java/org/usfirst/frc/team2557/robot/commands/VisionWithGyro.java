package org.usfirst.frc.team2557.robot.commands;

import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionWithGyro extends Command {
  double angleTarget = 0;
  double pixels_height = 240;
  double pixels_width = 320;
  double fwd = 0.2;
  double fwdCmp = 0;

  PIDController pidcontrollerrot;
  double outputr = 0;
  PIDController pidcontrollerstr;
  double outputs = 0;

  double x;
  double a0;
  double a1;
  double valid = 0;
  NetworkTable table;
  NetworkTableEntry ta0;
  NetworkTableEntry ta1;
  NetworkTableEntry tx;
  NetworkTableEntry tv;

  public VisionWithGyro() {
    requires(Robot.swerveDrive);
    table = NetworkTableInstance.getDefault().getTable("limelight");

    pidcontrollerrot = new PIDController(RobotMap.kProt, RobotMap.kIrot, RobotMap.kDrot, new PIDSource(){
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return RobotMap.gyro.getAngle()%360.0;
        }
    }, new PIDOutput(){
        @Override
        public void pidWrite(double output) {
            outputr = -output;
        }
    });
    pidcontrollerrot.setOutputRange(-1, 1);
    pidcontrollerrot.setAbsoluteTolerance(RobotMap.tolerancerot);
    pidcontrollerrot.setInputRange(0, 360);
    pidcontrollerrot.setContinuous();
    

    pidcontrollerstr = new PIDController(RobotMap.kPstr, RobotMap.kIstr, RobotMap.kDstr, new PIDSource(){
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return x;
        }
    }, new PIDOutput(){
        @Override
        public void pidWrite(double output) {
            outputs = -output;
        }
    });
    pidcontrollerstr.setOutputRange(-1, 1);
    pidcontrollerstr.setAbsoluteTolerance(RobotMap.tolerancestr);
  }

  @Override
  protected void initialize() {
    pidcontrollerrot.reset();
    pidcontrollerrot.enable();
    pidcontrollerstr.reset();
    pidcontrollerstr.setSetpoint(0);
    pidcontrollerstr.enable();
  }

  @Override
  protected void execute() {
    // the reason for big and small is to fine tune how it acts so that it is more accurate. just go with it
    if(Math.abs(pidcontrollerrot.getError()) > 5.0) pidcontrollerrot.setPID(RobotMap.kProtBig, RobotMap.kIrotBig,RobotMap.kDrotBig);
    else pidcontrollerrot.setPID(RobotMap.kProt, RobotMap.kIrot,RobotMap.kDrot);

    // the "mad town" driving secrets. TRIGGERS - R & L - AND BUTTONS FOR ANGLE SET
    if(Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5 || 
            Robot.m_oi.a.get() || Robot.m_oi.b.get() || Robot.m_oi.x.get() || Robot.m_oi.y.get()){
        getCamData();
        getForward();
        SmartDashboard.putNumber("vision rot output", pidcontrollerrot.get());
        SmartDashboard.putNumber("vision str output", pidcontrollerstr.get());

        if(valid == 1 && (Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5)) 
            Robot.swerveDrive.drive(outputs, fwdCmp, -outputr);
        else if(Robot.m_oi.a.get() || Robot.m_oi.b.get() || Robot.m_oi.x.get() || Robot.m_oi.y.get()) {
            double axis0 = Robot.m_oi.joystick1.getRawAxis(0);
            double axis1 = Robot.m_oi.joystick1.getRawAxis(1);
            double rad1 = Math.sqrt(Math.pow(axis0, 2) + Math.pow(axis1, 2));
            if (rad1 < RobotMap.JOYSTICK_DEADBAND) { axis0 = 0.0; axis1 = 0.0; }

            double mult = 0.7;
            if (rad1 > 1 - RobotMap.JOYSTICK_DEADBAND) mult = 1.0;
            else mult = 0.7;

            if(Robot.m_oi.bl.get()) mult = 0.2;

            if(axis0 != 0 || axis1 != 0) Robot.swerveDrive.gyroDrive(axis0*mult, axis1*mult, -outputr);
            else Robot.swerveDrive.gyroDrive(0, 0, -outputr);
        } else Robot.swerveDrive.drive(0, -fwdCmp, -outputr);
    }

    if(Robot.m_oi.a.get() && Robot.m_oi.x.get()) angleTarget = 0;
    else if(Robot.m_oi.a.get()) angleTarget = 28.77;
    else if(Robot.m_oi.b.get()) angleTarget = 151.23;
    else if(Robot.m_oi.x.get()) angleTarget = 331.23;
    else if(Robot.m_oi.y.get()) angleTarget = 208.77;
    else if(Robot.m_oi.joystick1.getRawAxis(2) > 0.5) angleTarget = 180.0;

    pidcontrollerrot.setSetpoint(angleTarget);
  }

  private void getForward() {
    if ((valid == 1 && (Robot.m_oi.joystick1.getRawAxis(3) > 0.5) && a0 < 1.15 && a1 < 1.15)) fwdCmp = (((a0+a1)/-2.85) + 1) * fwd;
    else if ((valid == 1 && (Robot.m_oi.joystick1.getRawAxis(3) > 0.5))) fwdCmp = (((a0+a1)/-5.0) + 1) * fwd;
    else if ((valid == 1 && (Robot.m_oi.joystick1.getRawAxis(2) > 0.5))) fwdCmp = (((a0+a1)/-8.0) + 1) * fwd;
    else if (Robot.m_oi.joystick1.getRawAxis(3) > 0.5 || Robot.m_oi.joystick1.getRawAxis(2) > 0.5) fwdCmp = fwd;
  }

  public void getCamData() {
    tx = table.getEntry("tx");
    ta0 = table.getEntry("ta0");
    ta1 = table.getEntry("ta1");
    tv = table.getEntry("tv");

    x = tx.getDouble(0.0);
    a0 = ta0.getDouble(0.0);
    a1 = ta1.getDouble(0.0);
    valid = tv.getDouble(0.0);
  }

  @Override
  protected boolean isFinished() {
    return pidcontrollerrot.onTarget() && pidcontrollerstr.onTarget();
  }

  @Override
  protected void end() {
    pidcontrollerrot.disable();
    pidcontrollerstr.disable();
  }

  @Override
  protected void interrupted() {
    pidcontrollerrot.disable();
    pidcontrollerstr.disable();
    this.end();
  }
}