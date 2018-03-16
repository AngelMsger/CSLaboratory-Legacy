package com.angelmsger.cslaboratory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ProprietaryReceiver extends BroadcastReceiver {
    public ProprietaryReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: 此方法在收到新的符合条件的 intent 时被回调。
        // 此 Receiver 原本用以定义对网络状态发生改变的系统事件进行监听及处理，但在 Android 5 后系统提供了新的 JobS
        // aduler API 对此功能进行了支持，并声明在 Android N 及后续版本中 App 不应再使用 Broadcast Receiver 对
        // 网络状态进行监听，因此此处 Receiver 类留空备用。
    }
}
