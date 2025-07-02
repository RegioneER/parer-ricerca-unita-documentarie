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

package it.eng.parer.ricerca.ud.beans.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import it.eng.parer.ricerca.ud.beans.exceptions.AppGenericRuntimeException;
import it.eng.parer.ricerca.ud.beans.exceptions.ErrorCategory;
import it.eng.parer.ricerca.ud.beans.utils.codec.Base64UrlDecodeInputStream;
import it.eng.parer.ricerca.ud.beans.utils.codec.Base64UrlEncodeOutputStream;

public class PageTokenUtils {

    private PageTokenUtils() {
	throw new IllegalStateException("Utility class");
    }

    public static String compressAndEncodeToken(final String input) {
	try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	    try (InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		    Base64UrlEncodeOutputStream base64Out = new Base64UrlEncodeOutputStream(out, -1,
			    null);
		    GZIPOutputStream zos = new GZIPOutputStream(base64Out);) {
		IOUtils.copy(inputStream, zos);
	    } catch (IOException e) {
		throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
			.cause(e).build();
	    }
	    return out.toString(StandardCharsets.UTF_8.name());
	} catch (IOException e) {
	    throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
		    .cause(e).build();
	}
    }

    public static String decodeAndDecompressToken(final String inputbase64) {
	try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	    try (InputStream inputStream = new ByteArrayInputStream(inputbase64.getBytes());
		    Base64UrlDecodeInputStream base64InputStream = new Base64UrlDecodeInputStream(
			    inputStream);
		    GZIPInputStream zis = new GZIPInputStream(base64InputStream);) {
		IOUtils.copy(zis, out);
	    } catch (IOException e) {
		throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
			.cause(e).build();
	    }
	    return out.toString(StandardCharsets.UTF_8.name());
	} catch (IOException e) {
	    throw AppGenericRuntimeException.builder().category(ErrorCategory.INTERNAL_ERROR)
		    .cause(e).build();
	}
    }
}
