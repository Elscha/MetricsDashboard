package net.ssehub.metricsDB;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
	public static final String[] SIZES = {"B", "KB", "MB", "GB", "TB"};
	public static final DecimalFormat FLOAT_FORMAT = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
	public static final DecimalFormat BIG_INT_FORMAT = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);
	
	static {
		DecimalFormatSymbols symbols = BIG_INT_FORMAT.getDecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		BIG_INT_FORMAT.setDecimalFormatSymbols(symbols);
		
		FLOAT_FORMAT.applyPattern("0.0#");
	}

}
