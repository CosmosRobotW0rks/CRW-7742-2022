// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.math.geometry.*;

public class Robot extends TimedRobot {
	public SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	public JoystickDriver joyDriver = new JoystickDriver(drivetrain);

	@Override
	public void robotInit() {
		drivetrain.Setup();
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void autonomousPeriodic() {
		drivetrain.Drive(new Translation2d(1, 0), 0.0);
	}

	@Override
	public void teleopPeriodic() {
		joyDriver.Drive();
	}
}
