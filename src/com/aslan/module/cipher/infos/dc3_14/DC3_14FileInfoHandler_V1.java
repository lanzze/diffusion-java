package com.aslan.module.cipher.infos.dc3_14;

import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.TextInfoHandler;

import static com.aslan.module.cipher.Consts.CIPHER_OPTION.ENCRYPT_FILENAME;
import static com.aslan.module.cipher.Consts.CIPHER_OPTION.PADDING;


public class DC3_14FileInfoHandler_V1 implements TextInfoHandler {

    //@formatter:off
    public int read(byte[] buf, int offset, CipherInfo info) {
		info.options   &= ~(PADDING | ENCRYPT_FILENAME);          //subtracting padding
		info.log        = buf[offset++];
		info.diff       = buf[offset++] << 8;
		info.diff      |= buf[offset++];
		info.algorithmV = buf[offset++];  // version
		info.algorithm  = buf[offset++];
		info.levelV     = buf[offset++];  // version
		info.level      = buf[offset++];
		info.cycle      = buf[offset++];
		info.group      = 1 << info.log;

		int options     = buf[offset++]<<8 | buf[offset];
		info.options   |= (options & 1 << 0) == 0 ? 0: PADDING;
		info.options   |= (options & 1 << 1) == 0 ? 0: ENCRYPT_FILENAME;

        return length();
    }

    public int write(byte[] buf, int offset, CipherInfo info) {
        buf[offset++]   = (byte) version();
        buf[offset++]   = (byte) info.log;
        buf[offset++]   = (byte) (info.diff >> 8 & 0xFF);
        buf[offset++]   = (byte) (info.diff >> 0 & 0xFF);
        buf[offset++]   = (byte) info.algorithmV;
        buf[offset++]   = (byte) info.algorithm;
        buf[offset++]   = (byte) info.levelV;
        buf[offset++]   = (byte) info.level;
        buf[offset++]   = (byte) info.cycle;
        short options   = 0;
        options        |= (info.options & PADDING) == 1 ? 1 << 0 : 0;
        options        |= (info.options & ENCRYPT_FILENAME) == 1 ? 1 << 1 : 0;
        buf[offset++]   = (byte) (options >> 8 & 0xFF);
        buf[offset++]   = (byte) (options >> 0 & 0xFF);
        return length();
    }

    public int length() {
        return 26;
    }

    public int version() {
        return 1;
    }
}