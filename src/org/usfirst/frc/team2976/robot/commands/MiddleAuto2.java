package org.usfirst.frc.team2976.robot.commands;

import org.usfirst.frc.team2976.robot.AutoTargetSide;
import org.usfirst.frc.team2976.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MiddleAuto2 extends CommandGroup {
	double turnPower = 0.5;
	double turnTime = 0.7;
	public MiddleAuto2() {
		System.out.println("AUTO BEGINS");
		Robot.rps.reset();
		addSequential(new TimedDrive(0.6,0.75));
		System.out.println("MiddleAutoSide " + Robot.autoTargetSide);
			if(AutoTargetSide.Right == Robot.autoTargetSide)	{
				System.out.println("MiddleAutoSide " + "Right");
				SmartDashboard.putNumber("isRight", 1);
				addSequential(new PIDTurn2(43));
				addSequential(new TimedDrive(0, 0.1));
				addSequential(new TimedDrive(0.55,1.7));
				addSequential(new PIDTurn2(0));
				addSequential(new TimedDrive(0, 0.1));
				addSequential(new TimedDrive(0.6,1.5));
				addSequential(new RollIntake(0.45,0.5,false));
				///Second Cube
				
				addSequential(new TimedDrive(-0.6,1.4));
				addSequential(new SwitchExtend());
				addSequential(new PIDTurn2(-35));//addSequential(new TimedTurnRight(-0.6,0.8));
				addSequential(new TimedDrive(0, 0.1));
				
				addParallel(new RollIntake(0.45,5,true));
				addSequential(new TimedDrive(0.6,1.8));
				addSequential(new TimedDrive(-0.6,1.5));
				addSequential(new PIDTurn2(0));
				addSequential(new TimedDrive(0, 0.1));
				addSequential(new SwitchRetract());
				addSequential(new TimedDrive(0.65,1.5));
				addSequential(new RollIntake(0.45,5,false));	
				
				
				
			}	else if(AutoTargetSide.Left == Robot.autoTargetSide)	{
				
				// leftleftleft
				
				System.out.println("MiddleAutoSide " + "Left");
				SmartDashboard.putNumber("isRight", 0);		
				addSequential(new PIDTurn2(-50));
				addSequential(new TimedDrive(0, 0.1));
				addSequential(new TimedDrive(0.55,1.95));
				addSequential(new PIDTurn2(0));
				addSequential(new TimedDrive(0, 0.1));
				addSequential(new TimedDrive(0.6,1.5));
				addSequential(new RollIntake(0.45,0.75,false));
				
				///Second Cube

				addSequential(new TimedDrive(-0.6,1.1));
				addSequential(new SwitchExtend());
				addSequential(new PIDTurn2(35));//addSequential(new TimedTurnRight(-0.6,0.8));
				addSequential(new TimedDrive(0, 0.1));
				addParallel(new RollIntake(0.45,6,true));
				addSequential(new TimedDrive(0.6,1.5));
				addSequential(new TimedDrive(-0.6, 1.2));
				addSequential(new PIDTurn2(0));				
				addSequential(new TimedDrive(0, 0.1));
				addSequential(new SwitchRetract());
				addSequential(new TimedDrive(0.65,1.5));
				addSequential(new RollIntake(0.45,5,false));	
			}	else {		
				SmartDashboard.putNumber("isRight", -1);
				
				System.out.println("MiddleAutoSide " + "N/A");
				addSequential(new TimedTurnRight(turnPower,turnTime));	
				addSequential(new TimedDrive(0.6,2.5));
			}
	
		}
		 
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

}
