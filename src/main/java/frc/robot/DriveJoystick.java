package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class DriveJoystick extends CommandBase {
    private final SwerveDrivetrain drivetrain;
    private final JoystickDriver joyDriver;

    public DriveJoystick(SwerveDrivetrain drive, AutopilotDriver driver) {
        drivetrain = drive;
        addRequirements(drivetrain);

        joyDriver = new JoystickDriver(drivetrain, driver);
    }

    @Override
    public void execute() {
        joyDriver.Drive();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
