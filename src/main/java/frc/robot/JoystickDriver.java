package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.geometry.Translation2d;

class JoystickDriver{
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;

    public JoystickDriver(SwerveDrivetrain drive){
        drivetrain =  drive;
    }
	public void Drive(){
		double xSpeed = joystick.getRawAxis(1);
		double ySpeed = joystick.getRawAxis(0);

		drivetrain.Drive(new Translation2d(xSpeed, ySpeed), 0);
	}
}