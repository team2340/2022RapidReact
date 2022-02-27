package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSubsystem extends SubsystemBase {
    ArrayList<UsbCamera> cameras;

    public CameraSubsystem() {
        cameras = new ArrayList<>();
        cameras.add(CameraServer.startAutomaticCapture(cameras.size())); //id 0
        cameras.add(CameraServer.startAutomaticCapture(cameras.size())); //id 1
    }

    public ArrayList<UsbCamera> getCameras() {
        return cameras;
    }
}
