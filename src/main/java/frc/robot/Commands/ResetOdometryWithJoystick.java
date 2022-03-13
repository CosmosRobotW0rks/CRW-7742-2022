package frc.robot.Commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.SwerveDrivetrain;

public class ResetOdometryWithJoystick extends CommandBase {
    private final SwerveDrivetrain Drivetrain;
    public Translation2d[] poses = new Translation2d[] { new Translation2d(0, 0) };
    public int[] buttons = new int[] { 0 };
    Timer resetTimer = new Timer();

    Joystick joy;

    public ResetOdometryWithJoystick(SwerveDrivetrain drivetrain, Joystick j) {
        Drivetrain = drivetrain;
        joy = j;
    }

    @Override
    public void initialize() {
        resetTimer.reset();
        resetTimer.start();
    }

    @Override
    public void execute(){
        System.out.println("Executex");
        for(int i = 0; i < buttons.length; i++){
            System.out.println("BUTTON " + i);
            System.out.println("BUTTON " + buttons[i]);
            if(joy.getPOV() == buttons[i]){
                System.out.println("BUTTON PRESSED");
                Pose2d pose = new Pose2d(poses[i].getX(), poses[i].getY(), Rotation2d.fromDegrees(-Drivetrain.gyro.getAngle()));
                Drivetrain.odometry.resetPosition(pose, Rotation2d.fromDegrees(-Drivetrain.gyro.getAngle()));
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return resetTimer.hasElapsed(2);
    }
}
