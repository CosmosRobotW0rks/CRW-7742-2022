package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


class JoystickDriver{
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;

    public JoystickDriver(SwerveDrivetrain drive){
        drivetrain = drive;
    }

	public void Drive(){
		double xSpeed = joystick.getRawAxis(1) / 1;
		double ySpeed = joystick.getRawAxis(0) / 1;
        double rot = joystick.getRawAxis(4)    / 8;

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