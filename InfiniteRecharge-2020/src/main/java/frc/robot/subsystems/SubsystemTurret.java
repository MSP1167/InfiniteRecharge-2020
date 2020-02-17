/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Util;
import frc.robot.util.Xbox;

public class SubsystemTurret extends SubsystemBase {
  /**
   * Creates a new SubsystemTurret.
   */
  private TalonSRX 
    turretYaw,
    turretPitch;

  public SubsystemTurret() {
    turretYaw = new TalonSRX(Constants.TURRET_YAW_ID);
    turretPitch = new TalonSRX(Constants.TURRET_PITCH_ID);

    configureMotors();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Yaw Position", turretYaw.getSensorCollection().getQuadraturePosition());
    SmartDashboard.putNumber("Pitch Position", turretPitch.getSensorCollection().getQuadraturePosition());

    SmartDashboard.putNumber("Yaw Forward Limit", turretYaw.isFwdLimitSwitchClosed());
    SmartDashboard.putNumber("Yaw Backward Limit", turretYaw.isRevLimitSwitchClosed());
  }

  /**
   * Move the turret
   * @param controller The controller to use.
   */
  public void moveTurret(Joystick controller) {
    double speedx;
    double speedy;

    speedx = Xbox.LEFT_X(controller);
    speedy = Xbox.RIGHT_Y(controller);

    speedx = speedx * Util.getAndSetDouble("Turret Spin Inhibitor Yaw", 1);
    speedy = speedy * Util.getAndSetDouble("Turret Spin Inhibitor Pitch", 1);

    turretYaw.set(ControlMode.PercentOutput, speedx);
    turretPitch.set(ControlMode.PercentOutput, speedy);
  }

  private void configureMotors() {
    turretPitch.setNeutralMode(NeutralMode.Brake);
    turretYaw.setNeutralMode(NeutralMode.Brake);
  }
}
