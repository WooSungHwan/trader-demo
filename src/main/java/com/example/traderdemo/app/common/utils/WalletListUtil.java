package com.example.traderdemo.app.common.utils;

import com.example.traderdemo.app.backtest.collection.WalletList;

public class WalletListUtil {

    private WalletListUtil() { }

    public static boolean isAskable(WalletList walletResult) {
        if (walletResult.isNotEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isBidable(WalletList walletResult) {
        if (walletResult.hasSlot()) {
            return true;
        }
        return false;
    }
}
