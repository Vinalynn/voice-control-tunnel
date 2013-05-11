package org.vinalynn.voicectrl.util;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: 下午4:56
 */
public class HttpUtils {

    public static String dump(InputStream is, String encoding) throws IOException {
        return IOUtils.toString(
                new BufferedReader(new InputStreamReader(is, encoding)));
    }
}
