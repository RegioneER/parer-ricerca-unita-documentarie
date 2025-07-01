package it.eng.parer.ricerca.ud.beans.utils.codec;

import java.io.InputStream;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodecInputStream;

public class Base64UrlDecodeInputStream extends BaseNCodecInputStream {

    public Base64UrlDecodeInputStream(final InputStream inputStream) {
	this(inputStream, false);
    }

    private Base64UrlDecodeInputStream(final InputStream inputStream, final boolean doEncode) {
	super(inputStream, new Base64(true), doEncode);
    }

    public Base64UrlDecodeInputStream(final InputStream inputStream, final int lineLength,
	    final byte[] lineSeparator) {
	super(inputStream, new Base64(lineLength, lineSeparator, true), false);
    }

    public Base64UrlDecodeInputStream(final InputStream inputStream, final int lineLength,
	    final byte[] lineSeparator, final CodecPolicy decodingPolicy) {
	super(inputStream, new Base64(lineLength, lineSeparator, true, decodingPolicy), false);
    }
}
