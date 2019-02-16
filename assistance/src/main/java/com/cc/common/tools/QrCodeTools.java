/**
 * 
 */
package com.cc.common.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.cc.common.exception.LogicException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author ws_yu
 *
 */
public class QrCodeTools {

	/**
	 * 创建二维码
	 * @param content 二维码内容
	 * @param size 二维码大小
	 * @param format 二维码图片格式
	 * @param outputStream 输出流
	 */
	public static void createQrCode(String content, int size, String format, OutputStream outputStream) {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
		} catch (WriterException e) {
			e.printStackTrace();
			throw new LogicException("E001", "生成二维码错误");
		} catch (IOException e) {
			e.printStackTrace();
			throw new LogicException("E002", "输出二维码错误");
		}
	}
}
