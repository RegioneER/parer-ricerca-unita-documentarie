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
public class Base64UrlEncodeOutputStream extends
        BaseNCodecOutputStream<Base64, Base64UrlEncodeOutputStream, Base64UrlEncodeOutputStream.Builder> {

    public static class Builder extends
            BaseNCodecOutputStream.AbstractBuilder<Base64UrlEncodeOutputStream, Base64, Builder> {

        @Override
        public Base64UrlEncodeOutputStream get() {
            return new Base64UrlEncodeOutputStream(getOutputStream(), true);
        }

        @Override
        protected Base64 newBaseNCodec() {
            return Base64.builder().setUrlSafe(true).get();
        }
    }

    public Base64UrlEncodeOutputStream(final OutputStream outputStream) {
        this(outputStream, true);
    }

    private Base64UrlEncodeOutputStream(final OutputStream outputStream, final boolean doEncode) {
        super(outputStream, Base64.builder().setUrlSafe(true).get(), doEncode);
    }

    public Base64UrlEncodeOutputStream(final OutputStream outputStream, final int lineLength,
            final byte[] lineSeparator) {
        super(outputStream, Base64.builder().setLineLength(lineLength)
                .setLineSeparator(lineSeparator).setUrlSafe(true).get(), true);
    }

    public Base64UrlEncodeOutputStream(final OutputStream outputStream, final int lineLength,
            final byte[] lineSeparator, final CodecPolicy decodingPolicy) {
        super(outputStream,
                Base64.builder().setLineLength(lineLength).setLineSeparator(lineSeparator)
                        .setUrlSafe(true).setDecodingPolicy(decodingPolicy).get(),
                true);
    }
}
