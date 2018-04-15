package com.android.anmol.feeds_cleanarch.base.usecase;

/**
 * Created by anmolsehgal on 29-03-2018.
 */
interface UseCaseScheduler {

    void execute(Runnable runnable);

    <V extends UseCase.ResponseValue> void notifyResponse(final V response,
                                                          final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValue> void onError(
            final UseCase.UseCaseCallback<V> useCaseCallback);
}
