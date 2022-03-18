package frc.robot.Commands.Autonomous;

import java.util.Date;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;

public class KeepLookingAt extends CommandBase {

    private final AutopilotDriver Driver;
    private final SwerveDrivetrain Drivetrain;
    private final Translation2d Target;

    long pms;

    public KeepLookingAt(AutopilotDriver driver, SwerveDrivetrain drivetrain, Translation2d target) {
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

        pms = new Date().getTime();
    }

    @Override
    public void execute() {
        if ((new Date().getTime() - pms) > 100) {
            double radians = Math.atan((Drivetrain.OdometryOutPose.getY() - Target.getY())
                    / (Drivetrain.OdometryOutPose.getX() - Target.getX()));

            if (Drivetrain.OdometryOutPose.getX() < 0)
                radians += Math.PI;

            double currentAngleClamped = Drivetrain.gyroAngle.getRadians() % (2 * Math.PI);
            currentAngleClamped += currentAngleClamped < 0 ? 2.0 * Math.PI : 0;

            // Find shortest route
            double newTarget = radians + Drivetrain.gyroAngle.getRadians() - currentAngleClamped;
            if (radians - currentAngleClamped > Math.PI)
                newTarget -= 2.0 * Math.PI;
            if (radians - currentAngleClamped < -Math.PI)
                newTarget += 2.0 * Math.PI;
            double degrees = (newTarget * 180) / 3.141;
            Pose2d tgt = new Pose2d(0, 0, Rotation2d.fromDegrees(degrees));

            Driver.Goto(tgt);
        }
    }

    @Override
    public void end(boolean interrupted) {
        Driver.Goto(null);
        Drivetrain.SetSpeed(new Translation2d(0, 0), 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
