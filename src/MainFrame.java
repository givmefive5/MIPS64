import java.awt.Frame;

import javax.swing.JFrame;

public class MainFrame {

	private static JFrame frame;

	public static JFrame getInstance() {
		if (frame == null)
			buildFrame();
		return frame;
	}

	private static void buildFrame() {
		frame = new JFrame("MIPS64 Processor");
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

}
