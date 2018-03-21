package com.xulc.myzxing.zxing.decoding;

/**
 * This class provides the constants to use when sending an Intent to Barcode Scanner.
 * These strings are effectively API and cannot be changed.
 */
public final class Intents {
  private Intents() {
  }

  public static final class Scan {

    public static final String ACTION = "com.google.zxing.client.android.SCAN";
    public static final String MODE = "SCAN_MODE";
    public static final String SCAN_FORMATS = "SCAN_FORMATS";
    public static final String CHARACTER_SET = "CHARACTER_SET";
    public static final String PRODUCT_MODE = "PRODUCT_MODE";
    public static final String ONE_D_MODE = "ONE_D_MODE";
    public static final String QR_CODE_MODE = "QR_CODE_MODE";
    public static final String DATA_MATRIX_MODE = "DATA_MATRIX_MODE";
    public static final String RESULT = "SCAN_RESULT";
    public static final String RESULT_FORMAT = "SCAN_RESULT_FORMAT";
    public static final String SAVE_HISTORY = "SAVE_HISTORY";

    private Scan() {
    }
  }

  public static final class Encode {
    public static final String ACTION = "com.google.zxing.client.android.ENCODE";
    public static final String DATA = "ENCODE_DATA";
    public static final String TYPE = "ENCODE_TYPE";
    public static final String FORMAT = "ENCODE_FORMAT";

    private Encode() {
    }
  }

  public static final class SearchBookContents {
    public static final String ACTION = "com.google.zxing.client.android.SEARCH_BOOK_CONTENTS";
    public static final String ISBN = "ISBN";
    public static final String QUERY = "QUERY";

    private SearchBookContents() {
    }
  }

  public static final class WifiConnect {
	    /**
	     * Internal intent used to trigger connection to a wi-fi network.
	     */
	    public static final String ACTION = "com.google.zxing.client.android.WIFI_CONNECT";

	    /**
	     * The network to connect to, all the configuration provided here.
	     */
	    public static final String SSID = "SSID";

	    /**
	     * The network to connect to, all the configuration provided here.
	     */
	    public static final String TYPE = "TYPE";

	    /**
	     * The network to connect to, all the configuration provided here.
	     */
	    public static final String PASSWORD = "PASSWORD";

	    private WifiConnect() {
	    }
	  }


  public static final class Share {
    /**
     * Give the user a choice of items to encode as a barcode, then render it as a QR Code and
     * display onscreen for a friend to scan with their phone.
     */
    public static final String ACTION = "com.google.zxing.client.android.SHARE";

    private Share() {
    }
  }
}
