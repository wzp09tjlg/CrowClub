package com.sina.crowclub.utils;

import com.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created by wu on 2016/8/20.
 */
public class KeySingnature  implements Key {
    private int currentVersion;

    @Override
    public boolean equals(Object o) {
        if (o instanceof KeySingnature) {
            KeySingnature other = (KeySingnature) o;
            return currentVersion == other.currentVersion;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return currentVersion;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update(ByteBuffer.allocate(Integer.SIZE)
                .putInt(1).array());
    }
}

/*public class IntegerVersionSignature implements Key {
    private int currentVersion;
    public IntegerVersionSignature(int currentVersion) {
        this.currentVersion = currentVersion;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof IntegerVersionSignature) {
            IntegerVersionSignature other = (IntegerVersionSignature) o;
            return currentVersion = other.currentVersion;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return currentVersion;
    }
    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        messageDigest.update(ByteBuffer.allocate(Integer.SIZE)
                .putInt(signature).array());
    }
}*/
