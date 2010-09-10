package com.atti;

/**
 * POJO for storing key and IV info.
 * 
 * TBD store SecretKey and IvParameterSpec?
 * Keeping these as byte[]s gives us some flexibility at the cost of clarity
 * 
 * @author nmatatall
 *
 */
public class SecretKeyWithIV {
	private byte[] key;
	private byte[] iv;

	public SecretKeyWithIV(byte[] key, byte[] iv) {
		super();
		this.key = key;
		this.iv = iv;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

}
