package org.firstinspires.ftc.teamcode.drive.competitioncode;

import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class CamDetector extends OpenCvPipeline {
    Telemetry telemetry;

    public enum Side {
        FIRST,
        SECOND,
        THIRD,
    }
    Side side;

    //region of interest (ROI); also known as the boxes
    //Here you can adjust the size of the boxes and position of them
    //Increasing the decreasing the x values moves the boxes right and left
    //The y values are flipped; increasing the y values moves the boxes down
    //This is because camera needs to be mounted upside down
    public static double ROI_WIDTH = 40;
    public static double ROI_HEIGHT = 40;
    //mess around with numbers
    public static double ROI_X_1 = 50;
    public static double ROI_Y_1 = 130;
    public static double ROI_X_2 = 165;
    public static double ROI_Y_2 = 120;
    public static double ROI_X_3 = 285;
    public static double ROI_Y_3 = 140;

    // in RGB
    // if white = good; else red = bad
    static Scalar HIGHLIGHT_BOX_COLOR = new Scalar(255, 255, 255);
    double max;

    // in HSV
    // for this year, it is programed to only detect blue and red and ignore other colors
    static Scalar HSV_LOW = new Scalar(0, 50, 40);
    static Scalar HSV_HIGH = new Scalar(180, 255, 255);

    static Rect ROI_1 = new Rect(
            new Point(ROI_X_1 - ROI_WIDTH / 2, ROI_Y_1 - ROI_HEIGHT / 2),
            new Point(ROI_X_1 + ROI_WIDTH / 2, ROI_Y_1 + ROI_HEIGHT / 2)
    );
    static Rect ROI_2 = new Rect(
            new Point(ROI_X_2 - ROI_WIDTH / 2, ROI_Y_2 - ROI_HEIGHT / 2),
            new Point(ROI_X_2 + ROI_WIDTH / 2, ROI_Y_2 + ROI_HEIGHT / 2)
    );
    static Rect ROI_3 = new Rect(
            new Point(ROI_X_3 - ROI_WIDTH / 2, ROI_Y_3 - ROI_HEIGHT / 2),
            new Point(ROI_X_3 + ROI_WIDTH / 2, ROI_Y_3 + ROI_HEIGHT / 2)
    );
    double ROI_AREA = ROI_1.area(); // use ROI_1 because they all have the area

    public CamDetector(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat mat = new Mat();

        // convert from RGB to HSV because HSV is easier to read
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        // convert all target colors to white and the rest to black
        Core.inRange(mat, HSV_LOW, HSV_HIGH, mat);

        // "cut out" the region of interest (ROI)
        Mat region1 = mat.submat(ROI_1);
        Mat region2 = mat.submat(ROI_2);
        Mat region3 = mat.submat(ROI_3);

        // calculate coverage of corresponding colors
        double coverage1 = Core.sumElems(region1).val[0] / ROI_AREA / 255;
        double coverage2 = Core.sumElems(region2).val[0] / ROI_AREA / 255;
        double coverage3 = Core.sumElems(region3).val[0] / ROI_AREA / 255;

        telemetry.clearAll();
        telemetry.addData("coverage1", Math.round(coverage1 * 100) + '%');
        telemetry.addData("coverage2", Math.round(coverage2 * 100) + '%');
        telemetry.addData("coverage3", Math.round(coverage3 * 100) + '%');
        telemetry.update();

        // max value is just compare which boxes has the highest coverage (most red or blue inside the boxes)
        max = Math.max(coverage1, Math.max(coverage2, coverage3));

        // highlight the box that has the highest max value
        // set slide to be first, second, or third
        if (coverage1 == max) {
            side = Side.FIRST;

            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);
            Imgproc.rectangle(mat, ROI_1, HIGHLIGHT_BOX_COLOR);
        } else if (coverage2 == max) {
            side = Side.SECOND;


            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);
            Imgproc.rectangle(mat, ROI_2, HIGHLIGHT_BOX_COLOR);
        } else if (coverage3 == max) {
            side = Side.THIRD;


            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);
            Imgproc.rectangle(mat, ROI_3, HIGHLIGHT_BOX_COLOR);
        }

        region1.release();
        region2.release();
        region3.release();
        // mat.release();

        return mat;
    }

    public Side getSide() {
        return side;
    }
}
