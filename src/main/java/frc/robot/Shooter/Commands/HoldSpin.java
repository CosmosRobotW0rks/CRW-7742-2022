package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;

public class HoldSpin extends CommandBase {
    private final Shooter shooter;
    private final double targetRPS;

    double MPS_PER_RPS = 0.51;
    double ENG_COEFF = 1.25;

    public HoldSpin(Shooter sh, double tMPS) {
        shooter = sh;
        targetRPS = (tMPS / MPS_PER_RPS) * ENG_COEFF;
    }

    @Override
    public void initialize() {
        shooter.shooterMotorController.SetTargetRPS(targetRPS);;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
