package frc.robot.commands;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;

import java.util.ArrayList;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CameraSubsystem;

public class CameraCommand extends CommandBase {
    ArrayList<UsbCamera> cameras;
    VideoSink server;
    int currentIndex;

	@Override
    public void initialize() {
    }

	public CameraCommand(CameraSubsystem subsystem) {
        addRequirements(subsystem);
        server = CameraServer.getServer();
        cameras = subsystem.getCameras();
        currentIndex = 0;
	}

	@Override
	public void execute() {
        if(cameras.size() != 0) {
            server.setSource(cameras.get(currentIndex++));
            if(currentIndex == cameras.size()) {
                currentIndex = 0;
            }
        }
	}

	@Override
	public boolean isFinished() {
        return true;
    }
}