package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Conveyor;

public class ApplyConveyorSpeed extends CommandBase {
    private final Conveyor conveyor;

    public ApplyConveyorSpeed(Conveyor cnv) {
        conveyor = cnv;
    }

    @Override
    public void initialize() {
        conveyor.SetSpeed(SmartDashboard.getNumber("Conveyor speed", 0));
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
