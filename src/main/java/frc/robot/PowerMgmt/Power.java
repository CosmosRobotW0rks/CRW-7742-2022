package frc.robot.PowerMgmt;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Power extends SubsystemBase {
    PowerDistribution pdp = new PowerDistribution();

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Total current", pdp.getTotalCurrent());
        SmartDashboard.putNumber("Input voltage", pdp.getVoltage());
    }
}
