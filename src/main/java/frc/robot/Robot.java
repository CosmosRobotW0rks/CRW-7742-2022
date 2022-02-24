// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.HomeWheels;
import frc.robot.Commands.Autonomous.TestAutoSequential;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	public AutopilotDriver autoDriver = new AutopilotDriver();

	public DriveJoystick driveJoystick = new DriveJoystick(drivetrain, autoDriver);
	public TestAutoSequential autoDriveCmd = new TestAutoSequential(drivetrain, autoDriver);

	SendableChooser<Command> autoModeChooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		drivetrain.Setup();
		autoDriver.Init(drivetrain);

		autoModeChooser.addOption("Home Wheels", new HomeWheels(drivetrain));
		autoModeChooser.addOption("Go Places", autoDriveCmd);
		SmartDashboard.putData(autoModeChooser);
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		if (autoModeChooser.getSelected() != null)
			autoModeChooser.getSelected().schedule();
	}

	@Override
	public void teleopInit() {
		driveJoystick.schedule(true);
	}

	@Override
	public void testInit() {
		driveJoystick.end(false);
		drivetrain.Home();

		System.out.println("X= " + drivetrain.OdometryOutPose.getX() + ", Y= " + drivetrain.OdometryOutPose.getY());
	}
}
