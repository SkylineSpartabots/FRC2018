/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2976.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.TMDColor;
import util.ArduinoSerialRead;
import util.RPS;

import org.usfirst.frc.team2976.robot.OI;
import org.usfirst.frc.team2976.robot.commands.TimedDrive;
import org.usfirst.frc.team2976.robot.commands.DriveToSwitchVision;
import org.usfirst.frc.team2976.robot.commands.DriveToSwitchBlindBackLidar;
import org.usfirst.frc.team2976.robot.commands.ExampleCommand;
import org.usfirst.frc.team2976.robot.commands.SwitchAuto;
import org.usfirst.frc.team2976.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2976.robot.subsystems.Intake;
import org.usfirst.frc.team2976.robot.subsystems.SwitchArm;
import org.usfirst.frc.team2976.robot.subsystems.RobotArm;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	Compressor c = new Compressor(0);
	public static DriveTrain drivetrain;
	public static RobotArm robotArm;
	public static SwitchArm switchArm;
	public static Intake intake;
	public static TMDColor color;
	public static OI oi;
	public static RPS rps;
	
	public static AxisCamera camera;

	public static ArduinoSerialRead arduino;
	

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		c.setClosedLoopControl(true);
		rps = new RPS(0, 0);
		
		color = new TMDColor();
		//TODO don't crash code if no arduino try catch surround
		arduino = new ArduinoSerialRead();
		drivetrain = new DriveTrain();
		robotArm = new RobotArm(2, 0, 0); // TODO add actual PID values here
		switchArm = new SwitchArm();
		intake = new Intake();
		oi = new OI();
		camera = CameraServer.getInstance().addAxisCamera("axis-camera.local");
		camera.setResolution(800, 640);
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putNumber("EncoderDistance", drivetrain.getAvgDistance());
		SmartDashboard.putNumber("EncoderRightDistance", drivetrain.getRightEncoderCount());
		SmartDashboard.putNumber("EncoderLeftDistance", drivetrain.getLeftEncoderCount());
		SmartDashboard.putNumber("LidarDistance", arduino.getDistance());
		
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// m_autonomousCommand = m_chooser.getSelected();
		// should drive straight 1 meter
		m_autonomousCommand = new DriveToSwitchBlindBackLidar();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Red", color.getRedColor());
		SmartDashboard.putNumber("Green", color.getGreenColor());
		SmartDashboard.putNumber("Blue", color.getBlueColor());
		SmartDashboard.putNumber("LidarDistance", arduino.getDistance());
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		 SmartDashboard.putNumber("Red", color.getRedColor());
		 SmartDashboard.putNumber("Green", color.getGreenColor());
		 SmartDashboard.putNumber("Blue", color.getBlueColor());
		 SmartDashboard.putNumber("LidarDistance", arduino.getDistance());
		 Scheduler.getInstance().run();
	}
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		 SmartDashboard.putNumber("Red", color.getRedColor());
		 SmartDashboard.putNumber("Green", color.getGreenColor());
		 SmartDashboard.putNumber("Blue", color.getBlueColor());
		 SmartDashboard.putNumber("LidarDistance", arduino.getDistance());

	}
}
