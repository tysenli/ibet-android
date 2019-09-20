package com.app.android.ibet.api

import com.app.android.ibet.BuildConfig

public final class URLs {
    companion object {
        val SIGNUP_URL = BuildConfig.BASE_URL + "/users/api/signup/"
        val ONE_CLICK_SIGNUP_URL = BuildConfig.BASE_URL + "/users/api/oneclicksignup/"
        val GENERATE_CODE = BuildConfig.BASE_URL + "/users/api/generateactivationcode/"
        val VERI_CODE = BuildConfig.BASE_URL + "/users/api/verifyactivationcode/"
        val LOGIN = BuildConfig.BASE_URL + "/users/api/login/"
        val FORGET_CODE = BuildConfig.BASE_URL + "/users/api/generatepasswordcode/"
        val FORGET_SEND_EMAIL = BuildConfig.BASE_URL + "/users/api/sendresetpasswordcode/"
        val VERI_PASS_CODE = BuildConfig.BASE_URL + "/users/api/verifyresetpasswordcode/"
        val CHANGE_PASS = BuildConfig.BASE_URL + "/users/api/validateandresetpassword/"
        val USER = BuildConfig.BASE_URL + "/users/api/user/"
        val BALANCE = BuildConfig.BASE_URL + "/users/api/addorwithdrawbalance/"
        val VERI_SIGNUP = BuildConfig.BASE_URL + "/users/api/sendemail/?case=signup"
        val SETLIMIT = BuildConfig.BASE_URL + "/users/api/set-limitations/"
        val REMOVELIMIT = BuildConfig.BASE_URL + "/users/api/delete-limitation/"
        val CANCELLIMIT = BuildConfig.BASE_URL + "/users/api/cancel-delete-limitation/"
        val GETLIMIT = BuildConfig.BASE_URL + "/users/api/get-limitations/?id="
        val LOCKTIME = BuildConfig.BASE_URL + "/users/api/set-block-time/"
        val MARKETING = BuildConfig.BASE_URL + "/users/api/marketing-settings/"
        val PRIVACY = BuildConfig.BASE_URL + "/users/api/privacy-settings/"
        val ACTIVITY = BuildConfig.BASE_URL + "/users/api/activity-check/"

        val PAYPAL = BuildConfig.BASE_URL + "/accounting/api/paypal/create_payment"
        val PAYPAL_ORDER = BuildConfig.BASE_URL + "/accounting/api/paypal/get_order"
        val QAICASH = BuildConfig.BASE_URL + "/accounting/api/qaicash/submit_deposit"
        val QAICASH_CONFIRM = BuildConfig.BASE_URL + "/accounting/api/qaicash/get_transaction_status"
        val WITHDRAW = BuildConfig.BASE_URL + "/accounting/api/qaicash/submit_payout"
        val WITHDRAW_ORDER = BuildConfig.BASE_URL + "/accounting/api/qaicash/payout_transaction"
        val LINE = BuildConfig.BASE_URL + "/accounting/api/linepay/reserve_payment"
        val ASIAPAY = BuildConfig.BASE_URL + "/accounting/api/asiapay/deposit"
        val ASIAPAY_CONFIRM = BuildConfig.BASE_URL + "/accounting/api/asiapay/orderStatus"
        val ASTROPAY = BuildConfig.BASE_URL + "/accounting/api/astropay/capture_transaction"
        val PAYZOD = BuildConfig.BASE_URL + "/accounting/api/payzod/deposit"
        val TRANSACTION_RECODE = BuildConfig.BASE_URL + "/accounting/api/transactions/get_transactions?userid="
        val Fgate = BuildConfig.BASE_URL + "/accounting/api/fgate/chargeCard"
        val Help2pay = BuildConfig.BASE_URL + "/accounting/api/help2pay/deposit"
        val Help2pay_CONFIRM = BuildConfig.BASE_URL + "/accounting/api/help2pay/deposit_status"
        val Circlepay = BuildConfig.BASE_URL + "/accounting/api/circlepay/deposit"
        val Scratch = BuildConfig.BASE_URL + "/accounting/api/scratchcard/deposit"

    }

}
