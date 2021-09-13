package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ScheduleConverter;
import actions.views.ScheduleView;
import constants.JpaConst;
import models.Schedule;
import models.validators.ScheduleValidator;

/**
 * 予定表テーブルの操作に関わる処理を行うクラス
 */
public class ScheduleService extends ServiceBase {

    /**
     * 指定した従業員が作成した予定表データを、指定されたページ数の一覧画面に表示する分取得しScheduleViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ScheduleView> getMinePerPage(EmployeeView employee, int page) {

        List<Schedule> schedules = em.createNamedQuery(JpaConst.Q_SCHE_GET_ALL_MINE, Schedule.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ScheduleConverter.toViewList(schedules);
    }

    /**
     * 指定した従業員が作成した予定表データの件数を取得し、返却する
     * @param employee
     * @return 予定表データの件数
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_SCHE_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する予定表データを取得し、ScheduleViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ScheduleView> getAllPerPage(int page) {

        List<Schedule> schedules = em.createNamedQuery(JpaConst.Q_SCHE_GET_ALL, Schedule.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ScheduleConverter.toViewList(schedules);
    }

    /**
     * 予定表テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long    schedules_count = (long) em.createNamedQuery(JpaConst.Q_SCHE_COUNT, Long.class)
                .getSingleResult();
        return schedules_count;
    }

    /**
     * idを条件に取得したデータをScheduleViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ScheduleView findOne(int id) {
        return ScheduleConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された予定表の登録内容を元にデータを1件作成し、予定表テーブルに登録する
     * @param sv 予定表の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ScheduleView sv) {
        List<String> errors = ScheduleValidator.validate(sv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            sv.setCreatedAt(ldt);
            sv.setUpdatedAt(ldt);
            createInternal(sv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された予定表の登録内容を元に、予定表データを更新する
     * @param sv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(ScheduleView sv) {

        //バリデーションを行う
        List<String> errors = ScheduleValidator.validate(sv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            sv.setUpdatedAt(ldt);

            updateInternal(sv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Schedule findOneInternal(int id) {
        return em.find(Schedule.class, id);
    }

    /**
     * 予定表データを1件登録する
     * @param sv 予定表データ
     */
    private void createInternal(ScheduleView sv) {

        em.getTransaction().begin();
        em.persist(ScheduleConverter.toModel(sv));
        em.getTransaction().commit();

    }

    /**
     * 予定表データを更新する
     * @param sv 予定表データ
     */
    private void updateInternal(ScheduleView sv) {

        em.getTransaction().begin();
        Schedule s = findOneInternal(sv.getId());
        ScheduleConverter.copyViewToModel(s, sv);
        em.getTransaction().commit();

    }

}