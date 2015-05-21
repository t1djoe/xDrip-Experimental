package com.eveningoutpost.dexdrip.utils;

import android.os.Environment;

import java.io.File;

class FileUtils {

	public static boolean makeSureDirectoryExists( final String dir ) {
		final File file = new File( dir );
        return file.exists() || file.mkdirs();
	}

	public static String getExternalDir() {

		final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xdrip";
		return dir;
	}

	public static String combine( final String path1, final String path2 ) {
		final File file1 = new File( path1 );
		final File file2 = new File( file1, path2 );
		return file2.getPath();
	}
}
