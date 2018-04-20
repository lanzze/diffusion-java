package com.aslan.module.cipher.keys;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;

public class FixedKey_V1 extends MixKey {

	public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
		super.init(cipherInfo, algorithmInfo);
		this.init_key(cipherInfo.key);
		this.update_key();
		this.update_key();
		this.update_key();
	}

	@Override
	public int level() {
		return 1;
	}

	@Override
	public int version() {
		return 1;
	}
}