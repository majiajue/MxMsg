package com.mx.client.cache;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;



public class ImageCache extends AbstractCache<String, byte[]> {

	public ImageCache(int initialCapacity, long expirationInMinutes, int maxConcurrentThreads) {
		super("ImageCache", initialCapacity, expirationInMinutes, maxConcurrentThreads);
	}

	public synchronized void removeAllWithPrefix(String urlPrefix) {
		CacheHelper.removeAllWithStringPrefix(this, urlPrefix);
	}

	@Override
	public String getFileNameForKey(String imageUrl) {
		return CacheHelper.getFileNameFromUrl(imageUrl);
	}

	@Override
	protected byte[] readValueFromDisk(File file) throws IOException {
		BufferedInputStream istream = new BufferedInputStream(new FileInputStream(file));
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			throw new IOException("Cannot read files larger than " + Integer.MAX_VALUE + " bytes");
		}

		int imageDataLength = (int) fileSize;

		byte[] imageData = new byte[imageDataLength];
		istream.read(imageData, 0, imageDataLength);
		istream.close();

		return imageData;
	}

	public synchronized Image getBitmap(Object elementKey) {
		byte[] imageData = super.get(elementKey);
		if (imageData == null) {
			return null;
		}
		//return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
		return Toolkit.getDefaultToolkit().createImage(imageData); 
	
	}

	@Override
	protected void writeValueToDisk(File file, byte[] imageData) throws IOException {
		BufferedOutputStream ostream = new BufferedOutputStream(new FileOutputStream(file));

		ostream.write(imageData);

		ostream.close();
	}

}
