package com.mjs.xiaomi.moudle.book;

import com.mjs.xiaomi.bean.Book;
import com.mjs.xiaomi.exception.ApiException;

import java.io.IOException;

/**
 * Created by dafei on 2017/9/8.
 */
public class GetBookAction {
    public static Book getBook(String isbn) {
        try {
            return (Book) new GetBookTask(isbn).execute().data;
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }
}
