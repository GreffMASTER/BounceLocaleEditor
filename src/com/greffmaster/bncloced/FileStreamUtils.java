package com.greffmaster.bncloced;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FileStreamUtils {
	
	// Reads 2 bytes from stream and forms them into short variable
	public static short readShort(FileInputStream stream, boolean bigEndian) throws IOException
	{
		byte a = (byte) stream.read();
		byte b = (byte) stream.read();
		if(bigEndian) return (short) (256 * a + b);
		return (short) (256 * b + a);	
	}
	
	// Writes 2 bytes to stream from short variable
	public static void writeShort(FileOutputStream stream, short val, boolean bigEndian) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.putShort(val);
		byte bytes[] = buffer.array();
		if(bigEndian)
		{
			stream.write(bytes[0]);
			stream.write(bytes[1]);
			return;
		}
		stream.write(bytes[1]);
		stream.write(bytes[0]);
	}
	
	// Writes string to stream (without terminator)
	public static void writeString(FileOutputStream stream, String str) throws IOException
	{
		for(char letter: str.toCharArray())
		{
			stream.write((byte)letter);
		}
	}
}
