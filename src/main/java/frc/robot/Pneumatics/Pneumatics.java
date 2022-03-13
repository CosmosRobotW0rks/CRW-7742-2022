package frc.robot.Pneumatics;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
    Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);

    public void Setup() {
        //
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Compressor", compressor.enabled());
        SmartDashboard.putString("Piston status", solenoid.get() ? "Extended" : "Retracted");

        if (!SmartDashboard.getBoolean("Compressor Enabled?", false))
            compressor.disable();
        else
            compressor.enableDigital();

            SmartDashboard.putNumber("Compressor Current", compressor.getCurrent());
    }

    public void SetPistons(boolean value){
        solenoid.set(value);
    }
}
