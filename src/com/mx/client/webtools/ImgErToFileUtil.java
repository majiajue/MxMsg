package com.mx.client.webtools;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;

/**
 * 将二进制流转换成图片文件
 * 
 * @author 马勇
 * 
 */

public class ImgErToFileUtil {

	/**
	 * 将接收的字符串转换成图片保存
	 * 
	 * @param imgStr
	 *            二进制流转换的字符串
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByStr(String imgStr, String imgPath,
			String imgName) {
		try {
			System.out.println("===imgStr.length()====>" + imgStr.length()
					+ "=====imgStr=====>" + imgStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int stateInt = 1;
		if (imgStr != null && imgStr.length() > 0) {
			try {

				// 将字符串转换成二进制，用于显示图片
				// 将上面生成的图片格式字符串 imgStr，还原成图片显示
				byte[] imgByte = hex2byte(imgStr);

				InputStream in = new ByteArrayInputStream(imgStr.getBytes());

				File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
				if (!file.exists())
					file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = in.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}
				fos.flush();
				fos.close();
				in.close();

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
			}
		}
		return stateInt;
	}

	/**
	 * 将二进制转换成图片保存
	 * 
	 * @param imgStr
	 *            二进制流转换的字符串
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByBytes(File imgFile, String imgPath,
			String imgName) {

		int stateInt = 1;
		if (imgFile.length() > 0) {
			try {
				File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
				FileOutputStream fos = new FileOutputStream(file);

				FileInputStream fis = new FileInputStream(imgFile);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = fis.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}
				fos.flush();
				fos.close();
				fis.close();

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
			}
		}
		return stateInt;
	}

	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) // 二进制转字符串
	{
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	/**
	 * @title 根据二进制字符串生成图片
	 * @param data
	 *            生成图片的二进制字符串
	 * @param fileName
	 *            图片名称(完整路径)
	 * @param type
	 *            图片类型
	 * @return
	 */
	public static void saveImage(String data, String fileName, String type) {

		BufferedImage image = new BufferedImage(300, 300,
				BufferedImage.TYPE_BYTE_BINARY);
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, byteOutputStream);
			// byte[] date = byteOutputStream.toByteArray();
			byte[] bytes = hex2byte(data);
			System.out.println("path:" + fileName);
			RandomAccessFile file = new RandomAccessFile(fileName, "rw");
			file.write(bytes);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 字符串转二进制
	 * 
	 * 
	 * 
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * 
	 * 
	 * 
	 * @return 转换后的二进制数组
	 */

	public static byte[] hex2byte(String str) { // 字符串转二进制

		if (str == null)

			return null;

		str = str.trim();

		int len = str.length();

		if (len == 0 || len % 2 == 1)

			return null;

		byte[] b = new byte[len / 2];

		try {

			for (int i = 0; i < str.length(); i += 2) {

				b[i / 2] = (byte) Integer

				.decode("0X" + str.substring(i, i + 2)).intValue();

			}

			return b;

		} catch (Exception e) {

			return null;

		}

	}

	public static void main(String[] args) throws IOException {
		saveToImgByStr(
				"1385034465168vRx:/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDACweISchHCwnJCcyLyw1Qm9IQj09QohhZlBvoY2ppp6Nm5ixx//Ysbzxv5ib3v/g8f//////rNX/////////////2wBDAS8yMkI6QoJISIL/t5u3////////////////////////////////////////////////////////////////////wAARCAGYATIDASIAA",
				"E:\\", "test.jpg");

		Image image = Toolkit
				.getDefaultToolkit()
				.createImage(
						"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDACweISchHCwnJCcyLyw1Qm9IQj09QohhZlBvoY2ppp6Nm5ixx//Ysbzxv5ib3v/g8f//////rNX/////////////2wBDAS8yMkI6QoJISIL/t5u3////////////////////////////////////////////////////////////////////wAARCAGYATIDASIAA");
		// ImageIO.write(image., ".jpg", new File("E:\\test.jpg"));
		// BufferedImage imag=ImageIO.read(new
		// ByteArrayInputStream("1385034465168vRx:/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDACweISchHCwnJCcyLyw1Qm9IQj09QohhZlBvoY2ppp6Nm5ixx//Ysbzxv5ib3v/g8f//////rNX/////////////2wBDAS8yMkI6QoJISIL/t5u3////////////////////////////////////////////////////////////////////wAARCAGYATIDASIAA".getBytes()));
		// BufferedImage bufferedImage = (BufferedImage) image;
		// BufferedImageBuilder builder = new BufferedImageBuilder();
		// builder.bufferImage(image);
		//
		// try {
		// ImageIO.write(builder.bufferImage(image), "PNG", new
		// File("E:\\yourImageName.PNG"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }//输出到 png文件

		BMPFile bmpFile = new BMPFile();
		bmpFile.saveBitmap("E:\\yourImageName.bmp", image,
				image.getWidth(null), image.getHeight(null));

	}

}
