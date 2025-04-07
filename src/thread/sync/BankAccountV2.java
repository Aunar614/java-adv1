package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountV2 implements BankAccount{

    private int balance;

    public BankAccountV2(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public synchronized boolean withdraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());

        // == 임계 영역 시작 ==
        log("[검증 시작] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
        if (balance < amount) {
            log("[검증 실패] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
            return false;
        }

        log("[검증 완료] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
        sleep(1000);
        balance = balance - amount;
        log("[출금 완료] 출금액 : " + amount + "원, 잔액 : " + balance + "원");
        // == 임계 영역 종료 ==

        log("거래 종료");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
