package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.math.geometry.Translation2d;

class JoystickDriver{
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;

    public JoystickDriver(SwerveDrivetrain drive){
        drivetrain = drive;
    }
	public void Drive(){
		double xSpeed = joystick.getRawAxis(1) / 1;
		double ySpeed = joystick.getRawAxis(0) / 1;
        double rot = joystick.getRawAxis(4)    / 4;

        double speed_decrease = 1 / (1 + joystick.getRawAxis(3) * 5);
        double speed_increase = (1 + joystick.getRawAxis(2) * 5);
        double speed_normal = 0.2;
        double speed = (speed_decrease * speed_increase) * speed_normal;

        if(speed > 1)
            joystick.setRumble(RumbleType.kLeftRumble, speed - 1);

        xSpeed *= speed;
        ySpeed *= speed;
        rot *= speed;


        System.out.println("X: " + xSpeed + ", Y: " + ySpeed);

		drivetrain.Drive(new Translation2d(xSpeed, ySpeed), rot);
	}
}