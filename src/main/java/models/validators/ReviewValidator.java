package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.ReviewView;
import constants.MessageConst;

/**
 * 評価インスタンスに設定されている値のバリデーションを行うクラス
 */
public class ReviewValidator {

    /**
     * インスタンスの各項目についてバリデーションを行う
     * @param rv 評価インスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(ReviewView rv) {
        List<String> errors = new ArrayList<String>();

        //内容のチェック
        String contentError = validateContent(rv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        return errors;
    }

    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param content 内容
     * @return エラーメッセージ
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_REVIEW_FAILURE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

}