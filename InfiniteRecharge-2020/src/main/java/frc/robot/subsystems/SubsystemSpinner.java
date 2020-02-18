/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import frc.robot.Constants;
import frc.robot.util.Util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 

public class SubsystemSpinner extends SubsystemBase {
  private TalonSRX spinner; 
  private ColorSensorV3 sensor;
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private int rotations = 0;
  private double trueRotations;
  private Color detectedColor;
  private boolean prevRed = false;

  private double[]
     localTargetRed = Constants.TARGET_RED,
     localTargetGreen = Constants.TARGET_GREEN,
     localTargetBlue = Constants.TARGET_BLUE,
     localTargetYellow = Constants.TARGET_YELLOW;

  private double displacement;

  public SubsystemSpinner() {
    spinner = new TalonSRX(Constants.SPINNER_ID);
    sensor = new ColorSensorV3(i2cPort);
    rotations = 0;
    trueRotations = 0;
    displacement = Util.getAndSetDouble("Color Calibration Displacement", 20);
  }

  @Override
  public void periodic() {
    detectedColor = sensor.getColor();
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("RED", detectedColor.red*255.0);
    SmartDashboard.putNumber("BLUE", detectedColor.blue*255.0);
    SmartDashboard.putNumber("GREEN", detectedColor.green*255.0);
  }
  public void startSpinner(double speed) {
    spinner.set(ControlMode.PercentOutput, speed);
  }

  public void stopSpinner() {
    spinner.set(ControlMode.PercentOutput, 0);
  }

  public Color getColor() {
    detectedColor = sensor.getColor();
    return detectedColor;
  }
  // pass though true to reset the rotations done, otherwise false should always be passed though
  public boolean spinRotations(boolean reset) {
    if (reset == true){
      rotations = 0;
      trueRotations = 0;
    }
    if((localTargetRed[0] < detectedColor.red*255.0 && detectedColor.red*255.0 < localTargetRed[3]) && (localTargetRed[1] < detectedColor.green*255.0 && detectedColor.green*255.0 < localTargetRed[4]) && (localTargetRed[2] < detectedColor.blue*255.0 && detectedColor.blue*255.0 < localTargetRed[5])){
      if(prevRed == false){
        prevRed = true;
        rotations++;
      }
    }

    if(!((localTargetRed[0] < detectedColor.red*255.0 && detectedColor.red*255.0 < localTargetRed[3]) && (localTargetRed[1] < detectedColor.green*255.0 && detectedColor.green*255.0 < localTargetRed[4]) && (localTargetRed[2] < detectedColor.blue*255.0 && detectedColor.blue*255.0 < localTargetRed[5]))){
      prevRed = false;
      }
    trueRotations = rotations/2;
    if (trueRotations >= Util.getAndSetDouble("Total Rotations", 3)){
      return true;
    }
    return false;
  }

