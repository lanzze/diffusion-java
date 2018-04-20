package com.aslan.module.cipher.infos;

import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.TextInfoHandler;
import com.aslan.module.cipher.TextInfoHandlerFactory;
import com.aslan.module.core.DiffusionException;
import com.aslan.module.utils.Utils;

import java.util.SortedMap;
import java.util.TreeMap;

public abstract class SeriesTextInfoHandlerFactory implements TextInfoHandlerFactory {
    protected TextInfoHandlerFactory next;
    protected SortedMap<Integer, Class<? extends TextInfoHandler>> map = new TreeMap<>();

    public SeriesTextInfoHandlerFactory(TextInfoHandlerFactory next) {
        this.next = next;
    }

    public int write(byte[] buf, int offset, CipherInfo info) throws Exception {
        byte[] header = getHeader();
        int length = header.length;
        System.arraycopy(header, 0, buf, 0, length);
        Class<? extends TextInfoHandler> cl = form(info.version);
        if (cl == null) {
            throw new DiffusionException("");
        }
        TextInfoHandler handler = cl.newInstance();
        info.version = handler.version();
        return handler.write(buf, offset + length, info) + length;
    }

    public int read(byte[] buf, int offset, CipherInfo info) throws Exception {
        byte[] header = getHeader();
        int length = header.length;
        if (!Utils.compare(buf, offset, header, 0, length)) {
            if (next != null) {
                return this.next.read(buf, offset, info);
            }
            throw new DiffusionException("不支持的信息头，或许不是本系列软件加密的，亦或者本软件版本较低！");
        }
        int version = buf[offset + length];
        Class<? extends TextInfoHandler> cl = form(version);
        if (cl == null) throw new DiffusionException("在DC3.14系列，不支持该信息头版本号{}，或许是本软件版本较低！");
        TextInfoHandler handler = cl.newInstance();
        return handler.read(buf, offset + length + 1, info) + length;
    }

    protected Class<? extends TextInfoHandler> form(int version) {
        if (version == 0) version = map.firstKey();
        return map.get(version);
    }

    protected abstract byte[] getHeader();
}