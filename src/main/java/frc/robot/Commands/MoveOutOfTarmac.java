package frc.robot.Commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SwerveDrivetrain;
import java.util.Date;

public class MoveOutOfTarmac extends CommandBase {
    private final SwerveDrivetrain drive;
    private long startMS;

    public MoveOutOfTarmac(SwerveDrivetrain dr) {
        drive = dr;
    }

    @Override
    public void initialize() {
        drive.SetSpeed(new Translation2d(-1.5, 0), 0);
        startMS = new Date().getTime();
    }

    @Override
    public void end(boolean interrupted) {
        drive.SetSpeed(new Translation2d(0, 0), 0);
    }

    @Override
    public boolean isFinished() {
        return (new Date().getTime() - startMS) > 1000;
    }
}

