package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Conveyor;

public class SetConveyor extends CommandBase {
    private final Conveyor conveyor;
    double speed;

    public SetConveyor(Conveyor cnv, double spd) {
        conveyor = cnv;
        speed = spd;
        SmartDashboard.putNumber("Conveyor speed", 0);
    }

    @Override
    public void initialize() {
        conveyor.SetSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
