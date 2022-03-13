package frc.robot.ControlSystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetJoystick extends CommandBase {
    private final JoystickDriver joyDriver;
    private final boolean tX;
    private final boolean tY;
    private final boolean rZ;

    public SetJoystick(JoystickDriver driver, boolean tx, boolean ty, boolean rz) {
        joyDriver = driver;
        tX = tx;
        tY = ty;
        rZ = rz;
    }

    @Override
    public void execute() {
        joyDriver.TranslationXEnabled = tX;
        joyDriver.TranslationYEnabled = tY;
        joyDriver.RotationZEnabled = rZ;

        SmartDashboard.putBoolean("Joystick X Enabled", tX);
        SmartDashboard.putBoolean("Joystick Y Enabled", tY);
        SmartDashboard.putBoolean("Joystick R Enabled", rZ);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
