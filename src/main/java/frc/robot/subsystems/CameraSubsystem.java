package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSubsystem extends SubsystemBase {
    ArrayList<UsbCamera> cameras;

    public CameraSubsystem(int numCameras) {
        cameras = new ArrayList<>();
        for(int i = 0; i < numCameras; ++i) {
            cameras.add(CameraServer.startAutomaticCapture(i));
        }
    }

    public ArrayList<UsbCamera> getCameras() {
        return cameras;
    }
}
