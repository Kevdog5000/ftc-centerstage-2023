package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class Auto {
    HardwareMap hardwareMap;
    Telemetry telemetry;
    OpenCvCamera camera;
    static CamDetector camDetector;
    public CamDetector.Side sleeveSide;

    public Auto(Telemetry telemetry) {
        this.telemetry = telemetry;
        this.camDetector = new CamDetector(telemetry);
    }

    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        telemetry.addLine("autonomous API is initializing");
        telemetry.addLine("camera is initializing");

        int cameraMonitorViewId = hardwareMap.appContext
                .getResources()
                .getIdentifier(
                        "cameraMonitorViewId",
                        "id",
                        hardwareMap.appContext.getPackageName()
                );
        camera =
                OpenCvCameraFactory
                        .getInstance()
                        .createInternalCamera(
                                OpenCvInternalCamera.CameraDirection.BACK,
                                cameraMonitorViewId
                        );
        camera.setPipeline(camDetector);
        camera.openCameraDeviceAsync(
                new OpenCvCamera.AsyncCameraOpenListener() {
                    @Override
                    public void onOpened() {
                        camera.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
                    }

                    @Override
                    public void onError(int errorCode) {
                        telemetry.addLine("ERROR: pipeline failed #" + errorCode);
                    }
                }
        );

        telemetry.addLine("camera initialized");
        telemetry.addLine("PRESS START");
    }

    public void readSleeve() {
        sleeveSide = camDetector.getSide();

        stopCam();

        telemetry.clearAll();
        telemetry.addLine("side detected " + sleeveSide.name());
        telemetry.update();
    }

    public void stopCam() {
        camera.stopStreaming();
    }

}
