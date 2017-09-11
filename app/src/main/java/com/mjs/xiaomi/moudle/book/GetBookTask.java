package com.mjs.xiaomi.moudle.book;

import com.mjs.xiaomi.bean.Book;
import com.mjs.xiaomi.net.FormRequest;
import com.mjs.xiaomi.net.HttpMethod;

/**
 * Created by dafei on 2017/9/8.
 */
public class GetBookTask extends FormRequest {
    String isbn;
    public GetBookTask(String isbn) {
        super();
        this.isbn = isbn;
    }

    @Override
    public String getApi() {
        return "v2/book/"+isbn;
    }

    @Override
    public int getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Class getModelClass() {
        return Book.class;
    }
}
