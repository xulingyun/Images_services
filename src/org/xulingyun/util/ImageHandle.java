package org.xulingyun.util;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class ImageHandle {
	int height = 0;
	int width = 0;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public BufferedImage resize(BufferedImage source, int targetW,int targetH,int getWH) {
		// targetW，targetH分别表示目标长和宽
		int sourceH = source.getHeight();
		int sourceW = source.getWidth();
		if (sourceW <= targetW || sourceH <= targetH) {
			targetW = sourceW;
			targetH = sourceH;
		}
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / sourceW;
		double sy = (double) targetH / sourceH;
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		} else {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		}
		if(getWH==1){
			width = targetW;
			height = targetH;
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int width, int hight,int getWH) throws Exception {
		BufferedImage srcImage;
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".png")) {
			imgType = "PNG";
		}
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		try {
			srcImage = ImageIO.read(fromFile);
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight,getWH);
			}
			ImageIO.write(srcImage, imgType, saveFile);
		} catch (IOException e) {
			UseLog4j.info(this.getClass(), "图片保存失败==》"+e.getStackTrace());
			e.printStackTrace();
		}
		
	}
}
