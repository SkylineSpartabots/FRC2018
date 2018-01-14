/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2976.robot.subsystems;

import org.usfirst.frc.team2976.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	WPI_TalonSRX leftFront, leftBack, rightFront, rightBack;
	SpeedControllerGroup left, right;

	DifferentialDrive m_drive;
	
	public DriveTrain() {
		leftFront = new WPI_TalonSRX(RobotMap.leftFrontDriveMotor);
		leftBack =  new WPI_TalonSRX(RobotMap.leftBackDriveMotor);
		rightFront =  new WPI_TalonSRX(RobotMap.rightFrontDriveMotor);
		rightBack = new WPI_TalonSRX(RobotMap.rightBackDriveMotor);
	
		left = new SpeedControllerGroup(leftFront, leftBack);
		right = new SpeedControllerGroup(rightFront, rightBack);
		
		m_drive = new DifferentialDrive(left, right);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
	}
	
	public void drive(double speed, double rotation) {
		m_drive.arcadeDrive(speed, rotation, true);
	}
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
