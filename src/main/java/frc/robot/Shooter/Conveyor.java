package frc.robot.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class Conveyor {
    public VictorSPX lMotor = new VictorSPX(13);
    public VictorSPX rMotor = new VictorSPX(14);

    public Conveyor(){
        VictorSPXConfiguration config = new VictorSPXConfiguration();
        config.peakOutputForward = 1;
        config.peakOutputReverse = -1;

        lMotor.configAllSettings(config);
        rMotor.configAllSettings(config);
    }

    public void SetSpeed(double power){
        lMotor.set(ControlMode.PercentOutput, power);
        rMotor.set(ControlMode.PercentOutput, -power);
    }
}
