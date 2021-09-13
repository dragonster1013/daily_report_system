package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Schedule;

public class ScheduleConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param sv Viewのインスタンス
     * @return Scheduleのインスタンス
     */
    public static Schedule toModel(ScheduleView sv) {
        return new Schedule(
                sv.getId(),
                EmployeeConverter.toModel(sv.getEmployee()),
                sv.getScheduleDate(),
                sv.getTitle(),
                sv.getContent(),
                sv.getCreatedAt(),
                sv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param s Scheduleのインスタンス
     * @return ScheduleViewのインスタンス
     */
    public static ScheduleView toView(Schedule s) {

        if (s == null) {
            return null;
        }

        return new ScheduleView(
                s.getId(),
                EmployeeConverter.toView(s.getEmployee()),
                s.getScheduleDate(),
                s.getTitle(),
                s.getContent(),
                s.getCreatedAt(),
                s.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ScheduleView> toViewList(List<Schedule> list) {
        List<ScheduleView> evs = new ArrayList<>();

        for (Schedule s : list) {
            evs.add(toView(s));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param s DTOモデル(コピー先)
     * @param sv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Schedule s, ScheduleView sv) {
        s.setId(sv.getId());
        s.setEmployee(EmployeeConverter.toModel(sv.getEmployee()));
        s.setScheduleDate(sv.getScheduleDate());
        s.setTitle(sv.getTitle());
        s.setContent(sv.getContent());
        s.setCreatedAt(sv.getCreatedAt());
        s.setUpdatedAt(sv.getUpdatedAt());

    }

    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param s DTOモデル(コピー元)
     * @param sv Viewモデル(コピー先)
     */
    public static void copyModelToView(Schedule s, ScheduleView sv) {
        sv.setId(s.getId());
        sv.setEmployee(EmployeeConverter.toView(s.getEmployee()));
        sv.setScheduleDate(s.getScheduleDate());
        sv.setTitle(s.getTitle());
        sv.setCreatedAt(s.getCreatedAt());
        sv.setUpdatedAt(s.getUpdatedAt());
    }

}
