// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.Autonomous.TestAutoSequential;

public class Robot extends TimedRobot {
	public SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	public AutopilotDriver autoDriver = new AutopilotDriver();

	public DriveJoystick driveJoystick = new DriveJoystick(drivetrain, autoDriver);
	public TestAutoSequential autoDriveCmd = new TestAutoSequential(drivetrain, autoDriver);

	@Override
	public void robotInit() {
		drivetrain.Setup();
		autoDriver.Init(drivetrain);
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autoDriveCmd.schedule(true);
	}

	@Override
	public void teleopInit() {
		driveJoystick.schedule(true);
	}

	@Override
	public void testInit(){
		driveJoystick.end(false);
		drivetrain.Home();

        System.out.println("X= " + drivetrain.OdometryOutPose.getX() + ", Y= " + drivetrain.OdometryOutPose.getY());
	}
}
