package frc.robot.Shooter;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncoderComms {
    public long shooterPos = 0;
    public long hoodPos = 0;

    NetworkTableEntry dataEntry;

    public void Setup(){
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("encoders");

        dataEntry = table.getEntry("data");
    }

    public void readEncoders() {
        String data = dataEntry.getString("");

        if (data.length() <= 3 || data.charAt(data.length() - 3) != ';'){
            System.out.println("[Encoder Comms] Transfer incomplete, received: " + data);
            return;
        }

        int pt = data.indexOf(';');
        data = data.subSequence(0, pt < 0 ? 0 : pt).toString();

        try {
            String shooterData = data.split(",")[0];
            String hoodData = data.split(",")[1];
            if (data.length() >= 1) {
                shooterPos = Long.parseLong(shooterData); // truncate empty bytes and
                hoodPos = Long.parseLong(hoodData);
            }
        } catch (Exception e) {
        }


        SmartDashboard.putNumber("Shooter Encoder", shooterPos);
        SmartDashboard.putNumber("Hood Encoder", hoodPos);
    }
}
