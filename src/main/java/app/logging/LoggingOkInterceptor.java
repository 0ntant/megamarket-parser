package app.logging;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class LoggingOkInterceptor implements Interceptor
{
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException
    {
        Request request = chain.request();
        Response response = chain.proceed(request);

        log.info("[{}] {}| ResponseCode: {}",
                request.method(),
                request.url(),
                response.code()
        );

        return response;
    }
}
