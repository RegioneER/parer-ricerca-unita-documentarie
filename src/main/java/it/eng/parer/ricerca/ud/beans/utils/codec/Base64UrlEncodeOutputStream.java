package it.eng.parer.ricerca.ud.beans.utils.codec;

import java.io.OutputStream;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodecOutputStream;

/**
 * Classe che estende {@link BaseNCodecOutputStream} delle librerie commons-codec di Apache e
 * permette di creare uno stream output che codifica un generico {@link OutputStream} in un Base64
 * URL-SAFE.
 *
 */
public class Base64UrlEncodeOutputStream extends BaseNCodecOutputStream {

    public Base64UrlEncodeOutputStream(final OutputStream outputStream) {
	this(outputStream, true);
    }

    private Base64UrlEncodeOutputStream(final OutputStream outputStream, final boolean doEncode) {
	super(outputStream, new Base64(true), doEncode);
    }

    public Base64UrlEncodeOutputStream(final OutputStream outputStream, final int lineLength,
	    final byte[] lineSeparator) {
	super(outputStream, new Base64(lineLength, lineSeparator, true), true);
    }

    public Base64UrlEncodeOutputStream(final OutputStream outputStream, final int lineLength,
	    final byte[] lineSeparator, final CodecPolicy decodingPolicy) {
	super(outputStream, new Base64(lineLength, lineSeparator, true, decodingPolicy), true);
    }
}
