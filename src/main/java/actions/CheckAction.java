package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CheckView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import services.CheckService;

/**
 * 評価に関する処理を行うActionクラス
 *
 */
public class CheckAction extends ActionBase {

    private CheckService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new CheckService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        //日報のIDを取得
        int report_id = toNumber(getRequestParam(AttributeConst.REP_ID));

        putRequestScope(AttributeConst.CHE_REP, report_id);
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //新規登録画面を表示
        forward(ForwardConst.FW_CHE_NEW);

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //日報のIDを取得
        int report_id = toNumber(getRequestParam(AttributeConst.REP_ID));

        //CSRF対策 tokenのチェック
        if (checkToken()) {

        //パラメータの値をもとに評価情報のインスタンスを作成する
        CheckView cv = new CheckView(
                null,
                report_id,
                getRequestParam(AttributeConst.CHE_CONTENT));

        //評価情報登録
        List<String> errors = service.create(cv);

        if (errors.size() > 0) {
            //登録中にエラーがあった場合

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.CHECK, cv);//入力された評価情報
            putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

            //新規登録画面を再表示
            forward(ForwardConst.FW_CHE_NEW);

        } else {
            //登録中にエラーがなかった場合

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_CHECKED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
        }
    }

    }
}
