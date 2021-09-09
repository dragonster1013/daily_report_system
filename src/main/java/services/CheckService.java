package services;

import java.util.List;

import actions.views.CheckConverter;
import actions.views.CheckView;
import models.Check;
import models.validators.CheckValidator;

public class CheckService extends ServiceBase {

    /**
     * idを条件に取得したデータをCheckViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public CheckView findOne(int id) {
        return CheckConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(CheckView cv) {
        List<String> errors = CheckValidator.validate(cv);
        if (errors.size() == 0) {
            createInternal(cv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された日報の登録内容を元に、日報データを更新する
     * @param rv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(CheckView cv) {

        //バリデーションを行う
        List<String> errors = CheckValidator.validate(cv);

        if (errors.size() == 0) {

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
     * 評価データを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(CheckView cv) {

        em.getTransaction().begin();
        em.persist(CheckConverter.toModel(cv));
        em.getTransaction().commit();

    }

    /**
     * 日報データを更新する
     * @param rv 日報データ
     */
    private void updateInternal(CheckView cv) {

        em.getTransaction().begin();
        Check c = findOneInternal(cv.getId());
        CheckConverter.copyViewToModel(c, cv);
        em.getTransaction().commit();

    }

}
