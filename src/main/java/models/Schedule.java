package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 予定表データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_SCHE)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_SCHE_GET_ALL,
            query = JpaConst.Q_SCHE_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_SCHE_COUNT,
            query = JpaConst.Q_SCHE_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_SCHE_GET_ALL_MINE,
            query = JpaConst.Q_SCHE_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_SCHE_COUNT_ALL_MINE,
            query = JpaConst.Q_SCHE_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Schedule {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.SCHE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 予定表を登録した従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.SCHE_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * いつの予定表かを示す日付
     */
    @Column(name = JpaConst.SCHE_COL_SCHE_DATE, nullable = false)
    private LocalDate scheduleDate;

    /**
     * 予定表のタイトル
     */
    @Column(name = JpaConst.SCHE_COL_TITLE, length = 255, nullable = false)
    private String title;

    /**
     * 予定表の内容
     */
    @Lob
    @Column(name = JpaConst.SCHE_COL_CONTENT, nullable = false)
    private String content;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.SCHE_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.SCHE_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}