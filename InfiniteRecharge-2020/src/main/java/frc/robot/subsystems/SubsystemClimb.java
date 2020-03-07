/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.enumeration.ClimbPosition;
import frc.robot.Constants;
import frc.robot.util.Xbox;


public class SubsystemClimb extends SubsystemBase {
  /**
   * Creates a new SubsystemClimb.
   */
  private static CANSparkMax 
    scissors,
    winch;
  private static ClimbPosition storedPosition;

  public SubsystemClimb() {
    scissors = new CANSparkMax(Constants.CLIMBER_SCISSOR_ID, MotorType.kBrushless);
    winch    = new CANSparkMax(Constants.CLIMBER_WINCH_ID, MotorType.kBrushless);
    storedPosition = ClimbPosition.LOWEST;
    configureMotors();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Scissor Position", scissors.getEncoder().getPosition());
    SmartDashboard.putNumber("Winch Position", winch.getEncoder().getPosition());

    SmartDashboard.putNumber("Scissor Amps", scissors.getOutputCurrent());
    SmartDashboard.putNumber("Winch Amps", winch.getOutputCurrent());

    SmartDashboard.putNumber("Scissor Out", scissors.getAppliedOutput());
    SmartDashboard.putNumber("Winch Out", winch.getAppliedOutput());
  }

  public double ascendByController(Joystick controller) {
    double speed = Xbox.LEFT_Y(controller);
    scissors.set(speed);
    return scissors.getOutputCurrent();
  }

  public double decendByController(Joystick controller) {
    double speed = Xbox.RT(controller) - Xbox.LT(controller);
    winch.set(speed);
    return winch.getOutputCurrent();
  }

  public void driveByJoystick(Joystick controller) {
    ascendByController(controller);
    decendByController(controller);
  }

  private void configureMotors() {
    setScissorBraking(IdleMode.kCoast);
    scissors.setInverted(Constants.CLIMBER_SCISSOR_INVERT);

    winch.setIdleMode(IdleMode.kBrake);
    winch.setInverted(Constants.CLIMBER_WINCH_INVERT);
  }
  
  public void zeroEncoders() {
    scissors.getEncoder().setPosition(0);
    winch.getEncoder().setPosition(0);
  }
  
public void setScissorsPercentOutput(double speedz) {
  scissors.set(speedz);
}

public void setWinchPercentOutput(double speedz) {
  winch.set(speedz);
}
  public void setScissorPIDF(double p, double i, double d, double f, double IZone, double lowLimit, double highLimit) {
    scissors.getPIDController().setP(p, 0);
    scissors.getPIDController().setI(i, 0);
    scissors.getPIDController().setD(d, 0);
    scissors.getPIDController().setFF(f, 0);
    scissors.getPIDController().setIZone(IZone, 0);
    scissors.getPIDController().setOutputRange(lowLimit, highLimit);
  }

  public void setScissorsPosition(double position) {
    scissors.getPIDController().setReference(position, ControlType.kPosition);
  }

  public void setWinchPosition(double position) {
    winch.getPIDController().setReference(position, ControlType.kPosition);
  }

  public double getWinchPosition() {
    return winch.getEncoder().getPosition();
  }

  public void setScissorBraking(IdleMode mode) {
    scissors.setIdleMode(mode);
  }

  public void setStoredPosition(ClimbPosition position) {
    storedPosition = position;
  }

  public ClimbPosition getStoredPosition() {
    return storedPosition;
  }
}
