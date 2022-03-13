// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.security.PublicKey;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.HomeWheels;
import frc.robot.Commands.Autonomous.TestAutoSequential;
import frc.robot.Intake.Intake;
import frc.robot.Pneumatics.Pneumatics;
import frc.robot.PowerMgmt.Power;
import frc.robot.Shooter.PhysicsProcessor;
import frc.robot.Shooter.Shooter;
import frc.robot.ControlSystem.DashboardControl;
import frc.robot.ControlSystem.JoystickDriver;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public Shooter shooterCTL = new Shooter();
	public Pneumatics pneumatics = new Pneumatics();
	public Intake intake = new Intake();

	public SwerveDrivetrain drivetrain = new SwerveDrivetrain();
	public AutopilotDriver autoDriver = new AutopilotDriver();
	public Power powerMgmt = new Power();

	public PhysicsProcessor physxCalc = new PhysicsProcessor();

	public JoystickDriver joyDriver = new JoystickDriver(drivetrain, autoDriver, shooterCTL, pneumatics, intake);
	public DashboardControl dashboard = new DashboardControl(drivetrain, autoDriver, pneumatics, shooterCTL, intake);

	SendableChooser<Command> autoModeChooser = new SendableChooser<>();

	public static boolean isdisabled = true;

	public Robot(){
		addPeriodic(() -> shooterCTL.SlowUpdate(), 0.1);
		addPeriodic(() -> intake.UpdateSpeed(), 0.1);
		addPeriodic(() -> physxCalc.UpdateData(), 0.1);
	}

	@Override
	public void robotInit() {
		drivetrain.Setup();
		autoDriver.Init(drivetrain);
		shooterCTL.Setup();
		pneumatics.Setup();
		physxCalc.Setup();

		autoModeChooser.addOption("Home Wheels", new HomeWheels(drivetrain));
		autoModeChooser.addOption("Go Places", new TestAutoSequential(drivetrain, autoDriver));
		
		SmartDashboard.putData(autoModeChooser);
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

		if (autoModeChooser.getSelected() != null)
			autoModeChooser.getSelected().schedule();
	}

	@Override
	public void teleopInit() {
		joyDriver.EnableAll();
	}
}
