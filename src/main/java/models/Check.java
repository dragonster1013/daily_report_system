package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 評価データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_CHE)

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Check {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.CHE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 評価をした日報のID
     */
    @Column(name = JpaConst.CHE_COL_REP, nullable = false)
    private Integer reportId;

    /**
     * 日報の内容
     */
    @Lob
    @Column(name = JpaConst.CHE_COL_CONTENT, nullable = false)
    private String content;

}
