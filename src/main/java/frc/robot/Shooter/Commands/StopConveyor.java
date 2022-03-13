package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Conveyor;

public class StopConveyor extends CommandBase {
    private final Conveyor conveyor;

    public StopConveyor(Conveyor cnv) {
        conveyor = cnv;
    }

    @Override
    public void initialize() {
        conveyor.SetSpeed(0);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