  // Pass though R,G,Y, or B to find that color, pass through anything else and it will start the spinner instead
  public boolean spinColor(char colorToFind) {
    detectedColor = sensor.getColor();
    startSpinner(Util.getAndSetDouble("Spin Inhibitor", Constants.SPINNER_SPEED));
    //SmartDashboard.putNumber("RED", detectedColor.red*255.0);
    //SmartDashboard.putNumber("BLUE", detectedColor.blue*255.0);
    //SmartDashboard.putNumber("GREEN", detectedColor.green*255.0);
    
    SmartDashboard.putBoolean("Found Red", false);
    SmartDashboard.putBoolean("Found Green", false);
    SmartDashboard.putBoolean("Found Blue", false);
    SmartDashboard.putBoolean("Found Yellow", false);
    
    switch(colorToFind){
      case 'R':
        //code for red
        if((localTargetRed[0] < detectedColor.red*255.0 && detectedColor.red*255.0 < localTargetRed[3]) && (localTargetRed[1] < detectedColor.green*255.0 && detectedColor.green*255.0 < localTargetRed[4]) && (localTargetRed[2] < detectedColor.blue*255.0 && detectedColor.blue*255.0 < localTargetRed[5])){
        stopSpinner();
        SmartDashboard.putBoolean("Found Red", true);
        SmartDashboard.putBoolean("Found Green", false);
        SmartDashboard.putBoolean("Found Blue", false);
        SmartDashboard.putBoolean("Found Yellow", false);
        return true;
        }
        return false;
      case 'G':
        //code for green
        if((localTargetGreen[0] < detectedColor.red*255.0 && detectedColor.red*255.0 < localTargetGreen[3]) && (localTargetGreen[1] < detectedColor.green*255.0 && detectedColor.green*255.0 < localTargetGreen[4]) && (localTargetGreen[2] < detectedColor.blue*255.0 && detectedColor.blue*255.0 < localTargetGreen[5])){
        stopSpinner();
        SmartDashboard.putBoolean("Found Red", false);
        SmartDashboard.putBoolean("Found Green", true);
        SmartDashboard.putBoolean("Found Blue", false);
        SmartDashboard.putBoolean("Found Yellow", false);
        return true;
        }
        return false;
      case 'B':
        //code for blue
        if((localTargetBlue[0] < detectedColor.red*255.0 && detectedColor.red*255.0 < localTargetBlue[3]) && (localTargetBlue[1] < detectedColor.green*255.0 && detectedColor.green*255.0 < localTargetBlue[4]) && (localTargetBlue[2] < detectedColor.blue*255.0 && detectedColor.blue*255.0 < localTargetBlue[5])){
        stopSpinner();
        SmartDashboard.putBoolean("Found Red", false);
        SmartDashboard.putBoolean("Found Green", true);
        SmartDashboard.putBoolean("Found Blue", false);
        SmartDashboard.putBoolean("Found Yellow", false);
        return true;
        }
        return false;
      case 'Y':
        //code for yellow
        if((localTargetYellow[0] < detectedColor.red*255.0 && detectedColor.red*255.0 < localTargetYellow[3]) && (localTargetYellow[1] < detectedColor.green*255.0 && detectedColor.green*255.0 < localTargetYellow[4]) && (localTargetYellow[2] < detectedColor.blue*255.0 && detectedColor.blue*255.0 < localTargetYellow[5])){
        stopSpinner();
        SmartDashboard.putBoolean("Found Red", false);
        SmartDashboard.putBoolean("Found Green", false);
        SmartDashboard.putBoolean("Found Blue", false);
        SmartDashboard.putBoolean("Found Yellow", true);
        return true;
        }
        return false;
      default:
        startSpinner(Util.getAndSetDouble("Spin Inhibitor", Constants.SPINNER_SPEED));
        SmartDashboard.putBoolean("Found Red", false);
        SmartDashboard.putBoolean("Found Green", false);
        SmartDashboard.putBoolean("Found Blue", false);
        SmartDashboard.putBoolean("Found Yellow", false);
        return false;
      }
  }
  public void calibrateRed(){
    detectedColor = sensor.getColor();
      localTargetRed[0] = detectedColor.red   *255.0 - displacement;
      localTargetRed[1] = detectedColor.green *255.0 - displacement;
      localTargetRed[2] = detectedColor.blue  *255.0 - displacement;
      localTargetRed[3] = detectedColor.red   *255.0 + displacement;
      localTargetRed[4] = detectedColor.green *255.0 + displacement;
      localTargetRed[5] = detectedColor.blue  *255.0 + displacement;
  }
  public void calibrateGreen(){
    detectedColor = sensor.getColor();
      localTargetGreen[0] = detectedColor.red   *255.0 - displacement;
      localTargetGreen[1] = detectedColor.green *255.0 - displacement;
      localTargetGreen[2] = detectedColor.blue  *255.0 - displacement;
      localTargetGreen[3] = detectedColor.red   *255.0 + displacement;
      localTargetGreen[4] = detectedColor.green *255.0 + displacement;
      localTargetGreen[5] = detectedColor.blue  *255.0 + displacement;
  }
  public void calibrateBlue(){
    detectedColor = sensor.getColor();
      localTargetBlue[0] = detectedColor.red   *255.0 - displacement;
      localTargetBlue[1] = detectedColor.green *255.0 - displacement;
      localTargetBlue[2] = detectedColor.blue  *255.0 - displacement;
      localTargetBlue[3] = detectedColor.red   *255.0 + displacement;
      localTargetBlue[4] = detectedColor.green *255.0 + displacement;
      localTargetBlue[5] = detectedColor.blue  *255.0 + displacement;
  }
  public void calibrateYellow(){
    detectedColor = sensor.getColor();
      localTargetYellow[0] = detectedColor.red   *255.0 - displacement;
      localTargetYellow[1] = detectedColor.green *255.0 - displacement;
      localTargetYellow[2] = detectedColor.blue  *255.0 - displacement;
      localTargetYellow[3] = detectedColor.red   *255.0 + displacement;
      localTargetYellow[4] = detectedColor.green *255.0 + displacement;
      localTargetYellow[5] = detectedColor.blue  *255.0 + displacement;
  }
  public boolean saveCalibration(String fileName){
    File tempFile = new File("TEMP"); 
    String pathname = tempFile.getAbsolutePath();
    tempFile.delete();
    String[] pathDir = pathname.split("/");
    int pathDirMaxSeg = pathDir.length - 1;
    String finalPathDir;
    for(int i = 0; i < pathDirMaxSeg - 6; i++){
      finalPathDir = pathDir[i] + "/";
    }
    // V Uncomment if need to make file V
    //File saveFile = new File(finalPathDir + "calibrationsave.txt");
    try {
      FileWriter saveCalibration = new FileWriter("calibrationsave.txt");
      // Save red
      for(int i = 0; i < 6; i++){
        saveCalibration.write(String.valueOf(localTargetRed[i]));
        saveCalibration.write("\\");
      }
      saveCalibration.write("\n");
      // Save green
      for(int i = 0; i < 6; i++){
        saveCalibration.write(String.valueOf(localTargetGreen[i]));
        saveCalibration.write("\\");
      }
      saveCalibration.write("\n");
      // Save blue
      for(int i = 0; i < 6; i++){
        saveCalibration.write(String.valueOf(localTargetBlue[i]));
        saveCalibration.write("\\");
      }
      saveCalibration.write("\n");
      // Save yellow
      for(int i = 0; i < 6; i++){
        saveCalibration.write(String.valueOf(localTargetYellow[i]));
        saveCalibration.write("\\");
      }
      saveCalibration.write("\n");
      saveCalibration.close();
      return true;
    } 
    catch (IOException e) {
      //add the code to write to driver station
      System.out.println("An error occurred.");
      return false;
    }
  }
  public boolean loadCalibration(){
    File tempFile = new File("TEMP"); 
    String pathname = tempFile.getAbsolutePath();
    tempFile.delete();
    String[] pathDir = pathname.split("/");
    int pathDirMaxSeg = pathDir.length - 1;
    String finalPathDir;
    for(int i = 0; i < pathDirMaxSeg - 6; i++){
      finalPathDir = pathDir[i] + "/";
    }
    String tempString = "";
    File calibrationSave = new File(finalPathDir + "calibrationsave.txt");
    Scanner fileScanner = new Scanner(calibrationSave).useDelimiter("\\");;
    List<String> temps = new ArrayList<String>();
    while (fileScanner.hasNext()) {
      // find next line
      tempString = fileScanner.next();
      temps.add(tempString);
    }
    fileScanner.close();
    Double[] tempsArray = temps.toArray(new Double[0]);
    int k = 0;
    for (Double d : tempsArray) {
      if (k < 6){
        localTargetRed[k] = d;
      }
      if (k > 5 && k < 12){
        localTargetGreen[k-6] = d;
      }
      if (k > 11 && k < 18){
        localTargetBlue[k-12] = d;
      }
      if (k > 17){
        localTargetYellow[k-18] = d;
      }
      k++;
    }
    return true;
  }
}
