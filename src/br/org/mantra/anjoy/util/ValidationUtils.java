package br.org.mantra.anjoy.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;


public abstract class ValidationUtils {



	public static boolean checkBrazilianCPF(String cpf) {

		boolean ret = false;

		if(cpf == null){
			return ret;
		}

		if(cpf.length()!=11){
			return ret;
		}

		String base = "000000000";
		String digitos = "00";
		if (cpf.length() <= 11) {
			if (cpf.length() < 11) {
				cpf = base.substring(0, 11 - cpf.length()) + cpf;
				base = cpf.substring(0, 9);
			}
			base = cpf.substring(0, 9);
			digitos = cpf.substring(9, 11);
			int soma = 0, mult = 11;
			int[] var = new int[11];
			// Recebe os números e realiza a multiplicação e soma.
			for (int i = 0; i < 9; i++) {
				var[i] = Integer.parseInt("" + cpf.charAt(i));
				if (i < 9)
					soma += (var[i] * --mult);
			}
			// Cria o primeiro dígito verificador.
			int resto = soma % 11;
			if (resto < 2) {
				var[9] = 0;
			} else {
				var[9] = 11 - resto;
			}
			// Reinicia os valores.
			soma = 0;
			mult = 11;
			// Realiza a multiplicação e soma do segundo dígito.
			for (int i = 0; i < 10; i++)
				soma += var[i] * mult--;
			// Cria o segundo dígito verificador.
			resto = soma % 11;
			if (resto < 2) {
				var[10] = 0;
			} else {
				var[10] = 11 - resto;
			}
			if ((digitos.substring(0, 1).equalsIgnoreCase(new Integer(var[9])
			.toString()))
			&& (digitos.substring(1, 2).equalsIgnoreCase(new Integer(
					var[10]).toString()))) {
				ret = true;
			}
		}

		return ret;
	}






	public static String unmask(String s) {
		return s.replaceAll("[.]", "").replaceAll("[-]", "")
				.replaceAll("[/]", "").replaceAll("[(]", "")
				.replaceAll("[)]", "");
	}

	public static TextWatcher insert(final String mask, final EditText ediTxt) {
		return new TextWatcher() {
			boolean isUpdating;
			String old = "";

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String str = unmask(s.toString());
				String mascara = "";
				if (isUpdating) {
					old = str;
					isUpdating = false;
					return;
				}
				int i = 0;
				for (char m : mask.toCharArray()) {
					if (m != '#' && str.length() > old.length()) {
						mascara += m;
						continue;
					}
					try {
						mascara += str.charAt(i);
					} catch (Exception e) {
						break;
					}
					i++;
				}
				isUpdating = true;
				ediTxt.setText(mascara);
				ediTxt.setSelection(mascara.length());
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		};
	}

	public static boolean checkEmail(String email){
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}




	public static String toCamelCase(String s) {
		String[] parts = s.split(" ");
		String camelCaseString = "";
		for (String part : parts) {
			camelCaseString = camelCaseString + toProperCase(part);
		}
		return camelCaseString;
	}

	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()+" ";
	}


}
