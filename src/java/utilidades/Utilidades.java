package utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utilidades {

    private static final SecureRandom RANDOM = new SecureRandom();

    private Utilidades() {
    }

    public static String hashPassword(String password) {
        if (password == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static Double calcularImc(Double peso, Double estatura) {
        if (peso == null || estatura == null || peso <= 0 || estatura <= 0) {
            return null;
        }
        return redondear(peso / (estatura * estatura));
    }

    public static String clasificarImc(Double imc) {
        if (imc == null) {
            return "Sin clasificacion";
        }
        if (imc < 18.5) {
            return "Bajo peso";
        }
        if (imc < 25.0) {
            return "Normal";
        }
        if (imc < 30.0) {
            return "Sobrepeso";
        }
        return "Obesidad";
    }

    public static String generarCodigoAcceso() {
        return String.format("%04d", RANDOM.nextInt(10000));
    }

    public static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static Double redondear(Double valor) {
        if (valor == null) {
            return null;
        }
        return Math.round(valor * 100.0) / 100.0;
    }
}
