package com.bt.andy.sanlianASxcx.utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author WHD
 *
 */
public class DESECBUtils {
	static SecretKeyFactory secretKeyFactory = null;
	// Cipher 的“算法/模式/填充”
	static final String CIPHER = "DES/ECB/PKCS5Padding";
	//	static final String CIPHER = "DES/CBC/PKCS5Padding";
	static {
		try {
			// 在静态代码块中获取秘钥工程
			secretKeyFactory = SecretKeyFactory.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	// 定义常量 ，编码格式
	private static final String UTF8 = "utf-8";

	/*
	 * 对象缓存的容器
	 */
	static abstract class Cache {
		private final Map innerCache = new HashMap();

		protected abstract Object createValue(Object key) throws Exception;

		public Object get(Object key) throws Exception {
			Object value;
			synchronized (innerCache) {
				value = innerCache.get(key);
				if (value == null) {
					value = new CreationPlaceholder();
					innerCache.put(key, value);
				}
			}
			if (value instanceof CreationPlaceholder) {
				synchronized (value) {
					CreationPlaceholder progress = (CreationPlaceholder) value;
					if (progress.value == null) {
						progress.value = createValue(key);
						synchronized (innerCache) {
							innerCache.put(key, progress.value);
						}
					}
					return progress.value;
				}
			}
			return value;
		}

		static final class CreationPlaceholder {
			Object value;
		}
	}

	/*
	 * hex->str & str->hex
	 */
	public static byte[] stringToHex(String ss) {
		// 字符串转化we
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	public static String hexToString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2) {
				hexString.append("0");
			}
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	private static byte[] _convertKeyIv(String text) throws IOException {
		if (text.length() == 8) {
			return text.getBytes(UTF8);
		}
		if (text.startsWith("0x") && text.length() == 32) {
			byte[] result = new byte[8];
			for (int i = 0; i < text.length(); i += 2) {
				if (text.charAt(i++) == '0' && text.charAt(i++) == 'x') {
					try {
						result[i / 4] = (byte) Integer.parseInt(text.substring(i, i + 2), 16);
					} catch (Exception e) {
						throw new IOException("TXT '" + text + "' is invalid!");
					}
				}
			}
			return result;
		}
		throw new IOException("TXT '" + text + "' is invalid!");
	}

	/*
	 * SecretKey & IvParameterSpec的缓存
	 */
	private static Cache SecretKeySpecs = new Cache() {
		protected Object createValue(Object key) throws Exception {
			SecretKey secretKeyObj = null;
			try {
				secretKeyObj = secretKeyFactory.generateSecret(new DESKeySpec(_convertKeyIv((String) key)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return secretKeyObj;
		}
	};
	private static Cache IvParamSpecs = new Cache() {
		protected Object createValue(Object key) throws Exception {
			IvParameterSpec ivObj = null;
			ivObj = new IvParameterSpec(_convertKeyIv((String) key));
			return ivObj;
		}
	};

	/*
	 * 加密&解密
	 */
	public static String encrypt(String text, String authKey) {
		SecretKey secretKeyObj = null;
		try {
			secretKeyObj = (SecretKey) SecretKeySpecs.get(authKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] data = null;
		try {
			data = text.getBytes(UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] authToken = null;
		try {
			authToken = encrypt(data, secretKeyObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexToString(authToken);
	}

	public static byte[] encrypt(byte[] data, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	public static String decrypt(String hexString, String authKey) throws Exception {
		SecretKey secretKeyObj = null;
		try {
			secretKeyObj = (SecretKey) SecretKeySpecs.get(authKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String text = decrypt(hexString, secretKeyObj);
		return text;
	}

	public static String decrypt(String message, SecretKey secretKey) throws Exception {
		byte[] data = stringToHex(message);
		return decrypt(data, secretKey);
	}

	public static String decrypt(byte[] data, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] retByte = cipher.doFinal(data);
		return new String(retByte);
	}

	public static void main(String[] args) throws Exception {
		// long begin= new Date().getTime();
		String authKey = "90908080";
		String authIv = "abcdhijk";
		String text = "90908080";
		//text="{\"name\":\"田鸡\",\"age\":\"10\"}";
		// 140CB412BA03869F
		// 140cb412ba03869f
		System.out.println("原文"+text);
		// 对原文进行加密
		String encryptedText = encrypt(text, authKey);
		System.out.println("加密后:" + encryptedText);
		// 对密文进行还原
		//encryptedText="690f2e6e6df2a330cf7a03c45b5aeaf3818da84b6f3dc9bfc222e8dd4901f94fb8d8f3044b2a911c5335dd8841159aa129b0f0e7c20696d5e1602ec4f3d0654c27352844d20ba2e839806bac11108fd116e700e293774e3a6198e7d259d0f12ba141e9749f50fa4ddc4eab029bc8f758a59f04828be89b512129d8ca28cddac9b85382f33bb3fdf0bb30e0cdc2941ca963e57e21a49b2d41765d0322fcfb3a23b0675e8540888f6672c08d93e533efac6d5f0233a3fae3a992c8b2370bd6e45a64f9150e229f787741a0ca5b98629eeeb51603bf374cfbe1d80dc6f5d2c443b701b50e3c644765304b9c3b2cdcec0ebbceee7aaa00124c38610a12d2736c6548ddf6cd4098e2a24a28b2e01ea6326447341c75e077df140ea1bb53ec1175a86d46b7f6a4da9388d7a21b2f05c22f9a9e90214d0f24b4aeee05441f98bb591efa6dbde308fad9f8b104061fc62de32f39efea394a6e335e2652444148374f4ab129c7a3cd704587163b6428e96c7a1e0f8162bbfbb1fd5f909f0db16a0ebdbdbb995d7f417268e4e1aad4d7e9e149cf2cc3ffd3365eb59c00042d6cae1aa50f6999f65f050b0200e05d4dc6256c355e1271b257adbc3244073425ff527077af4b57a92d23b43f30f326bea0bf00669f384e7d97a5b9a765c7e22237662d39eef643c91694289ad90a32196b67839ae6d0faad6cf9a52f52126c6b0cb90c5a0c81829179582c685718944435e84941d81cf7d29de4675143df14297ac617282656c1d28e763361024ed45e8733a3bdab861bcf2a0d9a8a30462a21c7ed2d421ce87c834971e78d3b36691baf5e29300b64c334a51006292a6ac45789470de2c019b4acd952b2a31684e0d5679eb6371a1fa9f98f4531525a1ba547221992bfd0d70170821f7752e733ec5bb4fefe89eca4841c1f0ee5c39052fee224742429d605c35d8e9e289f5ed821aa7a4074509c40a68cee88f6fac6cc64bccb901864756ea796ad57719974e775bbdd34c2a74c82f34340aeee08e6c75541b967da437394598b4d53dba921743ac7708f42536d71a63b0b01fcba6ede958132e63aeb40264df1d5191a1437ee4501984ffccf56f49fc19c49fb6c2a961207fe6b2e23d323d7cd99467a5202f933fd6961c50d464a09cbff4078cdf6561300adbd7fc3b2886a577daa5a7db14b45ae5347bec17d641869addccde5848dec55378e585cda0d8865b2dd369adcf07925fcf70845acdb6b14a427dee7fd3d776617f689c3c4d5c34a4441a99bb543df7f918dc109324891ba510d3152eb76d3b5f00a36f65951a5833f6a1964356a";
		String plainText = decrypt(encryptedText, authKey);
		System.out.println("解密后:" + plainText);
		// 2a329740ce15f549be64190b183a5be2
		// long end =new Date().getTime();
		// System.out.println(end-begin);
	}
}
