package com.tmlab.erp.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * Login verification code tool class
 */
public class RandImageUtil {

	public static final String key = "JEECG_LOGIN_KEY";

	/**
	 * Define graphic size
	 */
	private static final int width = 105;
	/**
	 * Define graphic size
	 */
	private static final int height = 35;

	/**
	 * Define the number of interference lines
	 */
	private static final int count = 200;

	/**
	 * Length of interference line=1.414*lineWidth
	 */
	private static final int lineWidth = 2;

	/**
	 * Image Format
	 */
	private static final String IMG_FORMAT = "JPEG";

	/**
	 * base64 image prefix
	 */
	private static final String BASE64_PRE = "data:image/jpg;base64,";

	/**
	 * Return the picture directly through the response
	 * 
	 * @param response
	 * @param resultCode
	 * @throws IOException
	 */
	public static void generate(HttpServletResponse response, String resultCode) throws IOException {
		BufferedImage image = getImageBuffer(resultCode);
		// output image to page
		ImageIO.write(image, IMG_FORMAT, response.getOutputStream());
	}

	/**
	 * Generate base64 string
	 * 
	 * @param resultCode
	 * @return
	 * @throws IOException
	 */
	public static String generate(String resultCode) throws IOException {
		BufferedImage image = getImageBuffer(resultCode);

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		// write to the stream
		ImageIO.write(image, IMG_FORMAT, byteStream);
		// convert to bytes
		byte[] bytes = byteStream.toByteArray();
		// Convert to base64 string
		String base64 = Base64.getEncoder().encodeToString(bytes).trim();
		base64 = base64.replaceAll("\n", "").replaceAll("\r", "");// remove \r\n

		// write to the specified location
		// ImageIO.write(bufferedImage, "png", new File(""));

		return BASE64_PRE + base64;
	}

	private static BufferedImage getImageBuffer(String resultCode) {
		// create image in memory
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// get graphics context
		final Graphics2D graphics = (Graphics2D) image.getGraphics();
		// set background color
		graphics.setColor(Color.WHITE); // ---1
		graphics.fillRect(0, 0, width, height);
		// set border color
// graphics.setColor(getRandColor(100, 200)); // ---2
		graphics.drawRect(0, 0, width - 1, height - 1);

		final Random random = new Random();
		// Randomly generate interference lines, so that the authentication code in the
		// image is not easy to be detected by other programs
		for (int i = 0; i < count; i++) {
			graphics.setColor(getRandColor(150, 200)); // ---3

			final int x = random.nextInt(width - lineWidth - 1) + 1; // Make sure to draw within the border
			final int y = random.nextInt(height - lineWidth - 1) + 1;
			final int xl = random.nextInt(lineWidth);
			final int yl = random.nextInt(lineWidth);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
		// Get the randomly generated authentication code
		for (int i = 0; i < resultCode.length(); i++) {
			// Display the authentication code in the image, the color of calling the
			// function is the same, maybe because the seeds are too close, so it can only
			// be generated directly
			// graphics.setColor(new Color(20 + random.nextInt(130), 20 + random
			// .nextInt(130), 20 + random.nextInt(130)));
			// set font color
			graphics.setColor(Color.BLACK);
			// set font style
// graphics.setFont(new Font("Arial Black", Font.ITALIC, 18));
			graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
			// Set characters, character spacing, top margin
			graphics.drawString(String.valueOf(resultCode.charAt(i)), (23 * i) + 8, 26);
		}
		// image takes effect
		graphics.dispose();
		return image;
	}

	private static Color getRandColor(int fc, int bc) { // Get a random color in a given range
		final Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}

		final int r = fc + random.nextInt(bc - fc);
		final int g = fc + random.nextInt(bc - fc);
		final int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}