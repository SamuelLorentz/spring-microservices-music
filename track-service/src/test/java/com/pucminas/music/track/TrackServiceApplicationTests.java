package com.pucminas.music.track;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.Cipher;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class TrackServiceApplicationTests {

	@Test
	public void contextLoads() throws InvalidKeySpecException, NoSuchAlgorithmException {

	    CriptografiaService.converteStringEmChavePublica("PUBLICA");
	}

}

class CriptografiaService {

	public static final String ALGORITHM = "RSA";

	/**
	 * Local da chave privada no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PRIVADA = "C:/keys/private.key";

	/**
	 * Local da chave pública no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PUBLICA = "C:/keys/public.key";

	/**
	 * Gera a chave que contém um par de chave Privada e Pública usando 1025 bytes.
	 * Armazena o conjunto de chaves nos arquivos private.key e public.key
	 */
	public static HashMap<String, String> gerarParDeChaves() {
		String chavePrivada = "";
		String chavePublica = "";
		HashMap<String, String> map = new HashMap<>();
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(2048);
			final KeyPair keyPair = keyGen.generateKeyPair();
			chavePublica = converteChavePublicaEmString(keyPair.getPublic());
			chavePrivada = converteChavePrivadaEmString(keyPair.getPrivate());
			map.put("chavePublica", chavePublica);
			map.put("chavePrivada", chavePrivada);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static String converteChavePublicaEmString(PublicKey chavePublica) {
		return Base64.getEncoder().encodeToString(chavePublica.getEncoded());
	}

	private static String converteChavePrivadaEmString(PrivateKey chavePrivada) {
		return Base64.getEncoder().encodeToString(chavePrivada.getEncoded());
	}

	public static PublicKey converteStringEmChavePublica(String chavePublica)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] chave = Base64.getDecoder().decode(chavePublica.getBytes());
		KeyFactory fact = KeyFactory.getInstance(ALGORITHM);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(chave);
		return fact.generatePublic(spec);
	}

	private static PrivateKey converteStringEmChavePrivada(String chavePrivada)
			throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
//		byte[] clear = Base64.getDecoder().decode(chavePrivada.getBytes());
//		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
//		KeyFactory fact = KeyFactory.getInstance(ALGORITHM);
//		PrivateKey privateKey = null;
//		privateKey = fact.generatePrivate(keySpec);
//		return privateKey;
		byte[] keyBytes = Base64.getDecoder().decode(chavePrivada.getBytes("utf-8"));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey key = (PrivateKey) keyFactory.generatePrivate(keySpec);
		return key;
	}

	/**
	 * Verifica se o par de chaves Pública e Privada já foram geradas.
	 */
	public static boolean verificaSeExisteChavesNoSO() {

		File chavePrivada = new File(PATH_CHAVE_PRIVADA);
		File chavePublica = new File(PATH_CHAVE_PUBLICA);

		if (chavePrivada.exists() && chavePublica.exists()) {
			return true;
		}

		return false;
	}

	public static byte[] criptografa(String texto, String chave) throws GeneralSecurityException {

		PublicKey chavePublica = converteStringEmChavePublica(chave);
		byte[] cipherText = null;

		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// Criptografa o texto puro usando a chave Púlica
			cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
			cipherText = cipher.doFinal(texto.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cipherText;
	}

	/**
	 * Decriptografa o texto puro usando chave privada.
	 * @throws UnsupportedEncodingException
	 */
	public static String decriptografa(byte[] texto, String chave) throws UnsupportedEncodingException {
		PrivateKey chavePrivada = null;
		try {
			chavePrivada = converteStringEmChavePrivada(chave);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] dectyptedText = null;

		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// Decriptografa o texto puro usando a chave Privada
			cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
			dectyptedText = cipher.doFinal(texto);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}

//	public Usuario criptografarDadosUsuario(UsuarioNewDTO usuario, String chavePublica) {
//
//	}
//
//	public Usuario descriptografarDadosUsuario(Usuario usuario, String chavePrivada) {
//		return
//	}

}
