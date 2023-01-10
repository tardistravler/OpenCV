import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.Face;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
/**
 * 
 * @author Sea
 *
 */
public class ImgShow {
	public JFrame frame;
	JLabel label;

	public boolean FaceDetectedTF;
	
	public ImgShow() {
		FaceDetectedTF = false;
		frame = new JFrame();
		label = new JLabel();
		frame.getContentPane().add(label);
	}

	public ImgShow(String title) {
		frame = new JFrame(title);
		label = new JLabel();
		frame.getContentPane().add(label);
	}

	public boolean getTF() {
		return FaceDetectedTF;
	}
	public void show(Mat matFrame) {
		try {
			int FaceDetect = 0;
			CascadeClassifier faceDetector = new CascadeClassifier("C:\\img\\trainerComputerParts2.xml");
			//C:\img
			//C:\\Program Files (x86)\\Python38-32\\Lib\\site-packages\\cv2\\data\\haarcascade_frontalface_default.xml
			MatOfRect faceDetections = new MatOfRect();
			
			faceDetector.detectMultiScale(matFrame, faceDetections);
			
				for (Rect rect : faceDetections.toArray()) {
					//System.out.println(FaceDetectedTF);
					org.opencv.imgproc.Imgproc.rectangle(
							matFrame, 
							new Point(rect.x, rect.y), 
							new Point(rect.x + rect.width, 
									rect.y + rect.height), 
							new Scalar(0, 255, 0));
					/*Imgcodecs.imwrite("C:\\img\\" + "FaceDetect" + FaceDetect + ".png", matFrame);
					FaceDetect++;*/
				
				}
				

				
				
			
			// Imgproc.resize(matFrame, matFrame, new Size(640, 480));
			MatOfByte matOfByte = new MatOfByte();
			Imgcodecs.imencode(".png", matFrame, matOfByte);
			byte[] byteArray = matOfByte.toArray();
			BufferedImage bufImage = null;

			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			label.setIcon(new ImageIcon(bufImage));
			frame.pack();
			frame.setVisible(true);
			
			
			
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}



	public void show(Mat matFrame, int width, int height) {
		try {
			Imgproc.resize(matFrame, matFrame, new Size(width, height));
			MatOfByte matOfByte = new MatOfByte();
			Imgcodecs.imencode(".jpg", matFrame, matOfByte);
			byte[] byteArray = matOfByte.toArray();
			BufferedImage bufImage = null;

			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			label.setIcon(new ImageIcon(bufImage));
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}
}
