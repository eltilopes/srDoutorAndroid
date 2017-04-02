package br.com.srdoutorandroid.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by elton on 30/03/2017.
 */

public class ContaGoogleUtil {

    private Context context;

    public ContaGoogleUtil(Context context) {
        this.context = context;
    }

    public String getEmiailID() {
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);
        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts1 = accountManager.getAccounts();
        String name;
        if (accounts1.length > 0) {
            name = accounts1[0].name;
        }
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }
}
