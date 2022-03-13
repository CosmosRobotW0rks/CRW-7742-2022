package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;

public class SetHood extends CommandBase {
    private final Shooter shooter;
    private final double targetAngle;

    public SetHood(Shooter sh, double targetangle) {
        shooter = sh;
        targetAngle = targetangle;
    }

    @Override
    public void initialize() {
        shooter.hoodMotorController.targetRevs = targetAngle / 360;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
