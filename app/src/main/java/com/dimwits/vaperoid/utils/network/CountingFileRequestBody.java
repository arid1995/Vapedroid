package com.dimwits.vaperoid.utils.network;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.Util;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by farid on 5/22/17.
 */

class CountingFileRequestBody extends RequestBody {

    private static final int SEGMENT_SIZE = 8 * 1024; // okio.Segment.SIZE

    private final File file;
    private final ProgressListener listener;
    private final String contentType;

    CountingFileRequestBody(File file, String contentType, ProgressListener listener) {
        this.file = file;
        this.contentType = contentType;
        this.listener = listener;
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(file);
            long total = 0;
            long read;

            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
                total += read;
                sink.flush();
                this.listener.transferred((100 * total) / contentLength());
            }
        } finally {
            Util.closeQuietly(source);
        }
    }

    public interface ProgressListener {
        void transferred(long num);
    }
}