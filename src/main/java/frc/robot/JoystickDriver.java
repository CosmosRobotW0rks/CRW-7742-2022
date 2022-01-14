package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.geometry.Translation2d;

class JoystickDriver{
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;

    public JoystickDriver(SwerveDrivetrain drive){
        drivetrain = drive;
    }
	public void Drive(){
		double xSpeed = -joystick.getRawAxis(1) / 1;
		double ySpeed = -joystick.getRawAxis(0) / 1;
        double rot = -joystick.getRawAxis(4)    / 4;

        xSpeed = Math.pow(xSpeed, 3);
        ySpeed = Math.pow(ySpeed, 3);
        rot =    Math.pow(rot, 3);


        System.out.println("X: " + xSpeed + ", Y: " + ySpeed);

		drivetrain.Drive(new Translation2d(xSpeed, ySpeed), rot);
	}
}