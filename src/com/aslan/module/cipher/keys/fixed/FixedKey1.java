package com.aslan.module.cipher.keys.fixed;

import com.aslan.module.cipher.AlgorithmInfo;
import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.Consts;
import com.aslan.module.cipher.keys.diffusion.DiffusionKey1;

public class FixedKey1 extends DiffusionKey1 {

	public void init(CipherInfo cipherInfo, AlgorithmInfo algorithmInfo) {
		super.init(cipherInfo, algorithmInfo);
		this.update();
		this.update();
	}

	@Override
	public int identity() {
		return Consts.KEYS.FIXED;
	}

	@Override
	public int version() {
		return 1;
	}
}