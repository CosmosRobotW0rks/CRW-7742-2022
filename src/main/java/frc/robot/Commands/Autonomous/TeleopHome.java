package frc.robot.Commands.Autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;
import frc.robot.ControlSystem.SetJoystick;

public class TeleopHome extends SequentialCommandGroup{
    public TeleopHome(SwerveDrivetrain drivetrain, AutopilotDriver driver) {
        addCommands(
            new AutopilotCommand(driver, drivetrain, new Pose2d(0, 0, Rotation2d.fromDegrees(0))),
            new SetJoystick(drivetrain.joyDriver, true, true, true)
            //new DriveJoystick(drivetrain, driver)
        );
      }
}
