package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CheckView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.CheckService;

/**
 * 確認サインに関する処理を行うActionクラス
 *
 */
public class CheckAction extends ActionBase{

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
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する確認サインデータを取得
        int page = getPage();
        List<CheckView> checks = service.getAllPerPage(page);

        //全確認サインデータの件数を取得
        long checksCount = service.countAll();

        putRequestScope(AttributeConst.CHECKS, checks); //取得した確認サインデータ
        putRequestScope(AttributeConst.CHE_COUNT, checksCount); //全ての確認サインデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_CHE_INDEX);
    }

}
