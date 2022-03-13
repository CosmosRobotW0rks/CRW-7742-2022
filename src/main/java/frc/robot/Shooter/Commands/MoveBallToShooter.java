package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;
import java.util.Date;

public class MoveBallToShooter extends CommandBase {
    private final Shooter shooter;
    private long startMS;

    public MoveBallToShooter(Shooter sh) {
        shooter = sh;
    }

    @Override
    public void initialize() {
        shooter.conveyor.SetSpeed(1);
        startMS = new Date().getTime();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.conveyor.SetSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return (new Date().getTime() - startMS) > 1000;
    }
}

