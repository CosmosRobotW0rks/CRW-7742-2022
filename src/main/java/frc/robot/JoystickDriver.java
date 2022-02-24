package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.HomeWheels;
import frc.robot.Commands.ResetOdometryWithJoystick;
import frc.robot.Commands.Autonomous.TeleopHome;
import edu.wpi.first.wpilibj2.command.button.*;


class JoystickDriver{
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;
    private AutopilotDriver driver;

    public JoystickDriver(SwerveDrivetrain drive, AutopilotDriver autodriver){
        drivetrain = drive;
        driver = autodriver;

        new JoystickButton(joystick, 1).whenPressed(new TeleopHome(drivetrain, driver));
        new JoystickButton(joystick, 4).whenPressed(new HomeWheels(drivetrain));
        new JoystickButton(joystick, 9).whenPressed(new ResetOdometryWithJoystick(drivetrain, joystick));
    }

	public void Drive(){
		double xSpeed = joystick.getRawAxis(1) / 1;
		double ySpeed = joystick.getRawAxis(0) / 1;
        double rot = joystick.getRawAxis(4)    / 8;

        xSpeed = Math.abs(xSpeed) > 0.2 ? xSpeed : 0;
        ySpeed = Math.abs(ySpeed) > 0.2 ? ySpeed : 0;
        rot    = Math.abs(rot   ) > 0.02 ? rot    : 0;

        double speed_decrease = joystick.getRawButton(6) ? 0.25 : 1; //TODO Speed decrease button
        double speed_increase = (1 + joystick.getRawAxis(3) * 15);
        double speed_normal = 0.5;
        double speed = speed_increase * speed_normal * speed_decrease;


        if(speed > 1)
            joystick.setRumble(RumbleType.kLeftRumble, speed - 1);

        xSpeed *= speed;
        ySpeed *= speed;
        rot *= speed;

        SmartDashboard.putString("Input Speed: ", ("x: " + xSpeed + ", y: " + ySpeed));

		drivetrain.Drive(new Translation2d(xSpeed, ySpeed), rot);
	}
}