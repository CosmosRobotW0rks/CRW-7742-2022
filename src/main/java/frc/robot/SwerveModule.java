package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {
    private CANSparkMax AngleSpark;
    private CANSparkMax DriveSpark;
    private RelativeEncoder AngleEncoder;
    private RelativeEncoder DriveEncoder;

    private SparkMaxPIDController angleController;
    private SparkMaxPIDController driveController;


    private final double SPEED_CALIB_VALUE =  0.33 / 2; // software value / real value

    public SwerveModule(int AngleCANID, int DriveCANID) {
        AngleSpark = new CANSparkMax(AngleCANID, CANSparkMaxLowLevel.MotorType.kBrushless);
        DriveSpark = new CANSparkMax(DriveCANID, CANSparkMaxLowLevel.MotorType.kBrushless);

        AngleEncoder = AngleSpark.getEncoder();
        AngleEncoder.setPositionConversionFactor(2.0 * Math.PI / (18 / 1));

        DriveEncoder = DriveSpark.getEncoder();


        angleController = AngleSpark.getPIDController();
        angleController.setP(1.5);
        angleController.setI(0);
        angleController.setD(0.5);

        driveController = DriveSpark.getPIDController();
        driveController.setP(0.01);
        driveController.setI(0);
        driveController.setD(0.1);
    }

    double TargetAngle = 0;

    public void Drive(double Speed) {
        driveController.setReference(Speed * 250.0 * SPEED_CALIB_VALUE, ControlType.kVelocity);
    }

    public double Angle() {
        return AngleEncoder.getPosition();
    }

    public void SetTarget(double angle) {
        TargetAngle = Math.toRadians(angle);

        double currentAngle = AngleEncoder.getPosition();
        
        // Clamp currentAngle
        double currentAngleClamped = currentAngle % (2.0 * Math.PI);
        currentAngleClamped += currentAngleClamped < 0 ? 2.0 * Math.PI : 0;

        // Find shortest route
        double newTarget = TargetAngle + currentAngle - currentAngleClamped;
        if (TargetAngle - currentAngleClamped > Math.PI)
            newTarget -= 2.0 * Math.PI;
        if (TargetAngle - currentAngleClamped < -Math.PI)
            newTarget += 2.0 * Math.PI;

        angleController.setReference(newTarget, ControlType.kPosition);
    }


    public SwerveModuleState GetState(){
        double speed = DriveEncoder.getVelocity() / 250.0 / SPEED_CALIB_VALUE;
        double angle = Math.toDegrees(Angle());

        SwerveModuleState state = new SwerveModuleState();
        state.angle = Rotation2d.fromDegrees(angle);
        state.speedMetersPerSecond = speed;

        return state;
    }
}