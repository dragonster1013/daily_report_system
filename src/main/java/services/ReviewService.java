package services;

import java.util.List;

import actions.views.ReviewConverter;
import actions.views.ReviewView;
import models.Review;
import models.validators.ReviewValidator;

/**
 * 評価テーブルの操作に関わる処理を行うクラス
 */
public class ReviewService extends ServiceBase{

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ReviewView findOne(int id) {
        return ReviewConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された評価の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ReviewView rv) {
        List<String> errors = ReviewValidator.validate(rv);
        if (errors.size() == 0) {
            createInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された評価の登録内容を元に、評価データを更新する
     * @param rv 評価の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(ReviewView rv) {

        //バリデーションを行う
        List<String> errors = ReviewValidator.validate(rv);

        if (errors.size() == 0) {
            updateInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Review findOneInternal(int id) {
        return em.find(Review.class, id);
    }

    /**
     * 評価データを1件登録する
     * @param rv 評価データ
     */
    private void createInternal(ReviewView rv) {

        em.getTransaction().begin();
        em.persist(ReviewConverter.toModel(rv));
        em.getTransaction().commit();

    }

    /**
     * 評価データを更新する
     * @param rv 評価データ
     */
    private void updateInternal(ReviewView rv) {

        em.getTransaction().begin();
        Review r = findOneInternal(rv.getId());
        ReviewConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }

}
