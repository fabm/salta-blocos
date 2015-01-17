package com.neet.blockbunny.handlers;

public interface B2DVars {
	
	// pixels per meter
	public static final float PPM = 100;
	
	// collision bit filters
	public static final short BIT_PLAYER = 2;
	public static final short BIT_RED_BLOCK = 4;
	public static final short BIT_GREEN_BLOCK = 8;
	public static final short BIT_BLUE_BLOCK = 16;
	public static final short BIT_CRYSTAL = 32;
	public static final short BIT_SPIKE = 64;
	
}