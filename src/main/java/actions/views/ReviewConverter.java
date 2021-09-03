package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Review;

/**
 * 評価データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReviewConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReviewViewのインスタンス
     * @return Reviewのインスタンス
     */
    public static Review toModel(ReviewView rv) {
        return new Review(
                rv.getId(),
                rv.getContent());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reviewのインスタンス
     * @return ReviewViewのインスタンス
     */
    public static ReviewView toView(Review r) {

        if (r == null) {
            return null;
        }

        return new ReviewView(
                r.getId(),
                r.getContent());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReviewView> toViewList(List<Review> list) {
        List<ReviewView> evs = new ArrayList<>();

        for (Review r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Review r, ReviewView rv) {
        r.setId(rv.getId());
        r.setContent(rv.getContent());
    }

    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param r DTOモデル(コピー元)
     * @param rv Viewモデル(コピー先)
     */
    public static void copyModelToView(Review r, ReviewView rv) {
        rv.setId(r.getId());
        rv.setContent(rv.getContent());
    }

}
