import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.opencv.*;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.video.*;
import static org.opencv.imgproc.Imgproc.rectangle;
public class Start {

	
	
	static ImgShow imgShowOrigin = new ImgShow("Origin");
	static int picCount = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 5;
			@Override
			public void run() {
				System.out.println(i--);
				if(i < 0) {
					timer.cancel();
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					openCamera();
				}
			}
		}, 0, 1000);
		

		/*while(true) {
			capture.read(mat);
			showResults(mat);
		}*/
		
	}
	
	
	public static void openCamera() {
		

		VideoCapture videoCapture = new VideoCapture(0);
		Mat frame = new Mat();


		videoCapture.read(frame);
		ThreadCapture capThread = new Start().new ThreadCapture(videoCapture);
		capThread.start();
		
		
		System.out.println("Enter A to capture or any to stop...");
		Scanner scanner = new Scanner(System.in);
		while(true) {
			
			String input = scanner.nextLine();
			/*
			 * 
			 * A is the key used, we can change it to something else later. 
			 * 
			 * 
			 * */
			if("A".equalsIgnoreCase(input)) {
			
				System.out.println("Image captured!");
				videoCapture.read(frame);
				Imgcodecs.imwrite("C:\\img\\" + "IMG" + picCount + ".png", frame);
				picCount++;
				
			}
		
		
			
		}


	}
	
	public static void showResults(Mat mat) {
		
		Imgproc.resize(mat, mat, new Size(640, 480));
		MatOfByte matOfByte = new MatOfByte();
		Imgcodecs.imencode(".jpg", mat, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		     JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	class ThreadCapture extends Thread{
		VideoCapture camera;
		boolean isStop = false;
		boolean isCapture = false;

		public ThreadCapture(VideoCapture videoCapture) {
			this.camera = videoCapture;
		}
		@Override
		public void run() {
			Mat frame = new Mat();
			while(!isStop) {
				//System.out.println(imgShowOrigin.getTF());
				if(camera.read(frame)) {
					if(isCapture) {
						//isCapture = false;

						//Imgcodecs.imwrite("C:\\img\\" + "1.png", frame);
						
					}
					imgShowOrigin.show(frame);
					isCapture = false;
				}
				
			}
			imgShowOrigin.frame.setVisible(false);
			imgShowOrigin.frame.dispose();
			camera.release();
			
		}
		public void setStop(boolean stop) {
			isStop = stop;
		}
	}
}
