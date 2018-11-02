package com.aslan.module.cipher.infos.dc3_14;

import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.TextInfoHandler;

import static com.aslan.module.cipher.Consts.CIPHER_OPTION.PADDING;


public class DC3_14TextInfoHandler_V1 implements TextInfoHandler {

    public int read(byte[] buf, int offset, CipherInfo info) {
        //@formatter:off
		info.options &= ~PADDING;          //subtracting padding
		info.log        = buf[offset++];
		info.diff       = buf[offset++] << 8;
		info.diff      |= buf[offset++];
		info.algorithmV = buf[offset++];  // version
		info.algorithm  = buf[offset++];
		info.keyV       = buf[offset++];  // version
		info.key        = buf[offset++];
		info.cycle      = buf[offset++];
		int  pad        = buf[offset++] & 0x1;
		info.options   |= pad==1 ? PADDING : 0;
		info.group      = 1 << info.log;
		//@formatter:on
        return length();
    }

    public int write(byte[] buf, int offset, CipherInfo info) {
        buf[offset++] = (byte) version();
        buf[offset++] = (byte) info.log;
        buf[offset++] = (byte) (info.diff >> 8 & 0xFF);
        buf[offset++] = (byte) (info.diff >> 0 & 0xFF);
        buf[offset++] = (byte) info.algorithmV;
        buf[offset++] = (byte) info.algorithm;
        buf[offset++] = (byte) info.keyV;
        buf[offset++] = (byte) info.key;
        buf[offset++] = (byte) info.cycle;
        buf[offset++] = (byte) (info.options & PADDING);
        return length();
    }

    public int length() {
        return 26;
    }

    public int version() {
        return 1;
    }
}