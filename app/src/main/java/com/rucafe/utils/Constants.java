package com.rucafe.utils;

/**
 * Global Constants to be used throughout the Project
 *
 * @author Reagan McFarland, Vatche Kafafian
 */
public class Constants {
    public static final String CURRENCY_FORMAT_STRING = "$%,.2f";
    public static final int FIRST_SPINNER_ITEM = 0;
    public static final double SALES_TAX_RATE = 0.06625;
    public static final Integer[] QUANTITY_SPINNER_VALUES = new Integer[]{1,2,3,4,5,6,7,8,9};

    // Order constants
    public static final String ORDER_STRING_DELIMITER = " - ";
    public static final String ORDER_TOTAL_PRICE_PREFIX_STR = "Total Price = ";

    // Donut specific constants
    public static final double DONUT_TYPE_YEAST_PRICE = 1.39;
    public static final double DONUT_TYPE_CAKE_PRICE = 1.59;
    public static final double DONUT_TYPE_HOLE_PRICE = 0.33;
    public static final String DONUT_STRING_DELIMITER = ":";
    public static final Integer[] DONUT_QUANTITY_SPINNER_VALUES = new Integer[]{1,2,3,4,5,6,7,8,9};

    // Coffee specific constants
    public static final int MAX_UNIQUE_ADDIN_COUNT = 5; // ex: maximum of 5 creams
    public static final Integer[] COFFEE_ADDIN_SPINNER_VALUES = new Integer[]{1,2,3,4,5,6,7,8,9};
    public static final double COFFEE_ADDIN_COST = 0.2;
    public static final double COFFEE_BASE_PRICE = 1.99;
    public static final double COFFEE_SIZE_SHORT_PRICE = COFFEE_BASE_PRICE;
    public static final double COFFEE_SIZE_TALL_PRICE = COFFEE_BASE_PRICE + 0.5;
    public static final double COFFEE_SIZE_GRANDE_PRICE = COFFEE_BASE_PRICE + 1.0;
    public static final double COFFEE_SIZE_VENTI_PRICE = COFFEE_BASE_PRICE + 1.5;
    public static final String COFFEE_STRING_DELIMITER = ":";
}