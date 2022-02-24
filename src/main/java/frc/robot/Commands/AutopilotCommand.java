package frc.robot.Commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;

public class AutopilotCommand extends CommandBase{

    private final AutopilotDriver Driver;
    private final SwerveDrivetrain Drivetrain;
    private final Pose2d Target;

    public AutopilotCommand(AutopilotDriver driver, SwerveDrivetrain drivetrain, Pose2d target) {
        Driver = driver;
        Drivetrain = drivetrain;
        addRequirements(Driver);
        addRequirements(Drivetrain);

        Target = target;
    }

    @Override
    public void initialize() {
        Driver.Goto(Target);
    }

    @Override
    public void end(boolean interrupted) {
        Driver.Goto(null);
        Drivetrain.Drive(new Translation2d(0, 0), 0);
    }

    @Override
    public boolean isFinished() {
        return Driver.AtTarget;
    }

}
