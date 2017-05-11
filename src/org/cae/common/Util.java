package org.cae.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {

	public static String zip(String path){
		try {
			File inputFile=new File(path);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(IConstant.ZIP_NAME));
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
	
	private static void zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws IOException{
		if (f.isDirectory()){
            File[] fl = f.listFiles();
            if (fl.length == 0){
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
	
}