package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.CheckView;
import constants.MessageConst;

/**
 * 確認サインインスタンスに設定されている値のバリデーションを行うクラス
 */
public class CheckValidator {

    /**
     * 確認サインインスタンスの各項目についてバリデーションを行う
     * @param cv 確認サインインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(CheckView cv) {
        List<String> errors = new ArrayList<String>();

        //確認サインのチェック
        String confirmError = validateConfirm(cv.getConfirm());
        if (!confirmError.equals("")) {
            errors.add(confirmError);
        }

        return errors;
    }

    /**
     * 確認サインに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param confirm 確認サイン
     * @return エラーメッセージ
     */
    private static String validateConfirm(String confirm) {
        if (confirm == null || confirm.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

}
