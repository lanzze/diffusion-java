package com.aslan.module.cipher.infos;

import com.aslan.module.cipher.CipherInfo;
import com.aslan.module.cipher.FileInfoHandler;
import com.aslan.module.cipher.FileInfoHandlerFactory;
import com.aslan.module.core.DiffusionException;
import com.aslan.module.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class SeriesFileInfoHandlerFactory implements FileInfoHandlerFactory {
    protected FileInfoHandlerFactory next;
    protected SortedMap<Integer, Class<? extends FileInfoHandler>> map = new TreeMap<>();

    public SeriesFileInfoHandlerFactory(FileInfoHandlerFactory next) {
        this.next = next;
    }

    public int write(FileChannel channel, CipherInfo info) throws Exception {
        byte[] header = getHeader();
        int length = header.length;
        Class<? extends FileInfoHandler> cl = form(info.version);
        if (cl == null) {
            throw new DiffusionException("");
        }
        FileInfoHandler handler = cl.newInstance();
        byte[] buf = new byte[length()];
        info.version = handler.version();
        return handler.write(channel, info) + length;
    }

    public int read(FileChannel channel, CipherInfo info) throws Exception {
        byte[] header = getHeader();
        int HL = header.length;
        ByteBuffer buf = ByteBuffer.allocate(length() + 1);
        channel.read(buf, channel.size() - length() + 1);
        if (!Utils.compare(buf.array(), 1, header, 0, HL)) {
            if (next != null) {
                return this.next.read(channel, info);
            }
            throw new DiffusionException("不支持的信息头，或许不是本系列软件加密的，亦或者本软件版本较低！");
        }
        int version = buf.get(0);
        Class<? extends FileInfoHandler> cl = form(version);
        if (cl == null) throw new DiffusionException("在DC3.14系列，不支持该信息头版本号{}，或许是本软件版本较低！");
        FileInfoHandler handler = cl.newInstance();
        return handler.read(channel, info) + HL;
    }

    protected Class<? extends FileInfoHandler> form(int version) {
        if (version == 0) version = map.firstKey();
        return map.get(version);
    }

    protected abstract byte[] getHeader();
}