package frc.robot.Shooter;

import java.security.spec.EncodedKeySpec;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncoderComms {
    I2C encoderWire = new I2C(Port.kOnboard, 4);

    public long shooterPos = 0;
    public long hoodPos = 0;

    private long shooterPrevpos = 0;
    private long hoodPrevpos = 0;

    public EncoderComms(){
        if(encoderWire.addressOnly()){
            System.out.println("FUCK NOT CONNECTED!");
        }
        else{
            System.out.println("Conencted to encoders!");
        }
    }

    void readEncoderRaw() {
        byte[] bytes = new byte[32];

        if (encoderWire.readOnly(bytes, 32)){
            System.out.println("[Encoder Comms] Transfer aborted, received: " + new String(bytes));
            return;
        }

        String output = new String(bytes);
        int pt = output.indexOf((char) 255);
        String data = output.subSequence(0, pt - 1 < 0 ? 0 : pt - 1).toString();

        if (output.charAt(pt - 1) != ';'){
            System.out.println("[Encoder Comms] Transfer incomplete, received: " + output);
            return;
        }

        try {
            String shooterData = data.split(",")[0];
            String hoodData = data.split(",")[1];
            if (pt >= 1) {
                shooterPos = Long.parseLong(shooterData); // truncate empty bytes and
                hoodPos = Long.parseLong(hoodData);
            }
        } catch (Exception e) {
        }

    }

    public void readEncoders() {
        readEncoderRaw();
        if (Math.abs(shooterPos - shooterPrevpos) > 10000 && shooterPrevpos >= 1){
            System.out.println("Shooter encoder message rejected! pos: " + shooterPos);
            shooterPos = shooterPrevpos;
        }

        if (Math.abs(hoodPos - hoodPrevpos) > 10000 && hoodPrevpos >= 1){
            System.out.println("Hood encoder message rejected! pos: " + hoodPos);
            hoodPos = hoodPrevpos;
        }

        hoodPrevpos = hoodPos;
        shooterPrevpos = shooterPos;

        SmartDashboard.putNumber("Shooter Encoder", shooterPos);
        SmartDashboard.putNumber("Hood Encoder", hoodPos);
    }
}
