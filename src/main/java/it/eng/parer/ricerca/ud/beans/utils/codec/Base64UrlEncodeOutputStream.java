/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

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
