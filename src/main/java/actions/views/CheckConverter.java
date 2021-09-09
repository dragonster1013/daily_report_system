package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Check;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class CheckConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param cv CheckViewのインスタンス
     * @return Checkのインスタンス
     */
    public static Check toModel(CheckView cv) {
        return new Check(
                cv.getId(),
                ReportConverter.toModel(cv.getReport()),
                cv.getContent());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param c Checkのインスタンス
     * @return CheckViewのインスタンス
     */
    public static CheckView toView(Check c) {

        if (c == null) {
            return null;
    }

        return new CheckView(
                c.getId(),
                ReportConverter.toView(c.getReport()),
                c.getContent());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<CheckView> toViewList(List<Check> list) {
        List<CheckView> evs = new ArrayList<>();

        for (Check c : list) {
            evs.add(toView(c));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param c DTOモデル(コピー先)
     * @param cv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Check c, CheckView cv) {
        c.setId(cv.getId());
        c.setReport(ReportConverter.toModel(cv.getReport()));
        c.setContent(cv.getContent());
    }

    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param c DTOモデル(コピー元)
     * @param cv Viewモデル(コピー先)
     */
    public static void copyModelToView(Check c, CheckView cv) {
        cv.setId(c.getId());
        cv.setReport(ReportConverter.toView(c.getReport()));
    }

}
