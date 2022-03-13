package frc.robot.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    VictorSPX intakeSpx = new VictorSPX(10);

    public Intake(){
        intakeSpx.set(ControlMode.PercentOutput, 0);
    }

    public void SetSpeed(double speed){
        intakeSpx.set(ControlMode.PercentOutput, speed); //TODO Condigurable speed
    }

    public void UpdateSpeed(){
        //SetSpeed(SmartDashboard.getNumber("Intake Power", 0));
    }
}
