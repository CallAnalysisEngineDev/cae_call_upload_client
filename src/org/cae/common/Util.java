package org.cae.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;

public class Util {

	public static String zip(String path) {
		try {
			File inputFile = new File(path);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					IConstant.ZIP_NAME));
			BufferedOutputStream bo = new BufferedOutputStream(out);
			zip(out, inputFile, inputFile.getName(), bo);
			bo.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return IConstant.ZIP_NAME;
	}

	private static void zip(ZipOutputStream out, File f, String base,
			BufferedOutputStream bo) throws IOException {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/"));
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo);
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b);
			}
			bi.close();
			in.close();
		}
	}

	public static void unzip(Log logger) {
		try {
			ZipInputStream Zin = new ZipInputStream(new FileInputStream(
					IConstant.ZIP_NAME));// 输入源zip路径
			BufferedInputStream Bin = new BufferedInputStream(Zin);
			File Fout = null;
			ZipEntry entry;
			try {
				while ((entry = Zin.getNextEntry()) != null
						&& !entry.isDirectory()) {
					Fout = new File(IConstant.DOWNLOAD_HTML_PATH,
							entry.getName());
					if (!Fout.exists()) {
						(new File(Fout.getParent())).mkdirs();
					}
					FileOutputStream out = new FileOutputStream(Fout);
					BufferedOutputStream Bout = new BufferedOutputStream(out);
					int b;
					while ((b = Bin.read()) != -1) {
						Bout.write(b);
					}
					Bout.close();
					out.close();
					logger.info(Fout + "已解压");
				}
				Bin.close();
				Zin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}