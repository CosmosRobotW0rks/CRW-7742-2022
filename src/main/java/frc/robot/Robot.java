// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.HomeWheels;
import frc.robot.Commands.MoveOutOfTarmac;
import frc.robot.Commands.Autonomous.AutoCommands;
import frc.robot.Intake.Intake;
import frc.robot.Pneumatics.Pneumatics;
import frc.robot.PowerMgmt.Power;
import frc.robot.Shooter.Shooter;
import frc.robot.ControlSystem.DashboardControl;
import frc.robot.ControlSystem.JoystickDriver;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	public AutopilotDriver autoDriver = new AutopilotDriver();
	public Power powerMgmt = new Power();

	public Shooter shooterCTL = new Shooter(drivetrain, autoDriver);
	public Pneumatics pneumatics = new Pneumatics();
	public Intake intake = new Intake();

	public JoystickDriver joyDriver = new JoystickDriver(drivetrain, autoDriver, shooterCTL, pneumatics, intake);
	public DashboardControl dashboard = new DashboardControl(drivetrain, autoDriver, pneumatics, shooterCTL, intake);

	public static boolean isdisabled = true;

	public Robot(){
		addPeriodic(() -> shooterCTL.SlowUpdate(), 0.1);
		addPeriodic(() -> intake.UpdateSpeed(), 0.1);
		
	}

	@Override
	public void robotInit() {
		drivetrain.Setup();
		autoDriver.Init(drivetrain);
		shooterCTL.Setup();
		pneumatics.Setup();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		drivetrain.Drive();

		isdisabled = isDisabled();
	}

	@Override
	public void autonomousInit() {
		joyDriver.Disable();

		new MoveOutOfTarmac(drivetrain).schedule();
	}

	@Override
	public void teleopInit() {
		joyDriver.EnableAll();
	}
}
