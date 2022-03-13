package frc.robot.Pneumatics;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetPistons extends CommandBase {
    private final Pneumatics pneumatics;
    private final boolean value;

    public SetPistons(Pneumatics pn, boolean val) {
        pneumatics = pn;
        value = val;
    }

    @Override
    public void initialize() {
        pneumatics.SetPistons(value);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
