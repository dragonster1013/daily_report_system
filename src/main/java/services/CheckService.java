package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.CheckConverter;
import actions.views.CheckView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Check;
import models.validators.CheckValidator;

/**
 * 確認サインテーブルの操作に関わる処理を行うクラス
 */
public class CheckService extends ServiceBase{

    /**
     * 指定した日報にした確認サインを、指定されたページ数の一覧画面に表示する分取得しCheckViewのリストで返却する
     * @param Report 日報
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<CheckView> getMinePerPage(ReportView report, int page) {

        List<Check> checks = em.createNamedQuery(JpaConst.Q_CHE_GET_ALL_MINE, Check.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return CheckConverter.toViewList(checks);
    }

    /**
     * 指定した日報にした確認サインの件数を取得し、返却する
     * @param report
     * @return 確認サインの件数
     */
    public long countAllMine(ReportView report) {

        long count = (long) em.createNamedQuery(JpaConst.Q_CHE_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する確認サインデータを取得し、CheckViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<CheckView> getAllPerPage(int page) {

        List<Check> checks = em.createNamedQuery(JpaConst.Q_REP_GET_ALL, Check.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return CheckConverter.toViewList(checks);
    }

    /**
     * 確認サインテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long checks_count = (long) em.createNamedQuery(JpaConst.Q_CHE_COUNT, Long.class)
                .getSingleResult();
        return checks_count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public CheckView findOne(int id) {
        return CheckConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された確認サインの登録内容を元にデータを1件作成し、確認サインテーブルに登録する
     * @param cv 確認サインの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(CheckView cv) {
        List<String> errors = CheckValidator.validate(cv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            cv.setCreatedAt(ldt);
            cv.setUpdatedAt(ldt);
            createInternal(cv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された確認サインの登録内容を元に、確認サインデータを更新する
     * @param cv 確認サインの更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(CheckView cv) {

        //バリデーションを行う
        List<String> errors = CheckValidator.validate(cv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            cv.setUpdatedAt(ldt);

            updateInternal(cv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Check findOneInternal(int id) {
        return em.find(Check.class, id);
    }

    /**
     * 確認サインデータを1件登録する
     * @param cv 確認サインデータ
     */
    private void createInternal(CheckView cv) {

        em.getTransaction().begin();
        em.persist(CheckConverter.toModel(cv));
        em.getTransaction().commit();

    }

    /**
     * 確認サインデータを更新する
     * @param cv 確認サインデータ
     */
    private void updateInternal(CheckView cv) {

        em.getTransaction().begin();
        Check c = findOneInternal(cv.getId());
        CheckConverter.copyViewToModel(c, cv);
        em.getTransaction().commit();

    }

}
