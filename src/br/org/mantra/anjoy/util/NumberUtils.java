package br.org.mantra.anjoy.util;

public class NumberUtils {

	public static String formatMoney(int decimals, Double value){
		return "R$ "+String.format("%."+decimals+"f",value);

	}

}
