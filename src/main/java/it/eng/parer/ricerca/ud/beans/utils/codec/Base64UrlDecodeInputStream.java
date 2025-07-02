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
