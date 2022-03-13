package frc.robot.Commands.Autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;

public class LookAt extends CommandBase{

    private final AutopilotDriver Driver;
    private final SwerveDrivetrain Drivetrain;
    private final Translation2d Target;

    public LookAt(AutopilotDriver driver, SwerveDrivetrain drivetrain, Translation2d target) {
        Driver = driver;
        Drivetrain = drivetrain;
        addRequirements(Driver);
        addRequirements(Drivetrain);

        Target = target;
    }

    @Override
    public void initialize() {
        Driver.tX = false;
        Driver.tY = false;
        Driver.rZ = true;
    }

    @Override
    public void execute() {
        Pose2d tgt = new Pose2d(0, 0, Rotation2d.fromDegrees(Math.atan((-Target.getY()) / (-Target.getX()))));
        Driver.Goto(tgt);
    }

    @Override
    public void end(boolean interrupted) {
        Driver.Goto(null);
        Drivetrain.SetSpeed(new Translation2d(0, 0), 0);
    }

    @Override
    public boolean isFinished() {
        return Driver.AtTarget;
    }

}

