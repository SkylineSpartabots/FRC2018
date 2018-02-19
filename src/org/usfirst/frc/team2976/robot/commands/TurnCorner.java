package org.usfirst.frc.team2976.robot.commands;
import org.usfirst.frc.team2976.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.PIDMain;
import util.PIDSource;
/**
 *
 */
public class TurnCorner extends Command {
    PIDSource NAVXSource;
	PIDMain turnPID;
	double initDegrees = 0;
	double degrees = 0;
	Timer timer;
	boolean firstTime = true;
	long timeStart = Long.MAX_VALUE;
    public TurnCorner(double degrees) {
    	
    	requires(Robot.drivetrain);
    	Robot.rps.reset();
    	initDegrees = Robot.rps.getAngle();
    	this.degrees = degrees + initDegrees;
    	NAVXSource =  new PIDSource()	{
    		public double getInput()	{
    			System.out.println("Angle" + Robot.rps.getAngle());
    			return Robot.rps.getAngle();
    		}
    	};
    	double kP = 0.008;//0.009;
    	double kI = 0.003	;//0.002;
    	double kD = 0.08;//0.00008;
    	
    	turnPID = new PIDMain(NAVXSource, this.degrees, 100, kP,kI,kD);
    	turnPID.setOutputLimits(-0.5, 0.5);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	turnPID.resetPID();
    	Robot.rps.reset();
    }	
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double output = turnPID.getOutput();
    	if(Math.abs(turnPID.getError())<2)	{
    		if(firstTime) {
    			timer.start();
    			System.out.println("TimerStart");
    			timeStart = System.currentTimeMillis();
    			firstTime = false;
    		}
    		Robot.drivetrain.setBrake();
    	}  	else 	{
    		Robot.drivetrain.tankDrive(-output, output);
    		//Robot.drivetrain.tankDrive(0.45, -0.45);
    	} 
    	//System.out.println(Math.abs(output) < 0.35 && Math.abs(turnPID.getError()) > 0.3);
    	/*
    	if(Math.abs(output) < 0.4 && Math.abs(turnPID.getError()) > 1.5 ) {
    		output = Math.signum(output) * 0.4;
    	} else if(Math.abs(output) < 0.23 && Math.abs(turnPID.getError()) > 0.8 ) {
    		output = Math.signum(output) * 0.23;
    	}
    	*/
    	//output = Math.signum(output)*(-0.488*Math.pow(Math.E,-5.23*Math.abs(output))+0.489);
    	
    	SmartDashboard.putNumber("Navx", turnPID.getError());
    	SmartDashboard.putNumber("NavxOut", output);
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("TurnFinished", System.currentTimeMillis() - timeStart>100);
        //return System.currentTimeMillis() - timeStart > 100;
        return timer.hasPeriodPassed(0.1);//Math.abs(turnPID.getError())<0.5;
    }
    
    // Called once after isFinished returns true
    protected void end() {
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
