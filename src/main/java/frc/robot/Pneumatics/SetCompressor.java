package frc.robot.Pneumatics;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetCompressor extends CommandBase {
    private final Pneumatics pneumatics;
    private final boolean value;

    public SetCompressor(Pneumatics pn, boolean val) {
        pneumatics = pn;
        value = val;
    }

    @Override
    public void initialize() {
        if (value)
            pneumatics.compressor.enableDigital();
        else
            pneumatics.compressor.disable();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
