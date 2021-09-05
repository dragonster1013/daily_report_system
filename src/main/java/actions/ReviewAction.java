package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ReviewView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.ReviewService;

/**
 * 評価に関する処理を行うActionクラス
 *
 */
public class ReviewAction extends ActionBase{

    private ReviewService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReviewService();

        //メソッドを実行
        invoke();
        service.close();

}

    /**
     * 評価画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

            //編集画面を表示
            forward(ForwardConst.FW_REV_NEW);
        }

    /**
     * 評価を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //パラメータの値をもとに評価情報のインスタンスを作成する
        ReviewView rv = new ReviewView(
                null,
                getRequestParam(AttributeConst.REV_CONTENT));

        //評価情報登録
        List<String> errors = service.create(rv);

        if (errors.size() > 0) {
        //登録中にエラーがあった場合
        putRequestScope(AttributeConst.REVIEW, rv);//入力された評価情報
        putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

        //新規登録画面を再表示
        forward(ForwardConst.FW_REV_NEW);

    } else {
        //登録中にエラーがなかった場合

        //セッションに登録完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_REVIEWED.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
    }

    }

}
