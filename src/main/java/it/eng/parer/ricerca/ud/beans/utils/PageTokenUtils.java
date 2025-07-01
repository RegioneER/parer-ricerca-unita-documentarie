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
