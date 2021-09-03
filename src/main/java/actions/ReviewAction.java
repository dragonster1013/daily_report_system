package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
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
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        //新規登録画面を表示
        forward(ForwardConst.FW_REV_NEW);
    }

}
