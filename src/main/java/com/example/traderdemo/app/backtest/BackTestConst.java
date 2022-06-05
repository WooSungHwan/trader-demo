package com.example.traderdemo.app.backtest;

public class BackTestConst {

    private BackTestConst() { }

    // 백테스트 매매 시작 잔고
    public static final double START_BALANCE = 1_000_000.0d;

    // 백테스트시 운영할 지갑 슬롯
    public static final int BID_SLOT = 1;

    // 백테스트 스탑로스(-2% 이하로 떨어지면 강제매도)
    public static int STOP_LOSS = -2;

}
