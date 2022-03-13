package frc.robot.Shooter;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SwerveDrivetrain;

public class PhysicsProcessor extends SubsystemBase {
    NetworkTableEntry dX;
    NetworkTableEntry dY;
    NetworkTableEntry land_angle;

    public NetworkTableEntry shootSpeed;
    public NetworkTableEntry shootAngle;

    SwerveDrivetrain drivetrain;

    public PhysicsProcessor(SwerveDrivetrain drive){
        drivetrain = drive;
    }

    public void Setup(){
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable physxTable = inst.getTable("physx-calc");

        dX = physxTable.getEntry("dX");
        dY = physxTable.getEntry("dY");
        land_angle = physxTable.getEntry("angle");

        shootSpeed = physxTable.getEntry("shoot-speed");
        shootAngle = physxTable.getEntry("shoot-angle");
    }

    public void UpdateData(){
        double x = drivetrain.OdometryOutPose.getX();
        double y = drivetrain.OdometryOutPose.getY();
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        dX.setDouble(distance);
        dY.setDouble(2.6);
        land_angle.setDouble(45);

        //double resultAngle = shootAngle.getDouble(0);
        //double resultSpeed = shootSpeed.getDouble(0);
        //System.out.println("Retrieved phyx calc data! Params are: dx=" + dX.getDouble(0) + ", dy=" + dY.getDouble(0) + ", angle=" + land_angle.getDouble(0) + ", results are: angle=" + resultAngle + ", spd=" + resultSpeed);
    }
}
