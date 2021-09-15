package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ScheduleView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ScheduleService;

/**
 * 予定表に関する処理を行うActionクラス
 *
 */
public class ScheduleAction extends ActionBase {

    private ScheduleService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ScheduleService();

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

        //指定されたページ数の一覧画面に表示する予定表データを取得
        int page = getPage();
        List<ScheduleView> schedules = service.getAllPerPage(page);

        //全予定表データの件数を取得
        long schedulesCount = service.countAll();

        putRequestScope(AttributeConst.SCHEDULES, schedules); //取得した予定表データ
        putRequestScope(AttributeConst.SCHE_COUNT, schedulesCount); //全ての予定表データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_SCHE_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //予定表情報の空インスタンスに、予定表の日付＝今日の日付を設定する
        ScheduleView sv = new ScheduleView();
        sv.setScheduleDate(LocalDate.now());
        putRequestScope(AttributeConst.SCHEDULE, sv); //日付のみ設定済みの予定表インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_SCHE_NEW);

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.SCHE_DATE) == null
                    || getRequestParam(AttributeConst.SCHE_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.SCHE_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //パラメータの値をもとに予定表情報のインスタンスを作成する
            ScheduleView sv = new ScheduleView(
                    null,
                    ev, //ログインしている従業員を、日報作成者として登録する
                    day,
                    getRequestParam(AttributeConst.SCHE_TITLE),
                    getRequestParam(AttributeConst.SCHE_CONTENT),
                    null,
                    null);

            //予定表情報登録
            List<String> errors = service.create(sv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.SCHEDULE, sv);//入力された予定表情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_SCHE_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SCHE, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に予定表データを取得する
        ScheduleView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SCHE_ID)));

        if (sv == null) {
            //該当の予定表データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.SCHEDULE, sv); //取得した予定表データ

            //詳細画面を表示
            forward(ForwardConst.FW_SCHE_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に予定表データを取得する
        ScheduleView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SCHE_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (sv == null || ev.getId() != sv.getEmployee().getId()) {
            //該当の予定表データが存在しない、または
            //ログインしている従業員が予定表の作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.SCHEDULE, sv); //取得した日報データ

            //編集画面を表示
            forward(ForwardConst.FW_SCHE_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に予定表データを取得する
            ScheduleView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SCHE_ID)));

            //入力された予定表内容を設定する
            sv.setScheduleDate(toLocalDate(getRequestParam(AttributeConst.SCHE_DATE)));
            sv.setTitle(getRequestParam(AttributeConst.SCHE_TITLE));
            sv.setContent(getRequestParam(AttributeConst.SCHE_CONTENT));

            //予定表データを更新する
            List<String> errors = service.update(sv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.SCHEDULE, sv); //入力された予定表情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_SCHE_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SCHE, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * 削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に予定表を削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.SCHE_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_SCHE, ForwardConst.CMD_INDEX);

        }
    }

}
