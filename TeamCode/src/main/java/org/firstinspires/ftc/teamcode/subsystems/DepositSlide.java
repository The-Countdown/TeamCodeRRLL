package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.OldTeleOp.Teleoperation.clawAngleVertical;
import static org.firstinspires.ftc.teamcode.OldTeleOp.Teleoperation.clawDownPos;
import static org.firstinspires.ftc.teamcode.OldTeleOp.Teleoperation.clawOpen;
import static org.firstinspires.ftc.teamcode.OldTeleOp.Teleoperation.depositPower;
import static org.firstinspires.ftc.teamcode.OldTeleOp.Teleoperation.depositRetracted;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class DepositSlide extends Robot.HardwareDevices {
    @Config
    public static final class position {
        public static final int retracted = 0;
        public static final int highBasket = 1675;
        public static final int lowBasket = 600;
        public static final int tolerance = 5;
        public static final int stepRange = 50;
        public static final int stopTolerance = 5;

    }
    @Config
    public static final class power {
        public static final double stop = 0;
        public static final double move = 1;
    }
    public void stop() {
        depositSlide.setPower(power.stop);
    }
    public void move(int amount){
        depositSlide.setTargetPosition(amount);
        depositSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        depositSlide.setPower(power.move);
    }
    public void retract() {
        depositSlide.setTargetPosition(DepositSlide.position.retracted);
        depositSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        depositSlide.setPower(power.move);
    }
    public void highBasket() {
        depositSlide.setTargetPosition(DepositSlide.position.highBasket);
        depositSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        depositSlide.setPower(power.move);
    }
    public void lowBasket() {
        depositSlide.setTargetPosition(DepositSlide.position.lowBasket);
        depositSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        depositSlide.setPower(power.move);
    }

    public void deposit() {
        try {
            claw.setPosition(Claw.position.open);
            clawArm.setPosition(Claw.position.down);
            clawAngle.setPosition(Claw.position.vertical);
            Thread.sleep(3000);
            claw.setPosition(Claw.position.closed);
            Thread.sleep(1500);
            clawArm.setPosition(Claw.position.forwards);
            Thread.sleep(1000);
            depositSlide.setTargetPositionTolerance(DepositSlide.position.tolerance);
            depositSlide.setTargetPosition(DepositSlide.position.highBasket);
            depositSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            depositSlide.setPower(power.move);
            while (!(depositSlide.getCurrentPosition() < (DepositSlide.position.highBasket + DepositSlide.position.stepRange))) {
                Thread.sleep(10);
            }
            clawArm.setPosition(Claw.position.back);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void condense() {
        try {
            clawArm.setPosition(clawDownPos);
            clawAngle.setPosition(clawAngleVertical);
            claw.setPosition(clawOpen);
            Thread.sleep(1000);
            depositSlide.setTargetPositionTolerance(5);
            depositSlide.setTargetPosition(depositRetracted);
            depositSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            depositSlide.setPower(depositPower);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
